package com.sigo.api.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@GetMapping(path = "/{codigo}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLogistic(@PathVariable(name = "codigo") Long codigo) {
		GetLogisticsResponse response01 = logisticaClient.getLogistic(String.valueOf(1));
		GetLogisticsResponse response02 = logisticaClient.getLogistic(String.valueOf(2));

		List<GetLogisticsResponse> list = new ArrayList<>();
		list.add(response01);
		list.add(response02);
		
		return ResponseEntity.ok(list);
	}
}
