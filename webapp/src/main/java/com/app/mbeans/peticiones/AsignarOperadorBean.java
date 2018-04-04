package com.app.mbeans.peticiones;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.core.app.servicios.ServicioPeticion;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class AsignarOperadorBean extends MBeanAbstracto {


	//Datos del Area de Asignacion

	//private String telefono;
	private Date fecha;
	private String operador;
	private String evidencia;
		
	private Peticion peticion;
	
//	private ServicioCatalogo servicioCatalogo;	
	private ServicioPeticiones servicioPeticiones;
	
	
	
	private boolean mostrarContenido;
	
	
	public void inicializar() {
	
		
		System.out.println("asignarOperadorBean.inicializar()");

		try {			
			
			if(peticion.getStatusPeticion().getIdStatusPeticion() >= 11){ 
			
				setMostrarContenido(true);
				
				setOperador( peticion.getUsuarioAdicional().getNombre() );
				setEvidencia( peticion.getRequiereEvidencia() == 1 ? "SI" : "NO") ;
				
				ConsultarFechaMovimientoPeticion consultarFechaMovimientoPeticion = new ConsultarFechaMovimientoPeticion();
				consultarFechaMovimientoPeticion.setIdMovimientoPeticion(11);
				consultarFechaMovimientoPeticion.setFolio(peticion.getFolio());					
				ConsultarFechaMovimientoRespuesta consultarFechaMovimientoRespuesta = 
				servicioPeticiones.consultarFechaMovimiento(consultarFechaMovimientoPeticion);
				setFecha(consultarFechaMovimientoRespuesta.getFecha());			
				
				
				FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"Operador ASIGNADO","" ));
				
				
			}else{
			
				setMostrarContenido(false);	
				
				FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en en el proceso","" ));							
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar los catalgos." + e.getMessage(),"" ));
		}
		
	}

		

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fechaAsignacionOperador) {
		this.fecha = fechaAsignacionOperador;
	}

	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}

//	public ServicioCatalogo getServicioCatalogo() {
//		return servicioCatalogo;
//	}
//
//	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
//		this.servicioCatalogo = servicioCatalogo;
//	}	
//	
	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}

	public boolean getMostrarContenido() {
		return mostrarContenido;
	}

	public void setMostrarContenido(boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
	}



	public String getOperador() {
		return operador;
	}



	public void setOperador(String operador) {
		this.operador = operador;
	}



	public String getEvidencia() {
		return evidencia;
	}



	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}	
	
	
	
}
