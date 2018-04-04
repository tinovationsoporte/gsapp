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

@ManagedBean(name="validaReporteActividadDetalleBean")
@ViewScoped
public class ValidaReporteActividadDetalleBean extends MBeanAbstracto {

	private String TEXTO_INICIAL =  "<div style=\"\"><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">Estimado <span style=\"font-weight: bold;\">#{usuarioReporte}</span>,</span></font></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\"><br></span></font></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">En respuesta a las actividades antes citadas hago de su conocimiento las siguientes observaciones:</span></font></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\"><br></span></font></div><div style=\"\"><span style=\"font-style: italic;\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">&lt;&nbsp;</span></font><span style=\"font-family: Arial, Verdana; font-size: 13.3333px;\">eliminar este texto y definir los comentarios aquí. Se recomienda hacer uso de las herramientas como viñetas &gt;</span></span></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\"><br></span></font></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\">Reitero mi agradecimiento a su dedicación&nbsp; y esfuerzo en llevar a cabo las labores pertinentes que permiten cumplir con los objetivos de esta administración.</span></font></div><div style=\"\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px;\"><br></span></font></div><div style=\"text-align: center;\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px; font-weight: bold;\">#{usuario}</span></font></div><div style=\"text-align: center;\"><font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333px; font-weight: bold;\">Atte.</span></font></div></div>";
	
	private ScheduleModel modeloCalendario;
	private String textoEditor;	
	private String textoObservaciones;	
	private UsuarioSistema usuario;
	private ReporteActividad reporte;
	
	private Boolean agregarComentarios;
	private String  textoBtnGuardar = "Validar";
	private Boolean mostrarPanelComentarios;	
	
	private Boolean mostrarBotones;
	private Boolean habilitarCaptura;
		
	@ManagedProperty(value="#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	@ManagedProperty(value="#{servicioReportes}")
	private ServicioReportes servicioReportes;

	/**
	 * 
	 */
	@PostConstruct
    public void init() {		
		try{			
			System.out.println("validarReporteActividadDetallBean init() ...");	
			
			usuario = (UsuarioSistema)getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
			
			textoBtnGuardar = "Validar";
			
			mostrarPanelComentarios = false;		
			
			Integer idReporte = Integer.valueOf(getRequestParameter("idReporte").toString());
			
			ObtenerCatalogoPeticion peticion = new ObtenerCatalogoPeticion();
			peticion.setClase(ReporteActividad.class);
			peticion.setIdEntidad(idReporte);
			reporte = (ReporteActividad) servicioCatalogo.obtenerCatalogo(peticion ).getEntidad();
			
			textoEditor  = reporte.getContenido();		
			
			System.out.println("statusReporte -> " + reporte.getStatusReporteActividad().getId());
			
			if(reporte.getStatusReporteActividad().getId() == 2 || reporte.getStatusReporteActividad().getId() == 3){//Capturado o revisado
				
				TEXTO_INICIAL = TEXTO_INICIAL.replace("#{usuario}", usuario.getNombre());
				TEXTO_INICIAL = TEXTO_INICIAL.replace("#{usuarioReporte}", reporte.getUsuario().getNombre());
				
				textoObservaciones = TEXTO_INICIAL;
			}
			
			if(reporte.getStatusReporteActividad().getId() == 4){//Revisado - con observaciones				
				textoObservaciones = reporte.getObservaciones();				
				setMostrarPanelComentarios(true);
				setAgregarComentarios(true);								
			}		
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("reporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrio un error al obtener el reporte de actividades. " + ex.getMessage(),""));
		}	
		        
    }
	
	public void definirComentarios(){		
		setMostrarPanelComentarios(agregarComentarios);
		setTextoBtnGuardar(agregarComentarios ? "Agregar Comentarios" : "Validar");		
	}
	
	public void guardarReporte(ActionEvent e){		
		try{
			
			if(getTextoObservaciones() == null || getTextoObservaciones().isEmpty()){
				FacesContext.getCurrentInstance().addMessage("detalleReporte_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"Es necesario capturar la informacion del reporte","" ));
				return;
			}
			
			ObtenerCatalogoPeticion obtenerCatalogoPeticion = new ObtenerCatalogoPeticion();
			obtenerCatalogoPeticion.setClase(StatusReporteActividad.class);
			
			
			
			obtenerCatalogoPeticion.setIdEntidad(agregarComentarios ? 28 :  27);			
			
			StatusReporteActividad statusReporteActividad = (StatusReporteActividad) servicioCatalogo.obtenerCatalogo(obtenerCatalogoPeticion ).getEntidad();
			
			reporte.setStatusReporteActividad(statusReporteActividad);
			reporte.setObservaciones(agregarComentarios? getTextoObservaciones(): null);
			
			ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
			peticion.setEntidad(reporte);
			servicioCatalogo.actualizarCatalogo(peticion );
			
			FacesContext.getCurrentInstance().addMessage("detalleReporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Los comentarios han sido guardado","" ));
			
		}catch(Exception ex ){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("detalleReporte_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al guardar el reporte. "  + ex.getMessage(),"" ));
		}
		
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
	
	public ReporteActividad getReporte() {
		return reporte;
	}

	public void setReporte(ReporteActividad reporte) {
		this.reporte = reporte;
	}

	public ServicioReportes getServicioReportes() {
		return servicioReportes;
	}

	public void setServicioReportes(ServicioReportes servicioReportes) {
		this.servicioReportes = servicioReportes;
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
