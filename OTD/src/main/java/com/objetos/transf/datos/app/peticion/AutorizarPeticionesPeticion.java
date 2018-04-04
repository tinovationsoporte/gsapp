package com.objetos.transf.datos.app.peticion;

public class AutorizarPeticionesPeticion {

	
	private Integer idPeticion;
	private Integer idUsuarioSistema;
	private Integer idUsuarioAsignado;
	private Integer idUsuarioPresupuesto;
	//private Integer idUsuarioAreaAdicional;
	private Integer idAreaAsingada;
	private Integer idAreaPresupuesto;
	//private Integer idAreaAdicional;
	private String comentarios;
	private Boolean reasignar = false;
	
	private Integer idPrioridad;
		
	public Integer getIdPrioridad() {
		return idPrioridad;
	}
	public void setIdPrioridad(Integer idPrioridad) {
		this.idPrioridad = idPrioridad;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Integer getIdUsuarioAsignado() {
		return idUsuarioAsignado;
	}
	public void setIdUsuarioAsignado(Integer idUsuarioAsignado) {
		this.idUsuarioAsignado = idUsuarioAsignado;
	}
	public Integer getIdUsuarioPresupuesto() {
		return idUsuarioPresupuesto;
	}
	public void setIdUsuarioPresupuesto(Integer idUsuarioPresupuesto) {
		this.idUsuarioPresupuesto = idUsuarioPresupuesto;
	}
	/*public Integer getIdUsuarioAreaAdicional() {
		return idUsuarioAreaAdicional;
	}
	public void setIdUsuarioAreaAdicional(Integer idUsuarioAreaAdicional) {
		this.idUsuarioAreaAdicional = idUsuarioAreaAdicional;
	}*/
	public Integer getIdAreaAsingada() {
		return idAreaAsingada;
	}
	public void setIdAreaAsingada(Integer idAreaAsingada) {
		this.idAreaAsingada = idAreaAsingada;
	}
	public Integer getIdAreaPresupuesto() {
		return idAreaPresupuesto;
	}
	public void setIdAreaPresupuesto(Integer idAreaPresupuesto) {
		this.idAreaPresupuesto = idAreaPresupuesto;
	}
	/*public Integer getIdAreaAdicional() {
		return idAreaAdicional;
	}
	public void setIdAreaAdicional(Integer idAreaAdicional) {
		this.idAreaAdicional = idAreaAdicional;
	}*/
	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
	public Boolean getReasignar() {
		return reasignar;
	}
	public void setReasignar(Boolean reasignar) {
		this.reasignar = reasignar;
	}
	
	
}
