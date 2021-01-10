package com.sigo.api;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	
	@Bean
	public JavaMailSender javaMailSender() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.connectiontimeout", 10000);

		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setJavaMailProperties(properties);
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername("sigoapp99@gmail.com");
		mailSenderImpl.setPassword("Sigo@123");

		return mailSenderImpl;
	}
}