package com.sigo.api.infra.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sigo.api.repository.LogisticaRepository;

import io.spring.guides.gs_producing_web_service.GetLogisticsRequest;
import io.spring.guides.gs_producing_web_service.GetLogisticsResponse;

@Endpoint
public class LogisticaEndpoint {

	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	@Autowired
	private LogisticaRepository logisticaRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLogisticsRequest")
	@ResponsePayload
	public GetLogisticsResponse getCountry(@RequestPayload GetLogisticsRequest request) {
		GetLogisticsResponse response = new GetLogisticsResponse();
		response.setLogistic(logisticaRepository.findLogistic(request.getCode()));

		return response;
	}
}
