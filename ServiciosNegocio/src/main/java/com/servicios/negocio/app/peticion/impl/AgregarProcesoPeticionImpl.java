package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;

public class AgregarProcesoPeticionImpl implements AgregarProcesoPeticion {

	
	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	
	@Override
	public AgregarProcesoPeticionRespuesta ejecutar(AgregarProcesoPeticionesPeticion peticion) throws ExcepcionServicio {
		
		
		
		AgregarProcesoPeticionRespuesta respuesta = null;		
		
		try{
			
			UsuarioSistema entidadUsuario = (UsuarioSistema)obtenerEntidad(peticion.getIdUsuarioSistema(),UsuarioSistema.class);			
			StatusPeticion entidadStatusPeticion = (StatusPeticion)obtenerEntidad(peticion.getIdStatusPeticion(),StatusPeticion.class);			
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(),Peticion.class);
			
			
			ProcesoPeticion entidadProcesoPeticion = new ProcesoPeticion();
			
			entidadProcesoPeticion.setFecha(peticion.getFecha());

			
			entidadProcesoPeticion.setStatusPeticion(entidadStatusPeticion);
			entidadProcesoPeticion.setPeticion(entidadPeticion);
			entidadProcesoPeticion.setUsuarioSistema(entidadUsuario);
			
			entidadProcesoPeticion.setComentarios(peticion.getComentarios());
			entidadProcesoPeticion.setMovimiento(peticion.getMovimiento());
			
			
			if(peticion.getListaArchivos() != null){
				
				for(Archivo archivo : peticion.getListaArchivos()){
					
					entidadProcesoPeticion.addArchivo(archivo);
				}				
			}		
			
			
			PeticionEntidad crearEntidadPeticion = new PeticionEntidad();
			crearEntidadPeticion.setEntidad(entidadProcesoPeticion);
			
			crearEntidad.ejecutar(crearEntidadPeticion);			
			
			respuesta = new AgregarProcesoPeticionRespuesta();
			
			respuesta.setFecha(entidadProcesoPeticion.getFecha());
			//respuesta.setIdPeticion(procesoPeticion.getPeticion().getId());
			respuesta.setIdProcesoPeticion(entidadProcesoPeticion.getIdProcesoPeticion());
			respuesta.setMovimiento(entidadProcesoPeticion.getMovimiento());
			
			
		}catch(Exception e){
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio AgregarProcesoPeticion", e);
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
