package com.app.mbeans.peticiones;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.EntidadMpal;
import com.modelo.datos.app.Prioridad;
import com.modelo.datos.app.StatusPeticion;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.estructuras.Peticiones;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.peticion.ActualizarStatusPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="consultaPeticionesBean")
@ViewScoped
public class ConsultaPeticionesBean extends MBeanAbstracto implements Serializable {
	
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;
	

	private ConsultarCatalogoPeticion consultarCatalogoPeticion;
	private ConsultarCatalagoRespuesta consultarCatalogoRespuesta;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	//Datos de busqueda
	private Long folio;
	private String solicitante;
	
	private List<Prioridad> listaPrioridades;
	private Integer idPrioridadSeleccionada;
	
	private List<Area> listaAreas;
	private Integer idAreaSeleccionada;
	
	private List<StatusPeticion> listaEstatus;
	private Integer idEstatusSeleccionado;	
	
	private List<EntidadMpal> listaEntidadMpal;
	private Integer idEntidadMpalSeleccionada;
	
	private Date fechaDeBusqueda;
	 
	private Integer idUsuarioSistema;
	
	private Integer idStatusPeticionActualizar;
	
	private Integer idPeticionActualizar;
	//Datos que se utilizan para manipular la tabla
	private List<Peticiones> listaPeticiones;
	
	private String mensajeConfirmDialog;

	private String movimientoActualizar;

	private String comentarioActualizar;

	private boolean mostrarColumnaSuspender;
	
	private Integer idArea;
	private Integer esAreaDefault;
	
	private Boolean mostrarCmbAreas;
	private Integer idRolUsuario;
	
	
	@PostConstruct
	public void inicializar() {
		
		UsuarioSistema usuario = (UsuarioSistema)getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
		idUsuarioSistema = usuario.getIdUsuarioSistema();
		idRolUsuario = usuario.getRol().getId();
		idArea = usuario.getArea().getId();
		esAreaDefault = usuario.getArea().getEsDefault();
		
		this.listaPrioridades = crearCatalogo(Prioridad.class, new Prioridad());;
		this.listaAreas = crearCatalogo(Area.class, new Area());
		this.listaEstatus = crearCatalogo(StatusPeticion.class, new StatusPeticion());
		this.listaEntidadMpal = crearCatalogo(EntidadMpal.class, new EntidadMpal());	
		
		ConsultarPeticionesPeticion consultarPeticion = new ConsultarPeticionesPeticion();
		
		
		if((idRolUsuario == 1 || idRolUsuario == 2 )|| esAreaDefault == 1){
			
			consultarPeticion.setIdArea(null);
		}else{
			
			consultarPeticion.setIdArea(idArea);
		}		
		
		this.listaPeticiones = consultarPeticiones(consultarPeticion);
		
		
		setMostrarCmbAreas((idRolUsuario == 1 || idRolUsuario == 2 ) || esAreaDefault == 1);
		setMostrarColumnaSuspender(usuario.getRol().getIdRol() == 1);
		
	}	
	
	
	
	
	
	public void buscar(ActionEvent evento) {
			
		
		ConsultarPeticionesPeticion peticion = new ConsultarPeticionesPeticion();
		peticion.setFolio(getFolio() != null  ? getFolio() :  null);		
		peticion.setSolicitante(getSolicitante() == null || getSolicitante().isEmpty() ? null : "%"+ getSolicitante()+"%" );
		//peticion.setFechaCaptura(getFechaDeBusqueda());
		peticion.setIdEntidadMpal(getIdEntidadMpalSeleccionada() == null || getIdEntidadMpalSeleccionada() == 0 ?  null : getIdEntidadMpalSeleccionada());
		
		
		if((idRolUsuario == 1 || idRolUsuario == 2 ) || esAreaDefault == 1){
			peticion.setIdArea(getIdAreaSeleccionada() == null || getIdAreaSeleccionada()== 0 ? null : getIdAreaSeleccionada());
		}else{
			peticion.setIdArea(idArea);
		}
		 
		
		
		peticion.setIdStatus(getIdEstatusSeleccionado() == null || getIdEstatusSeleccionado()== 0 ? null : getIdEstatusSeleccionado() );
				
		
		this.listaPeticiones = consultarPeticiones(peticion);
	}
	
	public void limpiar(ActionEvent evento) {
	
		ConsultarPeticionesPeticion consultarPeticion = new ConsultarPeticionesPeticion();
		
		if(idRolUsuario == 1 || esAreaDefault == 1){
			consultarPeticion.setIdArea(null);
		}else{
			consultarPeticion.setIdArea(idArea);
		}
		
		this.listaPeticiones = consultarPeticiones(consultarPeticion);
		
		setFolio(null);
		//setFechaDeBusqueda(null);		
		setIdEntidadMpalSeleccionada(0);		
		setIdAreaSeleccionada(0);		
		setIdEstatusSeleccionado(0);
		setSolicitante(null);
		
	}
	
	
	public void actualizarStatusPeticion(Integer idStatus, Integer idPeticion, String folio){
		
		System.out.println("idStatus-> " + idStatus);
		System.out.println("idPeticion-> " + idPeticion);
		
		
		idStatusPeticionActualizar = idStatus;
		idPeticionActualizar = idPeticion;
		
		StringBuilder sb = new StringBuilder("Esta seguro que desea ");
		
		switch (idStatus) {
		case 19:
			sb.append("SUSPENDER ");
			movimientoActualizar  = "SUSPENSION";
			comentarioActualizar = "Suspencion de peticion";		
			
			break;

		case 20:
			sb.append("CANCELAR ");
			movimientoActualizar  = "CANCELACION";
			comentarioActualizar = "Cancelacion de peticion";
			break;
			
		default:
			sb.append("REACTIVAR ");
			movimientoActualizar  = "REACTIVACION";
			comentarioActualizar = "Reactivacion de peticion";
			break;
		}		
		
		sb.append("el folio ")
		  .append(folio);
		
		setMensajeConfirmDialog(sb.toString());
		
	}
	
	
	public void realizarAccion(ActionEvent e){
		
		actualizarPeticion(idPeticionActualizar, idStatusPeticionActualizar, this.idUsuarioSistema, comentarioActualizar , movimientoActualizar);		
		buscar(e);
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_FATAL,"Se actualizo la peticion de manera exitosa","" ));
	}
	
	
	
	public void cancelar(ActionEvent e){
		Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticion").toString());	
		//System.out.println("idPeticion -> " + idPeticion);
		
		actualizarPeticion(idPeticion, 20, this.idUsuarioSistema, "Cancelacion de peticion", "CANCELACION");		
	}
	
	public void suspender(ActionEvent e){
		Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticion").toString());
		System.out.println("idPeticion -> " + idPeticion);
		
		actualizarPeticion(idPeticion, 19, this.idUsuarioSistema, "Suspencion de peticion", "SUSPENCION");		
	}
	
	public void reactivar(ActionEvent e){
		//Integer idPeticion = Integer.parseInt(getRequestParameter("idPeticion").toString());		
		//actualizarPeticion(idPeticion, 16, this.idUsuarioSistema, "Cancelacion de peticion", "CANCELACION");		
	}
	
	private List consultarPeticiones(ConsultarPeticionesPeticion peticion){		
		System.out.println("Entrando a consultarPeticiones");
		//peticion = new ConsultarPeticionesPeticion();
		ConsultarPeticionesRespuesta respuesta = null;
		try {			
			
			respuesta = servicioPeticiones.consultarPeticiones(peticion);
			
			System.out.println("respuesta -> " + respuesta);
		
		} catch (ExcepcionServicioFachada e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar los folios.","" ));
		}		
		return respuesta.getPeticiones();
	}
	
	
	private List crearCatalogo(Class clazz, Entidad entidad){
		
		consultarCatalogoPeticion = new ConsultarCatalogoPeticion();
		consultarCatalogoPeticion.setClase(clazz);
		consultarCatalogoPeticion.setEntidad(entidad);
		
		try {
			
			consultarCatalogoRespuesta= servicioCatalogo.consultarCatalogo(consultarCatalogoPeticion);
		
		} catch (ExcepcionServicioFachada e) {			
			
			FacesContext.getCurrentInstance().addMessage(null, 
				 new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje del Sistema. Ocurrio un error al inicializar la pantalla.", ""));
			e.printStackTrace();
		}		 		
		
		return consultarCatalogoRespuesta.getEntidades();	
		
	}
	
	private void actualizarPeticion(Integer idPeticion, Integer idStatusPeticion, Integer idUsuarioSistema
					, String comentarios, String movimiento) {
		
		
		ActualizarStatusPeticionesPeticion peticion = new ActualizarStatusPeticionesPeticion();
		peticion.setIdPeticion(idPeticion);
		peticion.setIdStatusPeticion(idStatusPeticion);
		peticion.setIdUsuarioSistema(idUsuarioSistema);
		peticion.setMovimiento(movimiento);
		peticion.setComentarios(comentarios);
		
		
		try {
			servicioPeticiones.actualizarStatusPeticion(peticion);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage("consulta_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrion un error al actualizar el estatus de la peticion." + e.getMessage(),"" ));		}
		
	}
	
	
	public List<Peticiones> getListaPeticiones() {
		return listaPeticiones;
	}
	public void setListaPeticiones(List<Peticiones> listaPeticiones) {
		this.listaPeticiones = listaPeticiones;
	}	
	
	public Long getFolio() {
		return folio;
	}
	public void setFolio(Long folio) {
		this.folio = folio;
	}	
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Boolean getMostrarCmbAreas() {
		return mostrarCmbAreas;
	}

	public void setMostrarCmbAreas(Boolean mostrarCmbAreas) {
		this.mostrarCmbAreas = mostrarCmbAreas;
	}





	public Integer getIdPrioridadSeleccionada() {
		return idPrioridadSeleccionada;
	}
	public void setIdPrioridadSeleccionada(Integer idPrioridadSeleccionada) {
		this.idPrioridadSeleccionada = idPrioridadSeleccionada;
	}
	public Integer getIdAreaSeleccionada() {
		return idAreaSeleccionada;
	}
	public void setIdAreaSeleccionada(Integer idAreaSeleccionada) {
		this.idAreaSeleccionada = idAreaSeleccionada;
	}
	public Integer getIdEstatusSeleccionado() {
		return idEstatusSeleccionado;
	}
	public void setIdEstatusSeleccionado(Integer idEstatusSeleccionado) {
		this.idEstatusSeleccionado = idEstatusSeleccionado;
	}
	public Date getFechaDeBusqueda() {
		return fechaDeBusqueda;
	}
	public void setFechaDeBusqueda(Date fechaDeBusqueda) {
		this.fechaDeBusqueda = fechaDeBusqueda;
	}
	public List<Prioridad> getListaPrioridades() {
		return listaPrioridades;
	}
	public void setListaPrioridades(List<Prioridad> listaPrioridades) {
		this.listaPrioridades = listaPrioridades;
	}
	public List<Area> getListaAreas() {
		return listaAreas;
	}
	public void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}
	public List<StatusPeticion> getListaEstatus() {
		return listaEstatus;
	}
	public void setListaEstatus(List<StatusPeticion> listaEstatus) {
		this.listaEstatus = listaEstatus;
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





	public List getListaEntidadMpal() {
		return listaEntidadMpal;
	}





	public void setListaEntidadMpal(List listaEntidadMpal) {
		this.listaEntidadMpal = listaEntidadMpal;
	}





	public Integer getIdEntidadMpalSeleccionada() {
		return idEntidadMpalSeleccionada;
	}





	public void setIdEntidadMpalSeleccionada(Integer idEntidadMpalSeleccionada) {
		this.idEntidadMpalSeleccionada = idEntidadMpalSeleccionada;
	}





	public String getMensajeConfirmDialog() {
		return mensajeConfirmDialog;
	}





	public void setMensajeConfirmDialog(String mensajeConfirmDialog) {
		this.mensajeConfirmDialog = mensajeConfirmDialog;
	}





	public boolean getMostrarColumnaSuspender() {
		return mostrarColumnaSuspender;
	}





	public void setMostrarColumnaSuspender(boolean mostrarColumnaSuspender) {
		this.mostrarColumnaSuspender = mostrarColumnaSuspender;
	}
	
	
	
}
