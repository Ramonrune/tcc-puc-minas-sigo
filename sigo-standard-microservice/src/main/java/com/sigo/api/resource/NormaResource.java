package com.sigo.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.Norma;
import com.sigo.api.repository.NormaRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/standards")
public class NormaResource {

	@Autowired
	private NormaRepository normaRepository;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Norma norma,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		normaRepository.save(norma);

		return ResponseEntity.status(HttpStatus.CREATED).body(norma);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> findOne(@PathVariable Long codigo) {
		Norma norma = normaRepository.findOne(codigo);

		return norma != null ? ResponseEntity.ok(norma) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<?> findAll(@PathVariable Long codigo) {
		List<Norma> findAll = normaRepository.findAll();

		return ResponseEntity.ok(findAll);
	}

}
