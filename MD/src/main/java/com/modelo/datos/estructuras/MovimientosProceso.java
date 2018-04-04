package com.modelo.datos.estructuras;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MovimientosProceso {
	
	
	
	private Integer idPeticion;
	private Integer idProcesoPeticion;
	private String nombreUsuario;
	private Date fecha;
	private String movimiento;
	private String comentarios;
	private Integer idStatusPeticion;
	private boolean nuevo;
	
	
	
	public boolean getNuevo() {
		return nuevo;
	}
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
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
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}
	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
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
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	@Override
	public String toString() {
		return  ToStringBuilder.reflectionToString(this);
	}

}
