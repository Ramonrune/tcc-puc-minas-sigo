package com.sigo.api.repository;

import org.springframework.stereotype.Component;

import io.spring.guides.gs_producing_web_service.Logistic;
import io.spring.guides.gs_producing_web_service.ResourceType;

@Component
public class LogisticaRepository {

	public Logistic findLogistic(String code) {

		int i = Integer.parseInt(code);
		Logistic logistic = new Logistic();

		if (i % 2 == 0) {
			logistic.setDescription("Insumos");
			logistic.setName("Insumos");
			logistic.setValue(String.valueOf(Long.parseLong(code) * 500));
			logistic.setResourceType(ResourceType.INPUT);
		} else {
			logistic.setDescription("Vendas");
			logistic.setName("Vendas");
			logistic.setValue(String.valueOf(Long.parseLong(code) * 550));
			logistic.setResourceType(ResourceType.OUTPUT);
		}

		return logistic;

	}

}
