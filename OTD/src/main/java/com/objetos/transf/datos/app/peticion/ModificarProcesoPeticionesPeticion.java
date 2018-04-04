package com.objetos.transf.datos.app.peticion;

import java.util.List;

import com.modelo.datos.app.Archivo;

public class ModificarProcesoPeticionesPeticion {
	
	private Integer idProcesoPeticion;
	private List<Archivo> listaArchivos;
	
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}
	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	
	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}
	
	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
}
