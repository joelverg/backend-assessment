package com.practice.bars.exception;

public class BarsExceptionResponse {

	private String timeStamp;
	private int status;
	private String Error;
	private String message;
	private String path;
	
	public BarsExceptionResponse(String timeStamp, int status, String error, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		Error = error;
		this.message = message;
		this.path = path;
	}
	
	public BarsExceptionResponse() {
		//Empty Constructor
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
