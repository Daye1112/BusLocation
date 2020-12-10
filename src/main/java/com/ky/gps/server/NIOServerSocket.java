package com.ky.gps.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO框架
 *
 * @author Alienware-Rocky
 */

public class NIOServerSocket {
    private final static Logger LOGGER = LoggerFactory.getLogger(NIOServerSocket.class);
    /**
     * 选择器
     */
    private Selector selector;
    /**
     * 通信管道
     */
    private ServerSocketChannel socketChannel;
    //	private static ServerSocket serverSocket;	// 通信服务
    private int port;


    public NIOServerSocket(int port) {
        this.port = port;
    }


    /**
     * NIO服务器初始化
     */
    public void init() throws IOException {
        // 创建通道选择器，并打开
        selector = Selector.open();
        // 创建ServerSocketChannel通道，并打开
        socketChannel = ServerSocketChannel.open();
        // 设置非阻塞模式，true为阻塞，
        socketChannel.configureBlocking(false);

    }

    /**
     * 监听服务开启
     */
    public void start() {
        ParseGPS parseGPS = new ParseGPS();
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

//			serverSocket = socketChannel.socket();							//
            // 将信道绑定到指定端口
            socketChannel.bind(new InetSocketAddress(port));
            // 把服务器通道注册到多路复用器上，并且监听阻塞事件
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 设置选择器开始监听端口，并获取可用通道
                int channels = selector.select();
                // 判断是否存在可用的通道
                if (channels <= 0) {
                    continue;
                }
                // 获得所有的keys
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 使用iterator遍历所有的keys
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;

                try {
                    // 循环遍历SelectionKeySet中的所有的SelectionKey
                    while (iterator.hasNext()) {
                        // 选择一个元素
                        key = iterator.next();
                        // 这里需要手动从键集中移除当前的key
                        iterator.remove();

                        try {
                            // 自定义事件处理
                            handleEvent(selector, key, parseGPS);

                        } catch (Exception e) {
                            LOGGER.error("", e);
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }

                    }

                } catch (Exception e) {
                    LOGGER.info("通道开启失败！");
                    e.printStackTrace();
                } finally {
                    if (selectionKeys != null) {
                        selectionKeys.clear();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.info("监听开启失败!");
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public void shutdown() {
        if (socketChannel != null) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleEvent(Selector selector, SelectionKey key, ParseGPS parseGPS) throws IOException {
        // 如果是有效的，则执行下一步
        if (key.isValid()) {
            // 处理新接入的请求消息
            if (key.isAcceptable()) {

                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // 定义有新请求的通道
                SocketChannel sc = ssc.accept();
                LOGGER.info("客户端请求连接...");
                // 设置非阻塞模式
                sc.configureBlocking(false);
                // 注册该通道为可读可写状态
                sc.register(selector, SelectionKey.OP_READ);
                LOGGER.info("Client: " + sc.getRemoteAddress() + " --> 连接成功! ");
            } else if (key.isReadable()) {
                // 判断通道是否可读
                // 获取之前注册的socket通道对象
                SocketChannel sc = (SocketChannel) key.channel();
                // 设置缓冲区
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                // 将通道中的数据读到缓冲区
                int readBytes = sc.read(readBuffer);

                if (readBytes > 0) {
                    // 有数据则进行读取，读取之前需要进行复位方法(把position 和limit进行复位)
                    readBuffer.flip();
                    // 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
                    byte[] bytes = new byte[readBuffer.remaining()];
                    // 接收缓冲数据
                    readBuffer.get(bytes);
                    // 接收到的输入，并设置编码格式
                    String request = new String(bytes, "UTF-8");
                    LOGGER.info("Client: " + sc.getRemoteAddress() + " --> msg: " + request);
                    //-------------------------------------------------------------------------//
                    LOGGER.info(parseGPS.hashCode() + "");
                    parseGPS.parse(request);

                    //-------------------------------------------------------------------------//

                    String response = " ok! ";
                    doWrite(sc, response);

                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                }
                readBuffer.clear();
            }
        }
    }

    public void doWrite(SocketChannel channel, String response) throws IOException {

        if (response != null && response.trim().length() > 0) {

            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
            writeBuffer.clear();
            // 为读入腾出更多空间
            writeBuffer.compact();
        }
    }
}
