package com.core.app.otd;

import com.core.app.modelo.Entidad;

public class RespuestaEntidad <E extends Entidad> {

	
	private E entidad;

	public E getEntidad() {
		return entidad;
	}

	public void setEntidad(E entidad) {
		this.entidad = entidad;
	}
	

}
