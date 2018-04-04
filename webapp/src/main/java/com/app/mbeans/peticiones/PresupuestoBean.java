package com.app.mbeans.peticiones;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.DetallePresupuesto;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Presupuesto;
import com.modelo.datos.app.StatusPresupuesto;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.PartidasPresupuesto;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.ActualizarPresupuestoRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.objetos.transf.datos.app.presupuesto.ActualizarPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.AgregarPartidaPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoRespuesta;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.CrearPresupuestoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.servicios.negocio.app.peticion.RemoverPartidaPresupuesto;


public class PresupuestoBean extends MBeanAbstracto {	
	
	private Date fechaElaboracionPresupuesto;
	
	private List<PartidasPresupuesto> listaPartidas;
	
	private Double sumaTotal;	
	
	private ServicioPeticiones servicioPeticiones;
	
//	private ServicioCatalogo servicioCatalogo;	
	
	private Peticion peticion;
	
	private Presupuesto presupuesto;
	
	private boolean mostrarContenido;
	
	private Integer idPresupuesto;
	
	private Integer idStatusPeticion;
	
	
	public void inicializar(){
		
	
		System.out.println("PresupuestoBean.inicializar()");

		UsuarioSistema usuario = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);		

		idStatusPeticion  = peticion.getStatusPeticion().getIdStatusPeticion();
		
		//System.out.println("\n\n\n\t requierePresupuest -> " + peticion.getRequierePresupuesto());
		
		//System.out.println("\n\n\n\t statusPeticion -> " + idStatusPeticion);
		
		//System.out.println("\n\n\n\t presupuesto -> " + (peticion.getPresupuesto() != null));
		
		if(peticion.getRequierePresupuesto() == 1 ){

			System.out.println("\n\n\n\t peticion.getArea().getEsPresupuesto() -> " + peticion.getArea().getEsPresupuesto());

			
			if(((usuario.getArea().getEsPresupuesto() == 1) || (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2))){
				setMostrarContenido(true);	
				
				if(idStatusPeticion > 5){
					
					if(peticion.getPresupuesto()  != null){					
						setIdPresupuesto(peticion.getPresupuesto().getIdPresupuesto());			
						inicializarListaPartidas();					
					}		
					
					if(idStatusPeticion == 6){
						FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
								new FacesMessage(FacesMessage.SEVERITY_WARN,"El folio esta PRESUPUESTADO","" ));
					}else{				
						if(idStatusPeticion == 8){
							FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
									new FacesMessage(FacesMessage.SEVERITY_INFO,"El presupuesto del folio ha sido RECHAZADO","" ));
						}else{						
							if(idStatusPeticion > 8){
								FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
										new FacesMessage(FacesMessage.SEVERITY_WARN,"El presupuesto del folio ha sido AUTORIZADO","" ));
							}else{
								FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
										new FacesMessage(FacesMessage.SEVERITY_FATAL,"ESTATUS NO VALIDO" + idStatusPeticion,"" ));				
							}						
						}					
					}	
				}else{				
					setMostrarContenido(false);				
					FacesContext.getCurrentInstance().addMessage("presupuesto_msges2", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en el proceso","" ));
				}
			}else{
				setMostrarContenido(false);			
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges2", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"No esta autorizado para ver el presupuesto","" ));
			}			
			
		}else{			
			setMostrarContenido(false);			
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges2", 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Esta peticion no requiere un presupuesto","" ));
		}
		
	}	
		
	
	
	
	private void inicializarListaPartidas(){
		
		ConsultarPartidasPresupuestoPeticion consultarPartidasPresupuestoPeticion  
												= new ConsultarPartidasPresupuestoPeticion();
		
		consultarPartidasPresupuestoPeticion.setIdPresupuesto(getIdPresupuesto());
		
		try {
			ConsultarPartidasPresupuestoRespuesta consultarPartidasPresupuestoRespuesta = 
					getServicioPeticiones().consultarPartidasPresupuesto(consultarPartidasPresupuestoPeticion);
			
			listaPartidas = consultarPartidasPresupuestoRespuesta.getListaPartidasPresupuesto();
			
			setSumaTotal(calcularSumaPartidas(listaPartidas));
			
			ConsultarFechaMovimientoPeticion consultarFechaMovimientoPeticion = new ConsultarFechaMovimientoPeticion();
			consultarFechaMovimientoPeticion.setIdMovimientoPeticion(6);
			consultarFechaMovimientoPeticion.setFolio(peticion.getFolio());					
			ConsultarFechaMovimientoRespuesta consultarFechaMovimientoRespuesta = 
			servicioPeticiones.consultarFechaMovimiento(consultarFechaMovimientoPeticion);
			setFechaElaboracionPresupuesto(consultarFechaMovimientoRespuesta.getFecha());
			
		} catch (ExcepcionServicioFachada e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al cargar las partidas" + e.getMessage(),"" ));
		}	
		
	}
	
	private double calcularSumaPartidas(List<PartidasPresupuesto> listaPartidas){
		Double resultado = 0.0;
		
		for(PartidasPresupuesto partida : listaPartidas){
			
			resultado += partida.getTotalPartida();
			
		}		
		
		return resultado;
	}
	
	
	
	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}

	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	public boolean getMostrarContenido() {
		return mostrarContenido;
	} 
	
	public void setMostrarContenido(boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
	}
	
	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}

	public Date getFechaElaboracionPresupuesto() {
		return fechaElaboracionPresupuesto;
	}


	public void setFechaElaboracionPresupuesto(Date fechaElaboracionPresupuesto) {
		this.fechaElaboracionPresupuesto = fechaElaboracionPresupuesto;
	}

	public Double getSumaTotal() {
		return sumaTotal;
	}



	public void setSumaTotal(Double sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	

	public List<PartidasPresupuesto> getListaPartidas() {
		return listaPartidas;
	}

	public void setListaPartidas(List<PartidasPresupuesto> listaPartidas) {
		this.listaPartidas = listaPartidas;
	}

//	public ServicioCatalogo getServicioCatalogo() {
//		return servicioCatalogo;
//	}
//	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
//		this.servicioCatalogo = servicioCatalogo;
//	}

	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}	
	

}
