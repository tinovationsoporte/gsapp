/*
 * Si no se envia el modelo con el catalogo, el servicio ignora la busqueda en todo el modelo
 * y unicamente compara las cadenas que se envien en la peticion.
 * 
 * */

package com.objetos.transf.datos.app.comun;

import javax.faces.model.ListDataModel;

public class ValidarCadenasSimilaresPeticion {
	
	private String cadenaExistente;
	private String cadenaNueva;
	private ListDataModel catalogo;
	private int umbral;
	
	public int getUmbral() {
		return umbral;
	}

	public void setUmbral(int umbral) {
		this.umbral = umbral;
	}

	public String getCadenaExistente() {
		return cadenaExistente;
	}
	
	public void setCadenaExistente(String cadenaExistente) {
		this.cadenaExistente = cadenaExistente;
	}
	
	public String getCadenaNueva() {
		return cadenaNueva;
	}
	
	public void setCadenaNueva(String cadenaNueva) {
		this.cadenaNueva = cadenaNueva;
	}

	public ListDataModel getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(ListDataModel catalogo) {
		this.catalogo = catalogo;
	}
	
}
