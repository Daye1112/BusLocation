package com.ky.gps.service;

import org.springframework.scheduling.annotation.Async;

/**
 * 邮件相关service类
 *
 * @author Darren
 */
public interface EmailService {

    /**
     * 发送邮件
     *
     * @param email     发送的邮箱地址
     * @param checkCode 验证码
     */
    @Async
    void sendEmail(String email, String checkCode);
}
