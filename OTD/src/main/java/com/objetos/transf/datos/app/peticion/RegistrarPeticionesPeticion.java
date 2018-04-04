package com.objetos.transf.datos.app.peticion;

import java.util.List;
import java.util.Map;

import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.UsuarioSistema;

public class RegistrarPeticionesPeticion {

	private String solicitante;
	private Integer idEntidadMpal;
	private String direccion;
	private Integer idTipoMedioContacto;
	private String medioConcato;
	private String descripcion;
	private Integer IdCategoria;
	private Integer idPrioridad;
	private String observaciones;
	private UsuarioSistema usuarioSistema;
	private Integer idArea;
	private Integer idUsuario;
	private Integer requierePresupuesto = 0;
	private List<Archivo> listaArchivos;
	private Integer claveExterna;
	
	
	public Integer getClaveExterna() {
		return claveExterna;
	}
	public void setClaveExterna(Integer claveExterna) {
		this.claveExterna = claveExterna;
	}
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}
	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public Integer getIdEntidadMpal() {
		return idEntidadMpal;
	}
	public void setIdEntidadMpal(Integer idEntidadMpal) {
		this.idEntidadMpal = idEntidadMpal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getIdTipoMedioContacto() {
		return idTipoMedioContacto;
	}
	public void setIdTipoMedioContacto(Integer idTipoMedioContacto) {
		this.idTipoMedioContacto = idTipoMedioContacto;
	}
	public String getMedioConcato() {
		return medioConcato;
	}
	public void setMedioConcato(String medioConcato) {
		this.medioConcato = medioConcato;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getIdCategoria() {
		return IdCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		IdCategoria = idCategoria;
	}
	public Integer getIdPrioridad() {
		return idPrioridad;
	}
	public void setIdPrioridad(Integer idPrioridad) {
		this.idPrioridad = idPrioridad;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}
	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getRequierePresupuesto() {
		return requierePresupuesto;
	}
	public void setRequierePresupuesto(Integer requierePresupuesto) {
		this.requierePresupuesto = requierePresupuesto;
	}	
	
}
