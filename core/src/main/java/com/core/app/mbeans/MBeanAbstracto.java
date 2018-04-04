package com.core.app.mbeans;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.core.app.ExcepcionAplicacion;

public abstract class MBeanAbstracto {

	
	
	private String redireccionar;	
	
	
	public String redireccionar() {
		return redireccionar;
	}


	public void setRedireccionar(String redireccionar) {
		this.redireccionar = redireccionar;
	}

	
	
	public void abrirSubPantallaNivel1(ActionEvent event){
		setValueToManagedBean((String) getRequestParameter("urlPagina"), "#{aplicacion.paginaNivel1.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel1.nombre}");
		
	}
	
	public void abrirSubPantallaNivel2(ActionEvent event){
		setValueToManagedBean((String) getRequestParameter("urlPagina"), "#{aplicacion.paginaNivel2.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel2.nombre}");
		
	}
	
	
	public void abrirSubPantallaNivel3(ActionEvent event){
		setValueToManagedBean((String) getRequestParameter("urlPagina"), "#{aplicacion.paginaNivel3.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel3.nombre}");
		
	}
	
	
	public void abrirMensajeSubPantallaNivel1(ActionEvent event){
		setValueToManagedBean("/WEB-INF/web/contenido/plantillas/mensaje-plantilla.xhtml", "#{aplicacion.paginaNivel1.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel1.nombre}");
		
	}
	
	public void abrirMensajeSubPantallaNivel2(ActionEvent event){
		setValueToManagedBean("/WEB-INF/web/contenido/plantillas/mensaje-plantilla.xhtml", "#{aplicacion.paginaNivel2.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel2.nombre}");
		
	}
	
	public void abrirMensajeSubPantallaNivel3(ActionEvent event){
		setValueToManagedBean("/WEB-INF/web/contenido/plantillas/mensaje-plantilla.xhtml", "#{aplicacion.paginaNivel3.urlPantalla}");		
		setValueToManagedBean((String) getRequestParameter("nombrePagina"), "#{aplicacion.paginaNivel3.nombre}");
		
	}
	
	
	public void abrirSubPantallaReporte(ActionEvent event){				
		setValueToManagedBean((String) getRequestParameter("direccionReporte"), "#{aplicacion.direccionReporte}");
	}
	
	public String getSessionId(){
		HttpSession session =  (HttpSession) getExternalContext().getSession(true);
		String result = session.getId(); 
		
		return result;		
	}
	

	public String getMessageFromBundle(String bundleName, String key){		
		return getApplication().getResourceBundle(getCurrentFacesContext(), bundleName).getString(key);
	}
	
	protected void setValueToManagedBean(Object value, String elExpresion){   
        
        ExpressionFactory ef = getApplication().getExpressionFactory( );         
        ValueExpression ve = ef.createValueExpression(getELContext(), elExpresion , value.getClass());        
        ve.setValue(getELContext(), value);
                
        
    }
    

    protected Object getManagedBean(String elExpresion, Class class_){
    
        return getApplication().evaluateExpressionGet(getCurrentFacesContext(), elExpresion, class_);
    }
    
    
    protected void redirectToPage(String urlPage) throws IOException{    
        getExternalContext().redirect(urlPage);    
    }


    protected Application getApplication(){
        return getCurrentFacesContext().getApplication();
    }
    
    protected ExternalContext getExternalContext(){    
        return getCurrentFacesContext().getExternalContext();
    }
    
    
    protected ELContext getELContext(){       
        return getCurrentFacesContext().getELContext();
    }
    
    protected FacesContext getCurrentFacesContext(){   
        return FacesContext.getCurrentInstance();
    }

    protected Object getRequestParameter(String paramName){
        return getExternalContext().getRequestParameterMap().get(paramName);
    }

    protected Map<String,Object> getViewContext(){
        return getCurrentFacesContext().getViewRoot().getViewMap();
    }
    
    protected Map<String,String> getRequestContext(){
        return getExternalContext().getRequestParameterMap();
    }
    
    protected Map<String,Object> getSessionContext(){
            return getExternalContext().getSessionMap();
    }
    
    protected Map<String,Object> getFlashContext(){
        return getExternalContext().getFlash();
    }
	
    
    protected void createMessage(FacesMessage.Severity severity, String resume, String message){
    	
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, resume, message));

    }
    

    protected void createMessage(String idTarget, FacesMessage.Severity severity, String resume, String message){
    	
		FacesContext.getCurrentInstance().addMessage(idTarget, new FacesMessage(severity, resume, message));

    }

    
    
    
    public void resetBean(ActionEvent event) throws ExcepcionAplicacion{
		
			Field[] campos = this.getClass().getDeclaredFields();
			Class<?> tipoClase = null;
			
			String nombreBean = null;
			
			try{
				for (int pos = 0; pos < campos.length ; pos++) {
					Field campo = campos[pos];	
					if(campo != null){				
						campo.setAccessible(true);
						tipoClase = campo.getType();
						if(campo.getType().isInterface()){
							if(tipoClase.equals(List.class)){
								if(campo.get(this) != null){
									List<?> l = (List<?>)campo.get(this);
									if(campo.get(this).getClass().getCanonicalName().equals("java.util.Arrays.ArrayList")){
										l = null;
									}else{
										purgarLista(l);
									}
								}
							}
							if(tipoClase.equals(Collection.class)){
								Collection<?> c = (Collection<?>)campo.get(this);
								if(c != null){
									c.clear();
									c = null;
								}
							}
							if(tipoClase.equals(Map.class)){
								Map<?,?> m = (Map<?,?>)campo.get(this);
								if(m != null){
									m.clear();
									m = null;
								}
							}
							campo.set(this,null);
							
						}else{
							if(!campo.getType().isPrimitive()){
								campo.set(this,null);
								
							}
							
						}
						
						campo = null;
					}
			    }
				
				Annotation [] annotations = this.getClass().getAnnotations();
				
				
				for (Annotation a : annotations){
					
					if(a instanceof ManagedBean){
						
						ManagedBean mBean =  (ManagedBean) a;
						nombreBean = mBean.name();
						
						Map<String,Object> mapa = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
						
						mapa.remove(nombreBean);
						
						System.out.println("\n\n\n *** Removiendo bean del viewContext-> " + nombreBean);
						
						mapa = null;
						mBean = null;
						nombreBean = null;
						
						//break;		
					
					
					}
						
						
					
					
					
				}
			
			}catch(NullPointerException e){
				throw new ExcepcionAplicacion(e.getMessage(), e);
			}catch(Exception e){
				//System.out.println("Ha ocurrido una excepcion en MBeanAbstracto en el Metodo resetBean - " + e.getMessage());
			
				throw new ExcepcionAplicacion(e.getMessage(), e);
			}
			finally{
				campos = null;
				tipoClase = null;
			}
			
			
		
	}
    
    //Metodo para limpiar lista y convertirla como candidata para el Garbage Collector
  	public void purgarLista(List<?> lista){
  		if(lista != null){
  			lista.clear();
  	  		((ArrayList<?>)lista).trimToSize();
  	  		lista = null;
  		}
  	}
  	
  	
  	

}
