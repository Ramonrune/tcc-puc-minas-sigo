package com.sigo.api.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.sigo.api.model.Norma;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PageNormaDTO {

	private List<Norma> content;
	private int number;
	private int numberOfElements;
	private int size;
	private int totalPages;
	private long totalElements;
	private Sort sort;
	
	public PageNormaDTO() {
		
	}

	public PageNormaDTO(Page<Norma> findAll) {
		content = findAll.getContent();
		number = findAll.getNumber();
		numberOfElements = findAll.getNumberOfElements();
		size = findAll.getSize();
		totalPages = findAll.getTotalPages();
		totalElements = findAll.getTotalElements();
		sort = findAll.getSort();
	}

	public List<Norma> getContent() {
		return content;
	}

	public void setContent(List<Norma> content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

}
