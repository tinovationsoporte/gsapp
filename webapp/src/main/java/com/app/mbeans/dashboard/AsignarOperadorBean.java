package com.app.mbeans.dashboard;

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

import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.core.app.servicios.ServicioPeticion;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Peticion;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorPeticion;
import com.objetos.transf.datos.app.peticion.AsignarOperadorRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="asignarOperadorBean")
@ViewScoped
public class AsignarOperadorBean extends MBeanAbstracto {


	//Datos del Area de Asignacion

	//private String telefono;
	private Date fecha;
	private List<UsuarioSistema> listaOperadores;
	private Integer idUsuarioOperador;
	private Integer requiereEvidencia;
		
	private Peticion peticion;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;	
	
	private boolean habilitarBtnAsignar;
	private boolean habilitarBtnReasignar;
	
	
	private Integer idUsuarioSistema;
	private Integer idAreaOperador;
	
	private UsuarioSistema usuarioSistema;
	
	private boolean habilitarCaptura;
	
	private boolean mostrarContenido;
	
	@PostConstruct
	public void inicializar() {
	
		
		System.out.println("***\n\nasignarOperadorBean.inicializar()...\n\n");

		try {
		
			Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticionDashboard").toString());
			
			peticion = consultarPeticion(idPeticion);
	
			System.out.println("***\n\nidPeticion -> " + idPeticion);

			
			usuarioSistema = (UsuarioSistema) getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);	
			
			setIdUsuarioSistema(usuarioSistema.getIdUsuarioSistema());
			setIdAreaOperador(usuarioSistema.getArea().getIdArea()); 
			
			
			Rol rol= new Rol();
			rol.setId(4);
			Area area = new Area();
			area.setId(peticion.getArea().getId());		
			
			UsuarioSistema usuario = new UsuarioSistema();
			usuario = new UsuarioSistema();
			usuario.setArea(area);
			usuario.setRol(rol);
		
		
		
			listaOperadores = consultarCatalogo(UsuarioSistema.class, usuario);
			
			setIdUsuarioOperador(peticion.getUsuarioAdicional() == null ? 0 : peticion.getUsuarioAdicional().getIdUsuarioSistema());
			
			if(peticion.getStatusPeticion().getIdStatusPeticion() == 3){ 
			
				setHabilitarBtnAsignar(false);
				setHabilitarBtnReasignar(false);
				setHabilitarCaptura(true);	
				setMostrarContenido(true);	
				
			}else{
			
				if(peticion.getStatusPeticion().getIdStatusPeticion() <= 8){
				
					setMostrarContenido(false);	
					
					FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"No se ha alcanzado la fase en en el proceso","" ));
					
					//No se debe de mostrar el contenido de la seccion de asingar la peticion
					
				}else{
					
					if(peticion.getStatusPeticion().getIdStatusPeticion() == 9
							||peticion.getStatusPeticion().getIdStatusPeticion() == 10 ){
						setHabilitarCaptura(true);
						setHabilitarBtnAsignar(false);
						setHabilitarBtnReasignar(false);
						setMostrarContenido(true);
						FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
								new FacesMessage(FacesMessage.SEVERITY_WARN,"La peticion puede ser ASIGNADA","" ));
					}else{
					
						if(peticion.getStatusPeticion().getIdStatusPeticion() == 11 
								|| peticion.getStatusPeticion().getIdStatusPeticion() == 13){
							
							setHabilitarCaptura(false);
							setHabilitarBtnAsignar(false);
							setHabilitarBtnReasignar(true);
							setMostrarContenido(true);
							FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
									new FacesMessage(FacesMessage.SEVERITY_WARN,"La peticion esta ASIGNADA pero puede ser REASINGNADA","" ));					
						}else{
							
							setHabilitarBtnAsignar(false);
							setHabilitarBtnReasignar(false);
							setHabilitarCaptura(false);		
							setMostrarContenido(true);
							FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
									new FacesMessage(FacesMessage.SEVERITY_WARN,"El estatus de la peticion no permite que sea asignada","" ));
							
						}					
					}				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar los catalgos." + e.getMessage(),"" ));
		}
		
	}

	public void onListChange(AjaxBehaviorEvent e){
		
		if(getIdUsuarioOperador() != 0){
			
			setHabilitarBtnAsignar(true);
			setHabilitarBtnReasignar(false);
		}else{
			setHabilitarBtnAsignar(false);
			setHabilitarBtnReasignar(false);
		}		
	}	
	
	public void asignarOperador(ActionEvent e){
		
			
			AsignarOperadorPeticion asignarOperadorPeticion = new AsignarOperadorPeticion();
			asignarOperadorPeticion.setIdPeticion(peticion.getId());
			asignarOperadorPeticion.setIdUsuarioOperador(getIdUsuarioOperador());
			asignarOperadorPeticion.setIdUsuarioSistema(getIdUsuarioSistema());
			asignarOperadorPeticion.setIdAreaOperador(getIdAreaOperador());
			asignarOperadorPeticion.setComentarios("Operador Asignado");
			asignarOperadorPeticion.setRequiereEvidencia(getRequiereEvidencia());
			
			try {
			
				AsignarOperadorRespuesta respuesta = servicioPeticiones.asignarOperador(asignarOperadorPeticion);
			
				setFecha(respuesta.getFecha());
				
				FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"El operador ha sido asignado exitosamente","" ));
				
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("asignacion_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al asingar el operador","" ));
			}
			
			setHabilitarBtnAsignar(false);
			setHabilitarBtnReasignar(true);
			
	}
	
	public void reasignarOperador(ActionEvent e){
		
		setHabilitarBtnAsignar(true);
		setHabilitarBtnReasignar(false);	
		setHabilitarCaptura(true);
		
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
		
		return consultarCatalogoRespuesta.getEntidades();			
	}	
	
	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public boolean isHabilitarCaptura() {
		return habilitarCaptura;
	}

	public void setHabilitarCaptura(boolean habilitarCaptura) {
		this.habilitarCaptura = habilitarCaptura;
	}

	public boolean isHabilitarBtnAsignar() {
		return habilitarBtnAsignar;
	}

	public void setHabilitarBtnAsignar(boolean habilitarBtnAsignar) {
		this.habilitarBtnAsignar = habilitarBtnAsignar;
	}

	public boolean isHabilitarBtnReasignar() {
		return habilitarBtnReasignar;
	}

	public void setHabilitarBtnReasignar(boolean habilitarBtnReasignar) {
		this.habilitarBtnReasignar = habilitarBtnReasignar;
	}

	public Integer getIdUsuarioOperador() {
		return idUsuarioOperador;
	}

	public void setIdUsuarioOperador(Integer idUsuarioOperador) {
		this.idUsuarioOperador = idUsuarioOperador;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fechaAsignacionOperador) {
		this.fecha = fechaAsignacionOperador;
	}

	public List<UsuarioSistema> getListaOperadores() {
		return listaOperadores;
	}

	public void setListaOperadores(List<UsuarioSistema> listaOperadores) {
		this.listaOperadores = listaOperadores;
	}

	public Integer getRequiereEvidencia() {
		return requiereEvidencia;
	}

	public void setRequiereEvidencia(Integer requiereEvidencia) {
		this.requiereEvidencia = requiereEvidencia;
	}

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

	public Integer getIdAreaOperador() {
		return idAreaOperador;
	}

	public void setIdAreaOperador(Integer idAreaOperador) {
		this.idAreaOperador = idAreaOperador;
	}

	public boolean getMostrarContenido() {
		return mostrarContenido;
	}

	public void setMostrarContenido(boolean mostrarContenido) {
		this.mostrarContenido = mostrarContenido;
	}	
		
	
}
