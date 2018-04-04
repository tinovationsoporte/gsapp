package com.objetos.transf.datos.app.peticion;

import java.util.Date;
import java.util.List;

import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;

public class AgregarProcesoPeticionesPeticion {
	
	
	private Integer idStatusPeticion;
	private Integer idPeticion;
	private Date fecha;
	private Integer idUsuarioSistema;
	private String comentarios;
	private String movimiento;
	private List<Archivo> listaArchivos;
	
	
	
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}
	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	
	
}
