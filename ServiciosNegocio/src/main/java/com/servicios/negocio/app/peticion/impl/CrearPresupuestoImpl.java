package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Presupuesto;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.StatusPresupuesto;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.CrearPresupuesto;

public class CrearPresupuestoImpl implements CrearPresupuesto {

	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	private ServicioEntidad actualizarEntidad;
	
	@Override
	public CrearPresupuestoRespuesta ejecutar(CrearPresupuestoPeticion peticion) throws ExcepcionServicioNegocio {
		 
		CrearPresupuestoRespuesta crearPresupuestoRespuesta = new CrearPresupuestoRespuesta();
		
		try {
			
			//Se actualiza la peticion
			StatusPeticion entidadStatusPeticion = (StatusPeticion)obtenerEntidad(6,StatusPeticion.class);
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(),Peticion.class);			
			entidadPeticion.setStatusPeticion(entidadStatusPeticion);					
			
			
			PeticionEntidad peticionEntidad = new PeticionEntidad();
			peticionEntidad.setEntidad(entidadPeticion);			
			actualizarEntidad.ejecutar(peticionEntidad);
			
			UsuarioSistema usuario = (UsuarioSistema)obtenerEntidad(peticion.getIdUsuario(), UsuarioSistema.class);
			
			//Se crea un movimiento del proceso
			ProcesoPeticion entidadProcesoPeticion = new ProcesoPeticion();
			entidadProcesoPeticion.setComentarios("Se crea el presupuesto");
			entidadProcesoPeticion.setFecha(new Date());
			entidadProcesoPeticion.setMovimiento("PRESUPUESTO");
			entidadProcesoPeticion.setPeticion(entidadPeticion);
			entidadProcesoPeticion.setUsuarioSistema(usuario);
			entidadProcesoPeticion.setStatusPeticion(entidadStatusPeticion);
			
			
			//entidadPeticion.addProcesoPeticion(entidadProcesoPeticion);
						
			peticionEntidad.setEntidad(entidadProcesoPeticion);			
			crearEntidad.ejecutar(peticionEntidad);		
			
			
						
			//Se crea el presupuesto			
			Presupuesto entidadPresupuesto = new Presupuesto();
			StatusPresupuesto statusPresupuesto = (StatusPresupuesto)obtenerEntidad(peticion.getIdStatusPresupuesto(),StatusPresupuesto.class);			
			entidadPresupuesto.setPeticion(entidadPeticion);
			entidadPresupuesto.setUsuarioSistema(usuario);
			entidadPresupuesto.setFechaCaptura(peticion.getFechaCaptura());
			entidadPresupuesto.setStatusPresupuesto(statusPresupuesto);
			
			
			//entidadPeticion.setPresupuesto(entidadPresupuesto);
			
			peticionEntidad.setEntidad(entidadPresupuesto);
			crearEntidad.ejecutar(peticionEntidad);
			
			
			//PeticionEntidad peticionEntidad = new PeticionEntidad();
			//peticionEntidad.setEntidad(entidadPeticion);			
			//actualizarEntidad.ejecutar(peticionEntidad);
			
			
			crearPresupuestoRespuesta.setIdPresupuesto(entidadPresupuesto.getIdPresupuesto());
			crearPresupuestoRespuesta.setIdStatusPresupuesto(entidadPresupuesto.getStatusPresupuesto().getIdtcgeneral());
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcepcionServicioNegocio("Ocurrio un error al ejecutar el servicio CrearPresupuesto", e.getCause());		
		}		
		
		
		//PeticionEntidad peticionEntidad = new PeticionEntidad();		
		//peticionEntidad.setEntidad(entidadPeticion);		
		
		
		return crearPresupuestoRespuesta;

	}
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
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

	public ServicioEntidad getActualizarEntidad() {
		return actualizarEntidad;
	}

	public void setActualizarEntidad(ServicioEntidad actualizarEntidad) {
		this.actualizarEntidad = actualizarEntidad;
	}
	
	

}
