package com.objetos.transf.datos.app.peticion;

import java.util.Date;
import java.util.List;

import com.modelo.datos.app.Archivo;

public class AgregarMovimientoGestionPeticion {

	
	private Integer idPeticion;
	private List<Archivo> listaArchivos;
	private Date fecha;
	private String comentario;
	private Integer idiUsuarioSistema;
	private Integer idStatusActualPeticion;
	private String movimiento;
	private String descripcionEntrega;
	
	
	public String getDescripcionEntrega() {
		return descripcionEntrega;
	}
	public void setDescripcionEntrega(String descripcionEntrega) {
		this.descripcionEntrega = descripcionEntrega;
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
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}
	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Integer getIdUsuarioSistema() {
		return idiUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idiUsuarioSistema) {
		this.idiUsuarioSistema = idiUsuarioSistema;
	}
	public Integer getIdStatusActualPeticion() {
		return idStatusActualPeticion;
	}
	public void setIdStatusActualPeticion(Integer idStatusActualPeticion) {
		this.idStatusActualPeticion = idStatusActualPeticion;
	}
	
	
	
	
}
