package com.objetos.transf.datos.app.presupuesto;

import java.util.Date;

public class CrearPresupuestoPeticion {
	
	private Integer idPeticion;
	private Date fechaCaptura;
	private Integer idUsuario;
	private Integer idStatusPresupuesto;
	
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Date getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdStatusPresupuesto() {
		return idStatusPresupuesto;
	}
	public void setIdStatusPresupuesto(Integer idStatusPresupuesto) {
		this.idStatusPresupuesto = idStatusPresupuesto;
	}
	
	
	
	
}
