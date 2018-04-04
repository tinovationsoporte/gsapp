
package com.servicios.negocio.app.peticion.impl;

import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.ProcesoPeticion;
import com.servicios.negocio.app.peticion.RemoverProcesoPeticion;

public class RemoverProcesoPeticionImpl implements RemoverProcesoPeticion {

	private ServicioEntidad eliminarEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	
	@Override
	public void ejecutar(Integer idProcesoPeticion) throws ExcepcionServicio {
		
		System.out.println("\n\nidProcesoPeticionRemover -> " + idProcesoPeticion + "\n\n");
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idProcesoPeticion);
		
		obtenerEntidadPeticion.setClase(ProcesoPeticion.class);
		
		ProcesoPeticion procesoPeticion = (ProcesoPeticion)obtenerEntidad.ejecutar(obtenerEntidadPeticion ).getEntidad();		
		

		
		if(procesoPeticion == null){
			System.out.println("\n\nprocesoPeticionRemover == null ->\n\n");
		}else{
			
			System.out.println("\n\nprocesoPeticionRemover.id ->" + procesoPeticion.getIdProcesoPeticion() );
			System.out.println("procesoPeticionRemover.movimiento ->" + procesoPeticion.getMovimiento());
			System.out.println("procesoPeticionRemover.fecha ->" + procesoPeticion.getFecha()+ "\n\n");
			
		}
		
		
		PeticionEntidad peticionEntidad = new PeticionEntidad();
		peticionEntidad.setEntidad(procesoPeticion);
		System.out.println("\n\neliminarEntidad.ejecutar... ejecutando\n\n");
		eliminarEntidad.ejecutar(peticionEntidad);
		System.out.println("\n\neliminarEntidad.ejecutar... ejecutando\n\n");
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
