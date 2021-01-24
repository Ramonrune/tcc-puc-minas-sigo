package com.sigo.api.infra;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pusher.rest.Pusher;
import com.sigo.api.RabbitAmqpLegadoProcessoIndustrialConfiguration;
import com.sigo.api.dto.QueueOperationProcessoIndustrialDTO;
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.model.ProcessoIndustrialItem;
import com.sigo.api.repository.ProcessoIndustrialItemRepository;
import com.sigo.api.repository.ProcessoIndustrialRepository;

@Service
public class ConsumerProcessoIndustrial {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;
	

	@Autowired
	private ProcessoIndustrialItemRepository processoIndustrialItemRepository;

	@RabbitListener(queues = RabbitAmqpLegadoProcessoIndustrialConfiguration.LEGADO_PROCESSO_INDUSTRIAL)
	public void processQueueMessages(Message message) throws Exception {

		QueueOperationProcessoIndustrialDTO queueOperationDTO = getProcessoIndustrial(message);

		String mode = queueOperationDTO.getMode();
		ProcessoIndustrial processoIndustrial = (ProcessoIndustrial) queueOperationDTO.getProcessoIndustrial();

		Pusher pusher = new Pusher(System.getenv("PUSHER_APP_ID"), System.getenv("PUSHER_APP_KEY"),
				System.getenv("PUSHER_APP_SECRET"));
		pusher.setCluster("us2");
		pusher.setEncrypted(true);

		if (mode.equals("INSERT")) {

			Long maxTransactionId = processoIndustrialRepository.getMaxTransactionId();

			processoIndustrial.setCodigo((maxTransactionId != null ? maxTransactionId : 0) + 1);
			ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);

			pusher.trigger("sigo-industry-management-" + processoIndustrialNew.getCodigoFilial(), "INSERT",
					processoIndustrialNew);

		}

		if (mode.equals("UPDATE")) {
			
			List<ProcessoIndustrialItem> processoIndustrialItems = getProcessoIndustrialItems(processoIndustrial);
			processoIndustrial.setItems(processoIndustrialItems);
			
			ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);
			pusher.trigger("sigo-industry-management-" + processoIndustrialNew.getCodigoFilial(), "UPDATE",
					processoIndustrialNew);

		}

		if (mode.equals("DELETE")) {
			processoIndustrialRepository.delete(processoIndustrial.getCodigo());
			pusher.trigger("sigo-industry-management-" + processoIndustrial.getCodigoFilial(), "DELETE",
					processoIndustrial);

		}

	}

	private List<ProcessoIndustrialItem> getProcessoIndustrialItems(ProcessoIndustrial processoIndustrial) {
		ProcessoIndustrialItem processoIndustrialItem = new ProcessoIndustrialItem();
		processoIndustrialItem.setCodigoProcessoIndustrial(processoIndustrial.getCodigo());
		Example<ProcessoIndustrialItem> of = Example.of(processoIndustrialItem);

		List<ProcessoIndustrialItem> findAll = processoIndustrialItemRepository.findAll(of);
		
		return findAll;
	}

	private QueueOperationProcessoIndustrialDTO getProcessoIndustrial(Message message) {
		String msg = new String(message.getBody());

		JsonObject json = JsonParser.parseString(msg).getAsJsonObject();

		Gson gson = new Gson();

		QueueOperationProcessoIndustrialDTO processModel = gson.fromJson(json,
				QueueOperationProcessoIndustrialDTO.class);
		return processModel;
	}
}
