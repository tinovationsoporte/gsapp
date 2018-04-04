package com.objetos.transf.datos.app.presupuesto;

public class ActualizarPresupuestoPeticion {
	
	private String movimiento;
	private Integer idStatusPeticion;
	private Integer idPeticion;
	private Integer idPresupuesto;
	private Integer idUsuario;
	private String comentarios;
	private Integer idStatusPresupuesto;
	
	
	
	public Integer getIdStatusPresupuesto() {
		return idStatusPresupuesto;
	}
	public void setIdStatusPresupuesto(Integer idStatusPresupuesto) {
		this.idStatusPresupuesto = idStatusPresupuesto;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public Integer getIdStatusPeticion() {
		return idStatusPeticion;
	}
	public void setIdStatusPeticion(Integer idStatusPeticion) {
		this.idStatusPeticion = idStatusPeticion;
	}
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}	
	
	
}
