package com.ky.gps.sys;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class EchoServer {
	
	private EventLoopGroup group ;
	private EventLoopGroup bossGroup ;
	private EventLoopGroup workerGroup ;
	private ServerBootstrap bootstrap;
	private int PORT;
	private final static Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
	
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
		
		bootstrap.group(bossGroup,workerGroup)
		
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
					ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
					ch.pipeline().addLast(new ServerHandler());
				}
			});
	}
	
	
	public void bind() throws InterruptedException {
		ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
		LOGGER.info("监听服务开启："+Thread.currentThread().toString()+" "+new Date());
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
