package com.naeng_biseo.naeng_biseo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Value("${MAIL_ID}")
    private String senderEmail;

    public String sendAuthCodeEmail(String toEmail) throws MessagingException {
        String authCode = createAuthCode();

        redisTemplate.opsForValue().set(toEmail,authCode,5, TimeUnit.MINUTES);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setFrom(senderEmail);
        helper.setSubject("[냉비서] 인증번호 안내");
        helper.setText("<h2>인증번호: <strong>" + authCode + "</strong></h2>", true);

        mailSender.send(message);

        return authCode;
    }

    private String createAuthCode(){
        int randomNum = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(randomNum);
    }

}
