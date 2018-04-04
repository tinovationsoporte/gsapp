package com.app.mbeans.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.app.mbeans.peticiones.ConsultaPeticionesBean;
import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.DetallePresupuesto;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Presupuesto;
import com.modelo.datos.app.StatusPresupuesto;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.PartidasPresupuesto;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.ActualizarPresupuestoRespuesta;
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

@ManagedBean(name="presupuestoBean")
@ViewScoped
public class PresupuestoBean extends MBeanAbstracto {	
	
	private Date fechaElaboracionPresupuesto;
	private String concepto;
	private Double cantidad;
	private Double pu;
	private List<PartidasPresupuesto> listaPartidas;
	
	private DetallePresupuesto partidaSeleccionada;
	private Double sumaTotal;
	
	private Boolean mostrarPresupuesto;
	private String mensajePresupuesto;
	
	private UsuarioSistema usuario;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;	
	
	private Peticion peticion;
	
	private Presupuesto presupuesto;
	
	private boolean mostrarContenido;
	
	private Integer idPresupuesto;
	private Integer idUsuarioSistema;
	private Integer idStatusPeticion;
	
	private boolean mostrarBtnRechazar;
	private boolean mostrarBtnAutorizar;
	private boolean habilitarBtnAgregarPartida;
	private boolean habilitarBtnGuardar;
	private boolean habilitarCaptura;
	private boolean mostrarLnkRemover;
	private boolean habilitarBtnAutorizar;
	private boolean habilitarBtnRechazar;
	
	
	@PostConstruct
	public void inicializar(){
		
	
		try{
			idUsuarioSistema = ((UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class) ).getIdUsuarioSistema();	
			
			Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticionDashboard").toString());
			
			peticion = consultarPeticion(idPeticion);
	
			
			System.out.println("\n\n\n\tPresupuestoBean.inicializar()");		
			System.out.println("***\n\nidPeticion -> " + idPeticion);

			
			
			System.out.println("\n\n\n\tidStatusPeticion -> " + peticion.getStatusPeticion().getIdStatusPeticion());
			if(peticion.getRequierePresupuesto() == 1){
			
				
				if(peticion.getStatusPeticion().getIdStatusPeticion() > 3){
					
					usuario = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
					
					setIdPresupuesto(0);
					
					if(peticion.getPresupuesto()  != null){
						
						setIdPresupuesto(peticion.getPresupuesto().getIdPresupuesto());			
						inicializarListaPartidas();
						
					}
					
					
					setMostrarContenido(true);
					
					setMostrarBtnRechazar((peticion.getStatusPeticion().getId() == 6) && (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2)); 
					
					setMostrarBtnAutorizar((peticion.getStatusPeticion().getId() == 6) && (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2));
					
					setHabilitarBtnRechazar((peticion.getStatusPeticion().getId() == 6) && (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2));					
					
					setHabilitarBtnAutorizar((peticion.getStatusPeticion().getId() == 6) && (usuario.getRol().getIdRol() == 1 || usuario.getRol().getIdRol() == 2));
					
					setHabilitarBtnAgregarPartida(peticion.getStatusPeticion().getId() == 4 
							|| peticion.getStatusPeticion().getId() == 5 
							|| peticion.getStatusPeticion().getId() == 8);
					
					setHabilitarBtnGuardar(peticion.getStatusPeticion().getId() == 4 
							|| peticion.getStatusPeticion().getId() == 5
							|| peticion.getStatusPeticion().getId() == 8);
					
					setHabilitarCaptura(peticion.getStatusPeticion().getId() == 4 
							|| peticion.getStatusPeticion().getId() == 5 
							|| peticion.getStatusPeticion().getId() == 8);
					
					setMostrarLnkRemover(peticion.getStatusPeticion().getId() == 4 
							|| peticion.getStatusPeticion().getId() == 5 
							|| peticion.getStatusPeticion().getId() == 8);				
					
				}else{
					
					setMostrarContenido(false);
					
					FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en el proceso","" ));
				}						
				
			}else{
				
				setMostrarContenido(false);
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges2", 
						new FacesMessage(FacesMessage.SEVERITY_WARN,"Esta peticion no requiere un presupuesto","" ));
			}
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en el proceso","" ));
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges2", 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Esta peticion no requiere un presupuesto","" ));
		}
	}	
		
	public void agregarPartida(ActionEvent e){		
		
		if(datosCapturados()){
			
			if(getIdPresupuesto() == 0){
				
				try {
				
					CrearPresupuestoPeticion crearPresupuestoPeticion = new CrearPresupuestoPeticion();
					crearPresupuestoPeticion.setFechaCaptura(new Date());
					crearPresupuestoPeticion.setIdPeticion(peticion.getIdPeticion());
					crearPresupuestoPeticion.setIdUsuario(idUsuarioSistema);
					crearPresupuestoPeticion.setIdStatusPresupuesto(20);			
					
					
					CrearPresupuestoRespuesta crearPresupuestoRespuesta = servicioPeticiones.crearPresupuesto(crearPresupuestoPeticion);
					setIdPresupuesto(crearPresupuestoRespuesta.getIdPresupuesto());		
				
				} catch (ExcepcionServicioFachada ex) {
					ex.printStackTrace();
					
					FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
							new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al crear el presupuesto" + ex.getMessage(),"" ));
					return;
				}
				
				
			}
			
			
			
			AgregarPartidaPresupuestoPeticion agregarPartidaPresupuestoPeticion = new AgregarPartidaPresupuestoPeticion();
			
			agregarPartidaPresupuestoPeticion.setFecha(new Date());
			agregarPartidaPresupuestoPeticion.setConcepto(getConcepto().toUpperCase());
			agregarPartidaPresupuestoPeticion.setCantidad(getCantidad());
			agregarPartidaPresupuestoPeticion.setPu(getPu());
			agregarPartidaPresupuestoPeticion.setIdUsuarioSistema(getUsuario().getIdUsuarioSistema());
			agregarPartidaPresupuestoPeticion.setIdPresupuesto(getIdPresupuesto());
			agregarPartidaPresupuestoPeticion.setIdPeticion(getPeticion().getIdPeticion());
			
			try {
				
				servicioPeticiones.agregarPartidaPresupuesto(agregarPartidaPresupuestoPeticion);
				
				inicializarListaPartidas();
				
				limpiarCampos();
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se ha agregado la partida al presupuesto","" ));
			
			} catch (ExcepcionServicioFachada ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al crear la partida" + ex.getMessage(),"" ));
			}
			
			
		}
		
				
	}
	
	public void quitarPartida(PartidasPresupuesto partida){
		
		
		try {
			
			if(listaPartidas.size() == 1){
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"El presupuesto debe de contar con almenos una partida.","" ));
				
			}else{
				
				servicioPeticiones.removerPartidaPresupuesto(partida.getIdPartida());
				inicializarListaPartidas();				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Se elimino la partida.","" ));
			}
			
			
			
			
		} catch (ExcepcionServicioFachada e) {
			// 
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al eliminar la partida" + e.getMessage(),"" ));
		}	
		
	}
	
	
	public void guardar(ActionEvent e){
		
		if(tienePartidas()){
		
			try {
				
				ActualizarPresupuestoPeticion actualizarPresupuestoPeticion = new ActualizarPresupuestoPeticion();
				actualizarPresupuestoPeticion.setMovimiento("PRESUPUESTADO");
				actualizarPresupuestoPeticion.setIdStatusPeticion(6); //status= PRESUPUESTADA	
				actualizarPresupuestoPeticion.setIdPeticion(getPeticion().getIdPeticion());
				actualizarPresupuestoPeticion.setIdPresupuesto(getIdPresupuesto());			
				actualizarPresupuestoPeticion.setIdUsuario(idUsuarioSistema);
				actualizarPresupuestoPeticion.setComentarios("Se guarda el presupuesto");
				actualizarPresupuestoPeticion.setIdStatusPresupuesto(20);
				
			
				ActualizarPresupuestoRespuesta actualizarPresupuestoRespuesta = 
							servicioPeticiones.actualizarPresupuesto(actualizarPresupuestoPeticion);
				
				idStatusPeticion = actualizarPresupuestoRespuesta.getIdStatusPeticion();
				
				setFechaElaboracionPresupuesto(new Date());
				
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"El presupuesto ha sido guardado","" ));
				
				setHabilitarCaptura(false);
				setHabilitarBtnAgregarPartida(false);
				setHabilitarBtnGuardar(false);
			
			} catch (ExcepcionServicioFachada ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al guardar el presupuesto","" ));
			}			
		}		
	}
	
	public void autorizar(ActionEvent e){
		
		if(tienePartidas()){
			
			try {
				
				ActualizarPresupuestoPeticion actualizarPresupuestoPeticion = new ActualizarPresupuestoPeticion();
				actualizarPresupuestoPeticion.setMovimiento("PRESUPUESTO AUTORIZADO");
				actualizarPresupuestoPeticion.setIdStatusPeticion(9); //status= PRESUPUESTO AUTORIZADO	
				actualizarPresupuestoPeticion.setIdPeticion(getPeticion().getIdPeticion());
				actualizarPresupuestoPeticion.setIdPresupuesto(getIdPresupuesto());
				actualizarPresupuestoPeticion.setIdUsuario(idUsuarioSistema);
				actualizarPresupuestoPeticion.setComentarios("Se guarda el presupuesto");
				actualizarPresupuestoPeticion.setIdStatusPresupuesto(20);
				
			
				ActualizarPresupuestoRespuesta actualizarPresupuestoRespuesta = 
							servicioPeticiones.actualizarPresupuesto(actualizarPresupuestoPeticion);
				
				idStatusPeticion = actualizarPresupuestoRespuesta.getIdStatusPeticion();
				
				setHabilitarBtnAutorizar(false);
				setHabilitarBtnRechazar(false);
				setHabilitarBtnGuardar(false);
				
				setFechaElaboracionPresupuesto(new Date());
				
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"El presupuesto ha sido autorizado","" ));
			
			} catch (ExcepcionServicioFachada ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al autorizar el presupuesto","" ));
			}	
		}
				
	}
	
	
	public void rechazar(ActionEvent e){
		
		if(tienePartidas()){
			
			try {
				
				ActualizarPresupuestoPeticion actualizarPresupuestoPeticion = new ActualizarPresupuestoPeticion();
				actualizarPresupuestoPeticion.setMovimiento("PRESUPUESTO RECHAZADO");
				actualizarPresupuestoPeticion.setIdStatusPeticion(8); //status= PRESUPUESTO RECHAZADO	
				actualizarPresupuestoPeticion.setIdPeticion(getPeticion().getIdPeticion());
				actualizarPresupuestoPeticion.setIdPresupuesto(getIdPresupuesto());
				actualizarPresupuestoPeticion.setIdUsuario(idUsuarioSistema);
				actualizarPresupuestoPeticion.setComentarios("Se rechazo el presupuesto");
				actualizarPresupuestoPeticion.setIdStatusPresupuesto(20);
				
			
				ActualizarPresupuestoRespuesta actualizarPresupuestoRespuesta = 
							servicioPeticiones.actualizarPresupuesto(actualizarPresupuestoPeticion);
				
				idStatusPeticion = actualizarPresupuestoRespuesta.getIdStatusPeticion();
				
				setHabilitarBtnAutorizar(false);
				setHabilitarBtnRechazar(false);
				setHabilitarBtnGuardar(false);
				
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"El presupuesto ha sido rechazado","" ));
			
			} catch (ExcepcionServicioFachada ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al rechazar el presupuesto","" ));
			}	
		}
				
	}
	
	@Override
	public void resetBean(ActionEvent e) throws ExcepcionAplicacion {
		System.out.println("\n\n\n\t **** resetando consultaDetalleBean");
			
		DashboardBean dashboardBean = (DashboardBean) getViewContext().get("dashboardBean");
		System.out.println("\n\n\n\t **** dashboardBean ->" + dashboardBean);
		dashboardBean.buscarFolio(e);			
				
		super.resetBean(e);
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
	
	
	private boolean datosCapturados(){
		
		boolean result = true;
		
		if(getConcepto() == null || getConcepto().isEmpty()){
			result = false;
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe ingresar el concepto","" ));			
		}
		
		if(getCantidad() == null || getCantidad().toString().isEmpty()){
			result = false;
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe ingresar la cantidad","" ));							
		}		
		
		if(getPu() == null || getPu().toString().isEmpty()){
			result = false;
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe ingresar un precio unitario (PU)","" ));			
		}
		
		return result;
	}
	
	
	private boolean tienePartidas(){
		
		boolean result = true;
		
		if(listaPartidas.isEmpty()){
			result = false;
			getCurrentFacesContext().validationFailed();
			FacesContext.getCurrentInstance().addMessage("presupuesto_msges", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Debe haber almenos una partida en el presupuesto","" ));			
		}	
		
		return result;
	}	
	
	private void limpiarCampos(){
		setCantidad(null);
		setConcepto(null);
		setPu(null);
		
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
	
	
	public boolean getMostrarContenido() {
		return mostrarContenido;
	} 
	
	public void setMostrarContenido(boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
	}
	
	public Peticion getPeticion() {
		return peticion;
	}
	
	public String getMensajePresupuesto() {
		return mensajePresupuesto;
	}

	public void setMensajePresupuesto(String mensajePresupuesto) {
		this.mensajePresupuesto = mensajePresupuesto;
	}	
	
	public Boolean getMostrarPresupuesto() {
		return mostrarPresupuesto;
	}

	public void setMostrarPresupuesto(Boolean mostrarPresupuesto) {
		this.mostrarPresupuesto = mostrarPresupuesto;
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
	
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}



	public Double getCantidad() {
		return cantidad;
	}



	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}



	public Double getPu() {
		return pu;
	}



	public void setPu(Double pu) {
		this.pu = pu;
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



	public DetallePresupuesto getPartidaSeleccionada() {
		return partidaSeleccionada;
	}



	public void setPartidaSeleccionada(DetallePresupuesto partidaSeleccionada) {
		this.partidaSeleccionada = partidaSeleccionada;
	}

	

	public List<PartidasPresupuesto> getListaPartidas() {
		return listaPartidas;
	}

	public void setListaPartidas(List<PartidasPresupuesto> listaPartidas) {
		this.listaPartidas = listaPartidas;
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






	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}






	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}






	public UsuarioSistema getUsuario() {
		return usuario;
	}






	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public boolean getMostrarBtnRechazar() {
		return mostrarBtnRechazar;
	}

	public void setMostrarBtnRechazar(boolean mostrarBtnRechazar) {
		this.mostrarBtnRechazar = mostrarBtnRechazar;
	}

	public boolean getMostrarBtnAutorizar() {
		return mostrarBtnAutorizar;
	}

	public void setMostrarBtnAutorizar(boolean mostrarBtnAutorizar) {
		this.mostrarBtnAutorizar = mostrarBtnAutorizar;
	}

	public boolean getHabilitarBtnAgregarPartida() {
		return habilitarBtnAgregarPartida;
	}

	public void setHabilitarBtnAgregarPartida(boolean habilitarBtnAgregarPartida) {
		this.habilitarBtnAgregarPartida = habilitarBtnAgregarPartida;
	}

	public boolean getHabilitarBtnGuardar() {
		return habilitarBtnGuardar;
	}

	public void setHabilitarBtnGuardar(boolean habilitarBtnGuardar) {
		this.habilitarBtnGuardar = habilitarBtnGuardar;
	}

	public boolean getHabilitarCaptura() {
		return habilitarCaptura;
	}

	public void setHabilitarCaptura(boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}

	public boolean getMostrarLnkRemover() {
		return mostrarLnkRemover;
	}

	public void setMostrarLnkRemover(boolean mostrarLnkRemover) {
		this.mostrarLnkRemover = mostrarLnkRemover;
	}

	public boolean getHabilitarBtnAutorizar() {
		return habilitarBtnAutorizar;
	}

	public void setHabilitarBtnAutorizar(boolean habilitarBtnAutorizar) {
		this.habilitarBtnAutorizar = habilitarBtnAutorizar;
	}

	public boolean getHabilitarBtnRechazar() {
		return habilitarBtnRechazar;
	}

	public void setHabilitarBtnRechazar(boolean habilitarBtnRechazar) {
		this.habilitarBtnRechazar = habilitarBtnRechazar;
	}
	
	

}
