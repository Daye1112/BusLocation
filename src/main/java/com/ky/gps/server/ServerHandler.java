package com.ky.gps.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Alienware
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
    private ParseGPS parseGPS;
    private boolean flag = false;

    /**
     * 连接时调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Client: " + ctx.channel() + " --> 连接成功! ");
        parseGPS = new ParseGPS();
    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Runtime.getRuntime().gc();
        ctx.channel().close();
        flag = false;
        LOGGER.info("客户端与服务端连接关闭: " + Thread.currentThread().toString() + " " + new Date());
    }

    /**
     * 服务端接收客户端发送过来的数据时调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ctx.channel().config().setWriteBufferHighWaterMark(20);
        ByteBuf buf = null;
        ByteBuf pingMessage = null;
        LOGGER.info("接收: " + ctx.name() + "-->msg: " + msg);
        try {
            buf = (ByteBuf) msg;
            ReferenceCountUtil.retain(buf);
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req).release();
            String body = new String(req, "UTF-8");
            LOGGER.info("接收客户端数据:" + body);

            /*
             * 解析信息
             */
            String response = parseGPS.parse(body);
            if (response != null && !response.isEmpty()) {
                byte[] resp = response.getBytes();
                pingMessage = Unpooled.buffer();
                ReferenceCountUtil.retain(pingMessage);
                pingMessage.writeBytes(resp);
                ctx.writeAndFlush(pingMessage);

//				else if (!HashThreadUtil.hasThread(ctx.channel().id().toString())) {
                if ("TRVBP01#".equals(response)) {
                    flag = true;
                    RequestThread thread = new RequestThread(ctx);
//					thread.setName(ctx.channel().id().toString());
//					thread.setDaemon(true);

                    /* 此处修改请求间隔 */
                    Thread.sleep(2000);
                    thread.run();
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (pingMessage != null) {
                ReferenceCountUtil.release(pingMessage);
            }
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }

            LOGGER.info("{当前线程: [存活: " + Thread.activeCount() + "|参数: " + Thread.currentThread().toString() + "|状态: "
                    + Thread.currentThread().getState() + "}");
            // Thread[] tarray = new Thread[100];
            // Thread.enumerate(tarray);
            // for (Thread thread : tarray) {
            // if (thread == null) {
            // continue;
            // }
            // System.err.println(thread.toString()+" "+thread.getState());
            // if (thread.getState() == State.TIMED_WAITING &&
            // thread.getThreadGroup().getName().equals("main")
            // && thread.getName().contains("pool") && thread.getName().contains("thread"))
            // {
            // thread.stop();
            // }
            // }
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    LOGGER.info(ctx.channel().remoteAddress() + "超时事件->" + eventType);
                    ctx.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    if (flag) {
                        eventType = "写空闲";
                        LOGGER.info(ctx.channel().remoteAddress() + "超时事件->" + eventType);
                        Thread thread = new Thread(new RequestThread(ctx));
                        thread.run();
                    }
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        LOGGER.info("信息交互完毕...\n");
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("", cause);
        flag = false;
        ctx.close();
    }

}
