package com.sandeep.ws.feign.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "micro2-ws", fallbackFactory = SecondMicroFallbackFactory.class)
public interface SecondMicroServiceClient {

	@GetMapping(path = "/m2/get")
	public ResponseEntity<String> getGreeting();

}

@Component
class SecondMicroFallbackFactory implements FallbackFactory<SecondMicroServiceClient> {

	@Override
	public SecondMicroServiceClient create(Throwable cause) {
		return new SecondMicroServiceClientFallback(cause);
	}
}

@Slf4j
class SecondMicroServiceClientFallback implements SecondMicroServiceClient {

	private final Throwable cause;

	public SecondMicroServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResponseEntity<String> getGreeting() {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			log.error("404 error took place when second Microservice was called " + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			log.error("Other error took place: " + cause.getLocalizedMessage());
		}

		return new ResponseEntity<String>(" ", HttpStatus.OK);
	}
}
