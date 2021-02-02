package com.sigo.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigo.api.model.Consultoria;


public interface ConsultoriaRepository extends JpaRepository<Consultoria, Long> {

	Page<Consultoria> findAll(Pageable pageable);
	
	@Query(value = "SELECT max(codigo) FROM Consultoria")
	Long getMaxTransactionId();
	
	@Query(value = "SELECT * FROM consultoria WHERE codigo_filial = :codigoFilial AND codigo_empresa_consultoria = :codigoEmpresaConsultoria AND (data_inicio >= :dataInicio AND data_fim <= :dataFim)", nativeQuery = true)
	List<Consultoria> findByCodigoFilialAndPeriodoData(@Param("codigoEmpresaConsultoria") Long codigoEmpresaConsultoria, @Param("codigoFilial") Long codigoFilial, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

}
