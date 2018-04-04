package com.app.mbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;


import com.app.vbeans.UsuarioViewBean;
import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.ServicioPeticionRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarMacPeticion;
import com.objetos.transf.datos.app.sistema.ValidarMacRespuesta;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ValidarUsuarioSistemaRespuesta;
import com.servicios.app.sistema.Sistema;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.site.build.StructureBuilder;
import com.site.md.Modulo;


@SuppressWarnings("unused")
@ManagedBean(name="loginBean")
@ViewScoped
public class LoginBean extends MBeanAbstracto implements Serializable{

	private static final long serialVersionUID = -2391942267133757899L;

	@ManagedProperty(value="#{sistema}")
	private Sistema sistema;
	
	
	
	@ManagedProperty(value="#{modulosBuilder}")
	private StructureBuilder<List<Modulo> > modulosBuilder;
	
	
	@ManagedProperty(value="#{primeMenuMapBuilder}")
	private StructureBuilder<Map<Integer,Object>> menuMapBuilder;
	
	
	
	
	private Boolean mostrarMensaje;
	
	
	public LoginBean() {
		super();
	}		
	
	
	public Sistema getSistema() {
		return sistema;
	}



	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}	

	
	
	
	public StructureBuilder<List<Modulo>> getModulosBuilder() {
		return modulosBuilder;
	}


	public void setModulosBuilder(StructureBuilder<List<Modulo>> modulosBuilder) {
		this.modulosBuilder = modulosBuilder;
	}


	public StructureBuilder<Map<Integer, Object>> getMenuMapBuilder() {
		return menuMapBuilder;
	}


	public void setMenuMapBuilder(
			StructureBuilder<Map<Integer, Object>> menuMapBuilder) {
		this.menuMapBuilder = menuMapBuilder;
	}


	public Boolean getMostrarMensaje() {
		return mostrarMensaje;
	}


	public void setMostrarMensaje(Boolean mostrarMensaje) {
		this.mostrarMensaje = mostrarMensaje;
	}

	public void onHelp(ActionEvent event){		
		//RequestContext.getCurrentInstance().execute("openHelp('http://localhost:8080/webapp/ayuda/GuiaRapidaDeUsuario.htm')");
	}

	public void onLogin(ActionEvent event) throws ExcepcionAplicacion {
		
		System.out.println("Ejecutando login de usuario");		
				
		try {
	     
            ValidarUsuarioSistemaPeticion peticion = (ValidarUsuarioSistemaPeticion)getViewContext().get("validarUsuarioSistemaPeticion");
			ValidarUsuarioSistemaRespuesta respuesta = null;			
			
			AplicacionBean aplicacion = (AplicacionBean) getManagedBean("#{aplicacion}", AplicacionBean.class);
						
			respuesta = sistema.validarUsuarioSistema(peticion);
			
			System.out.println("respuesta.getResultado() = " + respuesta.getResultado());
			
			if(respuesta.getResultado() == true){
			
				Integer idRol = respuesta.getUsuarioSistema().getRol().getIdRol();
							
				aplicacion.setUsuario(respuesta.getUsuarioSistema());	
					
				aplicacion.cargar();				
				 
				System.out.println("rol.descripcion ->" + respuesta.getUsuarioSistema().getRol().getDescripcion());
				
				Map<Object,Object> params = new HashMap<Object, Object>();
				params.put("idAplicacion", 1);
				params.put("idRol", idRol);
				
				System.out.println("params ->" + params);
					
				modulosBuilder.buildStructure(params);
				List<Modulo> modulos = modulosBuilder.getStructure();
				
				params.put("modulos", modulos);
				
				menuMapBuilder.buildStructure(params);
				
				aplicacion.getModulos().setModelo(modulos);
				aplicacion.setMapaNavegacion(menuMapBuilder.getStructure());
				    
				System.out.println("modulos -> " + modulos);
				System.out.println("mapaNavegacion -> " + aplicacion.getMapaNavegacion());
				
				aplicacion = null;
				
				setRedireccionar("exito");
				
			}else{
				getCurrentFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario o Password incorrectos.", "Ingrese de nuevo la informacion."));			
				setRedireccionar("fallo");
			}
			
			
			
		
		}catch (ExcepcionServicio e) {	
			
				throw new ExcepcionAplicacion("Error al ejecutar el metod onLogin del bean LoginBean servicio ValidarUsuarioSistemaImpl.-" + e.getMessage(), e.getCause());
		}
		
		
		
		
	}
 

	/*private String obtenerPrimerMac(String macsCliente){
		String [] arregloMacs = macsCliente.split("\\|");
		for(String mac : arregloMacs){
			if(mac!= null && !mac.trim().isEmpty() && mac.length() > 0)
				return mac;
		}
		return null;
	}*/
	
	
	
}
