package com.sandeep.ws.services;

import com.sandeep.ws.model.FirstMicroServiceResponseModel;

public interface FirstMicroService {
	
	public String getGreeting();
	public String getFullName(FirstMicroServiceResponseModel requestModel);

}
