package com.objetos.transf.datos.app.peticion;

public class AsignarOperadorPeticion {

	private Integer idUsuarioOperador;
	private Integer idAreaOperador;
	private Integer idPeticion;
	private Integer idUsuarioSistema;
	private String comentarios;
	private Integer requiereEvidencia;
	
	
	public Integer getRequiereEvidencia() {
		return requiereEvidencia;
	}
	public void setRequiereEvidencia(Integer requiereEvidencia) {
		this.requiereEvidencia = requiereEvidencia;
	}
	public Integer getIdAreaOperador() {
		return idAreaOperador;
	}
	public void setIdAreaOperador(Integer idAreaOperador) {
		this.idAreaOperador = idAreaOperador;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
	public Integer getIdUsuarioOperador() {
		return idUsuarioOperador;
	}
	public void setIdUsuarioOperador(Integer idUsuarioOperador) {
		this.idUsuarioOperador = idUsuarioOperador;
	}
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	
	
	
}
