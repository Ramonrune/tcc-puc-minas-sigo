package com.sigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.EmpresaConsultoria;


public interface EmpresaConsultoriaRepository extends JpaRepository<EmpresaConsultoria, Long> {

	Page<EmpresaConsultoria> findAll(Pageable pageable);
	
	@Query(value = "SELECT max(codigo) FROM EmpresaConsultoria")
	Long getMaxTransactionId();
}
