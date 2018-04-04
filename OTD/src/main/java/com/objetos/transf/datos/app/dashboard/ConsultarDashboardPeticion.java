package com.objetos.transf.datos.app.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConsultarDashboardPeticion {

	private Integer idArea;	
	private Integer idRol;	
	private Integer idUsuario;
	
	private String  folio;	
	private Integer idEstatus;
	private Integer idPrioridad;
	private String  fecha;
	
	private Integer idAreaPresupuesto;
	private Boolean esAreaPresupuesto;
	
	
	
	public Boolean getEsAreaPresupuesto() {
		return esAreaPresupuesto;
	}
	public void setEsAreaPresupuesto(Boolean esAreaPresupuesto) {
		this.esAreaPresupuesto = esAreaPresupuesto;
	}
	public Integer getIdAreaPresupuesto() {
		return idAreaPresupuesto;
	}
	public void setIdAreaPresupuesto(Integer idAreaPresupuesto) {
		this.idAreaPresupuesto = idAreaPresupuesto;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public Integer getIdRol() {
		return idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folioABuscar) {
		this.folio = folioABuscar;
	}
	public Integer getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(Integer idEstatus) {
		this.idEstatus = idEstatus;
	}
	public Integer getIdPrioridad() {
		return idPrioridad;
	}
	public void setIdPrioridad(Integer idPrioridad) {
		this.idPrioridad = idPrioridad;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public String toString(){		
		
    	return ToStringBuilder.reflectionToString(this);

	}
	
}
