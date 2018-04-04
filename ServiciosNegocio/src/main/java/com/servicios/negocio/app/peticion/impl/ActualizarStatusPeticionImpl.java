package com.servicios.negocio.app.peticion.impl;

import java.util.Date;

import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.otd.PeticionEntidad;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.estructuras.MovimientosProceso;
import com.objetos.transf.datos.app.peticion.ActualizarStatusPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoRespuesta;
import com.servicios.datos.app.peticion.ConsultarMovimientosProceso;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.peticion.ActualizarStatusPeticion;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;

public class ActualizarStatusPeticionImpl implements ActualizarStatusPeticion {
	
	
	private ServicioObtenerEntidad obtenerEntidad;
	private ServicioEntidad actualizarEntidad;
	private AgregarProcesoPeticion agregarProcesoPeticion;
	private ConsultarMovimientosProceso consultarUltimoMovimientoValido;
	
	@Override
	public void ejecutar(ActualizarStatusPeticionesPeticion peticion) throws ExcepcionServicio {

		try{
			System.out.println("\n\n***ACtualizarStatusPeticionImpl.ejecutar()");
			System.out.println("\n\n***peticion.getIdStatusPeticion -> " +  peticion.getIdStatusPeticion()) ;
			//Si el estatus es 0 (reactivar) se consulta el ultimo estatus valido para ser reactivada
			if(peticion.getIdStatusPeticion() == 0){
				ConsultarMovimientosProcesoPeticion consultarMovimientosProcesoPeticion = new ConsultarMovimientosProcesoPeticion();
				consultarMovimientosProcesoPeticion.setIdPeticion(peticion.getIdPeticion());
				System.out.println("\n\n***consultarMovimientosProcesoPeticion.getPeticion -> " +  consultarMovimientosProcesoPeticion.getIdPeticion()) ;
				ConsultarMovimientosProcesoRespuesta consultarMovimientosProcesoRespuesta = 
						consultarUltimoMovimientoValido.ejecutar(consultarMovimientosProcesoPeticion);
				System.out.println("\n\n***consultarMovimientosProcesoRespuesta.getListaMovimientosProceso().size() -> " +  consultarMovimientosProcesoRespuesta.getListaMovimientosProceso().size()) ;
				MovimientosProceso movimientoProceso = consultarMovimientosProcesoRespuesta.getListaMovimientosProceso().get(0);
				System.out.println("\n\n***movimientoProceso.getIdStatusPeticion() -> " +  movimientoProceso.getIdStatusPeticion()) ;
				//Se cambia el estatus al ultimo status valido
				peticion.setIdStatusPeticion(movimientoProceso.getIdStatusPeticion());
				
			}
			
			Peticion entidadPeticion = (Peticion)obtenerEntidad(peticion.getIdPeticion(), Peticion.class);
			StatusPeticion entidadStatusPeticion = (StatusPeticion)obtenerEntidad(peticion.getIdStatusPeticion(), StatusPeticion.class);
		
			entidadPeticion.setStatusPeticion(entidadStatusPeticion);
			
			PeticionEntidad actualizarEntidadPeticion = new PeticionEntidad();
			actualizarEntidadPeticion.setEntidad(entidadPeticion);
			
			actualizarEntidad.ejecutar(actualizarEntidadPeticion);
			
			AgregarProcesoPeticionesPeticion agregarProcesoPeticionesPeticion = new AgregarProcesoPeticionesPeticion();
			
			AgregarProcesoPeticionRespuesta procesoPeticionesRespuesta = null;
			
			agregarProcesoPeticionesPeticion.setIdPeticion(peticion.getIdPeticion());
			agregarProcesoPeticionesPeticion.setIdStatusPeticion(peticion.getIdStatusPeticion());
			agregarProcesoPeticionesPeticion.setFecha(new Date());
			agregarProcesoPeticionesPeticion.setIdUsuarioSistema(peticion.getIdUsuarioSistema());
			agregarProcesoPeticionesPeticion.setComentarios(peticion.getComentarios());
			agregarProcesoPeticionesPeticion.setMovimiento(peticion.getMovimiento());
				
			procesoPeticionesRespuesta = agregarProcesoPeticion.ejecutar(agregarProcesoPeticionesPeticion);			
		
		}catch(Exception e){
			
			throw new ExcepcionServicioNegocio("Ocurrion un error en el servicio ActualizarStatusPeticion", e);
		}	
		
	}

	
	
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerEntidadPeticion obtenerEntidadPeticion = new ObtenerEntidadPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = obtenerEntidad.ejecutar(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
	}




	public ServicioObtenerEntidad getObtenerEntidad() {
		return obtenerEntidad;
	}




	public void setObtenerEntidad(ServicioObtenerEntidad obtenerEntidad) {
		this.obtenerEntidad = obtenerEntidad;
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




	public ConsultarMovimientosProceso getConsultarUltimoMovimientoValido() {
		return consultarUltimoMovimientoValido;
	}




	public void setConsultarUltimoMovimientoValido(ConsultarMovimientosProceso consultarUltimoMovimientoValido) {
		this.consultarUltimoMovimientoValido = consultarUltimoMovimientoValido;
	}



	
	
}
