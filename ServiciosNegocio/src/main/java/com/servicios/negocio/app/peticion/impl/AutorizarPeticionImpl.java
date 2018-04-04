package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.ObtenerEntidadesPorCriterioPeticion;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesRespuesta;
import com.servicios.datos.app.peticion.ObtenerPeticionesPorCriterio;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;
import com.servicios.negocio.app.peticion.AutorizarPeticion;

public class AutorizarPeticionImpl implements AutorizarPeticion {

	private ServicioEntidad actualizarEntidad;
	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio;
	private AgregarProcesoPeticion agregarProcesoPeticion;
	private ServicioObtenerEntidad obtenerEntidad;
	
	
	@Override
	public AutorizarPeticionesRespuesta ejecutar(AutorizarPeticionesPeticion peticion) throws ExcepcionServicioNegocio {
		// TODO Auto-generated method stub
		
		AutorizarPeticionesRespuesta respuesta = null;
		
		try{
			
			int idProcesoPeticionSiguiente = verficarStatus(peticion.getIdAreaPresupuesto(), peticion.getIdUsuarioPresupuesto());
			
			//ObtenerEntidadesPorCriterioPeticion entidadesPorCriterioPeticion = new ObtenerEntidadesPorCriterioPeticion();
			
			//Se obtiene la entidad
			//Peticion entidadPeticion = new Peticion();			
			//entidadPeticion.setId(peticion.getIdPeticion());
			
			//entidadesPorCriterioPeticion.setClase(Peticion.class);
			//entidadesPorCriterioPeticion.setEntidad(entidadPeticion);
			
			//entidadPeticion = (Peticion) obtenerEntidadesPorCriterio.ejecutar(entidadesPorCriterioPeticion).getListaEntidades().get(0);			
			
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(), Peticion.class);
			//Se actualizan las informacion de la peticion
			entidadPeticion.setArea((Area)obtenerEntidad(peticion.getIdAreaAsingada(),Area.class));
			
			entidadPeticion.setAreaPresupuesto((Area)obtenerEntidad(peticion.getIdAreaPresupuesto(),Area.class));
			
			//entidadPeticion.setAreaAdicional((Area)obtenerEntidad(peticion.getIdAreaAdicional(),Area.class));			
			
			entidadPeticion.setUsuarioAsignado((UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioAsignado(),UsuarioSistema.class));
			
			entidadPeticion.setUsuarioPresupuesto((UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioPresupuesto(),UsuarioSistema.class));
			
			entidadPeticion.setPrioridad((Prioridad)obtenerEntidad(peticion.getIdPrioridad(),Prioridad.class));
			
			//entidadPeticion.setUsuarioAdicional((UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioAreaAdicional(),UsuarioSistema.class));			
			
			StatusPeticion statusPeticion = (StatusPeticion)obtenerEntidad(idProcesoPeticionSiguiente,StatusPeticion.class);			
			
			entidadPeticion.setStatusPeticion(statusPeticion);
			
			
			System.out.println("**idProcesoPeticionSiguiente -> "  + idProcesoPeticionSiguiente);
			entidadPeticion.setRequierePresupuesto(idProcesoPeticionSiguiente == 3 ? 0 : 1 );
			
			
			// Se actualiza la peticion
			PeticionEntidad actualizarEntidadPeticion = new PeticionEntidad();
			actualizarEntidadPeticion.setEntidad(entidadPeticion);
			actualizarEntidad.ejecutar(actualizarEntidadPeticion );
			
			//crearEntidad.ejecutar(actualizarEntidadPeticion );
			respuesta = new AutorizarPeticionesRespuesta();
			
			
			/*UsuarioSistema entidadUsuario = (UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioSistema(),UsuarioSistema.class);			
			StatusPeticion entidadStatusPeticion = (StatusPeticion)obtenerEntidad(idProcesoPeticion,StatusPeticion.class);			
			
			ProcesoPeticion entidadProcesoPeticion = new ProcesoPeticion();
			entidadProcesoPeticion.setPeticion(entidadPeticion);
			entidadProcesoPeticion.setComentarios(peticion.getComentarios());
			entidadProcesoPeticion.setFecha(new Date());
			entidadProcesoPeticion.setUsuarioSistema(entidadUsuario);
			entidadProcesoPeticion.setStatusPeticion(entidadStatusPeticion);

			
			PeticionEntidad crearEntidadPeticion = new PeticionEntidad();
			crearEntidadPeticion.setEntidad(entidadProcesoPeticion);
			crearEntidad.ejecutar(crearEntidadPeticion );
			
			respuesta.setFechaAutorizacion(entidadProcesoPeticion.getFecha());
			*/
			
			//Si es una validacion se crea el registro nuevo en procesoPeticion
			AgregarProcesoPeticionRespuesta procesoPeticionesRespuesta = null;
			AgregarProcesoPeticionesPeticion procesoPeticionesPeticion = new AgregarProcesoPeticionesPeticion();
			procesoPeticionesPeticion.setIdPeticion(peticion.getIdPeticion());
			procesoPeticionesPeticion.setIdStatusPeticion(idProcesoPeticionSiguiente);
			procesoPeticionesPeticion.setFecha(new Date());
			procesoPeticionesPeticion.setIdUsuarioSistema(peticion.getIdUsuarioSistema());
			procesoPeticionesPeticion.setComentarios(peticion.getComentarios());
			procesoPeticionesPeticion.setMovimiento("AUTORIZACION");
				
			procesoPeticionesRespuesta = agregarProcesoPeticion.ejecutar(procesoPeticionesPeticion);
				
			respuesta.setFechaAutorizacion(procesoPeticionesRespuesta.getFecha());			
			
		}catch(Exception e)
		{			
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio AutorizarPeticion.-" + e.getMessage(), e);			
		}
				
		
		return respuesta;
	}


	public ServicioEntidad getActualizarEntidad() {
		return actualizarEntidad;
	}


	public void setActualizarEntidad(ServicioEntidad actualizarEntidad) {
		this.actualizarEntidad = actualizarEntidad;
	}


	public ServicioEntidad getCrearEntidad() {
		return crearEntidad;
	}


	public void setCrearEntidad(ServicioEntidad crearEntidad) {
		this.crearEntidad = crearEntidad;
	}

	public ServicioObtenerEntidadesPorCriterio getObtenerEntidadesPorCriterio() {
		return obtenerEntidadesPorCriterio;
	}


	public void setObtenerEntidadesPorCriterio(ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio) {
		this.obtenerEntidadesPorCriterio = obtenerEntidadesPorCriterio;
	}


	public AgregarProcesoPeticion getAgregarProcesoPeticion() {
		return agregarProcesoPeticion;
	}


	public void setAgregarProcesoPeticion(AgregarProcesoPeticion agregarProcesoPeticion) {
		this.agregarProcesoPeticion = agregarProcesoPeticion;
	}

	
	private Integer verficarStatus(Integer idAreaPresupesto, Integer IdUsuarioPresupuesto){
		Integer status = 3;
		
		if(idAreaPresupesto > 0 && IdUsuarioPresupuesto > 0 ){
			status = 4;
		}
		
		return status;
	}
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
	}


	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}


	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}
	
	
}
