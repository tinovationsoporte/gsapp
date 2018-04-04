package com.app.mbeans.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.commandlink.CommandLink;

import com.app.mbeans.peticiones.AreaBean;
import com.app.mbeans.peticiones.ConsultaPeticionesBean;
import com.app.mbeans.peticiones.ConsultarPeticionDetalleBean;
import com.app.modelo.Usuario;
import com.core.app.ExcepcionAplicacion;
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
import com.objetos.transf.datos.app.peticion.ActualizarStatusPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.AutorizarPeticionesRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;


@ManagedBean(name="autorizarPeticionBean")
@ViewScoped
public class AutorizarPeticionBean extends MBeanAbstracto {

	
	private boolean habilitarCaptura;
	private boolean habilitarBtnValidar;
	private boolean habilitarBtnRechazar;
	private boolean habilitarBtnSuspender;
	private boolean habilitarBtnReasignar;
	private boolean habilitarBtnCancelar;
	private boolean habilitarLnkVer;

	
	
	private List<Area> listaAreasAsignar;	
	private List<Area> listaAreasPresupuesto;	
	private List<Area> listaAreasAdicional;
	
	
	private List<UsuarioSistema> listaUsuariosAreaAsignar = new ArrayList<UsuarioSistema>();
	private List<UsuarioSistema> listaUsuariosAreaPresupuesto = new ArrayList<UsuarioSistema>();	
	private List<UsuarioSistema> listaUsuariosAreaAdicional = new ArrayList<UsuarioSistema>();
	
	private List<Prioridad> listaPrioridad = new ArrayList<Prioridad>();
	
	private Integer idAreaAsignada;
	private Integer idAreaPresupuesto;
	private Integer idAreaAdicional;
	
	private Integer idUsuarioAreaAsignada;
	private Integer idUsuarioAreaPresupuesto;
	private Integer idUsuarioAreaAdicional;
	
	private int idPrioridadSeleccionada;
	
	private Date fechaAutorizacion;

	 
	private Peticion peticion;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;	
	
	//private AreaBean detalleArea;
	private String botonClickeado;
	private String mensajeConfirmDialog;
	
	private Integer idUsuarioSistema;
	
	private Boolean mostrarBotones;

	private String etiquetaBtnAutorizar;
	
	private String movimiento;
	
	@PostConstruct
	public void inicializar(){
		try {
			
			System.out.println("***\n\nautorizarPeticionBean.inicializar()...\n\n");

			Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticionDashboard").toString());
			System.out.println("***\n\nidPeticion -> " + idPeticion);

			
			movimiento = getRequestParameter("movimiento").toString();
			
			System.out.println("***\n\nmovimiento -> " + movimiento);

			
			setEtiquetaBtnAutorizar(movimiento);
			
			peticion = consultarPeticion(idPeticion);
			
			Integer idStatusPeticionActual = peticion.getStatusPeticion().getIdStatusPeticion();
			
			UsuarioSistema usuario = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);		
			setIdUsuarioSistema(usuario.getIdUsuarioSistema());
	
			setMostrarBotones(true);
			
			if (idStatusPeticionActual == 1){
			
				inicializarComponentes();
				habilitarCaptura = true;
				habilitarBtnValidar = true;
				habilitarBtnRechazar = true;
				
				habilitarLnkVer = true;
				
				FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"La peticion esta CAPTURADA puede ser VALIDADA","" ));
				
			}else{
				
				//Si la peticion esta asignada sin prespuesto o con presupuesto se puede reasingar
				if(idStatusPeticionActual == 3 || idStatusPeticionActual == 4){
					
					asignarValoresPeticiones(getPeticion(),idStatusPeticionActual);
					habilitarCaptura = true;
					habilitarBtnValidar = true;
					habilitarBtnRechazar = true;					
					habilitarLnkVer = true;
					
					FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"La peticion ha sido VALIDADA pero puede ser reasignada","" ));
				
				}else{
					
					if(idStatusPeticionActual != 1 || idStatusPeticionActual != 3 || idStatusPeticionActual != 4){
						asignarValoresPeticiones(getPeticion(),idStatusPeticionActual);
						habilitarCaptura = false;
						habilitarBtnValidar = false;
						habilitarBtnRechazar = false;						
						habilitarLnkVer = true;
						
						FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
								new FacesMessage(FacesMessage.SEVERITY_WARN,"El status de la peticion no permite que sea reasignada","" ));
					}
				}
			}
			
		} catch (Exception e) {
			//getCurrentFacesContext().validationFailed();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al cargar los catalogos." + e.getMessage(),"" ));		
		}
		
	
	
	
	}
	
	
	public void onListChange(AjaxBehaviorEvent event){
		
		//event.getComponent().getId()
		
		System.out.println("*** onListChange");
		
		System.out.println("componenteID -> " + event.getComponent().getId());
		
		Rol rol = new Rol();
		rol.setId(3);

		UsuarioSistema usuario = new UsuarioSistema();
		
		Area area = new Area();

		usuario.setArea(area);
		usuario.setRol(rol);
		
		try {
		
			if(event.getComponent().getId().equals("areaAtencion_listaAreasPrincipal")){
			
				area.setId(getIdAreaAsignada());
				
				System.out.println("idAreaAsignada -> " + getIdAreaAsignada());
				
				listaUsuariosAreaAsignar = consultarCatalogo(UsuarioSistema.class, usuario);
			}
				
			if(event.getComponent().getId().equals("areaAtencion_listaAreasPresupuesto")){
				area.setId(getIdAreaPresupuesto());
				listaUsuariosAreaPresupuesto = consultarCatalogo(UsuarioSistema.class, usuario);
				
			}
			
			
			if(event.getComponent().getId().equals("areaAtencion_listaUsrsAreaPrincipal")){
				
				
				System.out.println("idUsuarioAreaAsignada -> " + getIdUsuarioAreaAsignada());
				
			}
			
			if(event.getComponent().getId().equals("areaAtencion_listaUsrsAreaPresupuesto")){
				
				
				System.out.println("idUsuarioAreaPresupuesto -> " + getIdUsuarioAreaPresupuesto());
				
			}
			
			
			
		} catch (ExcepcionServicioFachada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar la Peticion." + e.getMessage(),"" ));
		}	
		
	}
		
	
	public void validarPeticion(ActionEvent e){
		
		
		System.out.println("***validandoPeticion ... ");
		
		
		/*System.out.println("idAareaAsignada -> " + idAreaAsignada);
		System.out.println("idUsuarioAreaAsignada -> " + idUsuarioAreaAsignada);
		
		System.out.println("idAreaPresupuesto -> " + idAreaPresupuesto);
		System.out.println("idUsuarioAreaPresupuesto -> " + idUsuarioAreaPresupuesto);
		
		System.out.println("idAreaAdicional -> " + idAreaAdicional);
		System.out.println("idUsuarioAreaAdicional -> " + idUsuarioAreaAdicional);
		*/
		if(datosCapturados()){
			
			try {
				AutorizarPeticionesPeticion autorizarPeticionesPeticion = new AutorizarPeticionesPeticion();
				autorizarPeticionesPeticion.setIdPeticion(getPeticion().getIdPeticion());
				autorizarPeticionesPeticion.setIdAreaAsingada(getIdAreaAsignada());
				autorizarPeticionesPeticion.setIdAreaPresupuesto(getIdAreaPresupuesto());
				//autorizarPeticionesPeticion.setIdAreaAdicional(getIdAreaAdicional());
				autorizarPeticionesPeticion.setIdUsuarioAsignado(getIdUsuarioAreaAsignada());
				autorizarPeticionesPeticion.setIdUsuarioPresupuesto(getIdUsuarioAreaPresupuesto());
				//autorizarPeticionesPeticion.setIdUsuarioAreaAdicional(getIdUsuarioAreaAdicional());
				autorizarPeticionesPeticion.setComentarios("AUTORIZACION PETICION");
				autorizarPeticionesPeticion.setIdUsuarioSistema(getIdUsuarioSistema());
				
				autorizarPeticionesPeticion.setIdPrioridad(getIdPrioridadSeleccionada());
				
				AutorizarPeticionesRespuesta autorizarPeticionRespuesta = 
								servicioPeticiones.autorizarPeticion(autorizarPeticionesPeticion );
			
				setFechaAutorizacion(autorizarPeticionRespuesta.getFechaAutorizacion());
			
				setHabilitarBtnValidar(false);
				setHabilitarBtnRechazar(false);
				setHabilitarCaptura(false);
				//setHabilitarBtnReasignar(true);
				
				FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"La peticion ha sido AUTORIZADA","" ));
			
				
			
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al autorizar la peticion.-"+ ex.getMessage(),"" ));
			
			}
			
			
		}	
		
	}
	
	
	public void rechazar(ActionEvent e){
		
		
		ActualizarStatusPeticionesPeticion peticion = new ActualizarStatusPeticionesPeticion();
		peticion.setIdPeticion(getPeticion().getIdPeticion());
		peticion.setIdStatusPeticion(18);
		peticion.setIdUsuarioSistema(getIdUsuarioSistema());
		peticion.setMovimiento("RECHAZO PETICION");
		peticion.setComentarios("PETICION RECHAZADA");
		
		
		try {
			servicioPeticiones.actualizarStatusPeticion(peticion);
			setHabilitarBtnValidar(false);
			setHabilitarBtnRechazar(false);
			setHabilitarCaptura(false);			
			
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"La peticion ha sido RECHAZADA","" ));
		} catch (Exception ex) {
			ex.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("consulta_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al rechazar peticion." + ex.getMessage(),"" ));		
		}		
	}	
	
	
	@Override
	public void abrirSubPantallaNivel2(ActionEvent e){				
		
		if(e.getComponent().getId().equals("areaAtencion_lnkAreaPrincipal")){
			if(idUsuarioAreaAsignada == 0 ){
				
				getCurrentFacesContext().validationFailed();
				FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar un usuario para el area asignada.","" ));				
				
				return;
			}			
		}		
		
		if(e.getComponent().getId().equals("areaAtencion_lnkAreaPresupuesto")){
		
			if(idUsuarioAreaPresupuesto == 0){
				
				getCurrentFacesContext().validationFailed();
				FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar un usuario para el area del presupuesto","" ));
			
				return;
			}			
		}
		
		super.abrirSubPantallaNivel2(e);
	}
	
	
	
	public void limpiar(ActionEvent e){
		System.out.println("*** Limpiando pantalla ... ");
		limpiarComponentes();		
	}
		
	
	public void reasignarPeticion(ActionEvent e ){		
		
		habilitarCaptura = true;
		habilitarBtnValidar = true;
		habilitarBtnReasignar= false;
		
		//btnValidarText = "Guardar";
		
	 
		
	}
		
	
	public void ejecutarAccionBoton(ActionEvent evento){
		
		System.out.println("*** ejecutarAccionBoton ... ");

		System.out.println("*** componentId -> " + evento.getComponent().getId());

		
		if(evento.getComponent().getId().equals("areaAtencion_btnValidar")){			
			botonClickeado = evento.getComponent().getId();
			mensajeConfirmDialog = "Esta seguro que desea validar la peticion";			
		}
		
		if (evento.getComponent().getId().equals("areaAtencion_btnCancelar")){
			botonClickeado = evento.getComponent().getId();
			mensajeConfirmDialog = "Esta seguro que desea limpiar la captura";
		}
				
	}
	
	
	public void realizarAccion(ActionEvent evento){
		
		System.out.println("*** realizarAccion ... ");

		System.out.println("*** botonClickeado-> " + botonClickeado);

		if ( botonClickeado.equals("areaAtencion_btnValidar") ){
			validarPeticion(evento);
		}
		
		if ( botonClickeado.equals("areaAtencion_btnCancelar")){
			limpiar(evento);
		}
	}
	
	
	
	@Override
	public void resetBean(ActionEvent e) throws ExcepcionAplicacion {
		System.out.println("\n\n\n\t **** resetando consultaDetalleBean");
		
		if( movimiento.equals("Autorizar") ){
			
			DashboardBean dashboardBean = (DashboardBean) getViewContext().get("dashboardBean");
			System.out.println("\n\n\n\t **** dashboardBean ->" + dashboardBean);
			dashboardBean.buscarFolio(e);			
		}
		
		if( movimiento.equals("Reasignar") ){
			
			ConsultaPeticionesBean consultaPeticionesBean = (ConsultaPeticionesBean) getViewContext().get("consultaPeticionesBean");
			System.out.println("\n\n\n\t **** consultaPeticionesBean ->" + consultaPeticionesBean);
			consultaPeticionesBean.buscar(e);			
		}
		
		
		super.resetBean(e);
	}
	
	
	
	private void inicializarComponentes() throws Exception{
		
		setIdAreaAsignada(0);
		setIdAreaPresupuesto(0);
		setIdAreaAdicional(0);
		
		setIdUsuarioAreaAsignada(0);
		setIdUsuarioAreaPresupuesto(0);
		setIdUsuarioAreaAdicional(0);
		
		setIdPrioridadSeleccionada(0);
		
		listaAreasAsignar = consultarCatalogo(Area.class, new Area());
		
		Area area = new Area();
		area.setEsPresupuesto(1);
		listaAreasPresupuesto = consultarCatalogo(Area.class, area);
		
		//listaAreasAdicional = consultarCatalogo(Area.class, new Area());
		
		listaPrioridad = consultarCatalogo(Prioridad.class, new Prioridad());
		//listaUsuariosAreaAdicional = new ArrayList<UsuarioSistema>();
		//listaUsuariosAreaPresupuesto = new ArrayList<UsuarioSistema>();
		//listaUsuariosAreaAsignar = new ArrayList<UsuarioSistema>();
		
		
	}
	
	
	private void limpiarComponentes(){
		
		setIdAreaAsignada(0);
		setIdAreaPresupuesto(0);
		setIdAreaAdicional(0);
		
		setIdUsuarioAreaAsignada(0);
		setIdUsuarioAreaPresupuesto(0);
		setIdUsuarioAreaAdicional(0);
		
		listaUsuariosAreaAdicional.clear();;
		listaUsuariosAreaPresupuesto.clear();;
		listaUsuariosAreaAsignar.clear();
	}
	
	private void asignarValoresPeticiones(Peticion peticion, Integer statusActualPeticion) throws Exception {
		
		
		
		//Este metodo solo se carga al inicializar la pantalla (@postconstruct)
		
		setIdPrioridadSeleccionada(peticion.getPrioridad().getIdtcgeneral());
		
		//Se valida que el statuas de la peticion sea "VALIDADA-3-" y se cargan las listas de las Areas
		//por si se quiere hacer una reasignacion
		if(statusActualPeticion == 3 || statusActualPeticion == 4){
			
			if(peticion.getArea()!= null){
				setIdAreaAsignada(peticion.getArea().getId());
			}
			if(peticion.getAreaPresupuesto()!= null){
				setIdAreaPresupuesto(peticion.getAreaPresupuesto().getId());
			}
			if(peticion.getAreaAdicional() != null){
				setIdAreaAdicional(peticion.getAreaAdicional().getId());
			}
			listaAreasAsignar = consultarCatalogo(Area.class, new Area());
			
			
			Area area = new Area();
			area.setEsPresupuesto(1);
			listaAreasPresupuesto = consultarCatalogo(Area.class, area);
			
			//listaAreasAdicional = consultarCatalogo(Area.class, new Area());
			
			listaPrioridad = consultarCatalogo(Prioridad.class, new Prioridad());
		}else{
			
			//Si el estatus es > de 3 solo se cargan las areas especificas ya que los combos estaran
			//inhabilitados
			listaAreasAsignar = new ArrayList<Area>();
			listaAreasPresupuesto = new ArrayList<Area>();
			
			//listaAreasAdicional = new ArrayList<Area>();
			
			if(peticion.getArea()!= null){
				setIdAreaAsignada(peticion.getArea().getId());
				Area area = (Area) obtenerEntidad(peticion.getArea().getId(), Area.class);
				setIdAreaAsignada(area.getIdArea());
				listaAreasAsignar.clear();
				listaAreasAsignar.add(area);
				
			}
			
			if(peticion.getAreaPresupuesto()!= null){
				setIdAreaPresupuesto(peticion.getAreaPresupuesto().getId());
				Area area = (Area) obtenerEntidad(peticion.getAreaPresupuesto().getId(), Area.class);
				setIdAreaPresupuesto(area.getIdArea());
				listaAreasPresupuesto.clear();
				listaAreasPresupuesto.add(area);
			}
			
			if(peticion.getAreaAdicional() != null){
				setIdAreaAdicional(peticion.getAreaAdicional().getId());
				Area area = (Area) obtenerEntidad(peticion.getAreaAdicional().getId(), Area.class);
				setIdAreaAdicional(area.getIdArea());
				listaAreasAdicional.clear();
				listaAreasAdicional.add(area);
			}
			
		}
		
				 		
		//Solo se cargan los usuarios especificos
		if(peticion.getUsuarioAsignado()!= null){
			UsuarioSistema usuario = (UsuarioSistema) obtenerEntidad(peticion.getUsuarioAsignado().getId(), UsuarioSistema.class);
			setIdUsuarioAreaAsignada(usuario.getIdUsuarioSistema());
			listaUsuariosAreaAsignar.add(usuario);
		}
		
		if(peticion.getUsuarioPresupuesto()!= null){
			UsuarioSistema usuario = (UsuarioSistema) obtenerEntidad(peticion.getUsuarioPresupuesto().getId(), UsuarioSistema.class);
			setIdUsuarioAreaPresupuesto(usuario.getIdUsuarioSistema());
			listaUsuariosAreaPresupuesto.add(usuario);
		}
		
		if(peticion.getUsuarioAdicional()!= null){
			UsuarioSistema usuario = (UsuarioSistema) obtenerEntidad(peticion.getUsuarioAdicional().getId(), UsuarioSistema.class);
			setIdUsuarioAreaAdicional(usuario.getIdUsuarioSistema());
			listaUsuariosAreaAdicional.add(usuario);
		}
		
		
		
	}
	
	
	private boolean datosCapturados(){
		
		boolean result = true;
		
		if(getIdUsuarioAreaAsignada() == 0){
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar un usuario para el area asignada",""));
			result = false;
		}
		
		/*if(getIdUsuarioAreaPresupuesto() == 0){
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar un usuario para el area de presupuesto",""));
			result = false;
		}*/
		
		if(getIdPrioridadSeleccionada() == 0){
			FacesContext.getCurrentInstance().addMessage("autorizar_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe seleccionar una prioridad valida [ALTA,MEDIA,BAJA O URGENTE]",""));
			result = false;
		}
		
		return result;
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
	
	
	public Boolean getMostrarBotones() {
		return mostrarBotones;
	}


	public void setMostrarBotones(Boolean mostrarBotones) {
		this.mostrarBotones = mostrarBotones;
	}


	/*public AreaBean getDetalleArea() {
		return detalleArea;
	}


	public void setDetalleArea(AreaBean detalleArea) {
		this.detalleArea = detalleArea;
	}*/


	public Peticion getPeticion() {
		return peticion;
	}


	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}


	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}


	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}


	public List<Area> getListaAreasAsignar() {
		return listaAreasAsignar;
	}

	public void setListaAreasAsignar(List<Area> listaAreasAsignar) {
		this.listaAreasAsignar = listaAreasAsignar;
	}

	public List<Area> getListaAreasPresupuesto() {
		return listaAreasPresupuesto;
	}

	public void setListaAreasPresupuesto(List<Area> listaAreasPresupuesto) {
		this.listaAreasPresupuesto = listaAreasPresupuesto;
	}

	public List<Area> getListaAreasAdicional() {
		return listaAreasAdicional;
	}

	public void setListaAreasAdicional(List<Area> listaAreasAdicional) {
		this.listaAreasAdicional = listaAreasAdicional;
	}

	public List<UsuarioSistema> getListaUsuariosAreaAsignar() {
		return listaUsuariosAreaAsignar;
	}

	public void setListaUsuariosAreaAsignar(List<UsuarioSistema> listaUsuariosAreaAsignar) {
		this.listaUsuariosAreaAsignar = listaUsuariosAreaAsignar;
	}

	public List<UsuarioSistema> getListaUsuariosAreaPresupuesto() {
		return listaUsuariosAreaPresupuesto;
	}

	public void setListaUsuariosAreaPresupuesto(List<UsuarioSistema> listaUsuariosAreaPresupuesto) {
		this.listaUsuariosAreaPresupuesto = listaUsuariosAreaPresupuesto;
	}

	public List<UsuarioSistema> getListaUsuariosAreaAdicional() {
		return listaUsuariosAreaAdicional;
	}

	public void setListaUsuariosAreaAdicional(List<UsuarioSistema> listaUsuariosAreaAdicional) {
		this.listaUsuariosAreaAdicional = listaUsuariosAreaAdicional;
	}

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

	public Integer getIdAreaAdicional() {
		return idAreaAdicional;
	}

	public void setIdAreaAdicional(Integer idAreaAdicional) {
		this.idAreaAdicional = idAreaAdicional;
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

	public Integer getIdUsuarioAreaAdicional() {
		return idUsuarioAreaAdicional;
	}

	public void setIdUsuarioAreaAdicional(Integer idUsuarioAreaAdicional) {
		this.idUsuarioAreaAdicional = idUsuarioAreaAdicional;
	}


	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}


	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}


	public boolean isHabilitarCaptura() {
		return habilitarCaptura;
	}


	public void setHabilitarCaptura(boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}


	public boolean isHabilitarBtnValidar() {
		return habilitarBtnValidar;
	}


	public void setHabilitarBtnValidar(boolean habilitarBtnValidar) {
		this.habilitarBtnValidar = habilitarBtnValidar;
	}


	public boolean isHabilitarBtnRechazar() {
		return habilitarBtnRechazar;
	}


	public void setHabilitarBtnRechazar(boolean habilitarBtnRechazar) {
		this.habilitarBtnRechazar = habilitarBtnRechazar;
	}


	public boolean isHabilitarBtnSuspender() {
		return habilitarBtnSuspender;
	}


	public void setHabilitarBtnSuspender(boolean habilitarBtnSuspender) {
		this.habilitarBtnSuspender = habilitarBtnSuspender;
	}


	public boolean isHabilitarBtnReasignar() {
		return habilitarBtnReasignar;
	}


	public void setHabilitarBtnReasignar(boolean habilitarBtnReasignar) {
		this.habilitarBtnReasignar = habilitarBtnReasignar;
	}


	public boolean isHabilitarBtnCancelar() {
		return habilitarBtnCancelar;
	}


	public void setHabilitarBtnCancelar(boolean habilitarBtnCancelar) {
		this.habilitarBtnCancelar = habilitarBtnCancelar;
	}


	public boolean isHabilitarLnkVer() {
		return habilitarLnkVer;
	}


	public void setHabilitarLnkVer(boolean habilitarLnkVer) {
		this.habilitarLnkVer = habilitarLnkVer;
	}


	public String getMensajeConfirmDialog() {
		return mensajeConfirmDialog;
	}


	public void setMensajeConfirmDialog(String mensajeConfirmDialog) {
		this.mensajeConfirmDialog = mensajeConfirmDialog;
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


	public List<Prioridad> getListaPrioridad() {
		return listaPrioridad;
	}


	public void setListaPrioridad(List<Prioridad> listaPrioridad) {
		this.listaPrioridad = listaPrioridad;
	}


	public int getIdPrioridadSeleccionada() {
		return idPrioridadSeleccionada;
	}


	public void setIdPrioridadSeleccionada(int idPrioridadSeleccionada) {
		this.idPrioridadSeleccionada = idPrioridadSeleccionada;
	}


	public String getEtiquetaBtnAutorizar() {
		return etiquetaBtnAutorizar;
	}


	public void setEtiquetaBtnAutorizar(String etiquetaBtnAutorizar) {
		this.etiquetaBtnAutorizar = etiquetaBtnAutorizar;
	}

	
	
	
}
