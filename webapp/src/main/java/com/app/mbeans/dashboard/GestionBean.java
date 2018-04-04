package com.app.mbeans.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

import com.app.mbeans.AplicacionBean;
import com.core.app.ExcepcionAplicacion;
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
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.objetos.transf.datos.app.peticion.ActualizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionPeticion;
import com.objetos.transf.datos.app.peticion.AgregarMovimientoGestionRespuesta;
import com.objetos.transf.datos.app.peticion.AgregarProcesoPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="gestionBean")
@ViewScoped
public class GestionBean extends MBeanAbstracto{
	
	private final String RPT_PETICION = "folioEntregaRpt";
	private final String RPT_FORMAT_PDF = "pdf";
	private String urlReportePeticion = null;
	private String URL_REPORTES; //= "http://localhost:8080/webreports/JasperReportServlet?";
	
	
	
	//Datos del Area de gestion
	private String operador;
	private Date fechaMovimiento;
	private String movimiento;
	private String comentario;
	private String descripcionEntrega;
	private List<MovimientosProceso> listaMovimientos;
	private ProcesoPeticion movimientoSeleccionado;
	
	private Boolean mostrarBtnImprimirEntrega;	
	private Boolean habilitarBtnImprimirEntrega;								
	private Boolean habilitarDescripcionEntrega;
	
	private List<Archivos> listaArchivos;
	
	private List<Archivo> listaArchivosAGuardar;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;	
	
	@ManagedProperty(value = "#{servicioComun}")
	private ServicioComun servicioComun;	
	
	private Peticion peticion;
	
	private Boolean mostrarEvidencias;
	
	private String idProcesoPeticion;
	
	private Boolean mostrarLinkRemover;
	
	private Boolean mostrarLinkNombreArchivo;
	
	private Boolean mostrarFileUploadBtn;
	
	private Boolean mostrarSeccionGestion;
	
	private Boolean mostrarLinkArchivos;
	
	private Boolean mostrarBtnTerminarGestion;
	
	private Boolean mostrarBtnAgregarEvidencia;
	
	private Boolean mostrarCargaArchivos;
	
	private Boolean habilitarCaptura;
	
	private Boolean habilitarBtnAgregarEvidencia;
	
	private Integer idUsuarioSistema;
	
	private Boolean mostarBtnCerrarGestion;
	
	private Boolean mostrarLinkRemoverMovimiento;
	
	private Boolean habilitarBtnCerrarGestion;
	
	private Integer idStatusPeticionActual;
	
	private Boolean mostrarBtnRechazarGestion;
	
	private Boolean  habilitarBtnRechazarGestion;
	
	private Boolean habilitarBtnTerminarGestion;
	
	private Integer idPeticion;
	
	@PostConstruct
	public void inicializar(){
		
		System.out.println("***\n\ngestionBean.inicializar()...\n\n");

		try {
		
			AplicacionBean app = (AplicacionBean) getManagedBean("#{aplicacion}", AplicacionBean.class);
			
			URL_REPORTES =  app.getUrlReportes();
			
			idPeticion = Integer.parseInt(getRequestParameter("idPeticionDashboard").toString());
			
			System.out.println("***\n\nidPeticion -> " + idPeticion);

			
			peticion = consultarPeticion(idPeticion);			
			
			idUsuarioSistema = ((UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class) ).getIdUsuarioSistema();	
	
			idStatusPeticionActual = peticion.getStatusPeticion().getIdStatusPeticion();	
			
			System.out.println("***\n\nidStatusPeticionActual ->" + idStatusPeticionActual);

							
			setOperador( peticion.getUsuarioAdicional().getNombre());
		
			setDescripcionEntrega(peticion.getDescripcionEntrega());
			
			inicializarListaMovimientos(getPeticion().getIdPeticion());			
		
			inicializarListaArchivos(getPeticion().getIdPeticion());
			
			setMostrarSeccionGestion(true);
			
			//setMostrarLinkRemover(listaArchivos.size() == 0);
			setMostrarLinkRemover(idStatusPeticionActual ==11 ||idStatusPeticionActual ==13 || idStatusPeticionActual == 21);
			
			//setMostrarLinkNombreArchivo(listaArchivos.size() > 0);
			setMostrarLinkNombreArchivo((listaArchivos.size() > 0) & (idStatusPeticionActual ==15 || idStatusPeticionActual == 17 ||  idStatusPeticionActual == 21));
				
			
			//setMostrarFileUploadBtn(listaArchivos.size() == 0);
			
			setMostrarFileUploadBtn((idStatusPeticionActual ==11 || idStatusPeticionActual ==13 || idStatusPeticionActual == 21));
			/*no se usa
			    setMostrarLinkArchivos(listaArchivos.size() > 0);			 
				setMostrarLinkRemoverMovimiento(peticion.getStatusPeticion().getIdStatusPeticion() == 13);
				setMostrarLinkRemoverMovimiento(idStatusPeticionActual == 13);
			*/
			
			
			setMostrarCargaArchivos(peticion.getRequiereEvidencia() == 1);
			setMostrarBtnAgregarEvidencia(peticion.getRequiereEvidencia()== 1);
				
			setMostrarBtnTerminarGestion((peticion.getRequiereEvidencia() == 0) & (idStatusPeticionActual == 11 || idStatusPeticionActual == 13 || idStatusPeticionActual == 21));
			setHabilitarBtnTerminarGestion((peticion.getRequiereEvidencia() == 0) & (idStatusPeticionActual == 11 || idStatusPeticionActual == 13 || idStatusPeticionActual == 21));
	
				
			setMostarBtnCerrarGestion(peticion.getStatusPeticion().getIdStatusPeticion() == 15);
			setMostrarBtnRechazarGestion(peticion.getStatusPeticion().getIdStatusPeticion() == 15);	
			
			setHabilitarBtnCerrarGestion(peticion.getStatusPeticion().getIdStatusPeticion() == 15);
			setHabilitarBtnRechazarGestion(peticion.getStatusPeticion().getIdStatusPeticion() == 15);
				
			//setHabilitarBtnAgregarEvidencia(listaMovimientos.size() > 0 & listaArchivos.size() == 0);
			setHabilitarBtnAgregarEvidencia((listaMovimientos.size() > 0) & (idStatusPeticionActual ==11 || idStatusPeticionActual ==13 || idStatusPeticionActual == 21) );	
			
			//setHabilitarCaptura(listaArchivos.size() == 0);
			setHabilitarCaptura(idStatusPeticionActual == 11 || idStatusPeticionActual == 13 || idStatusPeticionActual == 21);
				
			setMostrarBtnImprimirEntrega(idStatusPeticionActual == 13 || idStatusPeticionActual == 21);
			setHabilitarBtnImprimirEntrega(listaMovimientos.size() > 0);
			
				//setMostrarEvidencias(false);
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al inicializar los catalgos. " + e.getMessage(),"" ));				
				
		}	
	}
	
	public void imprimirEntrega(ActionEvent e){
		
		if(getDescripcionEntrega() == null || getDescripcionEntrega().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"La descripcion de la entrega no puede estar vacia.",""));
						
		}else{
			ActualizarPeticionesPeticion peticiones = new ActualizarPeticionesPeticion();
			try{
				peticiones.setIdPeticion(getPeticion().getIdPeticion());
				peticiones.setDescripcionEntrega(getDescripcionEntrega());				
				
				servicioPeticiones.actualizarPeticion(peticiones );
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se guardo la descripcion de la entrega",""));
				
				RequestContext.getCurrentInstance().execute("openReport('"+ crearUrlReporteEnrega() +"')");
				
			}catch (Exception ex ){
			
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al actualizar la peticion. " + ex.getMessage(),"" ));
			}
			
			
		}
	}
	
	public void agregarMovimiento(ActionEvent e){
		
		if(camposCapturados()){
			try {					
				
				AgregarMovimientoGestionPeticion agregarMovimientoGestionPeticion = new  AgregarMovimientoGestionPeticion();
				agregarMovimientoGestionPeticion.setComentario(getComentario().toUpperCase());
				agregarMovimientoGestionPeticion.setFecha(new Date());
				
				//Si el status de la peticion es ASIGNADA (11) lo cambia a EN GESTION(13), si status es EN GESTION se mantiene igual
				agregarMovimientoGestionPeticion.setIdPeticion(getPeticion().getIdPeticion());
				
				agregarMovimientoGestionPeticion.setIdStatusActualPeticion(idStatusPeticionActual);
				
				agregarMovimientoGestionPeticion.setIdUsuarioSistema(getIdUsuarioSistema());
				agregarMovimientoGestionPeticion.setMovimiento("GESTION");
				
				AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = 
								servicioPeticiones.agregarMovimientoGestion(agregarMovimientoGestionPeticion );
				
				idStatusPeticionActual = agregarMovimientoGestionRespuesta.getIdStatusActualPeticion();
				
				inicializarListaMovimientos(peticion.getId());//
				
				limpiarCampos();				
				
				setHabilitarBtnAgregarEvidencia(true);
				//setHabilitarBtnTerminarGestion(true);
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agrego el movimiento. " + agregarMovimientoGestionRespuesta.getMovimiento() ,"" ));				
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al agregar el movimiento. " + ex.getMessage(),"" ));				
				
			}
		}
		
	}
	
	
	public void eliminarMovimiento(MovimientosProceso movimiento){
		try {
			System.out.println("\n\nmovimiento -> " + movimiento + "\n\n");
			
			if (movimiento.getMovimiento().equals("RECHAZO") || movimiento.getMovimiento().equals("EVIDENCIA")){
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se puede eliminar un movimiento de " + movimiento.getMovimiento(),"" ));
			}else{
				
				servicioPeticiones.removerProcesoPeticion(movimiento.getIdProcesoPeticion());		
				//listaMovimientos.remove(movimiento);
				inicializarListaMovimientos(peticion.getId());
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha eliminado el movimento. ","" ));				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al eliminar el movimiento. " + e.getMessage(),"" ));				
			
		}
		
	}
	
	public void agregarEvidencia(ActionEvent e){
		
		if(tieneArchivos() && tieneMovimientos()){
			
			try {
				
				
				AgregarMovimientoGestionPeticion agregarMovimientoGestionPeticion = new  AgregarMovimientoGestionPeticion();
				agregarMovimientoGestionPeticion.setComentario("SE TERMINA LA GESTION - EVIDENCIAS");
				agregarMovimientoGestionPeticion.setFecha(new Date());
				agregarMovimientoGestionPeticion.setIdPeticion(getPeticion().getIdPeticion());
				agregarMovimientoGestionPeticion.setIdUsuarioSistema(getIdUsuarioSistema());
				//agregarMovimientoGestionPeticion.setIdStatusActualPeticion(getPeticion().getStatusPeticion().getIdStatusPeticion());
				
				agregarMovimientoGestionPeticion.setIdStatusActualPeticion(idStatusPeticionActual);

				agregarMovimientoGestionPeticion.setMovimiento("EVIDENCIA");
				agregarMovimientoGestionPeticion.setListaArchivos(copiarArchivos(getListaArchivos()));				
				
				AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = 
								servicioPeticiones.agregarMovimientoGestion(agregarMovimientoGestionPeticion );				
								
				inicializarListaMovimientos(getPeticion().getIdPeticion());
				
				inicializarListaArchivos(getPeticion().getIdPeticion());
				
				idStatusPeticionActual = agregarMovimientoGestionRespuesta.getIdStatusActualPeticion();
				
				setMostrarLinkRemover(false);
				setMostrarLinkArchivos(true);
				setMostrarFileUploadBtn(false);
				setHabilitarCaptura(false);
				setHabilitarBtnAgregarEvidencia(false);
				setMostrarLinkNombreArchivo(true);

				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agrego el movimiento EVIDENCIA","")); //+ agregarMovimientoGestionRespuesta.getMovimiento() ,"" ));		
			
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al agregar la evidencia. " + ex.getMessage(),"" ));				
				
			}
			
		}
		
	}


	public void terminarGestion(ActionEvent e){		
		
		
		if(tieneMovimientos()){
			try {
				
				
				AgregarMovimientoGestionPeticion agregarMovimientoGestionPeticion = new  AgregarMovimientoGestionPeticion();
				agregarMovimientoGestionPeticion.setComentario("SE TERMINA LA GESTION - NO REQUIERE EVIDENCIA");
				agregarMovimientoGestionPeticion.setFecha(new Date());
				agregarMovimientoGestionPeticion.setIdPeticion(getPeticion().getIdPeticion());
				agregarMovimientoGestionPeticion.setIdUsuarioSistema(getIdUsuarioSistema());				
				agregarMovimientoGestionPeticion.setIdStatusActualPeticion(idStatusPeticionActual);
				agregarMovimientoGestionPeticion.setMovimiento("TERMINADA");
				
				AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = 
								servicioPeticiones.agregarMovimientoGestion(agregarMovimientoGestionPeticion );				
								
				idStatusPeticionActual = agregarMovimientoGestionRespuesta.getIdProcesoPeticion();
				
				inicializarListaMovimientos(getPeticion().getIdPeticion());

				
				setMostrarLinkRemover(false);
				setMostrarLinkArchivos(true);
				setMostrarFileUploadBtn(false);
				setHabilitarCaptura(false);
				setHabilitarBtnAgregarEvidencia(false);
				setHabilitarBtnTerminarGestion(false);
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha terminado la gestion.","" ));		
			
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al agregar la evidencia. " + ex.getMessage(),"" ));				
				
			}
		}		
	}
	
	
	public void rechazarGestion(ActionEvent e){		
		
		
		if(tieneMovimientos()){
			try {
				
				
				AgregarMovimientoGestionPeticion agregarMovimientoGestionPeticion = new  AgregarMovimientoGestionPeticion();
				agregarMovimientoGestionPeticion.setComentario("SE RECHAZA LA GESTION");
				agregarMovimientoGestionPeticion.setFecha(new Date());
				agregarMovimientoGestionPeticion.setIdPeticion(getPeticion().getIdPeticion());
				agregarMovimientoGestionPeticion.setIdUsuarioSistema(getIdUsuarioSistema());				
				agregarMovimientoGestionPeticion.setIdStatusActualPeticion(idStatusPeticionActual);
				agregarMovimientoGestionPeticion.setMovimiento("RECHAZO");
				
				AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = 
								servicioPeticiones.agregarMovimientoGestion(agregarMovimientoGestionPeticion );				
								
				idStatusPeticionActual = agregarMovimientoGestionRespuesta.getIdProcesoPeticion();
				
				inicializarListaMovimientos(getPeticion().getIdPeticion());

				
				setMostrarLinkRemover(false);
				setMostrarLinkArchivos(true);
				setMostrarFileUploadBtn(false);
				setHabilitarCaptura(false);
				setHabilitarBtnAgregarEvidencia(false);
				
				
				setHabilitarBtnRechazarGestion(false);
				setHabilitarBtnCerrarGestion(false);
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se rechazo la gestion.","" ));		
			
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al agregar la evidencia. " + ex.getMessage(),"" ));				
				
			}
		}		
	}

	
	
	public void cerrarGestion(ActionEvent e){
		if(tieneMovimientos()){
			try {
				
				
				AgregarMovimientoGestionPeticion agregarMovimientoGestionPeticion = new  AgregarMovimientoGestionPeticion();
				agregarMovimientoGestionPeticion.setComentario("SE CIERRA LA GESTION");
				agregarMovimientoGestionPeticion.setFecha(new Date());
				
				agregarMovimientoGestionPeticion.setIdPeticion(getPeticion().getIdPeticion());
				
				agregarMovimientoGestionPeticion.setIdUsuarioSistema(getIdUsuarioSistema());				
				
				agregarMovimientoGestionPeticion.setIdStatusActualPeticion(idStatusPeticionActual);
				
				agregarMovimientoGestionPeticion.setMovimiento("CIERRE");
				AgregarMovimientoGestionRespuesta agregarMovimientoGestionRespuesta = 
								servicioPeticiones.agregarMovimientoGestion(agregarMovimientoGestionPeticion );				
								
				inicializarListaMovimientos(peticion.getIdPeticion());
				
				setMostrarLinkRemover(false);
				setMostrarLinkArchivos(true);
				setMostrarFileUploadBtn(false);
				setHabilitarCaptura(false);
				setHabilitarBtnAgregarEvidencia(false);
				setHabilitarBtnCerrarGestion(false);
				setHabilitarBtnRechazarGestion(false);
				
				
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha cerrado la gestion.","" ));		
			
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("gestion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al agregar la evidencia. " + ex.getMessage(),"" ));				
				
			}
		}
		
	}		

	
	public void cargarArchivo(FileUploadEvent event) {
	 	
		event.getComponent().setTransient(false);
		
		UploadedFile archivo = event.getFile();	 	 	
	 	
	 	try{
	 		
	 		Archivos archivoTmp = new Archivos();
	 		
	 		archivoTmp.setNombreArchivo(archivo.getFileName());
	 		archivoTmp.setBlob(IOUtils.toByteArray(archivo.getInputstream()));
	 		archivoTmp.setNuevo(true);
	 		
	 		listaArchivos.	add(archivoTmp);	 		
		 	
	 	}catch(Exception e){
	 		
	 		FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al adjuntar el archivo" +  e.getMessage(), ""));
	 	}
	 	
	}
	
	public void removerArchivo(Archivos archivos){
		
		listaArchivos.remove(archivos);
	
	}	
	
	@Override
	public void resetBean(ActionEvent e) throws ExcepcionAplicacion {
		System.out.println("\n\n\n\t **** resetando consultaDetalleBean");
			
		DashboardBean dashboardBean = (DashboardBean) getViewContext().get("dashboardBean");
		System.out.println("\n\n\n\t **** dashboardBean ->" + dashboardBean);
		dashboardBean.buscarFolio(e);			
				
		super.resetBean(e);
	}
	
	private Peticion consultarPeticion(Integer idPeticion) throws Exception{
		Peticion peticion = new Peticion();
		
		peticion.setId(idPeticion);;
		
		List<Peticion> listaPeticiones = consultarCatalogo(Peticion.class, peticion);
		
		if(listaPeticiones == null || listaPeticiones.size() == 0){
			peticion = null;
		}else{		
			if(listaPeticiones.size() > 1){
				
				throw new Exception("Existe mas de una peticion para el folio");
			
			}else{
				
				peticion = listaPeticiones.get(0);
						
			}
			
		}		
		return peticion; 
		
	}
	
	private List consultarCatalogo(Class clazz, Entidad entidad) throws ExcepcionServicioFachada {
		
		ConsultarCatalogoPeticion consultarCatalogoPeticion = new ConsultarCatalogoPeticion();
		consultarCatalogoPeticion.setClase(clazz);
		consultarCatalogoPeticion.setEntidad(entidad);
		ConsultarCatalagoRespuesta consultarCatalogoRespuesta = servicioCatalogo.consultarCatalogo(consultarCatalogoPeticion);
		
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta );
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta.getEntidades() );
		return consultarCatalogoRespuesta.getEntidades();			
	}
	
	
	private Entidad obtenerEntidad(Integer idEntidad, Class clazz) throws ExcepcionServicio{		
		
		ObtenerCatalogoPeticion obtenerEntidadPeticion = new ObtenerCatalogoPeticion();
		
		obtenerEntidadPeticion.setIdEntidad(idEntidad);
		
		obtenerEntidadPeticion.setClase(clazz);
		
		ObtenerEntidadRespuesta obtenerEntidadRespuesta = servicioCatalogo.obtenerCatalogo(obtenerEntidadPeticion);
		
		return obtenerEntidadRespuesta.getEntidad();
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
	
	
	
	
	private Boolean tieneMovimientos(){
		Boolean result = true;
		
		if(listaMovimientos.isEmpty()){
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe haber almenos un movimiento para procesar la gestion.","" ));				
			
			result = false;
		}
		
		return result;
	}
	
	
	private Boolean tieneArchivos(){
		Boolean result = true;
		
		if(listaArchivos.isEmpty()){							
			
			result = false;
		}else{
			
			for(Archivos a : listaArchivos){
				
				result = a.getNuevo();
			}
			
		}
		
		if(result == false){
			
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe haber almenos un archivo evidencia para terminar la gestion.","" ));
		}

		
		return result;
	}
	
	
	private Boolean camposCapturados(){
		Boolean result = true;
		
		if(getFechaMovimiento() == null || getFechaMovimiento().getTime() == 0 ){
			
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar una fecha","" ));				
			
			result = false;
		}
		
		/*if(getMovimiento() == null || getMovimiento().isEmpty() ){
			
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe agregar un movimiento","" ));				
			
			result = false;
		}*/
		
		if(getComentario() == null || getComentario().isEmpty() ){
			
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("gestion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe agregar un comentario","" ));				
			
			result = false;
		}
		
		return result;
	}	

	
	
	
	private void limpiarCampos(){
		
		setComentario(null);
		setMovimiento(null);
		setFechaMovimiento(null);
	}
	
	private List<Archivo> copiarArchivos(List<Archivos> listaArchivos2) {
		listaArchivosAGuardar = new ArrayList<Archivo>();
		
		for(Archivos a : listaArchivos2){
			if(a.getNuevo()){
			
				Archivo archivo = new Archivo();
				archivo.setNombre(a.getNombreArchivo());
				//archivo.setProcesoPeticion(procesoPeticion);
				archivo.setBlob(a.getBlob());
				listaArchivosAGuardar.add(archivo);
			}
			
		}	
		return listaArchivosAGuardar;
	}

	
	
	
	private String crearUrlReporteEnrega(){	
		
		StringBuilder url = new StringBuilder(URL_REPORTES);
		
		StringBuilder params = new StringBuilder("rptName=")
				.append(RPT_PETICION).append("&")
				.append("rptFormat=").append(RPT_FORMAT_PDF).append("&")	
				.append("idtmpeticion=").append(this.idPeticion);
				
		
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
	
	public String getIdProcesoPeticion() {
		return idProcesoPeticion;
	}



	public void setIdProcesoPeticion(String idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}



	public List<Archivos> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivos> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public Boolean getMostrarEvidencias() {
		return mostrarEvidencias;
	}

	public void setMostrarEvidencias(Boolean mostrarEvidencias) {
		this.mostrarEvidencias = mostrarEvidencias;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<MovimientosProceso> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<MovimientosProceso> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public ProcesoPeticion getMovimientoSeleccionado() {
		return movimientoSeleccionado;
	}

	public void setMovimientoSeleccionado(ProcesoPeticion movimientoSeleccionado) {
		this.movimientoSeleccionado = movimientoSeleccionado;
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

	public Boolean getMostrarLinkRemover() {
		return mostrarLinkRemover;
	}

	public void setMostrarLinkRemover(Boolean mostrarLinkRemover) {
		this.mostrarLinkRemover = mostrarLinkRemover;
	}

	public Boolean getMostrarLinkNombreArchivo() {
		return mostrarLinkNombreArchivo;
	}

	public void setMostrarLinkNombreArchivo(Boolean mostrarLinkNombreArchivo) {
		this.mostrarLinkNombreArchivo = mostrarLinkNombreArchivo;
	}

	public Boolean getMostrarFileUploadBtn() {
		return mostrarFileUploadBtn;
	}

	public void setMostrarFileUploadBtn(Boolean mostrarFileUploadBtn) {
		this.mostrarFileUploadBtn = mostrarFileUploadBtn;
	}

	public Boolean getMostrarLinkArchivos() {
		return mostrarLinkArchivos;
	}

	public void setMostrarLinkArchivos(Boolean mostrarLinkArchivos) {
		this.mostrarLinkArchivos = mostrarLinkArchivos;
	}

	public Boolean getMostrarSeccionGestion() {
		return mostrarSeccionGestion;
	}

	public void setMostrarSeccionGestion(Boolean mostrarSeccionGestion) {
		this.mostrarSeccionGestion = mostrarSeccionGestion;
	}

	public Boolean getMostrarBtnTerminarGestion() {
		return mostrarBtnTerminarGestion;
	}

	public void setMostrarBtnTerminarGestion(Boolean mostrarBtnTerminarGestion) {
		this.mostrarBtnTerminarGestion = mostrarBtnTerminarGestion;
	}

	public Boolean getMostrarBtnAgregarEvidencia() {
		return mostrarBtnAgregarEvidencia;
	}

	public void setMostrarBtnAgregarEvidencia(Boolean mostrarBtnAgregarEvidencia) {
		this.mostrarBtnAgregarEvidencia = mostrarBtnAgregarEvidencia;
	}

	public Boolean getMostrarCargaArchivos() {
		return mostrarCargaArchivos;
	}

	public void setMostrarCargaArchivos(Boolean mostrarCargaArchivos) {
		this.mostrarCargaArchivos = mostrarCargaArchivos;
	}

	public Boolean getHabilitarCaptura() {
		return habilitarCaptura;
	}

	public void setHabilitarCaptura(Boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}

	public Boolean getHabilitarBtnAgregarEvidencia() {
		return habilitarBtnAgregarEvidencia;
	}

	public void setHabilitarBtnAgregarEvidencia(Boolean habilitarBtnAgregarEvidencia) {
		this.habilitarBtnAgregarEvidencia = habilitarBtnAgregarEvidencia;
	}
	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}
	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}

	public List<Archivo> getListaArchivosAGuardar() {
		return listaArchivosAGuardar;
	}

	public void setListaArchivosAGuardar(List<Archivo> listaArchivosAGuardar) {
		this.listaArchivosAGuardar = listaArchivosAGuardar;
	}

	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}

	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}

	public Boolean getMostarBtnCerrarGestion() {
		return mostarBtnCerrarGestion;
	}

	public void setMostarBtnCerrarGestion(Boolean mostarBtnCerrarGestion) {
		this.mostarBtnCerrarGestion = mostarBtnCerrarGestion;
	}

	public Boolean getMostrarLinkRemoverMovimiento() {
		return mostrarLinkRemoverMovimiento;
	}

	public void setMostrarLinkRemoverMovimiento(Boolean mostrarLinkRemoverMovimiento) {
		this.mostrarLinkRemoverMovimiento = mostrarLinkRemoverMovimiento;
	}	

	public Boolean getHabilitarBtnCerrarGestion() {
		return habilitarBtnCerrarGestion;
	}

	public void setHabilitarBtnCerrarGestion(Boolean habilitarBtnCerrarGestion) {
		this.habilitarBtnCerrarGestion = habilitarBtnCerrarGestion;
	}

	public Integer getIdStatusPeticionActual() {
		return idStatusPeticionActual;
	}

	public void setIdStatusPeticionActual(Integer idStatusPeticionActual) {
		this.idStatusPeticionActual = idStatusPeticionActual;
	}

	public Boolean getMostrarBtnRechazarGestion() {
		return mostrarBtnRechazarGestion;
	}

	public void setMostrarBtnRechazarGestion(Boolean mostrarBtnRechazarGestion) {
		this.mostrarBtnRechazarGestion = mostrarBtnRechazarGestion;
	}

	public Boolean getHabilitarBtnRechazarGestion() {
		return habilitarBtnRechazarGestion;
	}

	public void setHabilitarBtnRechazarGestion(Boolean habilitarBtnRechazarGestion) {
		this.habilitarBtnRechazarGestion = habilitarBtnRechazarGestion;
	}

	public Boolean getHabilitarBtnTerminarGestion() {
		return habilitarBtnTerminarGestion;
	}

	public void setHabilitarBtnTerminarGestion(Boolean habilitarBtnTerminarGestion) {
		this.habilitarBtnTerminarGestion = habilitarBtnTerminarGestion;
	}

	public Boolean getMostrarBtnImprimirEntrega() {
		return mostrarBtnImprimirEntrega;
	}

	public void setMostrarBtnImprimirEntrega(Boolean mostrarBtnImprimirEntrega) {
		this.mostrarBtnImprimirEntrega = mostrarBtnImprimirEntrega;
	}

	public Boolean getHabilitarBtnImprimirEntrega() {
		return habilitarBtnImprimirEntrega;
	}

	public void setHabilitarBtnImprimirEntrega(Boolean habilitarBtnImprimirEntrega) {
		this.habilitarBtnImprimirEntrega = habilitarBtnImprimirEntrega;
	}

	public ServicioComun getServicioComun() {
		return servicioComun;
	}

	public void setServicioComun(ServicioComun servicioComun) {
		this.servicioComun = servicioComun;
	}

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getDescripcionEntrega() {
		return descripcionEntrega;
	}

	public void setDescripcionEntrega(String descripcionEntrega) {
		this.descripcionEntrega = descripcionEntrega;
	}

	public Boolean getHabilitarDescripcionEntrega() {
		return habilitarDescripcionEntrega;
	}

	public void setHabilitarDescripcionEntrega(Boolean habilitarDescripcionEntrega) {
		this.habilitarDescripcionEntrega = habilitarDescripcionEntrega;
	}	
	
	
	
}
