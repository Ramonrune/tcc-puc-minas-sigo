package com.sigo.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigo.api.model.ProcessoIndustrial;

public interface ProcessoIndustrialRepository extends JpaRepository<ProcessoIndustrial, Long> {

	@Query(value = "SELECT max(codigo) FROM ProcessoIndustrial")
	Long getMaxTransactionId();

	@Query(value = "SELECT * FROM processo_industrial WHERE codigo_filial = :codigoFilial AND (data_inicio_planejamento >= :dataInicioPlanejamento AND dataFimPlanejamento <= :dataFimPlanejamento)", nativeQuery = true)
	List<ProcessoIndustrial> findByCodigoFilialAndPeriodoData(@Param("codigoFilial") Long codigoFilial, @Param("dataInicioPlanejamento") LocalDate dataInicioPlanejamento, @Param("dataFimPlanejamento") LocalDate dataFimPlanejamento );

}
