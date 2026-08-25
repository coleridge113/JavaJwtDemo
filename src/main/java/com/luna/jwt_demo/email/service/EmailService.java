package com.luna.jwt_demo.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderItemResponse;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = RabbitMqConfig.NOTIFICATION_QUEUE)
    public void receiveMessage(OrderDto order) {

        log.info("Message recieved via RabbitMQ");
        log.info(order.toString());

        sendEmail("josemanuelmluna@gmail.com", "Test Mailpit email", buildOrderEmailBody(order));

    }

    public void sendEmail(String recipient, String subject, String bodyHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("no-reply@luna.com");
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true);

            mailSender.send(message);
            log.info("Email sent to Mailpit for receipient {}", recipient);

        } catch (Exception ex) {

            log.error("Failed to send email to Mailpit", ex);
        }
    }
    private String buildOrderEmailBody(OrderDto order) {
        StringBuilder html = new StringBuilder();
            
            html.append("<!DOCTYPE html><html><body style='font-family: Arial, sans-serif; color: #333;'>");
            html.append("<h2 style='color: #2c3e50;'>Order Confirmation</h2>");
            html.append("<p>Hi, thank you for your purchase!</p>");
            html.append("<p><strong>Order ID:</strong> #").append(order.id()).append("<br/>");
            html.append("<strong>Status:</strong> ").append(order.status()).append("</p>");
            
            if (order.orderItems() != null && !order.orderItems().isEmpty()) {
                html.append("<table style='width: 100%; border-collapse: collapse; margin-top: 15px;'>");
                    html.append("<thead>");
                    html.append("<tr style='background-color: #f8f9fa; text-align: left;'>");
                    html.append("<th style='padding: 8px; border: 1px solid #ddd;'>Product</th>");
                    html.append("<th style='padding: 8px; border: 1px solid #ddd;'>Qty</th>");
                    html.append("<th style='padding: 8px; border: 1px solid #ddd;'>Price</th>");
                    html.append("<th style='padding: 8px; border: 1px solid #ddd;'>Total</th>");
                    html.append("</tr>");
                    html.append("</thead>");
                    html.append("<tbody>");
                    
                    long grandTotalCents = 0;
                    
                    for (OrderItemResponse item : order.orderItems()) {
                        double price = item.amountInCents() / 100.0;
                        double total = item.totalAmountInCents() / 100.0;
                        grandTotalCents += item.totalAmountInCents();

                        html.append("<tr>");
                        html.append("<td style='padding: 8px; border: 1px solid #ddd;'>").append(item.productName()).append("</td>");
                        html.append("<td style='padding: 8px; border: 1px solid #ddd;'>").append(item.quantity()).append("</td>");
                        html.append("<td style='padding: 8px; border: 1px solid #ddd;'>$").append(String.format("%.2f", price)).append("</td>");
                        html.append("<td style='padding: 8px; border: 1px solid #ddd;'>$").append(String.format("%.2f", total)).append("</td>");
                        html.append("</tr>");
                    }

                    html.append("</tbody>");
                    html.append("</table>");

                    html.append("<h3 style='margin-top: 15px;'>Grand Total: $")
                        .append(String.format("%.2f", grandTotalCents / 100.0))
                        .append("</h3>");
            } else {
                html.append("<p><em>No itemized details available for this order.</em></p>");
            }

            html.append("<p style='margin-top: 20px; color: #777;'>If you have any questions, reply to this email.</p>");
            html.append("</body></html>");

            return html.toString();
    }
}
