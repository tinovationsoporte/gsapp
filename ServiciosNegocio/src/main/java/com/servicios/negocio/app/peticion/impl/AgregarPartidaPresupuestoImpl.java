package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.DetallePresupuesto;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Presupuesto;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.presupuesto.AgregarPartidaPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.AgregarPartidaPresupuestoRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.AgregarPartidaPresupuesto;

public class AgregarPartidaPresupuestoImpl implements AgregarPartidaPresupuesto {

	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	
	@Override
	public AgregarPartidaPresupuestoRespuesta ejecutar(AgregarPartidaPresupuestoPeticion peticion) throws ExcepcionServicioNegocio {	
		
		AgregarPartidaPresupuestoRespuesta respuesta = null;
		
		try{
		
			Presupuesto entidadPresupuesto = (Presupuesto)obtenerEntidad(peticion.getIdPresupuesto(), Presupuesto.class);
			UsuarioSistema entidadUsuario = (UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioSistema(),UsuarioSistema.class);
			
			DetallePresupuesto detallePresupuestoEntidad = new DetallePresupuesto();
			detallePresupuestoEntidad.setFecha(new Date());
			detallePresupuestoEntidad.setConcepto(peticion.getConcepto());
			detallePresupuestoEntidad.setCantidad(peticion.getCantidad());
			detallePresupuestoEntidad.setPu(peticion.getPu());
			
			detallePresupuestoEntidad.setUsuario(entidadUsuario);
			detallePresupuestoEntidad.setPresupuesto(entidadPresupuesto);
			 
			PeticionEntidad peticionEntidad = new PeticionEntidad();		
			peticionEntidad.setEntidad(detallePresupuestoEntidad);				
			
			crearEntidad.ejecutar(peticionEntidad);
			
			respuesta = new AgregarPartidaPresupuestoRespuesta();
			
			respuesta.setIdPartida(detallePresupuestoEntidad.getIdDetallePresupuesto());
			respuesta.setIdPresupuesto(entidadPresupuesto.getIdPresupuesto());
			
		}catch(Exception e){
			
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio AgregarPartidaPresupuesto.", e.getCause());
		}
		
		return respuesta;
	}

	public ServicioEntidad getCrearEntidad() {
		return crearEntidad;
	}

	public void setCrearEntidad(ServicioEntidad crearEntidad) {
		this.crearEntidad = crearEntidad;
	}

	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}

	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
	}
	

}
