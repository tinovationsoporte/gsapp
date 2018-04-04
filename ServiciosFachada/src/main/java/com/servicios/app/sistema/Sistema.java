package com.servicios.app.sistema;


import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarMacPeticion;
import com.objetos.transf.datos.app.sistema.ValidarMacRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.servicios.negocio.ExcepcionServicioNegocio;



public interface Sistema {


	ValidarUsuarioSistemaRespuesta validarUsuarioSistema(ValidarUsuarioSistemaPeticion peticion) throws ExcepcionServicioFachada;
	
	ObtenerUsuarioSistemaRespuesta obtenerUsuarioSistema(ObtenerUsuarioSistemaPeticion peticion) throws ExcepcionServicioFachada;

}
