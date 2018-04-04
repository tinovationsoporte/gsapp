package com.objetos.transf.datos.app.dashboard;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ConsultarResumenDashboardPeticion {

	private Integer idRol;
	private Integer idArea;
	private Integer idAreaPresupuesto;
	private Boolean esAreaPresupuesto;
	
	
	public Integer getIdAreaPresupuesto() {
		return idAreaPresupuesto;
	}
	public void setIdAreaPresupuesto(Integer idAreaPresupuesto) {
		this.idAreaPresupuesto = idAreaPresupuesto;
	}
	public Boolean getEsAreaPresupuesto() {
		return esAreaPresupuesto;
	}
	public void setEsAreaPresupuesto(Boolean esAreaPresupuesto) {
		this.esAreaPresupuesto = esAreaPresupuesto;
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
	

	@Override
	public String toString(){		
		
    	return ToStringBuilder.reflectionToString(this);

	}

	
}
