package com.sigo.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.infra.soap.LogisticaClient;

import io.spring.guides.gs_producing_web_service.GetLogisticsResponse;

@RestController
@RequestMapping("/logistics")
public class LogisticaResource {

	@Autowired
	private LogisticaClient logisticaClient;

	@GetMapping("/{codigo}")
	public ResponseEntity<?> getLogistic(@PathVariable(name = "codigo") Long codigo) {
		GetLogisticsResponse response = logisticaClient.getLogistic(String.valueOf(codigo));

		return ResponseEntity.ok(response);
	}
}
