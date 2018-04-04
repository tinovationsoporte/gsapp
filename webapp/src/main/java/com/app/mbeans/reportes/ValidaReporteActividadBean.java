package com.app.mbeans.reportes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.schedule.Schedule;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.ReporteActividad;
import com.modelo.datos.app.StatusReporteActividad;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.ReportesActividades;
import com.modelo.datos.estructuras.TotalReportes;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaRespuesta;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.reportes.ServicioReportes;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="validaReporteActividadBean")
@ViewScoped
public class ValidaReporteActividadBean extends MBeanAbstracto {

	
	
	private ScheduleModel modeloCalendario;
	private String textoEditor;	
	private String textoObservaciones;	
	private UsuarioSistema usuario;
	private ReporteActividad reporteSeleccionado;
	
	private Boolean agregarComentarios;
	private String  textoBtnGuardar = "Validar";
	private Boolean mostrarPanelComentarios;
	
	
	private Boolean mostrarBotones;
	private Boolean habilitarCaptura;
	
	private List<ReportesActividades> listaReportes;
	private List<TotalReportes> listaTotalReportes;
	
	
	@ManagedProperty(value="#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	@ManagedProperty(value="#{servicioReportes}")
	private ServicioReportes servicioReportes;

	/**
	 * 
	 */
	@PostConstruct
    public void init() {
		
		System.out.println("Calendario bean init() ...");		
		
		modeloCalendario = new DefaultScheduleModel();
		
		usuario = (UsuarioSistema)getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
		
		textoBtnGuardar = "Validar";
		
		mostrarPanelComentarios = false;
		
		agregarComentarios = false;
		
		cargarFechas();		
		        
    }
	
	
	
	private void cargarFechas(){
		
		try{			
			
			ConsultarReportesPorFechaRespuesta respuesta = servicioReportes.consultarFechasReportes(new ConsultarReportesPorFechaPeticion());
			
			List<ReportesActividades> listaReportes = respuesta.getListaReportes();
			
			for(ReportesActividades reporte : listaReportes){
				
				DefaultScheduleEvent eventoReporte = new DefaultScheduleEvent();
				
				eventoReporte.setStartDate(reporte.getFechaInicial());
				eventoReporte.setEndDate(reporte.getFechaInicial());				
				
				eventoReporte.setTitle(reporte.getStatus().toLowerCase().equals("terminado")?"Revisión Terminada" : "Revisión Pendiente");			
				
				eventoReporte.setStyleClass(reporte.getStatus().toLowerCase().equals("terminado")?"revisado" : "observaciones" );			
				
				modeloCalendario.addEvent(eventoReporte);
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrio un error al cargar los reportes de actividades. " + e.getMessage(),""));
		}
		
		
	}
	
	
	public void onEventSelect(SelectEvent selectEvent) {
		
		ScheduleEvent e = (ScheduleEvent) selectEvent.getObject();
		
		try{			
			ConsultarReportesPorFechaPeticion peticion = new ConsultarReportesPorFechaPeticion();			
			listaReportes = consultarReportesPorFecha(e.getStartDate());
			listaTotalReportes = consultarTotalReportes(e.getStartDate());
		
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrio un error al cargar las  fechas  de los reportes de actividades. " + ex.getMessage(),""));
		}	
    }
	
	
	private List<ReportesActividades> consultarReportesPorFecha(Date fechaInicial) throws ExcepcionServicioFachada{		
		ConsultarReportesPorFechaPeticion peticion = new ConsultarReportesPorFechaPeticion();
		peticion.setFechaInicial(fechaInicial);			
		return servicioReportes.consultarReportesPorFecha(peticion).getListaReportes();		
	}
	
	private List<TotalReportes> consultarTotalReportes(Date fechaInicial) throws ExcepcionServicioFachada{		
		ConsultarTotalReportesPeticion peticion = new ConsultarTotalReportesPeticion();
		peticion.setFechaInicial(fechaInicial);			
		return servicioReportes.consultarTotalReportes(peticion).getListaTotalesReportes();		
	}

		
	public Boolean getMostrarBotones() {
		return mostrarBotones;
	}



	public void setMostrarBotones(Boolean mostrarBotones) {
		this.mostrarBotones = mostrarBotones;
	}


	public Boolean getHabilitarCaptura() {
		return habilitarCaptura;
	}



	public void setHabilitarCaptura(Boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}



	public ScheduleModel getModeloCalendario() {
		return modeloCalendario;
	}

	public void setModeloCalendario(ScheduleModel modeloCalendario) {
		this.modeloCalendario = modeloCalendario;
	}



	public String getTextoEditor() {
		return textoEditor;
	}



	public void setTextoEditor(String textoEditor) {
		this.textoEditor = textoEditor;
	}



	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}



	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}



	public ReporteActividad getReporteSeleccionado() {
		return reporteSeleccionado;
	}



	public void setReporteSeleccionado(ReporteActividad reporteSeleccionado) {
		this.reporteSeleccionado = reporteSeleccionado;
	}



	public ServicioReportes getServicioReportes() {
		return servicioReportes;
	}



	public void setServicioReportes(ServicioReportes servicioReportes) {
		this.servicioReportes = servicioReportes;
	}



	public List<ReportesActividades> getListaReportes() {
		return listaReportes;
	}



	public void setListaReportes(List<ReportesActividades> listaReportes) {
		this.listaReportes = listaReportes;
	}



	public List<TotalReportes> getListaTotalReportes() {
		return listaTotalReportes;
	}



	public void setListaTotalReportes(List<TotalReportes> listaTotalReportes) {
		this.listaTotalReportes = listaTotalReportes;
	}



	public String getTextoObservaciones() {
		return textoObservaciones;
	}



	public void setTextoObservaciones(String textoObservaciones) {
		this.textoObservaciones = textoObservaciones;
	}



	public Boolean getAgregarComentarios() {
		return agregarComentarios;
	}



	public void setAgregarComentarios(Boolean agregarComentarios) {
		this.agregarComentarios = agregarComentarios;
	}



	public String getTextoBtnGuardar() {
		return textoBtnGuardar;
	}



	public void setTextoBtnGuardar(String textoBtnGuardar) {
		this.textoBtnGuardar = textoBtnGuardar;
	}



	public Boolean getMostrarPanelComentarios() {
		return mostrarPanelComentarios;
	}



	public void setMostrarPanelComentarios(Boolean mostrarPanelComentarios) {
		this.mostrarPanelComentarios = mostrarPanelComentarios;
	}
	
	
	
}
