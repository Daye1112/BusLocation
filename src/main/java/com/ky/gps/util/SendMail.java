package com.ky.gps.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendMail {
    /**
     * 发件人地址
     */
    private final static String SENDER_ADDRESS = "ludaye1112@163.com";
    /**
     * 发件人账户名
     */
    private final static String SENDER_ACCOUNT = "ludaye1112@163.com";
    /**
     * 发件人账户密码
     */
    private final static String SENDER_PASSWORD = "luyuhao2010";

    public static void sendEmil(String recipientAddress, String checkCode) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(false);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session, recipientAddress, checkCode);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(SENDER_ACCOUNT, SENDER_PASSWORD);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg, msg.getAllRecipients());

        //5、关闭邮件连接
        transport.close();
    }

    /**
     * 获得创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws Exception
     */
    private static MimeMessage getMimeMessage(Session session, String recipientAddress, String checkCode) throws Exception {
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(SENDER_ADDRESS));
        /*
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
        //4.设置邮件主题
        msg.setSubject("校车定位查询网站找回登录密码通知", "UTF-8");

        //下面是设置邮件正文
//        String textMail = "http://60.12.85.238:8081/busposition/realTime/setPsw";
        String textMail = "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n" +
                "                    <tbody>\n" +
                "                    <tr>\n" +
                "                        <td width=\"210\">\n" +
                "                           \n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n" +
                "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
                "                        亲爱的用户：\n" +
                "                        <span style=\"color:#f60;font-size: 16px;\"></span>您好！\n" +
                "                    </strong>\n" +
                "\n" +
                "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
                "                        您正在修改安全邮箱，请在验证码输入框中输入：\n" +
                "                        <span style=\"color:#f60;font-size: 24px\"><span style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\" t=\"7\" onclick=\"return false;\" data=\"898009\">" + checkCode + "</span></span>，以完成操作。\n" +
                "                    </strong>\n" +
                "                </div>\n" +
                "\n" +
                "                <div style=\"margin-bottom:30px;\">\n" +
                "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n" +
                "                        <p style=\"color:#747474;\">\n" +
                "                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
                "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
                "                        </p>\n" +
                "                    </small>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div style=\"width:700px;margin:0 auto;\">\n" +
                "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                    <p>此为系统邮件，请勿回复<br>\n" +
                "                        请保管好您的邮箱，避免账号被他人盗用\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>";
        msg.setContent(textMail, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
