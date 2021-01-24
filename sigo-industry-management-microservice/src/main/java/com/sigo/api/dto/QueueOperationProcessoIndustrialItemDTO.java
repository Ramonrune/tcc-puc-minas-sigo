package com.sigo.api.dto;

import com.sigo.api.model.ProcessoIndustrialItem;

public class QueueOperationProcessoIndustrialItemDTO {

	private ProcessoIndustrialItem processoIndustrialItem;
	private String mode;

	public ProcessoIndustrialItem getProcessoIndustrialItem() {
		return processoIndustrialItem;
	}

	public void setProcessoIndustrialItem(ProcessoIndustrialItem processoIndustrialItem) {
		this.processoIndustrialItem = processoIndustrialItem;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
