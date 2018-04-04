package com.app.mbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.core.app.mbeans.MBeanAbstracto;
import com.site.md.Pagina;

@ManagedBean(name = "opcionesUsuario")
@SessionScoped
public class OpcionesUsuarioBean extends MBeanAbstracto{
	
	private List<Pagina> listaOpcionesUsuario;
	private List<String> rutaNavegacion;
	
	public OpcionesUsuarioBean(){
		crearOpcionesUsuario();		
	}

	public List <Pagina> getListaOpcionesUsuario() {
		return listaOpcionesUsuario;
	}

	public void setListaOpcionesUsuario(List <Pagina> listaOpcionesUsuario) {
		this.listaOpcionesUsuario = listaOpcionesUsuario;
	}
	
	public List<String> getRutaNavegacion() {
		return rutaNavegacion;
	}

	public void setRutaNavegacion(List<String> rutaNavegacion) {
		this.rutaNavegacion = rutaNavegacion;
	}

	public void actualizarContenido(ActionEvent e) throws InterruptedException{
		System.out.println("Invocando Contenido");
		
		this.rutaNavegacion = new ArrayList<String>();
		
		if(e.getComponent().getId().compareTo("indicadorNav-linkInicio") == 0){
			// Nuevo valor para la RUTA DE NAVEGACION
			this.rutaNavegacion.add("Bienvenido");
			// Seteando valor PAGINA del AplicacionBean
			setValueToManagedBean( this.listaOpcionesUsuario.get(0) , "#{aplicacion.pagina}");
			// Seteando valor RUTANavegacion del AplicacionBean
     		setValueToManagedBean( rutaNavegacion, "#{aplicacion.rutaNavegacion}");	
			// Seteando valor Navegacion del AplicacionBean
     		setValueToManagedBean( new NavegacionBean(), "#{aplicacion.navegacion}");			
		}
		else 
    		if ( e.getComponent().getId().compareTo("indicadorNav-linkMensajes") == 0){
    			// Nuevo valor para la RUTA DE NAVEGACION
    			this.rutaNavegacion.add("Bandeja de Mensajes");
    			// Seteando valor PAGINA del AplicacionBean
    			setValueToManagedBean( this.listaOpcionesUsuario.get(1) , "#{aplicacion.pagina}");
    			// Seteando valor RUTANavegacion del AplicacionBean
         		setValueToManagedBean( rutaNavegacion, "#{aplicacion.rutaNavegacion}");	
    			// Seteando valor Navegacion del AplicacionBean
         		setValueToManagedBean( new NavegacionBean(), "#{aplicacion.navegacion}");    						
    		}
    	else 
    		if ( e.getComponent().getId().compareTo("indicadorNav-linkUsuario") == 0){
    			this.rutaNavegacion.add("Opciones de Usuario");
    			// Seteando valor PAGINA del AplicacionBean
    			setValueToManagedBean( this.listaOpcionesUsuario.get(2) , "#{aplicacion.pagina}");
    			// Seteando valor RUTANavegacion del AplicacionBean
         		setValueToManagedBean( rutaNavegacion, "#{aplicacion.rutaNavegacion}");	
    			// Seteando valor Navegacion del AplicacionBean
         		setValueToManagedBean( new NavegacionBean(), "#{aplicacion.navegacion}");    			    			
    		}
    	else 
    		if ( e.getComponent().getId().compareTo("indicadorNav-linkConfiguracion") == 0){
    			this.rutaNavegacion.add("Configuracion");
    			// Seteando valor PAGINA del AplicacionBean
    			setValueToManagedBean( this.listaOpcionesUsuario.get(3) , "#{aplicacion.pagina}");
    			// Seteando valor RUTANavegacion del AplicacionBean
         		setValueToManagedBean( rutaNavegacion, "#{aplicacion.rutaNavegacion}");	
    			// Seteando valor Navegacion del AplicacionBean
         		setValueToManagedBean( new NavegacionBean(), "#{aplicacion.navegacion}");   			
    		}
		//TimeUnit.MINUTES.sleep(10);
		System.out.println("Saliendo de actualizar contenido");
	}
	
	private void crearOpcionesUsuario(){
		this.listaOpcionesUsuario = new ArrayList<Pagina>();
		this.listaOpcionesUsuario.add( new Pagina(0, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/inicio-pantalla.xhtml", "#","#",null) );
		this.listaOpcionesUsuario.add( new Pagina(0, "Mensajes", "Mensajes", "/WEB-INF/web/contenido/pantallas/pagina-construccion-pantalla.xhtml", "#","#",null) );
		this.listaOpcionesUsuario.add( new Pagina(0, "Usuario", "Usuario", "/WEB-INF/web/contenido/pantallas/usuario-info-pantalla.xhtml", "#","#",null) );
		this.listaOpcionesUsuario.add( new Pagina(0, "Configuracion", "Configuracion", "/WEB-INF/web/contenido/pantallas/utilerias-pantalla.xhtml", "#","#",null) ); 
	}
}
