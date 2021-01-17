package com.sigo.api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.repository.ProcessoIndustrialRepository;

@Service
public class ProcessoIndustrialService {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	public ProcessoIndustrial save(ProcessoIndustrial processoIndustrial) {

		Long maxTransactionId = processoIndustrialRepository.getMaxTransactionId();

		processoIndustrial.setCodigo(maxTransactionId + 1);
		processoIndustrial.setCodigoExterno(UUID.randomUUID().toString());
		ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);

		return processoIndustrialNew;

	}

	public void delete(Long codigo) {

		processoIndustrialRepository.delete(codigo);

	}

	public void updateStatus(Long codigo, Long status) {

		ProcessoIndustrial findOne = processoIndustrialRepository.findOne(codigo);
		findOne.setStatus(Integer.parseInt(status.toString()));

		processoIndustrialRepository.save(findOne);

	}
}
