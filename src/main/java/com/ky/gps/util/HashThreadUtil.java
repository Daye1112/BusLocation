package com.ky.gps.util;

public class HashThreadUtil {
    public static boolean hasThread(String name) {
        // 获取所有线程
        Thread[] tarray = new Thread[1024];
        Thread.enumerate(tarray);
        boolean FLAG = false;
        for (Thread thread : tarray) {
            if (thread == null) {
                continue;
            }
            if (thread.getName().equals(name)) {
                FLAG = true;
            }
        }
        return FLAG;
    }
}
