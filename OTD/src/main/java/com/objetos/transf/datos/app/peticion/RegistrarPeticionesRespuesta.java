package com.objetos.transf.datos.app.peticion;

import java.util.Date;

public class RegistrarPeticionesRespuesta {

	private Integer idPeticion;
	
	private Date fechaCaptura;
	
	public Integer getIdPeticion() {
		return idPeticion;
	}
	
	public String folio;
	
	
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
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
	
	

}
