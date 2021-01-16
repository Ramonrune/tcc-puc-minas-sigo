package com.sigo.api.service;

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

		ProcessoIndustrial processoIndustrialNew = processoIndustrialRepository.save(processoIndustrial);

		return processoIndustrialNew;

	}
}
