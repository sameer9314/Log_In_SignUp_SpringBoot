package com.bridgelabz.loginregistration.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

	public void sendMail(Mail mail) throws MessagingException;
}
