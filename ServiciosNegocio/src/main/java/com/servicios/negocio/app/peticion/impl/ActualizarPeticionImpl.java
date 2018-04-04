package com.servicios.negocio.app.peticion.impl;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.TipoMedioContacto;
import com.objetos.transf.datos.app.peticion.ActualizarPeticionesPeticion;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.ActualizarPeticion;

public class ActualizarPeticionImpl implements ActualizarPeticion {

	private ServicioEntidad actualizarEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	
	
	@Override
	public void ejecutar(ActualizarPeticionesPeticion _peticion) throws ExcepcionServicio {

		try{
			
			Peticion peticion  = (Peticion) obtenerEntidad(_peticion.getIdPeticion(), Peticion.class);			
			
			if(_peticion.getDescripcionEntrega()!= null && !_peticion.getDescripcionEntrega().isEmpty())
				peticion.setDescripcionEntrega(_peticion.getDescripcionEntrega());
			
			if(_peticion.getClaveExterna()!= null)peticion.setClaveExterna(_peticion.getClaveExterna());
			
			if(_peticion.getIdMedioContacto() != null && _peticion.getIdMedioContacto() != 0){
				TipoMedioContacto tipoMedioContacto = (TipoMedioContacto)obtenerEntidad(_peticion.getIdMedioContacto(), TipoMedioContacto.class);				
				peticion.setTipoMedioContacto(tipoMedioContacto);				
				peticion.setMedioContacto(_peticion.getDescricionMedioContacto());
			}
			
			PeticionEntidad actualizarEntidadPeticion = new PeticionEntidad();
			
			actualizarEntidadPeticion.setEntidad(peticion);
			
			actualizarEntidad.ejecutar(actualizarEntidadPeticion);
			
		}catch(Exception e){
			
			throw new ExcepcionServicioNegocio("Ocurrio un erro al ejecutar el servicio de negocio ActualizarPeticion.-" + e.getMessage(), e);
		}	
		
	}


	


	public ServicioEntidad getActualizarEntidad() {
		return actualizarEntidad;
	}

	public void setActualizarEntidad(ServicioEntidad actualizarEntidad) {
		this.actualizarEntidad = actualizarEntidad;
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
