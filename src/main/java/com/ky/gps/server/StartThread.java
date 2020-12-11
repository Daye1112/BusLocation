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

    @Override
    public void run() {
        LOGGER.info("-------------监听端口GPSport: " + GPS_PORT + "-------------");
        EchoServer echoServer = null;
        try {
            echoServer = new EchoServer();
            echoServer.setPort(GPS_PORT);
            echoServer.init();
            echoServer.bind();
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        } finally {
            if (echoServer != null) {
                echoServer.shutdown();
            }
        }
    }

}
