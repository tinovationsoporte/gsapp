package com.core.app.servicios.crud.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Distinct;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
//import org.hibernate.hql.ast.tree.OrderByClause;

import com.core.app.bd.CustomEntityManagerFactory;
import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadesPorCriterioPeticion;
import com.core.app.otd.ObtenerEntidadesPorCriterioRespuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;

public class ObtenerEntidadesPorCriterioImpl implements
		ServicioObtenerEntidadesPorCriterio {

	
	
	private  EntityManager entityManager;
	
	private CustomEntityManagerFactory entityManagerFactory;
	
	@Override
	public ObtenerEntidadesPorCriterioRespuesta ejecutar(
			ObtenerEntidadesPorCriterioPeticion peticion)
			throws ExcepcionServicio {

		ObtenerEntidadesPorCriterioRespuesta respuesta = new ObtenerEntidadesPorCriterioRespuesta ();
		
		try{

			//Session session = (Session) getEntityManager().unwrap(Session.class);
			Session session = (Session) getEntityManagerFactory()
										.getExtendedEntityManagerInstace()
										.unwrap(Session.class);
			
			
			Class<?> clase = peticion.getClase();
			
			//Criteria criteria = session.createCriteria( peticion.getClase() ).add(Example.create( peticion.getEntidad() ).enableLike() );		
			
			Example example = Example.create( peticion.getEntidad() );
			
			if(peticion.isEnablesLike()){
				example.enableLike();
			}
			
			
			
			Criteria criteria = session.createCriteria( peticion.getClase() ).add( example );
		
			
			
			Field [] fields = clase.getDeclaredFields();
			
			for(Field f : fields){			
				
					f.setAccessible(true);				
					
					if( !f.getType().isPrimitive() && !f.getType().isInterface()){
						
						if(f.getType().getSuperclass().getCanonicalName().equals("com.core.app.modelo.Entidad") ){
							
							if( camposValidos( f.get(peticion.getEntidad() ) ) ){
								
								/* 
								
								LockMode lm = null; // El de hibernate
																
								if(peticion.getLockMode()!= null){ // El del core
								
									lm = createLockMode(peticion.getLoclMode());
								
								}else{
									
									if(isILock(f.getType())){ 
										
										
										lm = createLockMode( extractEnum ( f.getType() ) );
									}								
								}
								
								*/
								
								
								
								criteria.createCriteria( f.getName() , CriteriaSpecification.LEFT_JOIN ).add( Example.create(f.get(peticion.getEntidad()) ).enableLike() );
							}
						}						
					}				
			}
			
			if(peticion.getOrderByAsc()!= null){
				
				
				String [] sarray =  peticion.getOrderByAsc().split(",");
				
				for(String s: sarray){
					
					criteria.addOrder(Order.asc(s));
				}
				
				//criteria.addOrder(Order.asc(peticion.getOrderByAsc()));		
			
			}else{
				if(peticion.getOrderByDesc()!= null){
					
					String [] sarray = peticion.getOrderByDesc().split(",");
					
					for(String s: sarray){
						criteria.addOrder(Order.desc(s));
					}
					//criteria.addOrder(Order.desc(peticion.getOrderByDesc()));	
				}				
			}
			
			if(peticion.getBetween()!= null){
			
				criteria.add(Restrictions.between(peticion.getBetween().getField()
												, peticion.getBetween().getFrom(), peticion.getBetween().getTo()));
				
				
			}
			
			
			if(peticion.getNullables() != null){
				
				if(peticion.getNullables().length != 0){
					
					String [] nullables = peticion.getNullables(); 
					
					for(String nullField : nullables ){
						
						criteria.add(Restrictions.isNull(nullField));
						
					}
					
					nullables = null;
				}
				
			}
			
			
			if(peticion.getIn() != null){
				
				if(peticion.getIn().getValues().length != 0){
					
					
					if(peticion.getIn().isNotIn()){
						
						criteria.add(Restrictions.not(Restrictions.in(peticion.getIn().getField()
														, peticion.getIn().getValues() ) ) );
						
					}else{
					
						criteria.add(Restrictions.in(peticion.getIn().getField()
								, peticion.getIn().getValues() ) );

					}					
				}
				
			}
			
			
			if(peticion.getDistinct()!= null){
				
				criteria.setProjection( Projections.distinct( Projections.property(peticion.getDistinct() ) ) );
			}
			
			
			
			
			List listaEntidades = criteria.list(); 
			
			/*for(Entidad entidad: (List<Entidad>)listaEntidades){
				session.evict(entidad);
				session.update(entidad);
			}*/
			
			session.setFlushMode(FlushMode.ALWAYS);
			respuesta.setListaEntidades(listaEntidades) ;
			

		
		} catch(Throwable t){
			
			t.printStackTrace();
			
			throw new ExcepcionServicio("Ha ocurrido un error en el servicio ObtenerEntidadesPorCriterio.- " + t.getMessage(), t.getCause());
		
			//t.printStackTrace();
		
		}
			
		
		return respuesta;
	}
	
	private boolean camposValidos(Object o) throws IllegalArgumentException, IllegalAccessException  {		
				
		boolean resultado = false;		

		if(o != null){
			
			for(Field f: o.getClass().getDeclaredFields() ) {		
	
				f.setAccessible(true);				
				
				if( !f.getType().isPrimitive() && !f.getType().isInterface() ){	
					
					if( !f.getType().getSuperclass().getCanonicalName().equals("com.core.app.modelo.Entidad")  ){						
						
						if( f.get( o) != null ){
							resultado = true;
							break;
							
						}
					}				
				}
			}		
		
		}
		return resultado;
	}
	
	
	private LockMode createLockMode(com.core.app.bd.LockMode lockMode){
		LockMode lm = null;
		
		
		switch (lockMode) {
		
		case READPAST:
			
			lm = LockMode.READ;			
			break;

		default:
			break;
		}
		
		
		
		return lm;
	}
	
	public EntityManager getEntityManager() {
		
		
		/*if(entityManager == null) {
			EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MD");	
			entityManager = emf.createEntityManager();
		
		}*/
		
			
	    
	    return entityManager;
		//return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		entityManager = entityManager;
	}

	public CustomEntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(
			CustomEntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}


	
	public static void main(String [] args){
		
		String params1 = "uno,dos,tres";
		String params2 = "uno";

		
		String [] p1 = params1.split(",");
		String [] p2 = params2.split(",");
		
		System.out.println("p1.length" +  p1.length);
		for(String s : p1){
			System.out.println(s);
		}
		
		System.out.println("\np2.length" + p2.length);
		for(String s : p2){
			System.out.println(s);			
		}
	
	
	
	
	}
}
