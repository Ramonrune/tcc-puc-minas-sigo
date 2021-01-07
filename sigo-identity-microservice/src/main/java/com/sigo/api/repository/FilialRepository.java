package com.sigo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigo.api.model.Filial;

public interface FilialRepository extends JpaRepository<Filial, Long>{

	List<Filial> findByCnpj(String cnpj);
	
	@Query(value = "SELECT max(codigo) FROM Filial")
	Long getMaxTransactionId();
	
		
}
