package com.sigo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.ProcessoIndustrialItem;

public interface ProcessoIndustrialItemRepository extends JpaRepository<ProcessoIndustrialItem, Long> {
	
	@Query(value = "SELECT max(codigo) FROM ProcessoIndustrialItem")
	Long getMaxTransactionId();
}
