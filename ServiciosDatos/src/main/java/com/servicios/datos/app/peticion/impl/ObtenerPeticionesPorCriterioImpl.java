package com.servicios.datos.app.peticion.impl;

import java.util.List;

import com.core.app.otd.ObtenerEntidadesPorCriterioPeticion;
import com.core.app.otd.ObtenerEntidadesPorCriterioRespuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.modelo.datos.app.Peticion;
import com.objetos.transf.datos.app.peticion.ObtenerPeticionesPorCriterioPeticion;
import com.objetos.transf.datos.app.peticion.ObtenerPeticionesPorCriterioRespuesta;
import com.servicios.datos.app.peticion.ObtenerPeticionesPorCriterio;

public class ObtenerPeticionesPorCriterioImpl implements ObtenerPeticionesPorCriterio {

	private ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio;

	
	@Override
	public ObtenerPeticionesPorCriterioRespuesta ejecutar(ObtenerPeticionesPorCriterioPeticion peticion)
			throws ExcepcionServicio {

		
		Peticion peticion_ = new Peticion();
		peticion_.setFolio(peticion.getFolio().toString());
		
		
		
		ObtenerEntidadesPorCriterioPeticion obtenerEntidadesPorCriterioPeticion = new ObtenerEntidadesPorCriterioPeticion();
		obtenerEntidadesPorCriterioPeticion.setClase(peticion_.getClass());
		obtenerEntidadesPorCriterioPeticion.setEntidad(peticion_);
		
		ObtenerEntidadesPorCriterioRespuesta obtenerEntidadesPorCriterioRespuesta = 
				obtenerEntidadesPorCriterio.ejecutar(obtenerEntidadesPorCriterioPeticion);
		
		
		
		List<Peticion> peticiones = obtenerEntidadesPorCriterioRespuesta.getListaEntidades();
		
		
		
		
		return null;
	}

}
