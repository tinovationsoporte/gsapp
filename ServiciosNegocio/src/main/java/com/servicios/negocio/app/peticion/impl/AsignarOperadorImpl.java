package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorRespuesta;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;
import com.servicios.negocio.app.peticion.AsignarOperador;

public class AsignarOperadorImpl implements AsignarOperador {

	private ServicioEntidad actualizarEntidad;
	private ServicioEntidad crearEntidad;
	private AgregarProcesoPeticion agregarProcesoPeticion;
	private ServicioObtenerEntidad obtenerEntidad;
	
	
	@Override
	public AsignarOperadorRespuesta ejecutar(AsignarOperadorPeticion peticion) throws ExcepcionServicio {
		AsignarOperadorRespuesta respuesta = null;
		
		
		try{
			
			
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(), Peticion.class);
			
			//Se actualizan las informacion de la peticion
			
			entidadPeticion.setAreaAdicional((Area)obtenerEntidad(peticion.getIdAreaOperador(),Area.class));		
			
			entidadPeticion.setUsuarioAdicional((UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioOperador(),UsuarioSistema.class));			
			
			entidadPeticion.setRequiereEvidencia(peticion.getRequiereEvidencia());
			
			StatusPeticion statusPeticion = (StatusPeticion)obtenerEntidad(11,StatusPeticion.class);			
			
			
			
			entidadPeticion.setStatusPeticion(statusPeticion);	
			
			// Se actualiza la peticion
			PeticionEntidad actualizarEntidadPeticion = new PeticionEntidad();
			actualizarEntidadPeticion.setEntidad(entidadPeticion);
			actualizarEntidad.ejecutar(actualizarEntidadPeticion );
			
			
			respuesta = new AsignarOperadorRespuesta();
			
			
						
			//Si es una validacion se crea el registro nuevo en procesoPeticion
			AgregarProcesoPeticionRespuesta procesoPeticionesRespuesta = null;
			AgregarProcesoPeticionesPeticion procesoPeticionesPeticion = new AgregarProcesoPeticionesPeticion();
			procesoPeticionesPeticion.setIdPeticion(peticion.getIdPeticion());
			procesoPeticionesPeticion.setIdStatusPeticion(11);
			procesoPeticionesPeticion.setFecha(new Date());
			procesoPeticionesPeticion.setIdUsuarioSistema(peticion.getIdUsuarioSistema());
			procesoPeticionesPeticion.setComentarios(peticion.getComentarios());
				
			procesoPeticionesRespuesta = agregarProcesoPeticion.ejecutar(procesoPeticionesPeticion);
				
			respuesta.setFecha(procesoPeticionesRespuesta.getFecha());			
			
		}catch(Exception e)
		{			
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio AsignarOperador.-" + e.getMessage(), e);			
		}	
		
		return respuesta;
	}


	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
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


	public AgregarProcesoPeticion getAgregarProcesoPeticion() {
		return agregarProcesoPeticion;
	}


	public void setAgregarProcesoPeticion(AgregarProcesoPeticion agregarProcesoPeticion) {
		this.agregarProcesoPeticion = agregarProcesoPeticion;
	}


	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}


	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}
	
	

}
