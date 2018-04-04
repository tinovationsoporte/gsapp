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

public class ActualizarEntidadImpl implements ServicioEntidad {

	private EntityManager entityManager;
	
	public ActualizarEntidadImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar(
			PeticionEntidad peticion)
			throws ExcepcionServicio {
		
		try{
			
			//entityManager = getEntityManager();
			
			Entidad entidadMerged = entityManager.merge(peticion.getEntidad());
			
			//EntityTransaction et= entityManager.getTransaction();		
			//et.begin();				
			
			entityManager.persist(entidadMerged );
			entityManager.flush();
			//et.commit();
			
			entityManager.detach(peticion.getEntidad());		
		
		}catch(Throwable t){
			
			throw new ExcepcionServicio("Ha ocurrido un error en el servicio ActualizarEntidad.- " + t.getMessage(), t.getCause());
		}
		
				

	}

    public void setEntityManager(EntityManager entityManager){
    	this.entityManager = entityManager;
    }

    public EntityManager getEntityManager(){
    	return this.entityManager;
    }
	


	/*private EntityManager getEntityManager(){
		
		EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MD");
	    EntityManager em = emf.createEntityManager();      
	    
	      return em;
	}*/
}
