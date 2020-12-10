package com.ky.gps.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 主线程
 *
 * @author Rocky
 */
public class StartThread implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(StartThread.class);
    private final static int GPS_PORT = 20086;

    public StartThread() {

    }

    @Override
    public void run() {

        LOGGER.info("-------------监听端口GPSport: " + GPS_PORT + "-------------");

        EchoServer echoServer = null;
        try {
            echoServer = new EchoServer();
            //---------------------------------------------------------------//
            echoServer.setPort(GPS_PORT);
            echoServer.init();
            //---------------------------------------------------------------//
            try {
                echoServer.bind();
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (echoServer != null) {
                echoServer.shutdown();
            }
        }

        /*
         * 创建服务器，开启守护线程
         */
//		NIOServerSocket nss = new NIOServerSocket(GPSprot);
//		try {
//			
//			nss.start();
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			if (nss != null) {
//				nss.shutdown();
//			}
//		}
    }
}
