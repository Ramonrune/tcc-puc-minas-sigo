package com.sigo.api.resource;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.EmpresaConsultoria;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.repository.EmpresaConsultoriaRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/company-consultancy")
public class EmpresaConsultoriaResource {

	@Autowired
	private EmpresaConsultoriaRepository empresaConsultoriaRepository;


	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody EmpresaConsultoria consultoria,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Long maxTransactionId = empresaConsultoriaRepository.getMaxTransactionId();
		maxTransactionId = maxTransactionId != null ? maxTransactionId : 0;
		consultoria.setCodigo(maxTransactionId + 1);
		consultoria.setDataRegistro(LocalDate.now());

		EmpresaConsultoria consultoriaNew = empresaConsultoriaRepository.save(consultoria);

		return ResponseEntity.status(HttpStatus.CREATED).body(consultoriaNew);
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<EmpresaConsultoria> findAll = empresaConsultoriaRepository.findAll();

		return ResponseEntity.ok(findAll);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		empresaConsultoriaRepository.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}


}
