package com.objetos.transf.datos.app.reporte;

import java.util.Date;

public class ConsultarTotalReportesPeticion {

	private Date fechaInicial;
	private Date FechaFinal;
	private String idArea;
	private String idUsuario;
	
	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public Date getFechaFinal() {
		return FechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		FechaFinal = fechaFinal;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
}
