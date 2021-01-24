package com.sigo.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigo.api.infra.ProducerProcessoIndustrialItem;
import com.sigo.api.model.ProcessoIndustrialItem;
import com.sigo.api.repository.ProcessoIndustrialItemRepository;

@Service
public class ProcessoIndustrialItemService {

	@Autowired
	private ProcessoIndustrialItemRepository processoIndustrialItemRepository;

	@Autowired
	private ProducerProcessoIndustrialItem producer;

	public ProcessoIndustrialItem save(ProcessoIndustrialItem processoIndustrial) {

		Long maxTransactionId = processoIndustrialItemRepository.getMaxTransactionId();
		
		if(maxTransactionId == null) {
			maxTransactionId = Long.valueOf(0);
		}

		processoIndustrial.setCodigo(maxTransactionId + 1);

		ProcessoIndustrialItem processoIndustrialNew = processoIndustrialItemRepository.save(processoIndustrial);

		producer.produce(processoIndustrialNew, "INSERT");

		return processoIndustrialNew;

	}

	public void delete(Long codigo) {

		processoIndustrialItemRepository.delete(codigo);

		ProcessoIndustrialItem processoIndustrialNew = new ProcessoIndustrialItem();
		processoIndustrialNew.setCodigo(codigo);

		producer.produce(processoIndustrialNew, "DELETE");

	}

	public void updateStatus(Long codigo, Long status) {

		ProcessoIndustrialItem findOne = processoIndustrialItemRepository.findOne(codigo);
		findOne.setStatus(Integer.parseInt(status.toString()));

		ProcessoIndustrialItem processoIndustrialNew = processoIndustrialItemRepository.save(findOne);

		producer.produce(processoIndustrialNew, "UPDATE");

	}
	
	public void updateHour(Long codigo, Long hour) {

		ProcessoIndustrialItem findOne = processoIndustrialItemRepository.findOne(codigo);
		findOne.setQtdHorasRealizada(hour);

		ProcessoIndustrialItem processoIndustrialNew = processoIndustrialItemRepository.save(findOne);

		producer.produce(processoIndustrialNew, "UPDATE");

	}
}
