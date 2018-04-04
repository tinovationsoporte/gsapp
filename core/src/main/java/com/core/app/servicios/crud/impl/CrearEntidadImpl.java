package com.core.app.servicios.crud.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.core.app.modelo.Entidad;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;

public class CrearEntidadImpl implements ServicioEntidad {

	private EntityManager entityManager;
	
	public CrearEntidadImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(
			PeticionEntidad peticion)
			throws ExcepcionServicio {
		try{
			//entityManager = getEntityManager();
			//EntityTransaction et= em.getTransaction();
			//et.begin();		
			entityManager.persist(peticion.getEntidad());
			//et.commit();
			//entityManager.flush();
		    entityManager.detach(peticion.getEntidad());
		}catch(Throwable t){
			
			throw new ExcepcionServicio("Ha ocurrido un error en el servicio CrearEntidad.- " + t.getMessage(), t.getCause());
		}

	}
	
	public void setEntityManager(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager(){
		return this.entityManager;
	}



	

	/*public EntityManager getEntityManager(){
		
		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MD");
	    EntityManager em = emf.createEntityManager();      
	    
	      return em;
	}*/
	
	
	
}
