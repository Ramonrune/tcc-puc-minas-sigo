package com.sigo.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigo.api.model.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
	
	@Query(value = "SELECT max(codigo) FROM Ocorrencia")
	Long getMaxTransactionId();
	
	@Query(value = "SELECT * FROM ocorrencia WHERE codigo_filial = :codigoFilial AND (data_ocorrencia >= :dataInicio AND data_ocorrencia <= :dataFim)", nativeQuery = true)
	List<Ocorrencia> findByCodigoFilialAndPeriodoData(@Param("codigoFilial") Long codigoFilial, @Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

}
