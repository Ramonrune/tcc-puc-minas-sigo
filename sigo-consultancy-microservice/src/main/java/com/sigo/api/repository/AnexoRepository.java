package com.sigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.Anexo;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {

	Page<Anexo> findAll(Pageable pageable);

	@Query(value = "SELECT max(codigo) FROM Anexo")
	Long getMaxTransactionId();
}
