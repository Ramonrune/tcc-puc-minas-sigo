package com.sigo.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.Usuario;
import com.sigo.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/users")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario) {
		usuarioRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> find(@PathVariable Long codigo){
		Usuario usuario = usuarioRepository.findOne(codigo);
		usuario.setSenha("");
		return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		usuarioRepository.delete(codigo);
	}
}
 