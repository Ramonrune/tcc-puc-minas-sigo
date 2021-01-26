package com.sigo.api.infra.soap;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetLogisticsRequest;
import io.spring.guides.gs_producing_web_service.GetLogisticsResponse;

public class LogisticaClient extends WebServiceGatewaySupport {

	public GetLogisticsResponse getLogistic(String code) {

		GetLogisticsRequest request = new GetLogisticsRequest();
		request.setCode(code);


		GetLogisticsResponse response = (GetLogisticsResponse) getWebServiceTemplate().marshalSendAndReceive(
				"http://localhost:8003/logistics/ws/logistics.wsdl", request,
				new SoapActionCallback("http://spring.io/guides/gs-producing-web-service/GetLogisticsRequest"));

		return response;
	}

}
