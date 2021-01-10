package com.sigo.api.dto;

import com.sigo.api.model.Norma;

public class NormaModificacaoDTO {

	private Norma norma;
	private TipoMudanca tipoMudanca;
	private String descricao;

	public Norma getNorma() {
		return norma;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	public TipoMudanca getTipoMudanca() {
		return tipoMudanca;
	}

	public void setTipoMudanca(TipoMudanca tipoMudanca) {
		this.tipoMudanca = tipoMudanca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
