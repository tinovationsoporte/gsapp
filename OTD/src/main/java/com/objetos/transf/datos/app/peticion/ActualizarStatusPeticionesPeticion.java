package com.objetos.transf.datos.app.peticion;

public class ActualizarStatusPeticionesPeticion {
	
	private Integer idPeticion;
	private Integer idStatusPeticion;
	private Integer idUsuarioSistema;
	private String comentarios;
	private String movimiento;
	
	
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
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Integer getIdStatusPeticion() {
		return idStatusPeticion;
	}
	public void setIdStatusPeticion(Integer idStatusPeticion) {
		this.idStatusPeticion = idStatusPeticion;
	}
	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
	
	

}
