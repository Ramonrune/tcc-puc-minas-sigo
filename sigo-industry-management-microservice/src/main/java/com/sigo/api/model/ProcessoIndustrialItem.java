package com.sigo.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@Audited
@Table(name = "processo_industrial_item")
public class ProcessoIndustrialItem {

	@Id
	private Long codigo;

	@Column(name = "codigo_processo_industrial")
	private Long codigoProcessoIndustrial;

	private String nome;
	private Integer status;
	private String descricao;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;

	@Column(name = "qtd_horas_prevista")
	private Long qtdHorasPrevista;

	@Column(name = "qtd_horas_realizada")
	private Long qtdHorasRealizada;

	@Column(name = "codigo_externo_processo_industrial_item")
	private String codigoExternoProcessoIndustrialItem;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoProcessoIndustrial() {
		return codigoProcessoIndustrial;
	}

	public void setCodigoProcessoIndustrial(Long codigoProcessoIndustrial) {
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

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Long getQtdHorasPrevista() {
		return qtdHorasPrevista;
	}

	public void setQtdHorasPrevista(Long qtdHorasPrevista) {
		this.qtdHorasPrevista = qtdHorasPrevista;
	}

	public Long getQtdHorasRealizada() {
		return qtdHorasRealizada;
	}

	public void setQtdHorasRealizada(Long qtdHorasRealizada) {
		this.qtdHorasRealizada = qtdHorasRealizada;
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
