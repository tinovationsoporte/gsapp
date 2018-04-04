package com.app.mbeans.peticiones;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;

@ManagedBean(name="areaBean")
@ViewScoped
public class AreaBean extends MBeanAbstracto {

	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;

	
	private Area infoArea;
	private UsuarioSistema infoUsuario;
	
	private int idArea;
	private int idUsuario;
	
	
	@PostConstruct
	public void init(){
		
		System.out.println("\n\n\n***AreaBean.init()");
		
		idArea = Integer.valueOf( getRequestParameter("idArea").toString() );
		idUsuario = Integer.valueOf( getRequestParameter("idUsuario").toString() );
		
		System.out.println("idArea > " + idArea);
		System.out.println("idUsuario -> " + idUsuario);
		try{
			ObtenerCatalogoPeticion peticion = new ObtenerCatalogoPeticion();
			peticion.setClase(Area.class);
			peticion.setIdEntidad(idArea);	
			ObtenerCatalogoRespuesta respuesta = servicioCatalogo.obtenerCatalogo(peticion);
			
			infoArea = (Area) respuesta.getEntidad();
			
			peticion.setClase(UsuarioSistema.class);
			peticion.setIdEntidad(idUsuario);	
			respuesta = servicioCatalogo.obtenerCatalogo(peticion);
			
			infoUsuario = (UsuarioSistema) respuesta.getEntidad();
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar los catalogos." + e.getMessage(),"" ));
		}
	}
	
	
	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}





	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}





	public UsuarioSistema getInfoUsuario() {
		return infoUsuario;
	}



	public void setInfoUsuario(UsuarioSistema infoUsuario) {
		this.infoUsuario = infoUsuario;
	}



	public Area getInfoArea() {
		return infoArea;
	}



	public void setInfoArea(Area infoArea) {
		this.infoArea = infoArea;
	}



	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}
	
}
