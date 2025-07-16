package com.bts.mailservice.mail;

import com.bts.model.mail.EmailDto;
import com.bts.model.mail.MailGateway;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailGatewayAdapter implements MailGateway {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(EmailDto emailDto) throws Exception {

        new Thread(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setSubject(emailDto.getSubject());
                helper.setText(emailDto.getBody(), true);
                helper.setTo(emailDto.getAddressee());
                helper.setFrom("no_reply@btgpactual.com");
                javaMailSender.send(message);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ).start();

    }

}