package com.app.mbeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

//import com.app.modelo.Pagina;


import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.site.md.Pagina;

public class NavegacionBean extends MBeanAbstracto {
	
	private TreeNode modelo;
	
	private TreeNode nodoSeleccionado;	
	
	private TreeNode ultimoNodoSeleccionado;	

	public TreeNode getNodoSeleccionado() {
		return nodoSeleccionado;
	}

	public void setNodoSeleccionado(TreeNode nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;
	}

	
	
	public TreeNode getUltimoNodoSeleccionado() {
		return ultimoNodoSeleccionado;
	}

	public void setUltimoNodoSeleccionado(TreeNode ultimoNodoSeleccionado) {
		this.ultimoNodoSeleccionado = ultimoNodoSeleccionado;
	}

	public TreeNode getModelo() {
		
		return modelo;
	}

	public void setModelo(TreeNode modelo) {
		this.modelo = modelo;
	}	
	
	
	 public void cambiarNodo(NodeSelectEvent event) throws ExcepcionAplicacion{
		 
		 DefaultTreeNode nodo = (DefaultTreeNode) event.getTreeNode();
		 
		 
		 if( nodo.isLeaf() ){
			 
			 Pagina pagina = (Pagina) nodo.getData();
			 
			 setValueToManagedBean(pagina, "#{aplicacion.pagina}");
			 
			 setValueToManagedBean(crearIndicadorNavegacion(nodo), "#{aplicacion.rutaNavegacion}");			 
		 
			 
			
			 
			 if(getUltimoNodoSeleccionado()!= null){
				 
				 Map<String,?> mapa = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
				 
				 Pagina ultimaPaginaSeleccionada = (Pagina)getUltimoNodoSeleccionado().getData();
				 
				 if(ultimaPaginaSeleccionada.getNombreBean()!= null){
					 
					 	MBeanAbstracto beanAbstracto = (MBeanAbstracto)getViewContext().get(ultimaPaginaSeleccionada.getNombreBean()); 
						
					 	System.out.println("beanAbstracto -> " + beanAbstracto);
					 	
					 	if(beanAbstracto != null){
					 		beanAbstracto.resetBean(null);
					 		beanAbstracto = null;
					 	}
						//mapa.remove(ultimaPaginaSeleccionada.getNombreBean());
				 }
			 }
			 
			 setUltimoNodoSeleccionado(nodo);
		}
		 
		 
	 }

	 
	 
	 private List<String> crearIndicadorNavegacion(TreeNode nodo){
	        
	        List<String> listaInvertida = new ArrayList<String>();
	        
	        while(nodo.getParent()!= null){
	        
	            listaInvertida.add(((Pagina)nodo.getData()).getNombre());
	            nodo = nodo.getParent();           
	        }
	        
	        List<String> listaIndicadorNavegacion = new ArrayList<String>();
	        
	        
	        int size = listaInvertida.size();
	        
	        while(size > 0){
	        	//StringBuilder path = new StringBuilder(listaInvertida.get(size-1));
	            
	            /*if((size-1) != 0){
	            
	                path.append(" > ");
	            
	            }*/
	            
	            listaIndicadorNavegacion.add(listaInvertida.get(size-1));      
	            
	            --size;
	        }
	        
	        listaInvertida.clear();
	        listaInvertida = null;       
	    
	        return listaIndicadorNavegacion;
	    }
	 
	 
	 
	 
	 public void preparaModelo(){
		 
		 modelo = new DefaultTreeNode(new Pagina(1, "root", "nodo raiz", "#", "#", "#", "") ,  null);
	        
		 DefaultTreeNode nodo1 = new DefaultTreeNode(new Pagina(2, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/pagina-construccion-pantalla.xhtml", "#", "#",""), modelo);		 
		 
	         
	 }
	 
	 
	 
}
