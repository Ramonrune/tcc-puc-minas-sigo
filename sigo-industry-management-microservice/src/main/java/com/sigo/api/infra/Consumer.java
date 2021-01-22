package com.sigo.api.infra;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pusher.rest.Pusher;
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
		
		Pusher pusher = new Pusher(System.getenv("PUSHER_APP_ID"), System.getenv("PUSHER_APP_KEY"), System.getenv("PUSHER_APP_SECRET"));
		pusher.setCluster("us2");
		pusher.setEncrypted(true);

		

		if (mode.equals("INSERT")) {
			
			Long maxTransactionId = processoIndustrialRepository.getMaxTransactionId();

			processoIndustrial.setCodigo((maxTransactionId != null ? maxTransactionId : 0) + 1);
			ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);
			
			pusher.trigger("sigo-industry-management-" + processoIndustrialNew.getCodigoFilial(), "INSERT", processoIndustrialNew);


		}

		if (mode.equals("UPDATE")) {
			ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);
			pusher.trigger("sigo-industry-management-" + processoIndustrialNew.getCodigoFilial(), "UPDATE", processoIndustrialNew);

		}

		if (mode.equals("DELETE")) {
			processoIndustrialRepository.delete(processoIndustrial.getCodigo());
			pusher.trigger("sigo-industry-management-" + processoIndustrial.getCodigoFilial(), "DELETE", processoIndustrial);


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
