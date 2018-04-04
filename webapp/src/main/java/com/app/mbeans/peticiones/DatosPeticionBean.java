package com.app.mbeans.peticiones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.app.mbeans.AplicacionBean;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.servicios.ServicioPeticion;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.estructuras.Archivos;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class DatosPeticionBean extends MBeanAbstracto {
	
	private String RPT_PETICION = "folioPeticionRpt";
	private String RPT_FORMAT_PDF = "pdf";
	private String urlReportePeticion = null;
	private String URL_REPORTES; //= "http://localhost:8080/webreports/JasperReportServlet?";
	
	private List<Archivos> listaArchivos;
	
	//Datos del solicitante
	private String nombreSolicitante;
	private String coloniaSolicitante;
	private String direccionSolicitante;
	private String medioContacto;
	private String infoAdicionalContacto;
		
	//Datos de la peticion 
	private String folioPeticion;
	private Date fechaCapturaPeticion;
	private String descripcionPeticion;
	private String categoria;
	private String prioridad;
	private String observacionesPeticion;	
	
	private String estatusPeticion;
	
	private ServicioComun servicioComun;
	
	public String getEstatusPeticion() {
		return estatusPeticion;
	}

	public void setEstatusPeticion(String estatusPeticion) {
		this.estatusPeticion = estatusPeticion;
	}
	
//	private ServicioCatalogo servicioCatalogo;	
	
	private ServicioPeticiones servicioPeticiones;
	
	private Peticion peticion;

	private Integer idPeticion;
	
	private ConsultarCatalogoPeticion consultarCatalogoPeticion;
	private ConsultarCatalagoRespuesta consultarCatalogoRespuesta;	
	
	
	
	//@PostConstruct
	public void inicializar(){
		
		System.out.println("datosPeticion.inicializar()");
		
		AplicacionBean app = (AplicacionBean) getManagedBean("#{aplicacion}", AplicacionBean.class);
		
		URL_REPORTES =  app.getUrlReportes();
		
		initDatosPeticion();
	
		setPeticion(null);
	}
	
	private void initDatosPeticion(){

		try {
			setNombreSolicitante(peticion.getSolicitante());		
			
			setColoniaSolicitante(peticion.getEntidadMpal().getNombre());	
			
			setDireccionSolicitante(peticion.getDireccion());
			setMedioContacto(peticion.getTipoMedioContacto().getValor());
			setInfoAdicionalContacto(peticion.getMedioContacto());
			
			setFolioPeticion(peticion.getFolio());
			
			ConsultarFechaMovimientoPeticion consultarFechaMovimientoPeticion = new ConsultarFechaMovimientoPeticion();
			consultarFechaMovimientoPeticion.setIdMovimientoPeticion(1);
			consultarFechaMovimientoPeticion.setFolio(getFolioPeticion());					
			ConsultarFechaMovimientoRespuesta consultarFechaMovimientoRespuesta = 
			servicioPeticiones.consultarFechaMovimiento(consultarFechaMovimientoPeticion);
			setFechaCapturaPeticion(consultarFechaMovimientoRespuesta.getFecha());
						
			
			setDescripcionPeticion(peticion.getDescripcion());
			setCategoria(peticion.getCategoria().getDescripcion());
			setPrioridad(peticion.getPrioridad().getValor());
			setObservacionesPeticion(peticion.getObservaciones());	
			
			setEstatusPeticion(peticion.getStatusPeticion().getValor());		
			
			setIdPeticion(peticion.getIdPeticion());
			
	
			
			ConsultarArchivosPeticion consultarArchivosPeticion = new ConsultarArchivosPeticion();
			consultarArchivosPeticion.setMovimiento("CAPTURA");
			consultarArchivosPeticion.setIdPeticion(getIdPeticion());
					
			ConsultarArchivosRespuesta consultarArchivosRespuesta = 
					servicioPeticiones.consultarArchivos(consultarArchivosPeticion);
		
			setListaArchivos(consultarArchivosRespuesta.getListaArchivos());
			
		} catch (ExcepcionServicioFachada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al inicialiazar los datos de la peticion" +  e.getMessage(), ""));
		}
		
		
	}	
	
	public void imprimir(ActionEvent event){
		RequestContext.getCurrentInstance().execute("openReport('"+ crearUrlReportePeticion() +"')");

	}
	
	private String crearUrlReportePeticion(){	
		
		StringBuilder url = new StringBuilder(URL_REPORTES);
		
		StringBuilder params = new StringBuilder("rptName=")
				.append(RPT_PETICION).append("&")
				.append("rptFormat=").append(RPT_FORMAT_PDF).append("&")	
				.append("idtmpeticion=").append(getIdPeticion());
				
		
		if(getExternalContext().getInitParameter("ENABLE_GET_ENCRIPTION").toString().equals("TRUE")){
		
			CodificarPeticion codificarPeticion = new CodificarPeticion();
			codificarPeticion.setMensaje(params.toString());
			codificarPeticion.setLlave(getExternalContext().getInitParameter("AESKEY").toString());
			
			System.out.println("********\n\n\nurlParameters -> " + codificarPeticion.getMensaje() + "\n\n\n");
			
			CodificarRespuesta codificarRespuesta = null;		
			
			try {
				codificarRespuesta = servicioComun.codificarAES(codificarPeticion);
				url.append("values=").append(codificarRespuesta.getCodificado());
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al codificar la URL" +  e.getMessage(), ""));
			}
			
		}else{
			
			url.append(params);
		}		
		
		return url.toString();
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
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getColoniaSolicitante() {
		return coloniaSolicitante;
	}
	public void setColoniaSolicitante(String coloniaSolicitante) {
		this.coloniaSolicitante = coloniaSolicitante;
	}
	public String getDireccionSolicitante() {
		return direccionSolicitante;
	}
	public void setDireccionSolicitante(String direccionSolicitante) {
		this.direccionSolicitante = direccionSolicitante;
	}
	public String getMedioContacto() {
		return medioContacto;
	}
	public void setMedioContacto(String medioContacto) {
		this.medioContacto = medioContacto;
	}
	public String getInfoAdicionalContacto() {
		return infoAdicionalContacto;
	}
	public void setInfoAdicionalContacto(String infoAdicionalContacto) {
		this.infoAdicionalContacto = infoAdicionalContacto;
	}
	public String getFolioPeticion() {
		return folioPeticion;
	}
	public void setFolioPeticion(String folioPeticion) {
		this.folioPeticion = folioPeticion;
	}
	public Date getFechaCapturaPeticion() {
		return fechaCapturaPeticion;
	}
	public void setFechaCapturaPeticion(Date fechaCapturaPeticion) {
		this.fechaCapturaPeticion = fechaCapturaPeticion;
	}
	public String getDescripcionPeticion() {
		return descripcionPeticion;
	}
	public void setDescripcionPeticion(String descripcionPeticion) {
		this.descripcionPeticion = descripcionPeticion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getObservacionesPeticion() {
		return observacionesPeticion;
	}
	public void setObservacionesPeticion(String observacionesPeticion) {
		this.observacionesPeticion = observacionesPeticion;
	}

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public List<Archivos> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivos> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public ServicioComun getServicioComun() {
		return servicioComun;
	}

	public void setServicioComun(ServicioComun servicioComun) {
		this.servicioComun = servicioComun;
	}

	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}
	
	
	
	

}
