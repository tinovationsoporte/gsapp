package com.core.app.servicios.crud.impl;


import java.sql.SQLException;

import javax.persistence.EntityManager;


import org.hibernate.exception.ConstraintViolationException;

import com.core.app.modelo.Entidad;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;

public class EliminarEntidadImpl implements ServicioEntidad {

	private EntityManager entityManager;
	
	public EliminarEntidadImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecutar( PeticionEntidad peticion)
			throws ExcepcionServicio {
		
		
		try{
			
			Entidad entidadMerged = entityManager.merge(peticion.getEntidad());		
			
			entityManager.remove(entidadMerged );
			entityManager.flush();
			
		}catch(Exception e){			
			
			throw new ExcepcionServicio("Ha ocurrido un error en el servicio EliminarEntidad.- " + e.getMessage() ,e.getCause());		
						
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
