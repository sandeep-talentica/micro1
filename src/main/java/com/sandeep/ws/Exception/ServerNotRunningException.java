package com.sandeep.ws.Exception;

public class ServerNotRunningException extends RuntimeException {
	
	public ServerNotRunningException(String message){
		super(message);
	}

}
