package com.sigo.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigo.api.model.Ocorrencia;
import com.sigo.api.repository.OcorrenciaRepository;

@Service
public class OcorrenciaService {

	@Autowired
	private OcorrenciaRepository ocorrenciaRepository;

	public Ocorrencia save(Ocorrencia ocorrencia) {

		Long maxTransactionId = ocorrenciaRepository.getMaxTransactionId();

		if (maxTransactionId == null) {
			maxTransactionId = Long.valueOf(0);
		}

		ocorrencia.setCodigo(maxTransactionId + 1);

		Ocorrencia ocorrenciaNew = ocorrenciaRepository.save(ocorrencia);

		return ocorrenciaNew;

	}

	public void delete(Long codigo) {

		ocorrenciaRepository.delete(codigo);

	}

	public void updateStatus(Long codigo, Long status) {

		Ocorrencia findOne = ocorrenciaRepository.findOne(codigo);
		findOne.setStatus(Integer.parseInt(status.toString()));

		ocorrenciaRepository.save(findOne);

	}
}