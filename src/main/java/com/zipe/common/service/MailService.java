package com.zipe.common.service;

import com.zipe.common.payload.Mail;

import javax.mail.MessagingException;

public interface MailService {

	public void setInitData() throws MessagingException;

	public void sendEmail(Mail mail);

	public void simpleMailSend(Mail mail);

	public void attachedSend(Mail mail) throws MessagingException;

	public void richContentSend(Mail mail) throws MessagingException;

	public void sendBatchMailWithFile(Mail mail) throws Exception;
}
