package com.sigo.api.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?> edit(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Usuario user = usuarioRepository.findOne(codigo);
		if(user != null) {
			usuario.setSenha(user.getSenha());
		}
		Usuario newUsuario = usuarioRepository.save(usuario);

		return ResponseEntity.status(HttpStatus.OK).body(newUsuario);

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
	
	@GetMapping("exists/{email}")
	public ResponseEntity<?> find(@RequestHeader(name = "Authorization") String token, @PathVariable String email) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		Optional<Usuario> findByEmail = usuarioRepository.findByEmail(email);
		
		return findByEmail.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	
	@GetMapping
	public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Usuario> findAll = usuarioRepository.findAll();
		findAll = findAll.stream().map(e-> {
			e.setSenha("");
			
			return e;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(findAll);
	}

}
