package com.sigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.Norma;

public interface NormaRepository extends JpaRepository<Norma, Long> {

	Page<Norma> findAll(Pageable pageable);
	
	@Query(value = "SELECT max(codigo) FROM Norma")
	Long getMaxTransactionId();
}
