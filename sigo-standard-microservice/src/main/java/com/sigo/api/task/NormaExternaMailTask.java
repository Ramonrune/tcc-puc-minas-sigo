package com.sigo.api.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sigo.api.dto.NormaModificacaoDTO;
import com.sigo.api.dto.NormaModificacoesDTO;

@Component
public class NormaExternaMailTask {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine thymeLeaf;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${eureka.instance.hostname}")
	private String hostName;

	@Value("${server.port}")
	private String serverPort;

	@Scheduled(cron = "0 0 1 * * ?")
	//@Scheduled(fixedDelay = 60000)
	public void scheduleFixedDelayTask() {

		String template = "mail/aviso-normas-tecnicas";

		NormaModificacoesDTO normaModificacoesDTO = restTemplate.getForObject(
				"http://" + hostName + ":" + serverPort + "/standards/external", NormaModificacoesDTO.class);

		List<NormaModificacaoDTO> list = normaModificacoesDTO.getList();

		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("ramonrune@gmail.com");

		Context context = new Context(new Locale("pt", "BR"));

		Map<String, Object> avisoNormas = new HashMap<>();
		avisoNormas.put("modificacoes", list);
		avisoNormas.entrySet().forEach(e -> {
			context.setVariable(e.getKey(), e.getValue());
		});
		String mensagem = thymeLeaf.process(template, context);

		this.enviarEmail("sigoapp99@gmail.com", destinatarios,
				"Identificado alteração de normas técnicas", mensagem);
		

	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Problemas com o envio de e-mail", e);
		}

	}
}
