package com.sigo.api.resource;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.Usuario;
import com.sigo.api.repository.UsuarioRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/users")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		String password = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(password);

		usuarioRepository.save(usuario);

		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	@GetMapping("/info")
	public ResponseEntity<?> getUserInfo(@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		Usuario usuario = usuarioRepository.findOne(new Long(decoded.getCodigo().toString()));
		usuario.setSenha("");

		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		usuarioRepository.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
