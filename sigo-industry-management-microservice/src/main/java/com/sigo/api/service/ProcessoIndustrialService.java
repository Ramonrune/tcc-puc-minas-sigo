package com.sigo.api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigo.api.infra.ProducerProcessoIndustrial;
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.repository.ProcessoIndustrialRepository;

@Service
public class ProcessoIndustrialService {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	@Autowired
	private ProducerProcessoIndustrial producer;

	public ProcessoIndustrial save(ProcessoIndustrial processoIndustrial) {

		Long maxTransactionId = processoIndustrialRepository.getMaxTransactionId();

		if(maxTransactionId == null) {
			maxTransactionId = Long.valueOf(0);
		}
		
		processoIndustrial.setCodigo(maxTransactionId + 1);
		processoIndustrial.setCodigoExterno(UUID.randomUUID().toString());
		ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);

		producer.produce(processoIndustrialNew, "INSERT");

		return processoIndustrialNew;

	}

	public void delete(Long codigo, Long codigoFilial) {

		processoIndustrialRepository.delete(codigo);

		
		ProcessoIndustrial processoIndustrialNew = new ProcessoIndustrial();
		processoIndustrialNew.setCodigo(codigo);
		processoIndustrialNew.setCodigoFilial(codigoFilial);

		producer.produce(processoIndustrialNew, "DELETE");

	}

	public void updateStatus(Long codigo, Long status) {

		ProcessoIndustrial findOne = processoIndustrialRepository.findOne(codigo);
		findOne.setStatus(Integer.parseInt(status.toString()));

		ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(findOne);

		producer.produce(processoIndustrialNew, "UPDATE");

	}
}