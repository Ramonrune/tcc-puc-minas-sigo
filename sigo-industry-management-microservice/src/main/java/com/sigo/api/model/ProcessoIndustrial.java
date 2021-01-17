package com.sigo.api.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Entity
@Audited
@Table(name = "processo_industrial")
public class ProcessoIndustrial {

	@Id
	private Long codigo;

	private String nome;
	private Integer status;
	private String descricao;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "data_inicio_planejamento")
	private LocalDate dataInicioPlanejamento;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "data_fim_planejamento")
	private LocalDate dataFimPlanejamento;

	@Column(name = "codigo_usuario")
	private Long codigoUsuario;

	@Column(name = "codigo_filial")
	private Long codigoFilial;

	@Column(name = "codigo_externo")
	private String codigoExterno;

	@OneToMany(mappedBy = "codigoProcessoIndustrial", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ProcessoIndustrialItem> items;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public LocalDate getDataInicioPlanejamento() {
		return dataInicioPlanejamento;
	}

	public void setDataInicioPlanejamento(LocalDate dataInicioPlanejamento) {
		this.dataInicioPlanejamento = dataInicioPlanejamento;
	}

	public LocalDate getDataFimPlanejamento() {
		return dataFimPlanejamento;
	}

	public void setDataFimPlanejamento(LocalDate dataFimPlanejamento) {
		this.dataFimPlanejamento = dataFimPlanejamento;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Long getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(Long codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public List<ProcessoIndustrialItem> getItems() {
		return items;
	}

	public void setItems(List<ProcessoIndustrialItem> items) {
		this.items = items;
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
		ProcessoIndustrial other = (ProcessoIndustrial) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
