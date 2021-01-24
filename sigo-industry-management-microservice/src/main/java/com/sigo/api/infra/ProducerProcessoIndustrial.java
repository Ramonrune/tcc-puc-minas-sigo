package com.sigo.api.infra;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sigo.api.RabbitAmqpSigoProcessoIndustrialConfiguration;
import com.sigo.api.dto.QueueOperationProcessoIndustrialDTO;
import com.sigo.api.model.ProcessoIndustrial;

@Service
public class ProducerProcessoIndustrial {

	@Autowired
	private RabbitTemplate template;

	public void produce(ProcessoIndustrial processoIndustrial, String mode) {
		
		QueueOperationProcessoIndustrialDTO queueOperationDTO = new QueueOperationProcessoIndustrialDTO();
		queueOperationDTO.setProcessoIndustrial(processoIndustrial);
		queueOperationDTO.setMode(mode);

		final Gson gson = new GsonBuilder().create();

		String json = gson.toJson(queueOperationDTO);

		this.template.convertAndSend(RabbitAmqpSigoProcessoIndustrialConfiguration.SIGO_PROCESSO_INDUSTRIAL_EXCHANGE,
				RabbitAmqpSigoProcessoIndustrialConfiguration.SIGO_PROCESSO_INDUSTRIAL, json);

	}

}
