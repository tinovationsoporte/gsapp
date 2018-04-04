package com.app.mbeans.peticiones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.commandlink.CommandLink;

import com.app.modelo.Usuario;
import com.core.app.bd.query.In;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadPeticion;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.ServicioPeticion;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class AutorizarPeticionBean extends MBeanAbstracto {	
	
	private Integer idAreaAsignada;
	private Integer idAreaPresupuesto;
	
	private Integer idUsuarioAreaAsignada;
	private Integer idUsuarioAreaPresupuesto;

	
	private String areaAsignada;
	private String usuarioAreaAsignada;
	
	private String areaPresupuesto;
	private String usuarioAreaPresupuesto;
	
	private String prioridad;	
	
	private Date fechaAutorizacion;

	 
	private Peticion peticion;
	
//	private ServicioCatalogo servicioCatalogo;	
	private ServicioPeticiones servicioPeticiones;
	
	//private AreaBean detalleArea;
	
	
	private Integer idUsuarioSistema;
	
	private Boolean mostrarContenido;

	
	
	public void inicializar(){
		try {
			
			System.out.println("autorizarPeticionBean.inicializar()");
			
			Integer idStatusPeticionActual = peticion.getStatusPeticion().getIdStatusPeticion();			
			
			if (idStatusPeticionActual == 1){
			
				setMostrarContenido(false);
				FacesContext.getCurrentInstance().addMessage("autorizar_msges2", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en el proceso","" ));
				
			}else{
				
				
				setMostrarContenido(true);

				setAreaAsignada(getPeticion().getArea().getNombre());
				setIdAreaAsignada(getPeticion().getArea().getIdArea());
				
				setUsuarioAreaAsignada(peticion.getUsuarioAsignado().getNombre());
				setIdUsuarioAreaAsignada(peticion.getUsuarioAsignado().getIdUsuarioSistema());
				
				ConsultarFechaMovimientoPeticion consultarFechaMovimientoPeticion = new ConsultarFechaMovimientoPeticion();
				consultarFechaMovimientoPeticion.setIdMovimientoPeticion(4);
				consultarFechaMovimientoPeticion.setFolio(peticion.getFolio());					
				ConsultarFechaMovimientoRespuesta consultarFechaMovimientoRespuesta = 
				servicioPeticiones.consultarFechaMovimiento(consultarFechaMovimientoPeticion);
				setFechaAutorizacion(consultarFechaMovimientoRespuesta.getFecha());
				
				
				if(peticion.getAreaPresupuesto() != null){
					setAreaPresupuesto(getPeticion().getAreaPresupuesto().getNombre());
					setIdAreaPresupuesto(getPeticion().getAreaPresupuesto().getIdArea());
				
					setUsuarioAreaPresupuesto(peticion.getUsuarioPresupuesto().getNombre());				
					setIdUsuarioAreaPresupuesto(peticion.getUsuarioPresupuesto().getIdUsuarioSistema());				
				}else{
					setAreaPresupuesto("AREA NO ASIGANDA");
					setIdAreaPresupuesto(0);				
					
					setUsuarioAreaPresupuesto("USUARIO NO ASIGNADO");				
					setIdUsuarioAreaPresupuesto(0);					
				}				
				
				setPrioridad(getPeticion().getPrioridad().getValor());
				
				//Si la peticion esta asignada sin prespuesto o con presupuesto se puede reasingar
				if(idStatusPeticionActual == 3 || idStatusPeticionActual == 4){					
					
					FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"La peticion ha sido VALIDADA pero puede ser REASIGNADA","" ));
				
				}else{
					
					if(idStatusPeticionActual != 1 || idStatusPeticionActual != 3 || idStatusPeticionActual != 4){						
						
						FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
								new FacesMessage(FacesMessage.SEVERITY_WARN,"El estatus de la peticion no permite que sea REASIGNADA","" ));
					}
				}
			}
			
			setPeticion(null);
			
		} catch (Exception e) {
			//getCurrentFacesContext().validationFailed();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al cargar la informacion." + e.getMessage(),"" ));
			FacesContext.getCurrentInstance().addMessage("autorizar_msges2", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al cargar la informacion." + e.getMessage(),"" ));
		}	
	}		

	
	public Boolean getMostrarContenido() {
		return mostrarContenido;
	}


	public void setMostrarContenido(Boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
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
//	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
//		this.servicioCatalogo = servicioCatalogo;
//	}


	

	public Integer getIdAreaAsignada() {
		return idAreaAsignada;
	}

	public void setIdAreaAsignada(Integer idAreaAsignada) {
		this.idAreaAsignada = idAreaAsignada;
	}

	public Integer getIdAreaPresupuesto() {
		return idAreaPresupuesto;
	}

	public void setIdAreaPresupuesto(Integer idAreaPresupuesto) {
		this.idAreaPresupuesto = idAreaPresupuesto;
	}

	public Integer getIdUsuarioAreaAsignada() {
		return idUsuarioAreaAsignada;
	}

	public void setIdUsuarioAreaAsignada(Integer idUsuarioAreaAsignada) {
		this.idUsuarioAreaAsignada = idUsuarioAreaAsignada;
	}

	public Integer getIdUsuarioAreaPresupuesto() {
		return idUsuarioAreaPresupuesto;
	}

	public void setIdUsuarioAreaPresupuesto(Integer idUsuarioAreaPresupuesto) {
		this.idUsuarioAreaPresupuesto = idUsuarioAreaPresupuesto;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}
	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}	

	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}


	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}

	public String getAreaAsignada() {
		return areaAsignada;
	}


	public void setAreaAsignada(String areaAsignada) {
		this.areaAsignada = areaAsignada;
	}


	public String getUsuarioAreaAsignada() {
		return usuarioAreaAsignada;
	}


	public void setUsuarioAreaAsignada(String usuarioAreaAsignada) {
		this.usuarioAreaAsignada = usuarioAreaAsignada;
	}


	public String getAreaPresupuesto() {
		return areaPresupuesto;
	}


	public void setAreaPresupuesto(String areaPresupuesto) {
		this.areaPresupuesto = areaPresupuesto;
	}


	

	public String getUsuarioAreaPresupuesto() {
		return usuarioAreaPresupuesto;
	}


	public void setUsuarioAreaPresupuesto(String usuarioAreaPresupuesto) {
		this.usuarioAreaPresupuesto = usuarioAreaPresupuesto;
	}


	public String getPrioridad() {
		return prioridad;
	}


	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	
	
}
