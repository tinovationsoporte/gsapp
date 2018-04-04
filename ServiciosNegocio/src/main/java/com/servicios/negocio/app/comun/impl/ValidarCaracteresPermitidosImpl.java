/*
 * Valida si un caracter aparace mas de una vez de forma consecutiva en una cadena Ej. 
 * cadenaValidar = 'a.b.c.d', caracteres["."], devuelve 'true' ya que contiene mas de
 * una vez consectutivamente el caracter enviado en la peticion
 * 
 * */

package com.servicios.negocio.app.comun.impl;


import org.springframework.util.StringUtils;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosPeticion;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosRespuesta;
import com.servicios.negocio.app.comun.ValidarCaracteresPermitidos;

public class ValidarCaracteresPermitidosImpl implements
		ValidarCaracteresPermitidos {

	@Override
	public ValidarCaracteresPermitidosRespuesta ejecutar(
			ValidarCaracteresPermitidosPeticion peticion)
			throws ExcepcionServicio {
		
		ValidarCaracteresPermitidosRespuesta respuesta = new ValidarCaracteresPermitidosRespuesta();
		boolean resultado = false;
		int noOcurrencias = 0;
		String cadenaAuxiliar = "";
		int posicionCaracter = -1;
		
		for(int i = 0; i < peticion.getCaracteres().length; i++){
			
			noOcurrencias = StringUtils.countOccurrencesOf( peticion.getCadenaValidar(), peticion.getCaracteres()[i]);
			
			if(noOcurrencias > 1){
				cadenaAuxiliar = StringUtils.trimAllWhitespace(peticion.getCadenaValidar());
				posicionCaracter = cadenaAuxiliar.indexOf(peticion.getCaracteres()[i].toCharArray()[0]);
				resultado = cadenaAuxiliar.charAt(posicionCaracter + 1) == peticion.getCaracteres()[i].toCharArray()[0] ? true : false;
			}
		}
		
		respuesta.setResultadoValidacion(resultado);
		return respuesta;
	}

}
