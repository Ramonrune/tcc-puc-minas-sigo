package com.sigo.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.ProcessoIndustrialItem;
import com.sigo.api.repository.ProcessoIndustrialItemRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/industry-management-items")
public class ProcessoIndustrialItemsResource {

	@Autowired
	private ProcessoIndustrialItemRepository processoIndustrialItemRepository;

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

}
