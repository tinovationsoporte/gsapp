package com.core.app.servicios;

public class ExcepcionServicio extends Exception{

	private String clave;
	
	public ExcepcionServicio(String message, Throwable cause) {
		super(message, cause);	
	}

	public ExcepcionServicio(String message, Throwable cause, String clave) {		
		super(message, cause);
		this.clave = clave;
	}

	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	
	

}
