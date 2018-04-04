package com.servicios.negocio.app.peticion.impl;

import java.util.Date;
import java.util.List;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.StatusPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.AgregarMovientoGestion;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;

public class AgregarMovimientoGestionImpl implements AgregarMovientoGestion {

	private ServicioEntidad actualizarEntidad;
	//private ServicioEntidad crearEntidad;
	private AgregarProcesoPeticion agregarProcesoPeticion;
	private ServicioObtenerEntidad obtenerEntidad;
	
	@Override
	public AgregarMovimientoGestionRespuesta ejecutar(AgregarMovimientoGestionPeticion peticion) throws ExcepcionServicioNegocio {
		
		AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = null;
		try {
			
			System.out.println("\t\t\n\n idPeticion es null -> " + peticion.getIdPeticion() + "\n\n");


			Peticion entidadPeticion = (Peticion) obtenerEntidad(peticion.getIdPeticion(), Peticion.class);
		
			System.out.println("\t\t\n\n entidadPeticion es null -> " + (entidadPeticion == null) + "\n\n");

			
			Integer idSiguienteStatus = obtenerSiguienteEstatusPeticion(peticion.getIdStatusActualPeticion(), peticion.getMovimiento() );		
			
			System.out.println("\t\t\n\n idSiguienteStatusConsulta -> " + idSiguienteStatus + "\n\n");

			
			StatusPeticion entidadStatusPeticion = (StatusPeticion) obtenerEntidad(idSiguienteStatus, StatusPeticion.class);
			
			System.out.println("\t\t\n\n entidadStatusPeticion -> " + entidadStatusPeticion + "\n\n");

			
			entidadPeticion.setStatusPeticion(entidadStatusPeticion);
			
			entidadPeticion.setDescripcionEntrega(peticion.getDescripcionEntrega());			
			
			PeticionEntidad peticionEntidad = new PeticionEntidad();
			
			peticionEntidad.setEntidad(entidadPeticion);
			
			actualizarEntidad.ejecutar(peticionEntidad);			
			
			AgregarProcesoPeticionesPeticion agregarProcesoPeticionesPeticion = new AgregarProcesoPeticionesPeticion();
			agregarProcesoPeticionesPeticion.setIdPeticion(peticion.getIdPeticion());
			agregarProcesoPeticionesPeticion.setFecha(peticion.getFecha());
			agregarProcesoPeticionesPeticion.setIdStatusPeticion(idSiguienteStatus);
			agregarProcesoPeticionesPeticion.setMovimiento(peticion.getMovimiento());
			agregarProcesoPeticionesPeticion.setComentarios(peticion.getComentario());
			agregarProcesoPeticionesPeticion.setIdUsuarioSistema(peticion.getIdUsuarioSistema());		
			agregarProcesoPeticionesPeticion.setListaArchivos(peticion.getListaArchivos());			
			
			AgregarProcesoPeticionRespuesta agregarProcesoPeticionRespuesta = agregarProcesoPeticion.ejecutar(agregarProcesoPeticionesPeticion);
			
			agregarMovimientoGestionRespuesta = new AgregarMovimientoGestionRespuesta();
			
			agregarMovimientoGestionRespuesta.setIdProcesoPeticion(agregarProcesoPeticionRespuesta.getIdProcesoPeticion());
			agregarMovimientoGestionRespuesta.setFecha(agregarProcesoPeticionRespuesta.getFecha());
			agregarMovimientoGestionRespuesta.setMovimiento(agregarProcesoPeticionRespuesta.getMovimiento());
			agregarMovimientoGestionRespuesta.setIdStatusActualPeticion(idSiguienteStatus);			
			
		
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioNegocio("Ha ocurrido un error en el servicio AgregarMovimientoGestion", e);

		}
		
		return agregarMovimientoGestionRespuesta;
	}
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
	}

	private Integer obtenerSiguienteEstatusPeticion(Integer idStatusActual , String movimiento) throws ExcepcionServicioNegocio{
		
	 System.out.println("\t\t\n\n movimiento -> " + movimiento + "\n\n");
	 System.out.println("\t\t\n\n idSatusActual -> " + idStatusActual + "\n\n");
		
		Integer idSiguienteStatus = 0;		
		
		if(idStatusActual == 11 & movimiento.equals("GESTION")){	//Se cambia de ASIGNADO a GESTION		
			
			idSiguienteStatus = 13; 
			
		}else{
			if((idStatusActual == 13 | idStatusActual == 21) & movimiento.equals("GESTION")){ // Se agrega solo un movimiento de GESTION
					
					idSiguienteStatus = 13;
			}else{
				if((idStatusActual == 13| idStatusActual == 21) & movimiento.equals("EVIDENCIA")){ //se  agrega un movimiento de EVIDENCIA
						
					idSiguienteStatus = 15; 
					
				}else{
					if(idStatusActual == 13 & movimiento.equals("TERMINADA")){//Se agrega un movimiento de TERMINADA(sin evidencia)
						
						idSiguienteStatus = 15;
					
					}else{
						if(idStatusActual == 15 & movimiento.equals("CIERRE")){ //Agreaga un movimiento de CERRADA
							
							idSiguienteStatus = 17; 
							
						}else{
							if(idStatusActual == 15 & movimiento.equals("RECHAZO")){ //Agreaga un movimiento de RECHAZO DE GESTION
								
								idSiguienteStatus = 21; 
								
							}else{
								throw new ExcepcionServicioNegocio("Se ha agregado un estatus y movimiento no validos.", null);
							}	
						}										
					}
				}
					
			}			
		}		
			
		System.out.println("\t\t\n\n idSiguienteStatus -> " + idSiguienteStatus + "\n\n");

		return idSiguienteStatus;
	}
	
	public static void main(String ...args) throws Exception{
		
		AgregarMovimientoGestionImpl service = new AgregarMovimientoGestionImpl();
		
		int idStatusActual = 15;
		String movimiento = "RECHAZO";
		//System.out.println("idStatusActual ->" + idStatusActual);
		//System.out.println("movimiento ->" + movimiento);
		service.obtenerSiguienteEstatusPeticion(idStatusActual, movimiento);
		
	}
		
	public ServicioEntidad getActualizarEntidad() {
		return actualizarEntidad;
	}

	public void setActualizarEntidad(ServicioEntidad actualizarEntidad) {
		this.actualizarEntidad = actualizarEntidad;
	}

	public AgregarProcesoPeticion getAgregarProcesoPeticion() {
		return agregarProcesoPeticion;
	}

	public void setAgregarProcesoPeticion(AgregarProcesoPeticion agregarProcesoPeticion) {
		this.agregarProcesoPeticion = agregarProcesoPeticion;
	}

	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}

	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
	}
	
	

}
