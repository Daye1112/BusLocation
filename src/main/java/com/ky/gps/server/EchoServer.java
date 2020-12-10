package com.ky.gps.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ResourceLeakDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Rocky
 * <p>
 * 2019年5月11日-下午7:08:44
 */
public class EchoServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
    private EventLoopGroup group;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private int PORT;

    public int getPort() {
        return PORT;
    }

    public void setPort(int port) {
        this.PORT = port;
    }

    public void init() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(5);
        group = new NioEventLoopGroup(1);
        bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)

                // 保持连接数
                .option(ChannelOption.SO_BACKLOG, 20)
                // 有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                // 设置通道类型NIO
                .channel(NioServerSocketChannel.class)
                // 保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
//			.localAddress(new InetSocketAddress(port))			// 设置监听端口
                // 处理新连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //使用了netty自带的编码器和解码器
                        //心跳检测，读超时，写超时，读写超时
                        ByteBuf buf = Unpooled.copiedBuffer("#".getBytes());
//                    	ch.pipeline().addLast(new MyDecoder(1024,buf));
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        ch.pipeline().addLast(new IdleStateHandler(60, 4, 0, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
    }


    public void bind() throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
        LOGGER.info("监听服务开启：" + Thread.currentThread().toString() + " " + new Date());
        channelFuture.channel().closeFuture().sync();
    }

    public void shutdown() {
        if (group != null) {
            group.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

}
