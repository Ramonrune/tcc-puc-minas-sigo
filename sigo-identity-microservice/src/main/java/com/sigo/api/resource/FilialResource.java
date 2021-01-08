package com.sigo.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.Filial;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.repository.FilialRepository;
import com.sigo.api.token.JwtTokenDecoder;

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
	
	@PostMapping
	public ResponseEntity<?> find(@RequestHeader(name = "Authorization") String token, @RequestBody Filial filial) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		filial.setCodigo(filialRepository.getMaxTransactionId().longValue() + 1);
		filialRepository.save(filial);

		return ResponseEntity.status(HttpStatus.CREATED).body(filial);
		
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?> edit(@PathVariable Long codigo, @Valid @RequestBody Filial filial, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Filial newFilial = filialRepository.save(filial);

		return ResponseEntity.status(HttpStatus.OK).body(newFilial);

	}
	
	@GetMapping("exists/{cnpj}")
	public ResponseEntity<?> find(@RequestHeader(name = "Authorization") String token, @PathVariable String cnpj) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Filial> findByCnpj = filialRepository.findByCnpj(cnpj);
		
		return findByCnpj.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}


	@GetMapping
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Filial> filiais = filialRepository.findAll();
		return ResponseEntity.ok(filiais);

	}
	
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		filialRepository.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
