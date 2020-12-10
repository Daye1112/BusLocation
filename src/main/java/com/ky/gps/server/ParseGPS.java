package com.ky.gps.server;

import com.ky.gps.entity.SbBusPosition;
import com.ky.gps.entity.SbTerminal;
import com.ky.gps.util.CheckValidUtil;
import com.ky.gps.util.ParseGPSUtil;
import com.ky.gps.util.SbBusPositionServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseGPS {

    private final Logger LOGGER = LoggerFactory.getLogger(ParseGPS.class);
    private SbTerminal sbTerminal;

    public ParseGPS() {
        init();
    }

    private void init() {
        sbTerminal = new SbTerminal("", new SbBusPosition(), "");
    }

    public String parse(String sign) {
        sbTerminal = parseST(sign);
//		String sign = "$GPRMC,024813.640,A,3011.034344544,N,12048.852860598,E,10.05,324.27,150706,A*50";
        if (sbTerminal.getSbBusPosition().getValid()) {
            LOGGER.info("GPS:" + sbTerminal.getSbBusPosition().getSbGps().getId() + " 信息接收");
            return sbTerminal.getAgreement();
        } else {
            if (CheckValidUtil.isValid(sbTerminal)) {
                LOGGER.info("GPS:" + sbTerminal.getSbBusPosition().getSbGps().getId() + " 无效信息");
            } else {
                LOGGER.info("GPS:null 无效信息");
            }
            return sbTerminal.getAgreement();
        }
    }

    /**
     * @param by 返回值 1 正确 0校验失败 2非TRV信息 3无效定位 4格式错误
     * @return
     */
    private SbTerminal parseST(String by) {
        sbTerminal.setAgreement("");
        try {

            {
                // 判断非空
                if (by == null || by.isEmpty()) {
                    sbTerminal.getSbBusPosition().setValid(false);
                    LOGGER.info("信息为空，无效");
                    return sbTerminal;
                }
            }
            // if (checksum(by.getBytes()) == false)// 计算校验和并与语句中的校验和比较
            // return 5;

            /*
             * 确定是TRV
             */
            {
                if (!by.startsWith("TRV")) {
                    sbTerminal.getSbBusPosition().setValid(false);
                    LOGGER.info("非GPS终端信息，无效");
                    return sbTerminal;
                }
            }

//			{
//				if (!(by.endsWith("#") || by.length() > 53)) {
//					sbTerminal.getSbBusPosition().setValid(false);
//					LOGGER.info("GPS信息无中断，无效");
//					return sbTerminal;
//				}
//			}

            String gpsProtocol = by.substring(3, 7);
            String str = null;
            if (by.length() > 7) {
                str = by.substring(7);
                if (str.charAt(0) == ',') {
                    str = str.replaceFirst(",", "");
                }
            }

            /*
             * 协议选择器
             */
            switch (gpsProtocol) {

                case "AP00":
                    return ParseGPSUtil.getAP00(str, sbTerminal);

                case "AP01":
                    sbTerminal = ParseGPSUtil.getAP01(str, sbTerminal);
                    if (sbTerminal.getSbBusPosition().getValid()) {
                        SbBusPositionServiceUtil.savePosition(sbTerminal.getSbBusPosition());
                    }
                    return sbTerminal;

                case "CP01":
                    return ParseGPSUtil.getCP01(str, sbTerminal);

                case "YP02":
                    return ParseGPSUtil.getYP02(sbTerminal);

                case "CP35":
                    return ParseGPSUtil.getCP35(sbTerminal);
                default:
                    LOGGER.info("协议未开通-" + gpsProtocol);
                    sbTerminal.setAgreement("");
                    break;
            }
            return sbTerminal;
        } catch (Exception e) {
            LOGGER.error("", e);
            sbTerminal.getSbBusPosition().setValid(false);
            return sbTerminal;
        }

    }
}
