package com.simulator.backend.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${gmail.sender.address}")
    private String senderEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    /**
     * Send Verification Email
     */
    public void sendVerificationEmail(
            String email,
            String username,
            String token
    ) {

        try {

            log.info("Starting email sending...");

            String verificationUrl =
                    baseUrl + "/auth/verify-email?token=" + token;

            log.info("Verification URL : {}", verificationUrl);

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(email);
            helper.setSubject("Verify Your Email");

            helper.setText(
                    buildVerificationTemplate(
                            username,
                            verificationUrl
                    ),
                    true
            );

            log.info("Sending email to {}", email);

            mailSender.send(message);

            log.info("Email sent successfully.");

        } catch (Exception e) {

            log.error("MAIL ERROR", e);

            throw new RuntimeException(e);

        }

    }

    /**
     * HTML Email Template
     */
    private String buildVerificationTemplate(
            String username,
            String verificationUrl
    ) {

        return """
                <!DOCTYPE html>
                <html>

                <body style="
                    background:#f5f5f5;
                    padding:40px;
                    font-family:Arial,sans-serif;
                ">

                <div style="
                    max-width:650px;
                    margin:auto;
                    background:#ffffff;
                    border:1px solid #e5e5e5;
                    border-radius:10px;
                    padding:40px;
                ">

                    <h2 style="color:#171717;margin:0 0 24px 0;">
                        AI Interview Simulator
                    </h2>

                    <p style="color:#404040;">
                        Hi <b style="color:#171717;">%s</b>,
                    </p>

                    <p style="color:#404040;">
                        Thank you for creating your account.
                    </p>

                    <p style="color:#404040;">
                        Click the button below to verify your email address.
                    </p>

                    <p style="text-align:center;margin:40px 0;">

                        <a href="%s"
                           style="
                           background:#F97316;
                           color:#ffffff;
                           padding:15px 30px;
                           border-radius:8px;
                           text-decoration:none;
                           font-weight:bold;
                           display:inline-block;
                           ">
                           Verify Email
                        </a>

                    </p>

                    <p style="color:#737373;font-size:13px;">
                        This verification link will expire in
                        <b style="color:#404040;">24 hours</b>.
                    </p>

                    <br>

                    <p style="color:#737373;font-size:13px;">
                        Thanks,<br>
                        <span style="color:#171717;font-weight:bold;">AI Interview Simulator Team</span>
                    </p>

                </div>

                </body>

                </html>
                """.formatted(
                username,
                verificationUrl
        );

    }

}