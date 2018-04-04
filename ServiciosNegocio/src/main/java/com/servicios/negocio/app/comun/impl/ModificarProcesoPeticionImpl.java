package com.servicios.negocio.app.comun.impl;

import java.util.List;

import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.ProcesoPeticion;
import com.objetos.transf.datos.app.peticion.ModificarProcesoPeticionesPeticion;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.ModificarProcesoPeticion;

public class ModificarProcesoPeticionImpl implements ModificarProcesoPeticion {

	private ServicioEntidad actualizarEntidad;
	
	private ServicioObtenerEntidad obtenerEntidad;

	
	@Override
	public void ejecutar(ModificarProcesoPeticionesPeticion peticion) throws ExcepcionServicioNegocio {
		
		
		try{
			
			ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
			obtenerEntidadPeticion.setClase(ProcesoPeticion.class);
			obtenerEntidadPeticion.setIdEntidad(peticion.getIdProcesoPeticion());
			
			ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
			
			ProcesoPeticion procesoPeticion = (ProcesoPeticion) obtenerEntidadRespuesta.getEntidad();
			
			List<Archivo> listaArchivos = peticion.getListaArchivos(); 
			
			for(Archivo archivo : listaArchivos){
				
				procesoPeticion.addArchivo(archivo);
				archivo.setProcesoPeticion(procesoPeticion);
			}			
			
			PeticionEntidad peticionEntidad = new PeticionEntidad();
			peticionEntidad.setEntidad(procesoPeticion);		
			actualizarEntidad.ejecutar(peticionEntidad );			
		
		}catch(Exception e)
		{
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio ModificarProcesoPeticion.",e);			
		}		
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
}
