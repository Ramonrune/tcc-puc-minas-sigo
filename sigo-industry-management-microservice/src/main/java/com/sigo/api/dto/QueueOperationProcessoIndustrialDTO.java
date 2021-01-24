package com.sigo.api.dto;

import com.sigo.api.model.ProcessoIndustrial;

public class QueueOperationProcessoIndustrialDTO{

	private ProcessoIndustrial processoIndustrial;
	private String mode;

	public ProcessoIndustrial getProcessoIndustrial() {
		return processoIndustrial;
	}

	public void setProcessoIndustrial(ProcessoIndustrial processoIndustrial) {
		this.processoIndustrial = processoIndustrial;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
