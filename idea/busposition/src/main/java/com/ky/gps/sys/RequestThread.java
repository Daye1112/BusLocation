package com.ky.gps.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class RequestThread implements Runnable{
	private final static Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
	private ChannelHandlerContext ctx;
	public RequestThread(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		LOGGER.info("子线程开启: 参数: "+Thread.currentThread().toString()+
				"|状态: "+Thread.currentThread().getState()+"}");
		String re = "TRVDP35#";
		byte[] reby = re.getBytes();
		ByteBuf msgby = null;
		try {
			while(true) {
				Thread.sleep(2000);
				msgby = Unpooled.buffer();
				ReferenceCountUtil.retain(msgby);
				msgby.writeBytes(reby);
				ctx.writeAndFlush(msgby);
				LOGGER.info("子线程: "+Thread.currentThread().getName()+" -> 发送立即定位请求:"+re);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (msgby != null) {
				ReferenceCountUtil.release(msgby);
			}
		}
		LOGGER.info("子线程退出: {参数: "+Thread.currentThread().toString()+"}\n");
	}

}
