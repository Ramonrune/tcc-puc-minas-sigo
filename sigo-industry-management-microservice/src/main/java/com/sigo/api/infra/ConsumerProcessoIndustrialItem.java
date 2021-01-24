package com.sigo.api.infra;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pusher.rest.Pusher;
import com.sigo.api.RabbitAmqpLegadoProcessoIndustrialItemConfiguration;
import com.sigo.api.dto.QueueOperationProcessoIndustrialItemDTO;
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.model.ProcessoIndustrialItem;
import com.sigo.api.repository.ProcessoIndustrialItemRepository;
import com.sigo.api.repository.ProcessoIndustrialRepository;

@Service
public class ConsumerProcessoIndustrialItem {

	@Autowired
	private ProcessoIndustrialItemRepository processoIndustrialItemRepository;

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	@RabbitListener(queues = RabbitAmqpLegadoProcessoIndustrialItemConfiguration.LEGADO_PROCESSO_INDUSTRIAL)
	public void processQueueMessages(Message message) throws Exception {

		QueueOperationProcessoIndustrialItemDTO queueOperationDTO = getProcessoIndustrialItem(message);

		String mode = queueOperationDTO.getMode();
		ProcessoIndustrialItem processoIndustrial = queueOperationDTO.getProcessoIndustrialItem();

		Pusher pusher = new Pusher(System.getenv("PUSHER_APP_ID"), System.getenv("PUSHER_APP_KEY"),
				System.getenv("PUSHER_APP_SECRET"));
		pusher.setCluster("us2");
		pusher.setEncrypted(true);

		if (mode.equals("INSERT")) {

			Long maxTransactionId = processoIndustrialItemRepository.getMaxTransactionId();

			processoIndustrial.setCodigo((maxTransactionId != null ? maxTransactionId : 0) + 1);
			ProcessoIndustrialItem processoIndustrialNew = processoIndustrialItemRepository.save(processoIndustrial);

			ProcessoIndustrial probe = processoIndustrialRepository
					.findOne(processoIndustrial.getCodigoProcessoIndustrial());

			pusher.trigger("sigo-industry-management-" + probe.getCodigoFilial(), "INSERT", processoIndustrialNew);

		}

		if (mode.equals("UPDATE")) {

			ProcessoIndustrial probe = processoIndustrialRepository
					.findOne(processoIndustrial.getCodigoProcessoIndustrial());

			ProcessoIndustrialItem processoIndustrialNew = processoIndustrialItemRepository.save(processoIndustrial);
			pusher.trigger("sigo-industry-management-" + probe.getCodigoFilial(), "UPDATE", processoIndustrialNew);

		}

		if (mode.equals("DELETE")) {

			ProcessoIndustrial probe = processoIndustrialRepository
					.findOne(processoIndustrial.getCodigoProcessoIndustrial());

			processoIndustrialItemRepository.delete(processoIndustrial.getCodigo());
			pusher.trigger("sigo-industry-management-" + probe.getCodigoFilial(), "DELETE", processoIndustrial);

		}

	}

	private QueueOperationProcessoIndustrialItemDTO getProcessoIndustrialItem(Message message) {
		String msg = new String(message.getBody());

		JsonObject json = JsonParser.parseString(msg).getAsJsonObject();

		Gson gson = new Gson();

		QueueOperationProcessoIndustrialItemDTO processModel = gson.fromJson(json,
				QueueOperationProcessoIndustrialItemDTO.class);
		return processModel;
	}
}
