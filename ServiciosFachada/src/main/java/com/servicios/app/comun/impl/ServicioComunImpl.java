package com.servicios.app.comun.impl;

import java.awt.image.RescaleOp;

import com.core.app.servicios.ExcepcionServicio;
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
import com.servicios.app.comun.ServicioComun;


import com.servicios.excepcion.ExcepcionServicioFachada;

import com.servicios.negocio.app.comun.ValidarCadenasSimilares;
import com.servicios.negocio.app.comun.ValidarCaracteresPermitidos;
import com.servicios.negocio.app.comun.ValidarEmail;
import com.servicios.negocio.app.comun.ValidarFecha;
import com.servicios.negocio.app.comun.Codificar;
import com.servicios.negocio.app.comun.Decodificar;
import com.servicios.negocio.app.comun.ModificarTamanoImagen;
import com.servicios.negocio.app.comun.RegistrarImagen;

public class ServicioComunImpl implements ServicioComun{
	
	private ValidarFecha validarFecha;
	private ValidarCadenasSimilares validarCadenasSimilares;
	private ValidarCaracteresPermitidos validarCaracteresPermitidos;
	private ValidarEmail validarEmail;
	private Codificar codificarAES;
	private Decodificar decodificarAES;
	
	
	private ModificarTamanoImagen modificarTamanoImagen;
	
	private RegistrarImagen registrarImagen;	
	

	public RegistrarImagen getRegistrarImagen() {
		return registrarImagen;
	}

	public void setRegistrarImagen(RegistrarImagen registrarImagen) {
		this.registrarImagen = registrarImagen;
	}

	public ModificarTamanoImagen getModificarTamanoImagen() {
		return modificarTamanoImagen;
	}

	public void setModificarTamanoImagen(ModificarTamanoImagen modificarTamanoImagen) {
		this.modificarTamanoImagen = modificarTamanoImagen;
	}

	public ValidarEmail getValidarEmail() {
		return validarEmail;
	}
	
	public void setValidarEmail(ValidarEmail validarEmail) {
		this.validarEmail = validarEmail;
	}
	
	public ValidarCaracteresPermitidos getValidarCaracteresPermitidos() {
		return validarCaracteresPermitidos;
	}
	
	public void setValidarCaracteresPermitidos(
			ValidarCaracteresPermitidos validarCaracteresPermitidos) {
		this.validarCaracteresPermitidos = validarCaracteresPermitidos;
	}
	
	
	
	public ValidarFecha getValidarFecha() {
		return validarFecha;
	}

	public void setValidarFecha(ValidarFecha validarFecha) {
		this.validarFecha = validarFecha;
	}	
	
	public ValidarCadenasSimilares getValidarCadenasSimilares() {
		return validarCadenasSimilares;
	}
	public void setValidarCadenasSimilares(
			ValidarCadenasSimilares validarCadenasSimilares) {
		this.validarCadenasSimilares = validarCadenasSimilares;
	}
	
	public Codificar getCodificarAES() {
		return codificarAES;
	}

	public void setCodificarAES(Codificar codificarAES) {
		this.codificarAES = codificarAES;
	}

	public Decodificar getDecodificarAES() {
		return decodificarAES;
	}

	public void setDecodificarAES(Decodificar decodificarAES) {
		this.decodificarAES = decodificarAES;
	}

	@Override
	public ValidarFechaRespuesta validarFecha(ValidarFechaPeticion peticion)
			throws ExcepcionServicioFachada {
		
		ValidarFechaRespuesta respuesta=null;
		
		try{
		
			respuesta = validarFecha.ejecutar(peticion);
		
		}catch(ExcepcionServicio e){
			throw new ExcepcionServicioFachada("Error al ejecutar el metodo validarEfectivoCobro de la fachada Comun.-"+e.getMessage(),e.getCause());
		}
		return respuesta;
	}
		
	@Override
	public ValidarCadenasSimilaresRespuesta validarCadenasSimilares(
			ValidarCadenasSimilaresPeticion peticion)
			throws ExcepcionServicioFachada {
		ValidarCadenasSimilaresRespuesta respuesta = null;
		try {
			respuesta = validarCadenasSimilares.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada("Error al ejecutar el servicio ValidarCadenasSimilares de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
		return respuesta;
	}
	
	@Override
	public ValidarCaracteresPermitidosRespuesta validarCaracteresPermitidos(
			ValidarCaracteresPermitidosPeticion peticion)
			throws ExcepcionServicioFachada {
		
		ValidarCaracteresPermitidosRespuesta respuesta = null;
		try {
			respuesta = validarCaracteresPermitidos.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada("Error al ejecutar el servicio ValidarCaracteresPermitidos de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
		
		return respuesta;
	}
	@Override
	public ValidarEmailRespuesta validarEmail(ValidarEmailPeticion peticion)
			throws ExcepcionServicioFachada {
		// TODO Auto-generated method stub
		try {
			
			return validarEmail.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar la operacion validarEmail de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
	}
	
	
	@Override
	public ModificarTamanoImagenRespuesta ModificarTamanoImagen(ModificarTamanoImagenPeticion peticion)
			throws ExcepcionServicioFachada {
		try {
			
			return modificarTamanoImagen.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar la operacion modificarTamanoImagen de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public void RegistrarImagen(RegistrarImagenPeticion peticion) throws ExcepcionServicioFachada {
		
		try {
			
			registrarImagen.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar la operacion modificarTamanoImagen de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public CodificarRespuesta codificarAES(CodificarPeticion peticion) throws ExcepcionServicioFachada {
		try {
			
			return codificarAES.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar la operacion codificarAES de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
	}

	@Override
	public CodificarRespuesta decodificarAES(CodificarPeticion peticion) throws ExcepcionServicioFachada {
		try {
			
			return decodificarAES.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			
			throw new ExcepcionServicioFachada("Error al ejecutar la operacion decodificarAES de la fachada Comun.-" + e.getMessage(), e.getCause());
		}
	}
	
	
	
}
