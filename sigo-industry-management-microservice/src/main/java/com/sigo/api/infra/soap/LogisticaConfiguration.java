package com.sigo.api.infra.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class LogisticaConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
		return marshaller;
	}

	@Bean
	public LogisticaClient countryClient(Jaxb2Marshaller marshaller) {
		LogisticaClient client = new LogisticaClient();
		client.setDefaultUri("http://localhost:8003/logistics/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
