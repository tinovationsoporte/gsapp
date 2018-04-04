package com.servicios.negocio.app.peticion.impl;

import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.DetallePresupuesto;
import com.servicios.negocio.app.peticion.RemoverPartidaPresupuesto;

public class RemoverPartidaPresupuestoImpl implements RemoverPartidaPresupuesto {

	private ServicioEntidad eliminarEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	
	@Override
	public void ejecutar(Integer idPartidaPresupuesto) throws ExcepcionServicio {
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idPartidaPresupuesto);
		
		obtenerEntidadPeticion.setClase(DetallePresupuesto.class);
		
		DetallePresupuesto detallePresupuesto = (DetallePresupuesto)obtenerEntidad.ejecutar(obtenerEntidadPeticion ).getEntidad();		
		
		PeticionEntidad peticionEntidad = new PeticionEntidad();
		
		peticionEntidad.setEntidad(detallePresupuesto);
		
		eliminarEntidad.ejecutar(peticionEntidad);

	}

	public ServicioEntidad getEliminarEntidad() {
		return eliminarEntidad;
	}

	public void setEliminarEntidad(ServicioEntidad eliminarEntidad) {
		this.eliminarEntidad = eliminarEntidad;
	}

	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}

	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}

	
	
}
