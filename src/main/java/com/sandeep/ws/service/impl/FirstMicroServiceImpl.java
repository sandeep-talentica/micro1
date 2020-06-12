package com.sandeep.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sandeep.ws.feign.interfaces.SecondMicroServiceClient;
import com.sandeep.ws.feign.interfaces.ThirdMicroServiceClient;
import com.sandeep.ws.model.FirstMicroServiceResponseModel;
import com.sandeep.ws.services.FirstMicroService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirstMicroServiceImpl implements FirstMicroService {

	SecondMicroServiceClient secondMicroServiceClient;

	ThirdMicroServiceClient thirdMicroServiceClient;

	@Autowired
	public FirstMicroServiceImpl(SecondMicroServiceClient secondMicroServiceClient,
			ThirdMicroServiceClient thirdMicroServiceClient) {

		this.secondMicroServiceClient = secondMicroServiceClient;
		this.thirdMicroServiceClient = thirdMicroServiceClient;
	}

	@Override
	public String getGreeting() {

		log.info("Before calling second microservice");
		ResponseEntity<String> res = secondMicroServiceClient.getGreeting();
		log.info("After calling second microservice");
		return res.getBody();

	}

	@Override
	public String getFullName(FirstMicroServiceResponseModel requestModel) {

		log.info("Before calling third microservice");
		ResponseEntity<String> res = thirdMicroServiceClient.getFullName(requestModel);
		log.info("After calling third microservice");

		return res.getBody();
	}

}
