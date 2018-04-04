package com.objetos.transf.datos.app.peticion;

public class ActualizarPresupuestoRespuesta {
	
	private String movimiento;
	private Integer idProcesoPeticion;
	private Integer idStatusPeticion;
	private Integer idPeticion;
	
	
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}
	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	public Integer getIdStatusPeticion() {
		return idStatusPeticion;
	}
	public void setIdStatusPeticion(Integer idStatusPeticion) {
		this.idStatusPeticion = idStatusPeticion;
	}
	
}
