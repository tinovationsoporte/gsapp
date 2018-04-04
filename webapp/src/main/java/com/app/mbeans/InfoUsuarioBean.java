package com.app.mbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="infoUsuarioBean")
@ViewScoped
public class InfoUsuarioBean extends MBeanAbstracto {

	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	private UsuarioSistema usuarioSistema;
	
	private String password1;
	private String password2;
	
	private Integer[] mostrarPassword1;
	private Integer[] mostrarPassword2;
	
	private Boolean mostrarSeccionPasswords;
		
	private String etiquetaLink;
	
	@PostConstruct
	public void Inicializar(){
		
		System.out.println("***\n\n\n InfoUsuarioBean.Inicializar() ....");
		usuarioSistema = (UsuarioSistema)getManagedBean("#{aplicacion.usuario}", UsuarioSistema.class);
		//System.out.println("***\n\n\n usuarioSistema-> " + usuarioSistema);
		setMostrarSeccionPasswords(false);
		setEtiquetaLink("Actualizar Password");
	}
	
	
	public void actualizar(){	
		
		setMostrarSeccionPasswords(!getMostrarSeccionPasswords());
		
		setEtiquetaLink(getMostrarSeccionPasswords() ? "Ocultar Passwords" : "Actualizar Passwords");
	}
	
		
	public void actualizarCatalogo(ActionEvent e){	
		
		if(passwordsValidos()){
		
			try {
				
				usuarioSistema.setPassword(getPassword1());
				
				ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
				peticion.setEntidad(usuarioSistema);		
				servicioCatalogo.actualizarCatalogo(peticion);
				setMostrarSeccionPasswords(false);
				setEtiquetaLink("Actualizar Password");
				setPassword1(null);
				setPassword2(null);
				
				FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_INFO ,"El password ha sido actualizado. ",""));

			} catch (ExcepcionServicioFachada ex) {
				FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_FATAL ,"Ocurrio un error al actualizar el password. " + ex.getMessage(),""));
				ex.printStackTrace();
				
			}			
		}
		
				
	}
	
	protected boolean passwordsValidos(){	   
	   
	   if (getPassword1() == null || getPassword1().isEmpty()){
		   getCurrentFacesContext().validationFailed();
		   FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Debe capturar un password",""));
		   return false;
	   }
	   
	   if (getPassword2() == null || getPassword2().isEmpty()){
		   getCurrentFacesContext().validationFailed();
		   FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Debe capturar la confirmacion del password",""));
		   return false;
	   }
	   
	   if (getPassword1().length() < 8){
		   getCurrentFacesContext().validationFailed();
		   FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_ERROR ,"El pasword debe tener almenos 8 caracteres",""));
		   return false;
	   }
	   
	   if (getPassword1().compareTo(getPassword2()) != 0){
		   getCurrentFacesContext().validationFailed();
		   FacesContext.getCurrentInstance().addMessage("infoUsuario_msges", new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Los paswords deben ser iguales",""));
		   return false;
	   }						
	
	   return true;
	}

	public String getEtiquetaLink() {
		return etiquetaLink;
	}


	public void setEtiquetaLink(String etiquetaLink) {
		this.etiquetaLink = etiquetaLink;
	}


	public Boolean getMostrarSeccionPasswords() {
		return mostrarSeccionPasswords;
	}

	public void setMostrarSeccionPasswords(Boolean mostrarSeccionPasswords) {
		this.mostrarSeccionPasswords = mostrarSeccionPasswords;
	}	

	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	
	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Integer[] getMostrarPassword1() {
		return mostrarPassword1;
	}

	public void setMostrarPassword1(Integer[] mostrarPassword1) {
		this.mostrarPassword1 = mostrarPassword1;
	}

	public Integer[] getMostrarPassword2() {
		return mostrarPassword2;
	}

	public void setMostrarPassword2(Integer[] mostrarPassword2) {
		this.mostrarPassword2 = mostrarPassword2;
	}


	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}


	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}	
	
	
	
	
}
