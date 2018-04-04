package com.modelo.datos.estructuras;

import java.util.Date;

public class ReportesActividades {

	private Integer idReporte;
	private Date fechaInicial;
	private String nombre;
	private String area;
	private String status;
	private Integer idStatusReporte;
	
	
	
	public Integer getIdStatusReporte() {
		return idStatusReporte;
	}
	public void setIdStatusReporte(Integer idStatusReporte) {
		this.idStatusReporte = idStatusReporte;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getIdReporte() {
		return idReporte;
	}
	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}
	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	 
	 
	
}
