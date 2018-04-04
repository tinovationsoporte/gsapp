package com.app.mbeans.peticiones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.core.app.otd.ObtenerEntidadRespuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.ServicioPeticion;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.Archivos;
import com.modelo.datos.estructuras.MovimientosProceso;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class GestionBean extends MBeanAbstracto{
	
	//Datos del Area de gestion
	private String operador;
	
	private List<MovimientosProceso> listaMovimientos;
	
	
	private List<Archivos> listaArchivos;
	
	private ServicioCatalogo servicioCatalogo;	
	
	private ServicioPeticiones servicioPeticiones;	

	private Peticion peticion;	
	
	private boolean mostrarSeccionGestion;
	
	private String descripcionEntrega;
	
	public void inicializar(){
		
		
		System.out.println("gestionBean.inicializar()");

		if( peticion.getStatusPeticion().getIdStatusPeticion()  >= 13){
		
			try {				
				
				setOperador( peticion.getUsuarioAdicional().getNombre());
				
				//setListaMovimientos (new ArrayList<M>());			
				
				inicializarListaMovimientos(peticion.getIdPeticion());			
			
				inicializarListaArchivos(peticion.getIdPeticion());
			
				setMostrarSeccionGestion(true);				
				
				setDescripcionEntrega(peticion.getDescripcionEntrega());
				
				
				if(peticion.getStatusPeticion().getIdStatusPeticion() == 13 || peticion.getStatusPeticion().getIdStatusPeticion() == 14){
					
					FacesContext.getCurrentInstance().addMessage("gestion_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"El folio se encuentra en proceso de GESTION","" ));
					
					
				}
				
				if(peticion.getStatusPeticion().getIdStatusPeticion() == 15){
					
					FacesContext.getCurrentInstance().addMessage("gestion_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"El proceso de GESTION del folio ha sido TERMINADO","" ));
					
					
				}
				
				if(peticion.getStatusPeticion().getIdStatusPeticion() == 17){
					
					FacesContext.getCurrentInstance().addMessage("gestion_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"El proceso del folio ha  sido CONCLUIDO","" ));
					
					
				}
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"El folio se encuentra en proceso de GESTION","" ));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al inicializar los catalgos. " + e.getMessage(),"" ));				
				
			}
		}else{
				
				
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Esta fase del proceso aun no ha sido alcanzada ","" ));				
	
			setMostrarSeccionGestion(false);		
		}
		
	}
	
	private void inicializarListaMovimientos(Integer IdPeticion) throws Exception{		
		
		ConsultarMovimientosProcesoPeticion consultarMovimientosPeticion = new ConsultarMovimientosProcesoPeticion();
		
		consultarMovimientosPeticion.setIdPeticion(IdPeticion);
		consultarMovimientosPeticion.addIdStatusPeticion(13);
		consultarMovimientosPeticion.addIdStatusPeticion(15);
		consultarMovimientosPeticion.addIdStatusPeticion(17);
		consultarMovimientosPeticion.addIdStatusPeticion(21);

		//consultarMovimientosPeticion.setIdProcesoPeticion(11);
		
		listaMovimientos = servicioPeticiones.consultarMovimientosProceso(consultarMovimientosPeticion).getListaMovimientosProceso();		
		
	}
	
	private void inicializarListaArchivos(Integer idPeticion) throws Exception {		
		
		ConsultarArchivosPeticion consultarArchivosPeticion = new ConsultarArchivosPeticion();
		//consultarArchivosPeticion.setIdProcesoPeticion(11);		
		consultarArchivosPeticion.setIdPeticion(idPeticion);
		consultarArchivosPeticion.setMovimiento("EVIDENCIA");
		
		listaArchivos = servicioPeticiones.consultarArchivos(consultarArchivosPeticion).getListaArchivos();
		
	}
	
		
	public List<Archivos> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivos> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public List<MovimientosProceso> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<MovimientosProceso> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}


	public boolean getMostrarSeccionGestion() {
		return mostrarSeccionGestion;
	}

	public void setMostrarSeccionGestion(boolean mostrarSeccionGestion) {
		this.mostrarSeccionGestion = mostrarSeccionGestion;
	}

	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}

	public String getDescripcionEntrega() {
		return descripcionEntrega;
	}

	public void setDescripcionEntrega(String descripcionEntrega) {
		this.descripcionEntrega = descripcionEntrega;
	}
	
	
}
