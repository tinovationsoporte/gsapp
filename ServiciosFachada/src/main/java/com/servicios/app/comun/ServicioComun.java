package com.servicios.app.comun;

import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenPeticion;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenRespuesta;
import com.objetos.transf.datos.app.comun.ObtenerStatusPorCriterioPeticion;
import com.objetos.transf.datos.app.comun.ObtenerUsuarioSistema_PermisoPorCriterioPeticion;
import com.objetos.transf.datos.app.comun.ObtenerUsuarioSistema_PermisoPorCriterioRespuesta;
import com.objetos.transf.datos.app.comun.RegistrarImagenPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresRespuesta;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosPeticion;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosRespuesta;
import com.objetos.transf.datos.app.comun.ValidarEmailPeticion;
import com.objetos.transf.datos.app.comun.ValidarEmailRespuesta;
import com.objetos.transf.datos.app.comun.ValidarFechaPeticion;
import com.objetos.transf.datos.app.comun.ValidarFechaRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;

public interface ServicioComun {
	public ValidarFechaRespuesta validarFecha
	(ValidarFechaPeticion peticion)throws ExcepcionServicioFachada;
	
	public ValidarCadenasSimilaresRespuesta validarCadenasSimilares
	(ValidarCadenasSimilaresPeticion peticion) throws ExcepcionServicioFachada;
	
	public ValidarCaracteresPermitidosRespuesta validarCaracteresPermitidos
	(ValidarCaracteresPermitidosPeticion peticion) throws ExcepcionServicioFachada;
	
	public ValidarEmailRespuesta validarEmail(ValidarEmailPeticion peticion)
			throws ExcepcionServicioFachada;
	
	public ModificarTamanoImagenRespuesta ModificarTamanoImagen(ModificarTamanoImagenPeticion peticion) 
			throws ExcepcionServicioFachada;
	
	public void RegistrarImagen(RegistrarImagenPeticion peticion) 
			throws ExcepcionServicioFachada;	
	
	
	public CodificarRespuesta codificarAES(CodificarPeticion peticion) 
			throws ExcepcionServicioFachada;
	

	public CodificarRespuesta decodificarAES(CodificarPeticion peticion) 
			throws ExcepcionServicioFachada;
}


