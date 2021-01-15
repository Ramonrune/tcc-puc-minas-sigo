package com.sigo.api.model;

import java.util.ArrayList;

public class JsonWebToken {
	private Integer codigo;
	private String user_name;
	private ArrayList<Object> scope = new ArrayList<Object>();
	private String nome;
	private Integer exp;
	private ArrayList<Object> authorities = new ArrayList<Object>();
	private String jti;
	private String client_id;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public ArrayList<Object> getScope() {
		return scope;
	}

	public void setScope(ArrayList<Object> scope) {
		this.scope = scope;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public ArrayList<Object> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(ArrayList<Object> authorities) {
		this.authorities = authorities;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

}