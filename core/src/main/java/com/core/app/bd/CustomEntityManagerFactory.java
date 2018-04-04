package com.core.app.bd;

//
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContextType;

public class CustomEntityManagerFactory {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		
	}


	public EntityManager getExtendedEntityManagerInstace(){
		
		//if(entityManager == null){
			entityManager =  entityManagerFactory.createEntityManager();
			entityManager.setProperty("TRANSACTION_TYPE",PersistenceContextType.EXTENDED );
			
			
		//}
		
		//entityManager.flush();
			
		return entityManager;
	}	

}
