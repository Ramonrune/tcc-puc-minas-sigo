package com.sigo.api.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.Ocorrencia;
import com.sigo.api.repository.OcorrenciaRepository;
import com.sigo.api.service.OcorrenciaService;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/occurrence")
public class OcorrenciaResource {

	@Autowired
	private OcorrenciaRepository ocorrenciaRepository;
	
	@Autowired
	private OcorrenciaService ocorrenciaService;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Ocorrencia ocorrencia,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Ocorrencia save = ocorrenciaService.save(ocorrencia);

		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam("codigo_filial") Long codigoFilial,
			@RequestParam("data_inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam("data_fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

		if (dataInicio == null || dataFim == null || codigoFilial == null) {
			return ResponseEntity.badRequest().build();
		}
		
		

		List<Ocorrencia> findByCodigoFilialAndPeriodoData = ocorrenciaRepository
				.findByCodigoFilialAndPeriodoData(codigoFilial, LocalDateTime.of(dataInicio, LocalTime.of(0, 0)), LocalDateTime.of(dataFim, LocalTime.of(23, 59)));

		return ResponseEntity.ok(findByCodigoFilialAndPeriodoData);
	}

	@PutMapping("/{codigo}/status/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable(name = "codigo") Long codigo,
			@PathVariable(name = "status") Long status, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ocorrenciaService.updateStatus(codigo, status);

		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ocorrenciaService.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	

}
