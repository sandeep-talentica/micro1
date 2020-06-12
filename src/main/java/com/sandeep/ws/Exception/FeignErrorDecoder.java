package com.sandeep.ws.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {

		case 400: {
			// do something
			break;
		}
		case 404: {

			if (methodKey.contains("getFullName")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()),
						"Micro2 services are not found");
			}

			break;

		}
		default:
			return new Exception(response.reason());

		}

		return null;
	}

}
