package com.sigo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.ProcessoIndustrial;

public interface ProcessoIndustrialRepository extends JpaRepository<ProcessoIndustrial, Long> {
	
	@Query(value = "SELECT max(codigo) FROM ProcessoIndustrial")
	Long getMaxTransactionId();
}
