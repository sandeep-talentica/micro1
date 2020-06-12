package com.sandeep.ws.feign.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.sandeep.ws.model.FirstMicroServiceResponseModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "micro3-ws", fallbackFactory = ThirdMicroFallbackFactory.class)
public interface ThirdMicroServiceClient {

	@PostMapping(path = "/m3/get")
	public ResponseEntity<String> getFullName(FirstMicroServiceResponseModel requestModel);

}

@Component
class ThirdMicroFallbackFactory implements FallbackFactory<ThirdMicroServiceClient> {

	@Override
	public ThirdMicroServiceClient create(Throwable cause) {
		return new ThirdMicroServiceClientFallback(cause);
	}
}

@Slf4j
class ThirdMicroServiceClientFallback implements ThirdMicroServiceClient {

	private final Throwable cause;

	public ThirdMicroServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResponseEntity<String> getFullName( FirstMicroServiceResponseModel requestModel) {
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			log.error("404 error took place when second Microservice was called " + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			log.error("Other error took place: " + cause.getLocalizedMessage());
		}

		return new ResponseEntity<String>(" ", HttpStatus.OK);
	}
}
