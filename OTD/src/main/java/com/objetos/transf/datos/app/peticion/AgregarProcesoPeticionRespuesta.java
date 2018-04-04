package com.objetos.transf.datos.app.peticion;

import java.util.Date;

public class AgregarProcesoPeticionRespuesta {
	
	private Date fecha;
	private Integer idProcesoPeticion;
	private String movimiento;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}

	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	
	
	
	

}
