package com.sigo.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Entity
@Audited
@Table(name = "ocorrencia")
public class Ocorrencia {

	@Id
	private Long codigo;

	@Column(name = "codigo_processo_industrial")
	private Long codigoProcessoIndustrial;
	
	@Column(name = "codigo_usuario")
	private Long codigoUsuario;
	
	@Column(name = "codigo_filial")
	private Long codigoFilial;

	private String nome;
	private Integer status;
	private String descricao;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "data_ocorrencia")
	private LocalDateTime dataOcorrencia;


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

	
	public Long getCodigoFilial() {
		return codigoFilial;
	}
	
	public Long getCodigoUsuario() {
		return codigoUsuario;
	}
	
	public LocalDateTime getDataOcorrencia() {
		return dataOcorrencia;
	}
	
	
	public void setCodigoFilial(Long codigoFilial) {
		this.codigoFilial = codigoFilial;
	}
	
	
	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
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
		Ocorrencia other = (Ocorrencia) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
