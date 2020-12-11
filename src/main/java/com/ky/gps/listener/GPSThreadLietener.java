package com.ky.gps.listener;

import com.ky.gps.server.StartThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


/**
 * 监听器
 *
 * @author Alienware-Rocky
 */
public class GPSThreadLietener extends HttpServlet implements ServletContextListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(GPSThreadLietener.class);

    private Thread startThread;

    public GPSThreadLietener() {

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            /* startThread.stop(); */
            if (startThread != null && startThread.isAlive()) startThread.interrupt();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("----------- GPS解析系统停止 -----------");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        LOGGER.info("----------- GPS解析系统启动 -----------");
        //创建主线程对象
        startThread = new Thread(new StartThread());
        startThread.setName("MyThread");
        //设置为守护线程
        startThread.setDaemon(true);
        //启动主线程
        startThread.start();

        LOGGER.info("----------- GPS信号监听开始 -----------");
    }

}
