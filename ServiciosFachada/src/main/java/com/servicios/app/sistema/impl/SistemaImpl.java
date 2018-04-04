package com.servicios.app.sistema.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarMacPeticion;
import com.objetos.transf.datos.app.sistema.ValidarMacRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaRespuesta;
import com.servicios.app.sistema.Sistema;
import com.servicios.datos.app.sistema.ObtenerUsuarioSistema;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.sistema.ValidarUsuarioSistema;

public class SistemaImpl implements Sistema{

	
	private ObtenerUsuarioSistema obtenerUsuarioSistema;
	private ValidarUsuarioSistema validarUsuarioSistema;
	
	
	public ValidarUsuarioSistemaRespuesta validarUsuarioSistema(ValidarUsuarioSistemaPeticion peticion) throws ExcepcionServicioFachada {
		
		ValidarUsuarioSistemaRespuesta respuesta = null;
		
		
		
		try {
			
			//Integer.valueOf("X");
			
			//respuesta.setResultado(false);
			
			respuesta = validarUsuarioSistema.ejecutar(peticion);
		
			//throw new ExcepcionServicio("ERROR PROVOCADO PARA PROBAR EL HANDLER ... ",null );
			
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar el servicio de fachada Sistema.-" + e.getMessage(), e.getCause(), e.getClave());
			
		} 
		
		return respuesta;
	}
	
		
	public ObtenerUsuarioSistemaRespuesta obtenerUsuarioSistema(
			ObtenerUsuarioSistemaPeticion peticion) {
		
		return null;
	}

	public ObtenerUsuarioSistema getObtenerUsuarioSistema() {
		return obtenerUsuarioSistema;
	}

	public void setObtenerUsuarioSistema(ObtenerUsuarioSistema obtenerUsuarioSistema) {
		this.obtenerUsuarioSistema = obtenerUsuarioSistema;
	}

	public ValidarUsuarioSistema getValidarUsuarioSistema() {
		return validarUsuarioSistema;
	}

	public void setValidarUsuarioSistema(ValidarUsuarioSistema validarUsuarioSistema) {
		this.validarUsuarioSistema = validarUsuarioSistema;
	}
}
