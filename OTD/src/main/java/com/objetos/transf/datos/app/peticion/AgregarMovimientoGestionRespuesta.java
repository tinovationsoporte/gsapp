package com.objetos.transf.datos.app.peticion;

import java.util.Date;

public class AgregarMovimientoGestionRespuesta {

	private Integer idProcesoPeticion;
	private String movimiento;
	private Date fecha;
	private Integer idStatusActualPeticion;
	
	
	
	public Integer getIdStatusActualPeticion() {
		return idStatusActualPeticion;
	}
	public void setIdStatusActualPeticion(Integer idStatusActualPeticion) {
		this.idStatusActualPeticion = idStatusActualPeticion;
	}
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}
	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	
	
}
