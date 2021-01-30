package com.sigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.Consultoria;

public interface ConsultoriaRepository extends JpaRepository<Consultoria, Long> {

	Page<Consultoria> findAll(Pageable pageable);
	
	@Query(value = "SELECT max(codigo) FROM Consultoria")
	Long getMaxTransactionId();
}
