package com.sigo.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Entity
@Audited
@Table(name = "processo_industrial_item")
public class ProcessoIndustrialItem {

	@Id
	private Long codigo;

	@Column(name = "codigo_processo_industrial")
	private String codigoProcessoIndustrial;

	private String nome;
	private Integer status;
	private String descricao;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "data_inicio")
	private LocalDate dataInicio;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "data_fim")
	private LocalDate dataFim;

	@Column(name = "codigo_externo_processo_industrial_item")
	private String codigoExternoProcessoIndustrialItem;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoProcessoIndustrial() {
		return codigoProcessoIndustrial;
	}

	public void setCodigoProcessoIndustrial(String codigoProcessoIndustrial) {
		this.codigoProcessoIndustrial = codigoProcessoIndustrial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public String getCodigoExternoProcessoIndustrialItem() {
		return codigoExternoProcessoIndustrialItem;
	}

	public void setCodigoExternoProcessoIndustrialItem(String codigoExternoProcessoIndustrialItem) {
		this.codigoExternoProcessoIndustrialItem = codigoExternoProcessoIndustrialItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessoIndustrialItem other = (ProcessoIndustrialItem) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
