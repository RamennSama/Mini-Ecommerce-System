package com.ramennsama.springboot.system.service;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ramennsama.springboot.system.entity.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String userSending;

    public void sendOtpEmail(String to, String name, String otp)
            throws MessagingException, UnsupportedEncodingException {

        // 1. Tạo context cho template, cua thymeleaf
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        context.setVariable("expire", 1);

        // 2. Render HTML
        String html = templateEngine.process("funny-email", context);

        // 3. Tạo email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(userSending, "Hệ điều hành LABUBU");
        helper.setTo(to);
        helper.setSubject("Xác thực OTP, <no reply>");
        helper.setText(html, true);

        // 4. Gửi
        mailSender.send(message);
    }

    public void sendPaymentSuccessEmail(Order order, String customerEmail) {
        try {
            // 1. Tạo context cho template
            Context context = new Context();
            context.setVariable("customerName", order.getUser().getFullName());
            context.setVariable("orderId", order.getId());
            context.setVariable("txnRef", order.getVnpTxnRef());
            context.setVariable("totalAmount", order.getTotalAmount());
            context.setVariable("orderDate", order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            context.setVariable("items", order.getItems());

            // 2. Render HTML
            String html = templateEngine.process("email/payment-success-email", context);

            // 3. Tạo email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(userSending, "Hệ điều hành LABUBU");
            helper.setTo(customerEmail);
            helper.setSubject("Payment Confirmation - Order #" + order.getId());
            helper.setText(html, true);

            // 4. Gửi
            mailSender.send(message);
            log.info("Payment success email sent to: {} for Order #{}", customerEmail, order.getId());

        } catch (Exception e) {
            log.error("Failed to send payment success email to: {} for Order #{}", customerEmail, order.getId(), e);
            // Don't throw - email failure shouldn't break payment flow
        }
    }
}
