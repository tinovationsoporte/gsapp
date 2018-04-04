package com.servicios.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioObtenerEntidad;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.objetos.transf.datos.app.peticion.ActualizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ActualizarPresupuestoRespuesta;
import com.objetos.transf.datos.app.peticion.ActualizarStatusPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorRespuesta;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesRespuesta;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesRespuesta;
import com.objetos.transf.datos.app.presupuesto.ActualizarPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.AgregarPartidaPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoRespuesta;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoRespuesta;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.datos.app.peticion.ConsultarArchivos;
import com.servicios.datos.app.peticion.ConsultarFechaMovimiento;
import com.servicios.datos.app.peticion.ConsultarMovimientosProceso;
import com.servicios.datos.app.peticion.ConsultarPartidasPresupuesto;
import com.servicios.datos.app.peticion.ConsultarPeticion;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.servicios.negocio.app.peticion.ActualizarPeticion;
import com.servicios.negocio.app.peticion.ActualizarPresupuesto;
import com.servicios.negocio.app.peticion.ActualizarStatusPeticion;
import com.servicios.negocio.app.peticion.AgregarMovientoGestion;
import com.servicios.negocio.app.peticion.AgregarPartidaPresupuesto;
import com.servicios.negocio.app.peticion.AgregarProcesoPeticion;
import com.servicios.negocio.app.peticion.AsignarOperador;
import com.servicios.negocio.app.peticion.AutorizarPeticion;
import com.servicios.negocio.app.peticion.CrearPresupuesto;
import com.servicios.negocio.app.peticion.RegistrarPeticion;
import com.servicios.negocio.app.peticion.RemoverPartidaPresupuesto;
import com.servicios.negocio.app.peticion.RemoverProcesoPeticion;

public class ServicioPeticionesImpl implements ServicioPeticiones {

	private RegistrarPeticion registrarPeticion;
	private ConsultarPeticion consultarPeticion;
	private AutorizarPeticion autorizarPeticion;
	private AsignarOperador asignarOperador;
	private ActualizarPeticion actualizarPeticion;  
	
	private AgregarProcesoPeticion agregarProcesoPeticion;
	private RemoverProcesoPeticion removerProcesoPeticion;
	private ConsultarArchivos consultarArchivos;
	private ConsultarFechaMovimiento consultarFechaMovimiento;
	private ConsultarMovimientosProceso consultarMovimientosProceso;
	private ConsultarMovimientosProceso consultarUltimoMovimientoValido;
	private AgregarMovientoGestion agregarMovimientoGestion;
	private ActualizarStatusPeticion actualizarStatusPeticion;
	
	
	private ConsultarPartidasPresupuesto consultarPartidasPresupuesto;	
	private RemoverPartidaPresupuesto removerPartidaPresupuesto;
	//Agregar servicios de primer nivel y logica
	private AgregarPartidaPresupuesto agregarPartidaPresupuesto;
	private CrearPresupuesto crearPresupuesto;
	private ActualizarPresupuesto actualizarPresupuesto;
	
	
	

	@Override
	public RegistrarPeticionesRespuesta registrarPeticiones(RegistrarPeticionesPeticion peticion)
			throws ExcepcionServicioFachada {
		RegistrarPeticionesRespuesta respuesta = null;
		try {
			respuesta = registrarPeticion.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion registrarPeticion. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}

	@Override
	public ConsultarPeticionesRespuesta consultarPeticiones(ConsultarPeticionesPeticion peticion)
			throws ExcepcionServicioFachada {
		ConsultarPeticionesRespuesta respuesta = null;
		try {
			respuesta = consultarPeticion.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarPeticiones. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	@Override
	public ConsultarFechaMovimientoRespuesta consultarFechaMovimiento(ConsultarFechaMovimientoPeticion peticion)
			throws ExcepcionServicioFachada {
		ConsultarFechaMovimientoRespuesta respuesta = null;
		try {
			respuesta = consultarFechaMovimiento.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarFechaMovimiento. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}

	@Override
	public AutorizarPeticionesRespuesta autorizarPeticion(AutorizarPeticionesPeticion peticion)
			throws ExcepcionServicioFachada {
		AutorizarPeticionesRespuesta respuesta = null;
		try {
			respuesta = autorizarPeticion.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion autorizarPeticion. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	@Override
	public AsignarOperadorRespuesta asignarOperador(AsignarOperadorPeticion peticion) throws ExcepcionServicioFachada {
		AsignarOperadorRespuesta respuesta = null;
		try {
			respuesta = asignarOperador.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion asignarOperador. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}	

	@Override
	public AgregarMovimientoGestionRespuesta agregarMovimientoGestion(AgregarMovimientoGestionPeticion peticion)
			throws ExcepcionServicioFachada {
		AgregarMovimientoGestionRespuesta respuesta = null;
		try {
			respuesta = agregarMovimientoGestion.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion asignarOperador. " + e.getMessage(), e.getCause());
		}

		return respuesta;

	}
	
	
	@Override
	public AgregarProcesoPeticionRespuesta agregarProcesoPeticion(AgregarProcesoPeticionesPeticion peticion)
			throws ExcepcionServicioFachada {
		AgregarProcesoPeticionRespuesta respuesta = null;
		try {
			respuesta = agregarProcesoPeticion.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion agregarProcesoPeticion. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}

	@Override
	public void removerProcesoPeticion(Integer idProcesoPeticion) throws ExcepcionServicioFachada {

		try {
			removerProcesoPeticion.ejecutar(idProcesoPeticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion removerProcesoPeticion. " + e.getMessage(), e.getCause());
		}		
	}

	@Override
	public ConsultarArchivosRespuesta consultarArchivos(ConsultarArchivosPeticion peticion)
			throws ExcepcionServicioFachada {
		ConsultarArchivosRespuesta respuesta = null;
		try {
			respuesta = consultarArchivos.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarArchivos. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}

	@Override
	public ConsultarMovimientosProcesoRespuesta consultarMovimientosProceso(
			ConsultarMovimientosProcesoPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarMovimientosProcesoRespuesta respuesta = null;
		try {
			respuesta = consultarMovimientosProceso.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarMovimientosProceso. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	@Override
	public ConsultarMovimientosProcesoRespuesta consultarUltimoMovimientoValido(
			ConsultarMovimientosProcesoPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarMovimientosProcesoRespuesta respuesta = null;
		try {
			respuesta = consultarUltimoMovimientoValido.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarUltimoMovimientoValido. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	@Override
	public ConsultarPartidasPresupuestoRespuesta consultarPartidasPresupuesto(
			ConsultarPartidasPresupuestoPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarPartidasPresupuestoRespuesta respuesta = null;
		try {
			respuesta = consultarPartidasPresupuesto.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion consultarPartidasPresupuesto. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	@Override
	public void agregarPartidaPresupuesto(AgregarPartidaPresupuestoPeticion peticion) throws ExcepcionServicioFachada {
		try {
			agregarPartidaPresupuesto.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion agregarPartidaPresupuesto. " + e.getMessage(), e.getCause());
		}		
	}

	@Override
	public void removerPartidaPresupuesto(Integer idPartidaPresupuesto) throws ExcepcionServicioFachada {
		try {
			removerPartidaPresupuesto.ejecutar(idPartidaPresupuesto);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion removerPartidaPresupuesto. " + e.getMessage(), e.getCause());
		}		
	}

	@Override
	public CrearPresupuestoRespuesta crearPresupuesto(CrearPresupuestoPeticion peticion)
			throws ExcepcionServicioFachada {
		CrearPresupuestoRespuesta respuesta = null;
		try {
			respuesta = crearPresupuesto.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion crearPresupuesto. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}

	@Override
	public ActualizarPresupuestoRespuesta actualizarPresupuesto(ActualizarPresupuestoPeticion peticion)
			throws ExcepcionServicioFachada {
		ActualizarPresupuestoRespuesta respuesta = null;
		try {
			respuesta = actualizarPresupuesto.ejecutar(peticion);
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada(
					"Ocurrion un eror al ejecutar la operacion crearPresupuesto. " + e.getMessage(), e.getCause());
		}

		return respuesta;
	}
	
	
	@Override
	public void actualizarStatusPeticion(ActualizarStatusPeticionesPeticion peticion) throws ExcepcionServicioFachada{
		
		try{			
			actualizarStatusPeticion.ejecutar(peticion);
		}catch(ExcepcionServicio e){
			throw new ExcepcionServicioFachada("Ocurrio un erro al ejecutar la operacion ActualizarStatusPeticion. " + e.getMessage(), e);
			
		}
		
		
	}
	
	@Override
	public void actualizarPeticion(ActualizarPeticionesPeticion peticion) throws ExcepcionServicioFachada{
		
		try{			
			actualizarPeticion.ejecutar(peticion);
		}catch(ExcepcionServicio e){
			throw new ExcepcionServicioFachada("Ocurrio un erro al ejecutar la operacion ActualizarStatusPeticion. " + e.getMessage(), e);
			
		}
		
		
	}
	
	
	public RegistrarPeticion getRegistrarPeticion() {
		return registrarPeticion;
	}

	public void setRegistrarPeticion(RegistrarPeticion registrarPeticion) {
		this.registrarPeticion = registrarPeticion;
	}

	public ConsultarPeticion getConsultarPeticion() {
		return consultarPeticion;
	}

	public void setConsultarPeticion(ConsultarPeticion consultarPeticion) {
		this.consultarPeticion = consultarPeticion;
	}

	public AutorizarPeticion getAutorizarPeticion() {
		return autorizarPeticion;
	}

	public void setAutorizarPeticion(AutorizarPeticion autorizarPeticion) {
		this.autorizarPeticion = autorizarPeticion;
	}

	public AsignarOperador getAsignarOperador() {
		return asignarOperador;
	}

	public void setAsignarOperador(AsignarOperador asignarOperador) {
		this.asignarOperador = asignarOperador;
	}

	public AgregarProcesoPeticion getAgregarProcesoPeticion() {
		return agregarProcesoPeticion;
	}

	public void setAgregarProcesoPeticion(AgregarProcesoPeticion agregarProcesoPeticion) {
		this.agregarProcesoPeticion = agregarProcesoPeticion;
	}

	public RemoverProcesoPeticion getRemoverProcesoPeticion() {
		return removerProcesoPeticion;
	}

	public void setRemoverProcesoPeticion(RemoverProcesoPeticion removerProcesoPeticion) {
		this.removerProcesoPeticion = removerProcesoPeticion;
	}

	public ConsultarArchivos getConsultarArchivos() {
		return consultarArchivos;
	}

	public void setConsultarArchivos(ConsultarArchivos consultarArchivos) {
		this.consultarArchivos = consultarArchivos;
	}

	public ConsultarMovimientosProceso getConsultarMovimientosProceso() {
		return consultarMovimientosProceso;
	}

	public void setConsultarMovimientosProceso(ConsultarMovimientosProceso consultarMovimientosProceso) {
		this.consultarMovimientosProceso = consultarMovimientosProceso;
	}

	public ConsultarPartidasPresupuesto getConsultarPartidasPresupuesto() {
		return consultarPartidasPresupuesto;
	}

	public void setConsultarPartidasPresupuesto(ConsultarPartidasPresupuesto consultarPartidasPresupuesto) {
		this.consultarPartidasPresupuesto = consultarPartidasPresupuesto;
	}

	public AgregarPartidaPresupuesto getAgregarPartidaPresupuesto() {
		return agregarPartidaPresupuesto;
	}

	public void setAgregarPartidaPresupuesto(AgregarPartidaPresupuesto agregarPartidaPresupuesto) {
		this.agregarPartidaPresupuesto = agregarPartidaPresupuesto;
	}

	public RemoverPartidaPresupuesto getRemoverPartidaPresupuesto() {
		return removerPartidaPresupuesto;
	}

	public void setRemoverPartidaPresupuesto(RemoverPartidaPresupuesto removerPartidaPresupuesto) {
		this.removerPartidaPresupuesto = removerPartidaPresupuesto;
	}

	public CrearPresupuesto getCrearPresupuesto() {
		return crearPresupuesto;
	}

	public void setCrearPresupuesto(CrearPresupuesto crearPresupuesto) {
		this.crearPresupuesto = crearPresupuesto;
	}

	public AgregarMovientoGestion getAgregarMovimientoGestion() {
		return agregarMovimientoGestion;
	}

	public void setAgregarMovientoGestion(AgregarMovientoGestion agregarMovimientoGestion) {
		this.agregarMovimientoGestion = agregarMovimientoGestion;
	}

	public void setAgregarMovimientoGestion(AgregarMovientoGestion agregarMovimientoGestion) {
		this.agregarMovimientoGestion = agregarMovimientoGestion;
	}

	public ActualizarPresupuesto getActualizarPresupuesto() {
		return actualizarPresupuesto;
	}
	

	public ConsultarMovimientosProceso getConsultarUltimoMovimientoValido() {
		return consultarUltimoMovimientoValido;
	}

	public void setConsultarUltimoMovimientoValido(ConsultarMovimientosProceso consultarUltimoMovimientoValido) {
		this.consultarUltimoMovimientoValido = consultarUltimoMovimientoValido;
	}

	public void setActualizarPresupuesto(ActualizarPresupuesto actualizarPresupuesto) {
		this.actualizarPresupuesto = actualizarPresupuesto;
	}

	public ActualizarStatusPeticion getActualizarStatusPeticion() {
		return actualizarStatusPeticion;
	}

	public void setActualizarStatusPeticion(ActualizarStatusPeticion actualizarStatusPeticion) {
		this.actualizarStatusPeticion = actualizarStatusPeticion;
	}

	public ActualizarPeticion getActualizarPeticion() {
		return actualizarPeticion;
	}

	public void setActualizarPeticion(ActualizarPeticion actualizarPeticion) {
		this.actualizarPeticion = actualizarPeticion;
	}

	public ConsultarFechaMovimiento getConsultarFechaMovimiento() {
		return consultarFechaMovimiento;
	}

	public void setConsultarFechaMovimiento(ConsultarFechaMovimiento consultarFechaMovimiento) {
		this.consultarFechaMovimiento = consultarFechaMovimiento;
	}

	
	
	
}
