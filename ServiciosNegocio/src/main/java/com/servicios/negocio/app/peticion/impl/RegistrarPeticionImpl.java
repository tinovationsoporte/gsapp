package com.servicios.negocio.app.peticion.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.engine.jdbc.internal.LobCreatorBuilder;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.otd.Respuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Categoria;
import com.modelo.datos.app.EntidadMpal;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.TipoMedioContacto;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenPeticion;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenRespuesta;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.comun.ModificarTamanoImagen;
import com.servicios.negocio.app.peticion.RegistrarPeticion;

public class RegistrarPeticionImpl implements RegistrarPeticion {

	private ServicioEntidad crearEntidad;
	private ServicioObtenerEntidad obtenerEntidad;
	private ModificarTamanoImagen modificarTamanoImagen;
	
	

	public ModificarTamanoImagen getModificarTamanoImagen() {
		return modificarTamanoImagen;
	}

	public void setModificarTamanoImagen(ModificarTamanoImagen modificarTamanoImagen) {
		this.modificarTamanoImagen = modificarTamanoImagen;
	}

	public ServicioEntidad getCrearEntidad() {
		return crearEntidad;
	}

	public void setCrearEntidad(ServicioEntidad crearEntidad) {
		this.crearEntidad = crearEntidad;
	}

	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}

	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}

	@Override
	public RegistrarPeticionesRespuesta ejecutar(RegistrarPeticionesPeticion peticion_) throws ExcepcionServicio {
		
		System.out.println("\t\t***RegistrarPeticionImpl.ejecutar() ...");	
		System.out.println("\t\t***peticion.getClaveExterna() -> " + peticion_.getClaveExterna());
		
		
		RegistrarPeticionesRespuesta respuesta  = null;
		
		try{
			Peticion peticion= new Peticion();
			
			peticion.setSolicitante(peticion_.getSolicitante());		
			peticion.setDireccion(peticion_.getDireccion());		
			peticion.setMedioContacto(peticion_.getMedioConcato());
			peticion.setDescripcion(peticion_.getDescripcion());
			peticion.setObservaciones(peticion_.getObservaciones());
			peticion.setRequierePresupuesto(peticion_.getRequierePresupuesto());
			peticion.setRequiereEvidencia(1);
			peticion.setClaveExterna(peticion_.getClaveExterna());
					
			/*
			 * Generacion del folio
			 */
			peticion.setFolio(new StringBuilder().append(new Date().getTime()).toString());
			
			
			/*
			 * Agregacion de entidades
			 */
			peticion.setTipoMedioContacto((TipoMedioContacto)obtenerEntidad(peticion_.getIdTipoMedioContacto(), TipoMedioContacto.class));
					
			peticion.setCategoria((Categoria)obtenerEntidad(peticion_.getIdCategoria(), Categoria.class));
					
			peticion.setPrioridad((Prioridad)obtenerEntidad(peticion_.getIdPrioridad(), Prioridad.class));		
			
			peticion.setEntidadMpal((EntidadMpal)obtenerEntidad(peticion_.getIdEntidadMpal(), EntidadMpal.class));
			
			peticion.setArea((Area)obtenerEntidad(peticion_.getIdArea(), Area.class));
			
			StatusPeticion statusPeticion = (StatusPeticion)obtenerEntidad(1,StatusPeticion.class);		
			
			peticion.setStatusPeticion(statusPeticion);
	
			
			ProcesoPeticion procesoPeticion = new ProcesoPeticion();
			
			procesoPeticion.setComentarios("CAPTURA DE PETICION");
			procesoPeticion.setMovimiento("CAPTURA");
			procesoPeticion.setFecha(new Date());	
					
			procesoPeticion.setStatusPeticion(statusPeticion);		
			
			procesoPeticion.setPeticion(peticion);
			
			procesoPeticion.setUsuarioSistema( (UsuarioSistema)obtenerEntidad(peticion_.getIdUsuario(),UsuarioSistema.class));
			
			
			//Se agregan los archivo registrados		
			
			List<Archivo> listaArchivos = peticion_.getListaArchivos();
			System.out.println("***Tamaño de la lista de archivos -> " + listaArchivos.size());		
			
			for(Archivo archivo : listaArchivos){
					
				archivo.setProcesoPeticion(procesoPeticion);			
					
				if(esImagen(archivo.getNombre())){
					
					ModificarTamanoImagenPeticion modificarImagenPeticion = new ModificarTamanoImagenPeticion();			
						
					modificarImagenPeticion.setImageBytes(archivo.getBlob()); 			
					modificarImagenPeticion.setTamanoAComprimir(750);				
					modificarImagenPeticion.setTipoImagen("jpg");
						
					ModificarTamanoImagenRespuesta modificarImagenRespuesta = modificarTamanoImagen.ejecutar(modificarImagenPeticion);
						
					archivo.setBlob(modificarImagenRespuesta.getImageBytes());
			
				}				
					
				procesoPeticion.addArchivo(archivo);				
			}			
					
			
			peticion.addProcesoPeticion(procesoPeticion );		
			
			/*
			 * Envio de la entidad al servicio de crearEntidad
			 */
			PeticionEntidad peticionEntidad = new PeticionEntidad();
			
			peticionEntidad.setEntidad(peticion);
			
			crearEntidad.ejecutar(peticionEntidad);		
			
			respuesta = new RegistrarPeticionesRespuesta();
			
			respuesta.setFechaCaptura(procesoPeticion.getFecha());
			
			respuesta.setIdPeticion(peticion.getIdPeticion());
			
			respuesta.setFolio(peticion.getFolio());
			

		
		}catch(Exception  e){
			
			throw new ExcepcionServicioNegocio("Ocurrio un erro al ejecutar el servicio de negocio RegistrarPeticion.-" + e.getMessage(), e);
		}
		
		return respuesta;
	}

	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
	}
	
	
	private boolean esImagen(String nombreArchivo){		
		return  nombreArchivo.contains(".jpg") || nombreArchivo.contains(".gif") || nombreArchivo.contains(".png"); 
	}
}
