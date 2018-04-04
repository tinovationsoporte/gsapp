package com.objetos.transf.datos.app.catalogo;

import java.util.List;

import javax.faces.model.ListDataModel;

import com.core.app.modelo.Entidad;

public class ConsultarCatalagoRespuesta {

	private ListDataModel<? extends Entidad> listaEntidades;
	private List entidades;

	public ListDataModel<? extends Entidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(ListDataModel<? extends Entidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public List getEntidades() {
		return entidades;
	}

	public void setEntidades(List entidades) {
		this.entidades = entidades;
	}	

	
	
}
