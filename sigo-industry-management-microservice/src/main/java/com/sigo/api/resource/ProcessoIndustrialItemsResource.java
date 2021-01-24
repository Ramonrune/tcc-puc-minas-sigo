package com.sigo.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.sigo.api.model.ProcessoIndustrialItem;
import com.sigo.api.repository.ProcessoIndustrialItemRepository;
import com.sigo.api.service.ProcessoIndustrialItemService;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/industry-management-items")
public class ProcessoIndustrialItemsResource {

	@Autowired
	private ProcessoIndustrialItemRepository processoIndustrialItemRepository;
	
	@Autowired
	private ProcessoIndustrialItemService processoIndustrialItemService;

	@GetMapping("/{codigo}")
	public ResponseEntity<?> findProcessItems(@PathVariable(name = "codigo") Long codigo,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);
		if (!decoded.getAuthorities().contains("ROLE_ADMIN")
				&& !decoded.getAuthorities().contains("ROLE_COMMON_USER")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ProcessoIndustrialItem processoIndustrialItem = new ProcessoIndustrialItem();
		processoIndustrialItem.setCodigoProcessoIndustrial(codigo);
		Example<ProcessoIndustrialItem> of = Example.of(processoIndustrialItem);

		List<ProcessoIndustrialItem> findAll = processoIndustrialItemRepository.findAll(of);

		return ResponseEntity.status(HttpStatus.OK).body(findAll);

	}

	@PostMapping
	public ResponseEntity<?> saveItem(@Valid @RequestBody ProcessoIndustrialItem processoIndustrial,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ProcessoIndustrialItem save = processoIndustrialItemService.save(processoIndustrial);

		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@PutMapping("/{codigo}/status/{status}")
	public ResponseEntity<?> updateItemStatus(@PathVariable(name = "codigo") Long codigo,
			@PathVariable(name = "status") Long status, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		processoIndustrialItemService.updateStatus(codigo, status);

		return ResponseEntity.status(HttpStatus.OK).build();

	}
	
	@PutMapping("/{codigo}/hour/{hour}")
	public ResponseEntity<?> updateItemHour(@PathVariable(name = "codigo") Long codigo,
			@PathVariable("hour") Long hour,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		processoIndustrialItemService.updateHour(codigo, hour);

		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> removeItem(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		processoIndustrialItemService.delete(codigo);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
