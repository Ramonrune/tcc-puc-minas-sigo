package com.sigo.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.Filial;
import com.sigo.api.repository.FilialRepository;

@RestController
@RequestMapping("/companies")
public class FilialResource {

	@Autowired
	private FilialRepository filialRepository;

	@GetMapping("/{codigo}")
	public ResponseEntity<?> find(@PathVariable Long codigo) {
		Filial filial = filialRepository.findOne(codigo);
		return filial != null ? ResponseEntity.ok(filial) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Filial> filiais = filialRepository.findAll();
		return ResponseEntity.ok(filiais);

	}
}
