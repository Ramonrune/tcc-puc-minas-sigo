package com.sigo.api.resource;

import java.time.LocalDate;
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
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.repository.ProcessoIndustrialRepository;
import com.sigo.api.service.ProcessoIndustrialService;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/industry-management")
public class ProcessoIndustrialResource {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	@Autowired
	private ProcessoIndustrialService processoIndustrialService;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProcessoIndustrial processoIndustrial,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ProcessoIndustrial save = processoIndustrialService.save(processoIndustrial);

		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam("codigo_filial") Long codigoFilial,
			@RequestParam("data_inicio_planejamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicioPlanejamento,
			@RequestParam("data_fim_planejamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFimPlanejamento) {

		if (dataInicioPlanejamento == null || dataFimPlanejamento == null || codigoFilial == null) {
			return ResponseEntity.badRequest().build();
		}

		List<ProcessoIndustrial> findByCodigoFilialAndPeriodoData = processoIndustrialRepository
				.findByCodigoFilialAndPeriodoData(codigoFilial, dataInicioPlanejamento, dataFimPlanejamento);

		return ResponseEntity.ok(findByCodigoFilialAndPeriodoData);
	}

	@PutMapping("/{codigo}/status/{status}")
	public ResponseEntity<?> updateStatus(@PathVariable(name = "codigo") Long codigo,
			@PathVariable(name = "status") Long status, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		processoIndustrialService.updateStatus(codigo, status);

		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token,
			@RequestParam("codigo_filial") Long codigoFilial) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		processoIndustrialService.delete(codigo, codigoFilial);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	

}
