package com.sigo.api.dto;

public enum TipoMudanca {
	ALTERACAO("ALTERACAO"), NOVA_VERSAO("NOVA_VERSAO");

	TipoMudanca(String tipoMudanca) {
		this.tipoMudanca = tipoMudanca;
	}

	String tipoMudanca;
}
