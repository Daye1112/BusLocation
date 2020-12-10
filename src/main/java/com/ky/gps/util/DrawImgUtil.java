package com.ky.gps.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author Daye
 */
public class DrawImgUtil {

    /**
     * 随机串长度
     */
    private final static int CODE_LENGTH = 5;
    /**
     * 干扰线数量
     */
    private final static int LINE_SIZE = 10;
    /**
     * 图片宽
     */
    private final static int WIDTH = 70;
    /**
     * 图片高
     */
    private final static int HEIGHT = 25;
    private static DrawImgUtil drawImgUtil = null;

    private DrawImgUtil() {
    }

    public static DrawImgUtil getInstance() {
        if (drawImgUtil == null) {
            synchronized (DrawImgUtil.class) {
                if (drawImgUtil == null) {
                    drawImgUtil = new DrawImgUtil();
                }
            }
        }
        return drawImgUtil;
    }

    /**
     * 绘画验证码
     *
     * @param output 字节流
     * @return 返回生成的随机String
     */
    public String drawImg(ByteArrayOutputStream output) {
        String code;
        // 随机产生4个字符
        code = RandomUtil.randomChar(CODE_LENGTH);
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        // 调用Graphics2D绘画验证码
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, WIDTH, HEIGHT);
        drawLine(g);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (WIDTH - bounds.getWidth()) / 2;
        double y = (HEIGHT - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 绘制干扰线
     *
     * @param g
     */
    private void drawLine(Graphics2D g) {
        Random random = new Random();
        //绘制干扰线
        for (int i = 0; i < LINE_SIZE; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(13);
            int yl = random.nextInt(15);
            g.drawLine(x, y, x + xl, y + yl);
        }
    }

}
