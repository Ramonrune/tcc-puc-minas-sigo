package com.sigo.api.infra;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sigo.api.RabbitAmqpSigoProcessoIndustrialItemConfiguration;
import com.sigo.api.dto.QueueOperationProcessoIndustrialItemDTO;
import com.sigo.api.model.ProcessoIndustrialItem;

@Service
public class ProducerProcessoIndustrialItem {

	@Autowired
	private RabbitTemplate template;

	public void produce(ProcessoIndustrialItem processoIndustrial, String mode) {
		
		QueueOperationProcessoIndustrialItemDTO queueOperationDTO = new QueueOperationProcessoIndustrialItemDTO();
		queueOperationDTO.setProcessoIndustrialItem(processoIndustrial);
		queueOperationDTO.setMode(mode);

		final Gson gson = new GsonBuilder().create();

		String json = gson.toJson(queueOperationDTO);

		this.template.convertAndSend(RabbitAmqpSigoProcessoIndustrialItemConfiguration.SIGO_PROCESSO_INDUSTRIAL_EXCHANGE,
				RabbitAmqpSigoProcessoIndustrialItemConfiguration.SIGO_PROCESSO_INDUSTRIAL, json);

	}

}
