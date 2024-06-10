package com.citiustech.exception;

public class TreatmentDetailsNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9125641720653110840L;
	
	private String message;

	public TreatmentDetailsNotFoundException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return message;
	}

}
