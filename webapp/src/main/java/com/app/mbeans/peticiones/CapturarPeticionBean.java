package com.app.mbeans.peticiones;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.app.mbeans.AplicacionBean;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Categoria;
import com.modelo.datos.app.EntidadMpal;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.ProcesoPeticion;
import com.modelo.datos.app.TipoMedioContacto;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.objetos.transf.datos.app.peticion.ActualizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;


@ManagedBean(name="capturarPeticionBean")
@ViewScoped
public class CapturarPeticionBean extends MBeanAbstracto implements Serializable{
		
	/**
	 * ejecutarAccionBoton
	 */
	private static final long serialVersionUID = 1L;
	
	private final String RPT_PETICION = "folioPeticionRpt";
	private final String RPT_FORMAT_PDF = "pdf";
	private String urlReportePeticion = null;
	private String URL_REPORTES; //= "http://localhost:8080/webreports/JasperReportServlet?";
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;
	
	@ManagedProperty(value = "#{servicioComun}")
	private ServicioComun servicioComun;
	
	private ConsultarCatalogoPeticion consultarCatalogoPeticion;
	private ConsultarCatalagoRespuesta consultarCatalogoRespuesta;

	private List<Archivo> listaArchivos = new ArrayList<Archivo>();

	private Peticion peticion; //Referencia que guardara la peticion a ser registrada/consultada
	
	private UploadedFile archivo; 
	
	//Datos de Contacto
	private String nombre;
	private List<EntidadMpal> listaColonias;
	private Integer idColoniaSeleccionada;
	private String direccion;
	private List<TipoMedioContacto> listaMediosContacto;
	private Integer idMedioContactoSeleccionado;
	private String descripcionContacto;
	private Integer claveExterna;
	
	
	//Datos de la Peticion
	private String folioPeticion;
	private Date fechaCaptura;
	private String descripcion;
	private List<Categoria> listaCategorias;
	private Integer idCategoriaSeleccionada;
	//private List<Prioridad> listaPrioridad;
	//private Integer idPrioridadSeleccionada;
	private String observaciones;	
	
	private Boolean habilitarCaptura = false;
	
	private Boolean deshabilitarCapturaArchivo = false;
	
	private String folioABuscar;
	
	//Datos para deshabilitar los componentes de la busqueda
	private Boolean deshabilitarCampoFolio;
	private Boolean deshabilitarBtnBuscar;
	private Boolean mostrarLinkRemover;
	
	
	//Datos para deshabilitar los botones 
	private Boolean deshabilitarBtnNuevo;
	private Boolean deshabilitarBtnGuardar;
	private Boolean deshabilitarBtnCancelar;
	private Boolean deshabilitarBtnImprimir;
	private Boolean deshabilitarCamposEditables;
	
	private boolean mostrarLinkNombreArchivo;
	
	private Boolean esBusqueda;
		
	// Valores para controlar el comportamiento del ConfirmDialog	
	private String botonClickeado;
	private String mensajeConfirmDialog;
	
	private String idPeticion;
	
	@PostConstruct
	public void inicializar(){			
		
		AplicacionBean app = (AplicacionBean) getManagedBean("#{aplicacion}", AplicacionBean.class);
		
		URL_REPORTES =  app.getUrlReportes();
		
		try{
			listaColonias = consultarCatalog(EntidadMpal.class, new EntidadMpal());//crearColonias();		
			listaMediosContacto = consultarCatalog(TipoMedioContacto.class, new TipoMedioContacto());//crearMediosContacto();	
		
			listaCategorias = consultarCatalog(Categoria.class, new Categoria());//crearListaCategorias();
			//listaPrioridad = consultarCatalog(Prioridad.class, new Prioridad());//crearListaPrioridades();	
		}catch(Exception e){
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocurrion un error de sistema. No se pudieron inicializar los catalgos." , null));
			
		}
		
		
		deshabilitarBtnNuevo = false;
		deshabilitarBtnImprimir = true;
		esBusqueda = false;
		mostrarLinkRemover = false;
		mostrarLinkNombreArchivo = false;
		deshabilitarTodo(true);
		deshabilitarBtnBuscar = false;
		deshabilitarCampoFolio = false;
	}	
	
	public void deshabilitarBotonesPrincipales(boolean deshabilitarComponente){
		this.deshabilitarBtnGuardar = deshabilitarComponente;		
		this.deshabilitarBtnCancelar = deshabilitarComponente;
	}
	
	public void deshabilitarTodo(boolean deshabilitarComponente){
		habilitarCaptura = deshabilitarComponente;
		deshabilitarCapturaArchivo = deshabilitarComponente;	
		deshabilitarCamposEditables = deshabilitarComponente;
		deshabilitarBotonesPrincipales(deshabilitarComponente);		
	}
	
	public void limpiarTodo(){
		
		nombre = null;
		idColoniaSeleccionada = 0;
		direccion = null;
		idMedioContactoSeleccionado = 0;
		descripcionContacto = null;
		claveExterna=null;
		
		folioPeticion = null;
		fechaCaptura = null;
		descripcion = null;
		idCategoriaSeleccionada = 0;
		//idPrioridadSeleccionada = 0;
		observaciones = null;
		
		listaArchivos.clear();
		esBusqueda = false;
		mostrarLinkRemover = false;
		deshabilitarTodo(true);
	}
	
	public void nuevo(ActionEvent evento){
		limpiarTodo();
		deshabilitarBtnNuevo = true;
		deshabilitarTodo(false);
		deshabilitarBtnImprimir = true;
		deshabilitarCampoFolio = true;	
		deshabilitarBtnBuscar = true;
		mostrarLinkRemover = false;
		mostrarLinkNombreArchivo = false;
		
		
	}
	
	/*public void ejecutarAccionBoton(ActionEvent evento){
		
		if(evento.getComponent().getId().compareTo("m1p1_btnGuardar") == 0){			
			botonClickeado = evento.getComponent().getId();
			mensajeConfirmDialog = "Esta seguro que desea guardar la captura";
			
			if(listaArchivos.size() == 0){				
				mensajeConfirmDialog = mensajeConfirmDialog +  ", no ha adjutado nigun archivo.";
			}
			
		}
		
		if (evento.getComponent().getId().compareTo("m1p1_btnCancelar") == 0){
			botonClickeado = evento.getComponent().getId();
			mensajeConfirmDialog = "Esta seguro que desea cancelar la captura";
		}
				
	}
	
	public void realizarAccion(ActionEvent evento){
		
		if ( botonClickeado.compareTo("m1p1_btnGuardar") == 0 ){
			guardar(evento);
		}
		
		if ( botonClickeado.compareTo("m1p1_btnCancelar") == 0 ){
			cancelar(evento);
		}
	}*/
	
	public void guardar(ActionEvent evento){
		
		
		/*if(getListaArchivos().size() != 0){
			
			crearPeticion();
		}else{
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe Ingresar almenos un archivo para registrar la peticion", ""));
		}*/
		
		if(esBusqueda){
			//agregarArchivosAPeticion();
			actualizarPeticion();
		}else{
			if(getListaArchivos().size() != 0){
			
				crearPeticion();
			}else{
			
				FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe Ingresar almenos un archivo para registrar la peticion", ""));
			}
		}
	
	}	
	
	
	public void imprimir(ActionEvent evento){
		deshabilitarBtnNuevo = false;		
		deshabilitarBtnGuardar = true;
		deshabilitarBtnBuscar = false;
		deshabilitarBtnImprimir = true;	
		deshabilitarCampoFolio = false;
		
		
		deshabilitarTodo(true);
		
		limpiarTodo();
		
		
		RequestContext.getCurrentInstance().execute("openReport('"+ crearUrlReportePeticion() +"')");
	}
	
	public void cancelar(ActionEvent evento){

		limpiarTodo();
		
		deshabilitarBtnNuevo = false;
		deshabilitarBtnImprimir = true;
		esBusqueda = false;
		mostrarLinkRemover = false;
		deshabilitarTodo(true);
		deshabilitarBtnBuscar = false;
		deshabilitarCampoFolio = false;
		deshabilitarBtnCancelar=true;
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN,"La captura ha sido cancelada" ,""));
		
	}
	
	public void buscarFolio(ActionEvent event){
		
		
		//Validar que se haya ingresado un folio. Si no mostrar mensaje
		
		if(folioPeticion == null || folioPeticion.equals("")){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe Ingresar un folio para realizar la busqueda.", ""));			
		}else{
		
			//Consultar la peticion. 
			//Si no hay datos mostrar mensaje

			try{
				
				peticion = consultarPeticion(folioPeticion);
			
				//System.out.println("peticion -> " + peticion);
				
				if(peticion == null){
					
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"No se encontro informacion del folio.", ""));
					
					listaArchivos.clear();
					
				}else{
	
					StringBuilder mensaje = new StringBuilder("Se encotro el folio con estatus " + peticion.getStatusPeticion().getValor());
					FacesMessage facesMessage = new FacesMessage();
					
					this.idPeticion = peticion.getIdPeticion().toString();
					setNombre(peticion.getSolicitante());
					setIdColoniaSeleccionada(peticion.getEntidadMpal().getId());
					setDireccion(peticion.getDireccion());
					setIdMedioContactoSeleccionado(peticion.getTipoMedioContacto().getId());
					setDescripcionContacto(peticion.getMedioContacto());
					setClaveExterna(peticion.getClaveExterna());
					
					setFechaCaptura(peticion.getListaProcesoPeticion().get(0).getFecha());
					
					setDescripcion(peticion.getDescripcion());
					
					setIdCategoriaSeleccionada(peticion.getCategoria().getIdCategoria());
										
					//setIdPrioridadSeleccionada(peticion.getPrioridad().getIdtcgeneral());
					
					setObservaciones(peticion.getObservaciones());
									
					//Si hay datos habilitar componentes de UI		
					
					
					List<Archivo> listaArchivosPeticion =  peticion.getListaProcesoPeticion().get(0).getListaArchivo();					
					
					//System.out.println("Archivos ->" +  listaArchivosPeticion.size());
					
					/*if(listaArchivosPeticion.size() == 0){					
						
						deshabilitarCapturaArchivo = false;
						listaArchivos.clear();
						mostrarLinkNombreArchivo = false;

					
						if(peticion.getStatusPeticion().getIdStatusPeticion() != 1){
							mensaje.append(". No se pueden adjuntar archivos.");
							facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
							deshabilitarCapturaArchivo = true;
						}else{
							mensaje.append(". Se pueden adjuntar archivos.");
							facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
							deshabilitarCapturaArchivo = false;		
							mostrarLinkRemover = false;
						}
						
						
					}else{*/
						
						listaArchivos.clear();
						
						for(Archivo archivo : listaArchivosPeticion){					
							
							listaArchivos.add(archivo);
						}
						
						//mensaje.append("con archivos adjuntos. No se pueden adjuntar mas archivos.");
						facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
						deshabilitarCapturaArchivo = true;
						mostrarLinkRemover = false;
						mostrarLinkNombreArchivo = true;
											
					//}				
					
					//Verificar si tiene archivos adjuntos si no indicar con un mensaje				
					
					//esBusqueda = true;				
					//deshabilitarBtnCancelar = false;
					deshabilitarBtnCancelar = true;
					deshabilitarBtnImprimir = false;	
					
					//Se habilitan los componentes para actualizar el medio de contacto y la clave
					deshabilitarBtnGuardar= false;
					deshabilitarCamposEditables = false;
					esBusqueda  = true;
					
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(facesMessage.getSeverity(),mensaje.toString(), ""));
				}				
			}catch(Exception e){
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrio un error interno al consultar la peticion. " + e.getMessage(), ""));
			}			
			
		}		
	}
	
		
	
	public void cargarArchivo(FileUploadEvent event) {
	 	
		event.getComponent().setTransient(false);
		
		UploadedFile archivo = event.getFile();
	 	
	 	System.out.println("nombre archivo -> " + archivo.getFileName());
	 	System.out.println("tamaño -> " + archivo.getSize()); 	
	 	
	 	try{
	 		
	 		Archivo archivoTmp = new Archivo();
	 		
	 		archivoTmp.setNombre(archivo.getFileName());
	 		archivoTmp.setBlob(IOUtils.toByteArray(archivo.getInputstream()));
	 		archivoTmp.setRuta("NA");
	 		
	 		listaArchivos.add(archivoTmp); 			
		 	
		 	if(esBusqueda){
		 		
		 		deshabilitarBtnGuardar = false;
		 		mostrarLinkRemover = false;		
		 	}else{
		 		mostrarLinkRemover = true;
		 	}
	 		
		 	
	 	}catch(Exception e){
	 		
	 		FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al adjuntar el archivo" +  e.getMessage(), ""));
	 	}
	 	
	}
	
	public void removerArchivo(ActionEvent event){
		
		String nombreArchivo = getRequestParameter("archivoRemover").toString();
		System.out.println("Removiendo archivo -> " + nombreArchivo);
		
		for(Archivo a : listaArchivos){		
			if(nombreArchivo.equals(a.getNombre())){
				listaArchivos.remove(a);				
				break;
			}			
		}	
	}
	
	
	private String crearUrlReportePeticion(){	
		
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
	
	private List consultarCatalog(Class clazz, Entidad entidad) throws ExcepcionServicioFachada {
		
		consultarCatalogoPeticion = new ConsultarCatalogoPeticion();
		consultarCatalogoPeticion.setClase(clazz);
		consultarCatalogoPeticion.setEntidad(entidad);
	
		consultarCatalogoRespuesta= servicioCatalogo.consultarCatalogo(consultarCatalogoPeticion);
		
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta );
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta.getEntidades() );
		return consultarCatalogoRespuesta.getEntidades();			
	}
	
		
	private void crearPeticion(){
		
		System.out.println("\n\t\t***actualizarPeticion()");
		
		UsuarioSistema usuario = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);		
		
		RegistrarPeticionesPeticion peticion = new RegistrarPeticionesPeticion();
		peticion.setSolicitante(getNombre().toUpperCase());
		peticion.setDireccion(getDireccion().toUpperCase());
		peticion.setDescripcion(getDescripcion().toUpperCase());
		
		//System.out.println("\t\t***claveExterna -> " + getClaveExterna());

		peticion.setClaveExterna(getClaveExterna() == null || getClaveExterna().toString().isEmpty() ? 0 : getClaveExterna());
		peticion.setIdArea(usuario.getArea().getIdArea());
		
		peticion.setIdCategoria(getIdCategoriaSeleccionada());
		
		peticion.setIdEntidadMpal(getIdColoniaSeleccionada());
		peticion.setIdPrioridad(24);
		peticion.setIdTipoMedioContacto(getIdMedioContactoSeleccionado());
		
		peticion.setMedioConcato(getDescripcionContacto());
		peticion.setObservaciones(getObservaciones().toUpperCase());
		
		peticion.setIdUsuario(usuario.getIdUsuarioSistema());
		
		peticion.setRequierePresupuesto(1);
		
		peticion.setListaArchivos(listaArchivos);
		
		
		RegistrarPeticionesRespuesta respuesta = null;
		try {
			respuesta = servicioPeticiones.registrarPeticiones(peticion);
			
			setFolioPeticion(respuesta.getFolio());
			setFechaCaptura(respuesta.getFechaCaptura());
			idPeticion = respuesta.getIdPeticion().toString();
			
			this.deshabilitarTodo(true);

			this.deshabilitarBtnImprimir = false;
			deshabilitarBtnBuscar = true; //se habilita despues de imprimir
			mostrarLinkRemover = false;
			
			mostrarLinkNombreArchivo = true;
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"La captura se registró con el folio " + getFolioPeticion() + ".", null));
			
			
		} catch (ExcepcionServicioFachada e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"No se pudo registrar la peticion debido a un error interno en el sistema" +  e.getMessage(), ""));
		}		
	}
	
	private void actualizarPeticion(){
		
		System.out.println("\n\t\t***actualizarPeticion()");
		
		try{
			
			ActualizarPeticionesPeticion _peticion = new ActualizarPeticionesPeticion();
			_peticion.setClaveExterna(getClaveExterna() == null || getClaveExterna().toString().isEmpty() ? 0 : getClaveExterna());
			_peticion.setDescricionMedioContacto(getDescripcionContacto());
			_peticion.setIdMedioContacto(getIdMedioContactoSeleccionado());
			_peticion.setIdPeticion(peticion.getId());
			servicioPeticiones.actualizarPeticion(_peticion );
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha actualizado la peticion", null));
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"No se pudo actualiazar la peticion debido a un error interno en el sistema" +  e.getMessage(), ""));
			
		}
		
		
	}
	
	
	private void agregarArchivosAPeticion() {
	
		System.out.println("-> agregarArchivosAPeticion()");
		System.out.println("listaArchivos.size() -> " + listaArchivos.size());
		if(listaArchivos.size() > 0){
		
			try {
				
				ProcesoPeticion procesoPeticion = peticion.getListaProcesoPeticion().get(0);
				System.out.println("idProcesoPeticion -> " + procesoPeticion.getIdProcesoPeticion());
				System.out.println("Status Peticion -> " + procesoPeticion.getStatusPeticion().getValor());
				
				for(Archivo archivo:listaArchivos){
					System.out.println("-> Asignando archivio");
					archivo.setProcesoPeticion(peticion.getListaProcesoPeticion().get(0));
					peticion.getListaProcesoPeticion().get(0).addArchivo(archivo);
					
					System.out.println("archivo.idtppeticion -> " + archivo.getProcesoPeticion().getIdProcesoPeticion());
					
					CrearCatalogoPeticion crearCatalagoPeticion = new CrearCatalogoPeticion();
					crearCatalagoPeticion.setEntidad(archivo);				
					servicioCatalogo.crearCatalogo(crearCatalagoPeticion);
				}
				 
				
				System.out.println("-> servicioCatalogo.actualizarCatalogo()");
				//ActualizarCatalogoPeticion actualizarCatalogoPeticion = new ActualizarCatalogoPeticion();
				//actualizarCatalogoPeticion.setEntidad(procesoPeticion);
				//servicioCatalogo.actualizarCatalogo(actualizarCatalogoPeticion );
				
				
							
				
				deshabilitarBtnGuardar = true;
				deshabilitarCapturaArchivo = true;
				deshabilitarBtnNuevo = false;
				deshabilitarBtnCancelar = false;
				mostrarLinkRemover = false;
				
				mostrarLinkNombreArchivo = true;

				
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agregaron los archivos al folio " + getFolioPeticion() + ".", null));
				
				
			} catch (ExcepcionServicioFachada e) {
				e.printStackTrace();
				
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"No se pudo actualiazar el folio debido a un error interno en el sistema." + e.getMessage(), ""));
			}
		
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe adjuntar almenos un archivo para guardar los cambios.", ""));
		}
		
		
	}
	
	
	private Peticion consultarPeticion(String folio) throws Exception{
		Peticion peticion = new Peticion();
		
		peticion.setFolio(folio);
		
		List<Peticion> listaPeticiones = consultarCatalog(Peticion.class, peticion);
		
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
	
	
		
	
	public Boolean getDeshabilitarCamposEditables() {
		return deshabilitarCamposEditables;
	}

	public void setDeshabilitarCamposEditables(Boolean deshabilitarCamposEditables) {
		this.deshabilitarCamposEditables = deshabilitarCamposEditables;
	}

	public boolean getMostrarLinkNombreArchivo() {
		return mostrarLinkNombreArchivo;
	}

	public void setMostrarLinkNombreArchivo(boolean mostrarLinkNombreArchivo) {
		this.mostrarLinkNombreArchivo = mostrarLinkNombreArchivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<EntidadMpal> getListaColonias() {
		return listaColonias;
	}

	public void setListaColonias(List<EntidadMpal> listaColonias) {
		this.listaColonias = listaColonias;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<TipoMedioContacto> getListaMediosContacto() {
		return listaMediosContacto;
	}

	public void setListaMediosContacto(List<TipoMedioContacto> listaMediosContacto) {
		this.listaMediosContacto = listaMediosContacto;
	}

	public String getDescripcionContacto() {
		return descripcionContacto;
	}

	public void setDescripcionContacto(String descripcionContacto) {
		this.descripcionContacto = descripcionContacto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	/*
	public List<Prioridad> getListaPrioridad() {
		return listaPrioridad;
	}

	public void setListaPrioridad(List<Prioridad> listaPrioridad) {
		this.listaPrioridad = listaPrioridad;
	}*/

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFolioABuscar() {
		return folioABuscar;
	}

	public void setFolioABuscar(String folioABuscar) {
		this.folioABuscar = folioABuscar;
	}	

	public Boolean getDeshabilitarBtnNuevo() {
		return deshabilitarBtnNuevo;
	}

	public void setDeshabilitarBtnNuevo(Boolean deshabilitarBtnNuevo) {
		this.deshabilitarBtnNuevo = deshabilitarBtnNuevo;
	}

	public Boolean getDeshabilitarBtnGuardar() {
		return deshabilitarBtnGuardar;
	}

	public void setDeshabilitarBtnGuardar(Boolean deshabilitarBtnGuardar) {
		this.deshabilitarBtnGuardar = deshabilitarBtnGuardar;
	}

	public Boolean getDeshabilitarBtnCancelar() {
		return deshabilitarBtnCancelar;
	}

	public void setDeshabilitarBtnCancelar(Boolean deshabilitarBtnCancelar) {
		this.deshabilitarBtnCancelar = deshabilitarBtnCancelar;
	}

	public Boolean getDeshabilitarBtnImprimir() {
		return deshabilitarBtnImprimir;
	}

	public void setDeshabilitarBtnImprimir(Boolean deshabilitarBtnImprimir) {
		this.deshabilitarBtnImprimir = deshabilitarBtnImprimir;
	}

	public Integer getIdColoniaSeleccionada() {
		return idColoniaSeleccionada;
	}

	public void setIdColoniaSeleccionada(Integer idColoniaSeleccionada) {
		this.idColoniaSeleccionada = idColoniaSeleccionada;
	}

	public Integer getIdMedioContactoSeleccionado() {
		return idMedioContactoSeleccionado;
	}

	public void setIdMedioContactoSeleccionado(Integer idMedioContactoSeleccionado) {
		this.idMedioContactoSeleccionado = idMedioContactoSeleccionado;
	}

	public Integer getIdCategoriaSeleccionada() {
		return idCategoriaSeleccionada;
	}

	public void setIdCategoriaSeleccionada(Integer idCategoriaSeleccionada) {
		this.idCategoriaSeleccionada = idCategoriaSeleccionada;
	}

	/*public Integer getIdPrioridadSeleccionada() {
		return idPrioridadSeleccionada;
	}

	public void setIdPrioridadSeleccionada(Integer idPrioridadSeleccionada) {
		this.idPrioridadSeleccionada = idPrioridadSeleccionada;
	}*/

	public Boolean getDeshabilitarCampoFolio() {
		return deshabilitarCampoFolio;
	}

	public void setDeshabilitarCampoFolio(Boolean deshabilitarCampoFolio) {
		this.deshabilitarCampoFolio = deshabilitarCampoFolio;
	}

	public Boolean getDeshabilitarBtnBuscar() {
		return deshabilitarBtnBuscar;
	}

	public void setDeshabilitarBtnBuscar(Boolean deshabilitarBtnBuscar) {
		this.deshabilitarBtnBuscar = deshabilitarBtnBuscar;
	}

	public Boolean getMostrarLinkRemover() {
		return mostrarLinkRemover;
	}

	public void setMostrarLinkRemover(Boolean mostrarLinkRemover) {
		this.mostrarLinkRemover = mostrarLinkRemover;
	}

	public String getBotonClickeado() {
		return botonClickeado;
	}

	public void setBotonClickeado(String botonClickeado) {
		this.botonClickeado = botonClickeado;
	}

	public String getMensajeConfirmDialog() {
		return mensajeConfirmDialog;
	}

	public void setMensajeConfirmDialog(String mensajeConfirmDialog) {
		this.mensajeConfirmDialog = mensajeConfirmDialog;
	}
	
	public String getFolioPeticion() {
		return folioPeticion;
	}

	public void setFolioPeticion(String folioPeticion) {
		this.folioPeticion = folioPeticion;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public List<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	public void setListaArchivos(List<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	/*public List<String> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<String> archivos) {
		this.archivos = archivos;
	}*/

	public Boolean getHabiltarCaptura() {
		return habilitarCaptura;
	}

	public void setHabiltarCaptura(Boolean habiltarCaptura) {
		this.habilitarCaptura = habiltarCaptura;
	}

	public Boolean getHabilitarCaptura() {
		return habilitarCaptura;
	}

	public void setHabilitarCaptura(Boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}

	public Boolean getDeshabilitarCapturaArchivo() {
		return deshabilitarCapturaArchivo;
	}

	public void setDeshabilitarCapturaArchivo(Boolean habilitarCapturaArchivo) {
		this.deshabilitarCapturaArchivo = habilitarCapturaArchivo;
	}

	public Boolean getEsBusqueda() {
		return esBusqueda;
	}

	public void setEsBusqueda(Boolean esBusqueda) {
		this.esBusqueda = esBusqueda;
	}

	public ServicioComun getServicioComun() {
		return servicioComun;
	}

	public void setServicioComun(ServicioComun servicioComun) {
		this.servicioComun = servicioComun;
	}

	public Integer getClaveExterna() {
		return claveExterna;
	}

	public void setClaveExterna(Integer claveExterna) {
		this.claveExterna = claveExterna;
	}
	
	
}
