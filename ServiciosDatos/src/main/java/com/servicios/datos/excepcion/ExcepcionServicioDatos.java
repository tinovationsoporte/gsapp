package com.servicios.datos.excepcion;

import com.core.app.servicios.ExcepcionServicio;

public class ExcepcionServicioDatos extends ExcepcionServicio{

	public ExcepcionServicioDatos(String message, Throwable cause) {
		super(message, cause);
	}	

	public ExcepcionServicioDatos(String message, Throwable cause, String clave) {
		super(message, cause, clave);
	}	
}
