package com.bts.model.mail;

public interface MailGateway {
    void sendMail(EmailDto emailDto) throws Exception;
}
