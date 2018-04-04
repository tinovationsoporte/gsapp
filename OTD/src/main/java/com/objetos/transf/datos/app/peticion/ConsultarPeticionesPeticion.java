package com.objetos.transf.datos.app.peticion;

public class ConsultarPeticionesPeticion {

	private Long folio;
	private Integer idEntidadMpal;
	private Integer idArea;
	private Integer idStatus;
	private Integer idPrioridad;
	private String solicitante;
	private String fechaCaptura;
	private Integer idPeticion;
	
	
	
	public Long getFolio() {
		return folio;
	}
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public String getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(String fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	public Integer getIdEntidadMpal() {
		return idEntidadMpal;
	}
	public void setIdEntidadMpal(Integer idEntidadMpal) {
		this.idEntidadMpal = idEntidadMpal;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public Integer getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}
	public Integer getIdPrioridad() {
		return idPrioridad;
	}
	public void setIdPrioridad(Integer idPrioridad) {
		this.idPrioridad = idPrioridad;
	}
	
	
}
