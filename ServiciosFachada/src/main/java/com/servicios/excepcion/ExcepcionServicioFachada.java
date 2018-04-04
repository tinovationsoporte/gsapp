package com.servicios.excepcion;

import com.core.app.servicios.ExcepcionServicio;

public class ExcepcionServicioFachada extends ExcepcionServicio{

	public ExcepcionServicioFachada(String message, Throwable cause) {
		super(message, cause);
	}	

	
	public ExcepcionServicioFachada(String message, Throwable cause, String clave) {
		super(message, cause, clave);
	}
}
