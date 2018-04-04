package com.servicios.negocio.app.sistema.impl;


import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaRespuesta;
import com.servicios.datos.app.sistema.ObtenerUsuarioSistema;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.sistema.ValidarUsuarioSistema;

public class ValidarUsuarioSistemaImpl implements ValidarUsuarioSistema {

	
	private ObtenerUsuarioSistema obtenerUsuarioSistema;
	
	public ValidarUsuarioSistemaRespuesta ejecutar(ValidarUsuarioSistemaPeticion peticion) throws ExcepcionServicioNegocio  {
		
				
		ValidarUsuarioSistemaRespuesta respuesta = new ValidarUsuarioSistemaRespuesta();	
		
		try {
		
			
			respuesta.setUsuarioSistema(obtenerUsuarioSistema.ejecutar(peticion.getObtenerUsuarioSistemaPeticion()).getUsuarioSistema());
			respuesta.setResultado(true);
				
			if( respuesta.getUsuarioSistema() == null ){
				
				respuesta.setResultado(false);			
			}
			
			
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioNegocio("Error al ejecutar el servicio ValidarUsuarioSistemaImpl.-" + e.getMessage(), e.getCause(), e.getClave());
			
		}			
		
		return respuesta;
	}


	public ObtenerUsuarioSistema getObtenerUsuarioSistema() {
		return obtenerUsuarioSistema;
	}


	public void setObtenerUsuarioSistema(ObtenerUsuarioSistema obtenerUsuarioSistema) {
		this.obtenerUsuarioSistema = obtenerUsuarioSistema;
	}


	

}
