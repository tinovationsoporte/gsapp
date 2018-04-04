package com.core.app.servicios.crud.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.otd.RespuestaEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioObtenerEntidad;

public class ObtenerEntidadImpl implements ServicioObtenerEntidad {

	private EntityManager entityManager;
	
	public ObtenerEntidadImpl() {
		// TODO Auto-generated constructor stub
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public ObtenerEntidadRespuesta ejecutar(ObtenerEntidadPeticion peticion)
			throws ExcepcionServicio {

		ObtenerEntidadRespuesta respuesta = new ObtenerEntidadRespuesta();

		//ntityManager = getEntityManager();
		try{
		
			Entidad entidad = (Entidad)entityManager.find(peticion.getClase(), peticion.getIdEntidad());
			
			//entityManager.detach(entidad);
		
			respuesta.setEntidad(entidad);
		
		}catch(Throwable t){
			
			throw new ExcepcionServicio("Ha ocurrido un error en el servicio ObtnerEntidad.- " + t.getMessage(), t.getCause());
		}
		return respuesta;
	}
	
	
	private EntityManager getEntityManager(){    		
	    return this.entityManager;
	}



	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	



	

}
