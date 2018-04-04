package com.objetos.transf.datos.app.peticion;

import java.util.Date;

public class AutorizarPeticionesRespuesta {
	
	private Date fechaAutorizacion;
	private String idPeticion;
	
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	public String getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}	

}
