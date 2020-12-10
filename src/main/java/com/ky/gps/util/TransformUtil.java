package com.ky.gps.util;

import com.ky.gps.entity.SbTerminal;

/**
 * 纬度转换84坐标系： 30°11′2.06″ 转换 X = 30 + 11/60 + 2.06/3600 =
 * 30.183905555555555555555555555556 数据包转换坐标系：
 * 3011.034344544,N,12048.852860598,E 30.1839057424 = 30 + N/60
 * 30.1839057424,120.8142143433
 * <p>
 * 谷歌地图：30.1814064743,120.8184254115 百度地图：30.1874780000,120.8248980000 *
 * 腾讯高德：30.1813946024,120.8184192033 图吧地图：30.1780857424,120.8198243433
 * 谷歌地球：30.1839057424,120.8142143433 北纬N30°11′2.06″ 东经E120°48′51.17″
 * <p>
 * 谷歌地图：30.1814064743,120.8184234115 百度地图：30.1874780000,120.8248960000
 * 腾讯高德：30.1813946024,120.8184172033 图吧地图：30.1780857424,120.8198223433
 * 谷歌地球：30.1839057424,120.8142123433 * 北纬N30°11′2.06″ 东经E120°48′51.16″
 * <p>
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。 chenhua
 */
class TransformUtil {
    static double x_PI = 3.1415926535897932384626 * 3000.0 / 180.0;
    static double PI = 3.1415926535897932384626;
    static double a = 6378245.0;
    static double ee = 0.00669342162296594323;

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换 即谷歌、高德 转 百度
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static String gcj02tobd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_PI);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        // Point point=new Point(bd_lng, bd_lat);
        // return point;
        return bd_lat + "," + bd_lng;
    }

    /**
     * WGS84转GCj02
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static String wgs84togcj02(double lng, double lat) {
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        // Point point=new Point(mglng, mglat);
        // return point;
        return mglat + "," + mglng;
    }

    ;

    /**
     * WGS84 转换为 BD-09
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static SbTerminal wgs84tobd09(SbTerminal sbTerminal) {
        // 第一次转换
        double lng = sbTerminal.getSbBusPosition().getSbpLongitude();
        double lat = sbTerminal.getSbBusPosition().getSbpLatitude();
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;

        // 第二次转换
        double z = Math.sqrt(mglng * mglng + mglat * mglat) + 0.00002 * Math.sin(mglat * x_PI);
        double theta = Math.atan2(mglat, mglng) + 0.000003 * Math.cos(mglng * x_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        sbTerminal.getSbBusPosition().setSbpLongitude(bd_lng);
        sbTerminal.getSbBusPosition().setSbpLatitude(bd_lat);
        return sbTerminal;
    }

    ;

    public static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat
                + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    ;

    public static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换 即 百度 转 谷歌、高德
     *
     * @param bd_lon
     * @param bd_lat
     * @returns {*[]}
     */
    public String bd09togcj02(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        // Point point=new Point(gg_lng, gg_lat);
        // return point;
        return gg_lat + "," + gg_lng;
    }

    /**
     * GCJ02 转换为 WGS84
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public String gcj02towgs84(double lng, double lat) {
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        // Point point=new Point(mglng, mglat);
        // return point;
        return mglat + "," + mglng;
    }

}
