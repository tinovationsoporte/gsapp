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
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;

@ManagedBean(name="capturaReporteActividadBean")
@ViewScoped
public class CapturaReporteActividadBean extends MBeanAbstracto {

	private String TEXTO_INICIAL = "<div style=\"font-weight: normal;\"><span style=\"font-weight: bold;\">ING. JORGE VERA HERNÁNDEZ</span></div><div style=\"font-weight: normal;\"><span style=\"font-weight: 700;\">PRESIDENTE MPAL DE ÁLAMO TEMAPACHE, VER.</span></div><div style=\"font-weight: normal;\"><span style=\"font-weight: bold;\">PRESENTE.</span></div><div style=\"font-weight: normal;\"><br></div><div style=\"\">El que&nbsp; suscribe&nbsp;<span style=\"font-weight: bold;\">#{usuario}</span> director del área <span style=\"font-weight: bold;\">#{area}</span> tiene a bien informarle las actividades planeadas para la fecha&nbsp;<span style=\"font-weight: bold;\">#{periodo}</span>.</div><div style=\"font-weight: normal;\"><br></div><div style=\"font-weight: normal;\"><span style=\"font-style: italic;\">&lt; eliminar este texto y definir las actividades aquí. Se recomienda hacer uso de las herramientas como viñetas &gt;</span></div><div style=\"font-weight: normal;\"><br></div><div style=\"font-weight: normal;\">Sin mas por el momento, quedo al pendiente de las observaciones con respecto a lo planeado.</div><div style=\"font-weight: normal;\"><br></div><div style=\"font-weight: normal; text-align: center;\"><span style=\"font-weight: bold;\">#{usuario}</span></div><div style=\"font-weight: normal; text-align: center;\"><span style=\"font-weight: bold;\">Atte.</span></div>";
	
	private ScheduleModel modeloCalendario;
	private String textoEditor;
	private String textoObservaciones;	
	private UsuarioSistema usuario;
	private ReporteActividad reporteSeleccionado;
	private ScheduleEvent scheduleEvent;

	private Boolean mostrarBotones;
	private Boolean habilitarCaptura;
	private Boolean mostrarObservaciones;
	
	@ManagedProperty(value="#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;

	/**
	 * 
	 */
	@PostConstruct
    public void init() {
		
		System.out.println("capturaReporteActividadBean init() ...");		
		
		modeloCalendario = new DefaultScheduleModel();
		
		usuario = (UsuarioSistema)getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
		
		cargarFechas(usuario);			

	}	
	
	
	private void cargarFechas(UsuarioSistema _usuario){
		System.out.println("cargarFecha() ...");
		
		ReporteActividad reporteActividad = new ReporteActividad();
		reporteActividad.setUsuario(_usuario);
		
		ConsultarCatalogoPeticion peticion= new ConsultarCatalogoPeticion();
		peticion.setEntidad(reporteActividad);
		peticion.setClase(ReporteActividad.class);
		
		try{			
						
			ConsultarCatalagoRespuesta respuesta = servicioCatalogo.consultarCatalogo(peticion);			
			
			List<ReporteActividad> reportes = respuesta.getEntidades();
			
			if(reportes!= null && reportes.size() > 0){
			
				for(ReporteActividad reporte: reportes){			
					
					DefaultScheduleEvent eventoReporte = new DefaultScheduleEvent();
					eventoReporte.setStartDate(reporte.getFechaInicial());
					eventoReporte.setEndDate(reporte.getFechaFinal());
					eventoReporte.setData(reporte);
					eventoReporte.setTitle("Reporte Actividad");
					
					if(reporte.getStatusReporteActividad().getId() == 1)//Por Capturar
						eventoReporte.setStyleClass("porCapturar");
					
					if(reporte.getStatusReporteActividad().getId() == 2)//Capturado
						eventoReporte.setStyleClass("capturado");
					
					if(reporte.getStatusReporteActividad().getId() == 3)//Revisado
						eventoReporte.setStyleClass("revisado");			
					
					if(reporte.getStatusReporteActividad().getId() == 4)//Revisado con observaciones
						eventoReporte.setStyleClass("observaciones");			
					
					modeloCalendario.addEvent(eventoReporte);			
				}
				
			}else{				
				FacesContext.getCurrentInstance().addMessage("reporte_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontro informacion para la fecha seleccionada. ",""));				
			}			
					
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrio un error al cargar los reportes de actividades. " + e.getMessage(),""));
		}
		
		
	}
	
	public void guardarReporte(ActionEvent e){
		
		try{
			
			if(getTextoEditor() == null || getTextoEditor().isEmpty()){
				FacesContext.getCurrentInstance().addMessage("reporte_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"Es necesario capturar la informacion del reporte","" ));
				return;
			}
			
			ObtenerCatalogoPeticion obtenerCatalogoPeticion = new ObtenerCatalogoPeticion();
			obtenerCatalogoPeticion.setClase(StatusReporteActividad.class);
			obtenerCatalogoPeticion.setIdEntidad(26);
			StatusReporteActividad statusReporteActividad = (StatusReporteActividad) servicioCatalogo.obtenerCatalogo(obtenerCatalogoPeticion ).getEntidad();
			
			reporteSeleccionado.setStatusReporteActividad(statusReporteActividad);
			reporteSeleccionado.setContenido(getTextoEditor());
			reporteSeleccionado.setArea(usuario.getArea());
			
			ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
			peticion.setEntidad(reporteSeleccionado);
			servicioCatalogo.actualizarCatalogo(peticion );
			
			((DefaultScheduleEvent)scheduleEvent).setStyleClass("capturado");
			
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"El reporte ha sido guardado","" ));
			
		}catch(Exception ex ){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al guardar el reporte. "  + ex.getMessage(),"" ));
		}
		
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		
		scheduleEvent = (ScheduleEvent) selectEvent.getObject();
		reporteSeleccionado = (ReporteActividad)scheduleEvent.getData();
		textoEditor = reporteSeleccionado.getContenido();
		
		
		FacesMessage facesMessage = null;		
		
		StringBuilder sb = new StringBuilder("El Reporte de actividades con fecha ")
				.append(new SimpleDateFormat("dd/MM/yyyy").format(reporteSeleccionado.getFechaInicial())).append(" ");		
		
		if(reporteSeleccionado.getStatusReporteActividad().getId() == 1){//Por Capturar
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, sb.append("aun no esta Capturado").toString() ,"" );
			textoEditor = TEXTO_INICIAL.replace("#{periodo}", new SimpleDateFormat("dd/MM/yyyy").format(scheduleEvent.getStartDate()))
					.replace("#{usuario}", usuario.getNombre())
					.replace("#{area}", usuario.getArea().getNombre());
			
			
			textoObservaciones = null;
			setHabilitarCaptura(true);			
			setMostrarBotones(true);
			setMostrarObservaciones(false);
		}
		
		if(reporteSeleccionado.getStatusReporteActividad().getId() == 2){//Capturado
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, sb.append("ya fue Capturado").toString() ,"" );
			textoEditor = reporteSeleccionado.getContenido();
			textoObservaciones = null;
			setHabilitarCaptura(true);
			setMostrarBotones(true);
			setMostrarObservaciones(false);
		}
		
		if(reporteSeleccionado.getStatusReporteActividad().getId() == 3){//Revisado
			facesMessage  = new FacesMessage(FacesMessage.SEVERITY_INFO, sb.append("ha sido Revisado").toString() ,"" );
			textoEditor = reporteSeleccionado.getContenido();
			textoObservaciones = reporteSeleccionado.getObservaciones();
			setHabilitarCaptura(false);
			setMostrarBotones(false);
			setMostrarObservaciones(false);
		}
		
		
		if(reporteSeleccionado.getStatusReporteActividad().getId() == 4){//Revisado con observaciones
			facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, sb.append("ha sido Revisado con Observaciones").toString(),"");
			textoEditor = reporteSeleccionado.getContenido();
			textoObservaciones = reporteSeleccionado.getObservaciones();
			setHabilitarCaptura(false);
			setMostrarBotones(false);
			setMostrarObservaciones(true);
		}
		
		FacesContext.getCurrentInstance().addMessage("reporte_msges", facesMessage);	
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



	public Boolean getMostrarObservaciones() {
		return mostrarObservaciones;
	}



	public void setMostrarObservaciones(Boolean mostrarObservaciones) {
		this.mostrarObservaciones = mostrarObservaciones;
	}



	public String getTextoObservaciones() {
		return textoObservaciones;
	}



	public void setTextoObservaciones(String textoObservaciones) {
		this.textoObservaciones = textoObservaciones;
	}
	
	
	
}
