package com.sigo.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.Permissao;
import com.sigo.api.repository.PermissaoRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/permissions")
public class PermissaoResource {

	@Autowired
	private PermissaoRepository permissaoRepository;


	@GetMapping
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Permissao> permissoes = permissaoRepository.findAll();
		return ResponseEntity.ok(permissoes);

	}
	
	
}
