package com.sandeep.ws.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandeep.ws.Exception.ServerNotRunningException;
import com.sandeep.ws.model.FirstMicroServiceResponseModel;
import com.sandeep.ws.services.FirstMicroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/m1")
@Api(value = "First Micro Service", description = "You can get concated result from two other services ")
public class FirstMicroServiceController {

	@Autowired
	FirstMicroService firstMicroService;
	@ApiOperation(value = "Check the running status", response = ResponseEntity.class)
	@GetMapping(path = "/status")
	public ResponseEntity<String> checkStatus() {

		String runningStatus = "Running Successfully";

		return new ResponseEntity<String>(runningStatus, HttpStatus.OK);
	}
	@ApiOperation(value = "get the concated result from two other micro service", response = ResponseEntity.class)
	@PostMapping(path = "/name", produces = { "application/json" })
	public ResponseEntity<String> getConcatanatedName(@Valid @RequestBody FirstMicroServiceResponseModel requestModel) {

		String greeting = firstMicroService.getGreeting();

		String fullName = firstMicroService.getFullName(requestModel);
		
		log.info("The fulname returned by Micro3 service is"+":"+fullName);

		if ((greeting == null || greeting == "")) {

			throw new ServerNotRunningException("Some problem occurs in Micro2 server");

		}

		if ((fullName == null || fullName == "")) {

			throw new ServerNotRunningException("Some problem occurs in Micro3 server");
		}

		String concatedName = greeting + " " + fullName;
		return new ResponseEntity<String>(concatedName, HttpStatus.OK);
	}

}
