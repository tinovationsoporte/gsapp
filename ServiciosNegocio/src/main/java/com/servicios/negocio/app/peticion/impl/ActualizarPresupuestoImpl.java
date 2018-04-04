package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Presupuesto;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.StatusPresupuesto;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.peticion.ActualizarPresupuestoRespuesta;
import com.objetos.transf.datos.app.presupuesto.ActualizarPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.ActualizarPresupuesto;

public class ActualizarPresupuestoImpl implements ActualizarPresupuesto {

	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	private ServicioEntidad actualizarEntidad;
	
	@Override
	public ActualizarPresupuestoRespuesta ejecutar(ActualizarPresupuestoPeticion peticion) throws ExcepcionServicio {
		
		ActualizarPresupuestoRespuesta actualizarPresupuestoRespuesta = new ActualizarPresupuestoRespuesta();
		
		try {
			
			//Se actualiza la peticion
			StatusPeticion entidadStatusPeticion = (StatusPeticion)obtenerEntidad(peticion.getIdStatusPeticion(), StatusPeticion.class);
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(),Peticion.class);			
			entidadPeticion.setStatusPeticion(entidadStatusPeticion);					
			
			/*PeticionEntidad peticionEntidad = new PeticionEntidad();
			peticionEntidad.setEntidad(entidadPeticion);			
			actualizarEntidad.ejecutar(peticionEntidad);*/
			
			
			
			Presupuesto entidadPresupuesto = (Presupuesto) obtenerEntidad(peticion.getIdPresupuesto(), Presupuesto.class);
			StatusPresupuesto entidadStatusPresupuesto = (StatusPresupuesto)obtenerEntidad(peticion.getIdStatusPresupuesto(), StatusPresupuesto.class);
			entidadPresupuesto.setStatusPresupuesto(entidadStatusPresupuesto);
			
			entidadPeticion.setPresupuesto(entidadPresupuesto);
			
			PeticionEntidad peticionEntidad = new PeticionEntidad();
			peticionEntidad.setEntidad(entidadPeticion);			
			actualizarEntidad.ejecutar(peticionEntidad);
			
			
			//peticionEntidad.setEntidad(entidadPresupuesto);			
			//actualizarEntidad.ejecutar(peticionEntidad);
			
			
			UsuarioSistema usuario = (UsuarioSistema)obtenerEntidad(peticion.getIdUsuario(), UsuarioSistema.class);
			
			
			//Se crea un movimiento del proceso
			ProcesoPeticion entidadProcesoPeticion = new ProcesoPeticion();
			entidadProcesoPeticion.setComentarios(peticion.getComentarios());
			entidadProcesoPeticion.setFecha(new Date());
			entidadProcesoPeticion.setMovimiento(peticion.getMovimiento());
			entidadProcesoPeticion.setPeticion(entidadPeticion);
			entidadProcesoPeticion.setUsuarioSistema(usuario);
			entidadProcesoPeticion.setStatusPeticion(entidadStatusPeticion);
						
			peticionEntidad.setEntidad(entidadProcesoPeticion);			
			crearEntidad.ejecutar(peticionEntidad);		
			
			actualizarPresupuestoRespuesta.setIdPeticion(entidadPeticion.getIdPeticion());
			actualizarPresupuestoRespuesta.setIdProcesoPeticion(entidadProcesoPeticion.getIdProcesoPeticion());
			actualizarPresupuestoRespuesta.setIdStatusPeticion(entidadStatusPeticion.getIdStatusPeticion());
			actualizarPresupuestoRespuesta.setMovimiento(entidadProcesoPeticion.getMovimiento());
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcepcionServicioNegocio("Ocurrio un error al ejecutar el servicio ActualizarPresupuesto", e.getCause());		
		}		
	
		
		
		return actualizarPresupuestoRespuesta;
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
