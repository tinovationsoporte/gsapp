package com.servicios.app.catalogos.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

//import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;

import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class ServicioCatalogoImpl implements ServicioCatalogo{ 

	private ServicioEntidad crearEntidad;
	
	private ServicioEntidad eliminarEntidad;
	
	private ServicioEntidad actualizarEntidad;
	
	private ServicioObtenerEntidad obtenerEntidad;
	
	private ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio;
	
	
	public ServicioObtenerEntidadesPorCriterio getObtenerEntidadesPorCriterio() {
		return obtenerEntidadesPorCriterio;
	}

	public void setObtenerEntidadesPorCriterio(
			ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio) {
		this.obtenerEntidadesPorCriterio = obtenerEntidadesPorCriterio;
	}

	public ServicioEntidad getCrearEntidad() {
		return crearEntidad;
	}

	public void setCrearEntidad(ServicioEntidad crearEntidad) {
		this.crearEntidad = crearEntidad;
	}

	public ServicioEntidad getEliminarEntidad() {
		return eliminarEntidad;
	}

	public void setEliminarEntidad(ServicioEntidad eliminarEntidad) {
		this.eliminarEntidad = eliminarEntidad;
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

	@Override
	public void crearCatalogo(CrearCatalogoPeticion peticion)
			throws ExcepcionServicioFachada {
		try{
			
			getCrearEntidad().ejecutar(peticion);		
			
		}catch(Exception e){
			//e.printStackTrace();
			throw new ExcepcionServicioFachada("Ocurrrio un error en la operacion crearCatalogo  de la fachada Catalogo ", e.getCause());
		}
		
	}

	@Override
	public void eliminarCatalogo(EliminarCatalogoPeticion peticion)
			throws ExcepcionServicioFachada {
		try{
			
			getEliminarEntidad().ejecutar(peticion);		
			
		}catch(Exception e){
			
			
			if(e instanceof ExcepcionServicio){	
				
				throw new ExcepcionServicioFachada(e.getMessage(), e.getCause(), ((ExcepcionServicio) e).getClave());
				
			}
			
			throw new ExcepcionServicioFachada("Ocurrrio un error en la operacion eliminarCatalogo de la fachada Catalogo ", e.getCause());
		}
	}

	@Override
	public void actualizarCatalogo(ActualizarCatalogoPeticion peticion)
			throws ExcepcionServicioFachada {
		try{
			
			getActualizarEntidad().ejecutar(peticion);		
			
		}catch(Exception e){
			
			throw new ExcepcionServicioFachada("Ocurrrio un error en la operacion actualizarCatalogo de la fachada Catalogo ", e.getCause());
		}
		
	}

	@Override
	public ConsultarCatalagoRespuesta consultarCatalogo(ConsultarCatalogoPeticion peticion)
			throws ExcepcionServicioFachada {
		ConsultarCatalagoRespuesta respuesta = new ConsultarCatalagoRespuesta();
		
		try{
			
			List lista =  getObtenerEntidadesPorCriterio().ejecutar(peticion).getListaEntidades();		
			
			if(lista.size() > 0){				
				
				respuesta.setEntidades(new ArrayList(lista));
				respuesta.setListaEntidades( new ListDataModel(lista) );
				
			}
			
		}catch(Exception e){
			
			throw new ExcepcionServicioFachada("Ocurrrio un error en la operacion consultarCatalogo de la fachada Catalogo ", e.getCause());
		}
		
		return respuesta;
	}

	@Override
	public ObtenerCatalogoRespuesta obtenerCatalogo(
			ObtenerCatalogoPeticion peticion) throws ExcepcionServicioFachada {
		
		ObtenerCatalogoRespuesta respuesta = new ObtenerCatalogoRespuesta();
		
		try{
						
			respuesta.setEntidad(getObtenerEntidad().ejecutar(peticion).getEntidad());
			
		}catch(Exception e){
			//e.printStackTrace();
			throw new ExcepcionServicioFachada("Ocurrrio un error en la operacion obtenerCatalogo de la fachada Catalogo ", e.getCause());
		}
		
		return respuesta;
	}

	
}
