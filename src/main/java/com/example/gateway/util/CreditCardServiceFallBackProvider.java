package com.example.gateway.util;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.exception.HystrixTimeoutException;

@Component
public class CreditCardServiceFallBackProvider implements FallbackProvider {
	private static final String DEFAULT_MSG = "Internal Server Error. Please try again later";

	@Override
	public String getRoute() {
		return "credit-card-service";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		if(cause instanceof HystrixTimeoutException)
			return new GatewayClientResponse(HttpStatus.GATEWAY_TIMEOUT, DEFAULT_MSG);
		return new GatewayClientResponse(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MSG);
	}

}
