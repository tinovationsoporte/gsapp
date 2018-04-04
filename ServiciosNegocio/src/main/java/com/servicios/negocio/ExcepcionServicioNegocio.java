package com.servicios.negocio;

import com.core.app.servicios.ExcepcionServicio;

public class ExcepcionServicioNegocio extends ExcepcionServicio{

	public ExcepcionServicioNegocio(String message, Throwable cause) {
		super(message, cause);
	}	

	public ExcepcionServicioNegocio(String message, Throwable cause, String clave) {
		super(message, cause, clave);
	}
}