package com.sigo.api.resource;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.Consultoria;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.repository.ConsultoriaRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/consultancy")
public class ConsultoriaResource {

	@Autowired
	private ConsultoriaRepository consultoriaRepository;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Consultoria consultoria,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Long maxTransactionId = consultoriaRepository.getMaxTransactionId();
		maxTransactionId = maxTransactionId != null ? maxTransactionId : 0;
		consultoria.setCodigo(maxTransactionId + 1);

		Consultoria save = consultoriaRepository.save(consultoria);

		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll() {

		List<Consultoria> findByCodigoFilialAndPeriodoData = consultoriaRepository.findAll();

		return ResponseEntity.ok(findByCodigoFilialAndPeriodoData);
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(name = "codigo_empresa_consultoria") Long codigoEmpresaConsultoria,
			@RequestParam("codigo_filial") Long codigoFilial,
			@RequestParam("data_inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam("data_fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

		if (dataInicio == null || dataFim == null || codigoFilial == null || codigoEmpresaConsultoria == null) {
			return ResponseEntity.badRequest().build();
		}

		List<Consultoria> findByCodigoFilialAndPeriodoData = consultoriaRepository
				.findByCodigoFilialAndPeriodoData(codigoEmpresaConsultoria, codigoFilial, dataInicio, dataFim);

		return ResponseEntity.ok(findByCodigoFilialAndPeriodoData);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		consultoriaRepository.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
