package com.sigo.api.infra;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sigo.api.RabbitAmqpLegadoProcessoIndustrialConfiguration;
import com.sigo.api.dto.QueueOperationDTO;
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.repository.ProcessoIndustrialRepository;

@Service
public class Consumer {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	@RabbitListener(queues = RabbitAmqpLegadoProcessoIndustrialConfiguration.LEGADO_PROCESSO_INDUSTRIAL)
	public void processQueueMessages(Message message) throws Exception {

		QueueOperationDTO queueOperationDTO = getProcessoIndustrial(message);

		String mode = queueOperationDTO.getMode();
		ProcessoIndustrial processoIndustrial = queueOperationDTO.getProcessoIndustrial();

		if (mode.equals("INSERT")) {

			processoIndustrial.setCodigo(processoIndustrialRepository.getMaxTransactionId() + 1);
			processoIndustrialRepository.save(processoIndustrial);

		}

		if (mode.equals("UPDATE")) {
			processoIndustrialRepository.save(processoIndustrial);

		}

		if (mode.equals("DELETE")) {
			processoIndustrialRepository.delete(processoIndustrial.getCodigo());

		}

	}

	private QueueOperationDTO getProcessoIndustrial(Message message) {
		String msg = new String(message.getBody());

		JsonObject json = JsonParser.parseString(msg).getAsJsonObject();

		Gson gson = new Gson();

		QueueOperationDTO processModel = gson.fromJson(json, QueueOperationDTO.class);
		return processModel;
	}
}
