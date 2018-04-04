package com.app.mbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

//import com.app.modelo.Modulo;
//import com.app.modelo.Pagina;


import com.core.app.mbeans.MBeanAbstracto;
import com.site.md.Modulo;
import com.site.md.Pagina;

public class ModulosBean extends MBeanAbstracto{



	List<Modulo> modelo;
	private boolean cargarApplet;
	
	public boolean isCargarApplet() {
		return cargarApplet;
	}

	public void setCargarApplet(boolean cargarApplet) {
		this.cargarApplet = cargarApplet;
	}

	public List<Modulo> getModelo() {

		//preparaModelo();
		return modelo;
	}

	public void setModelo(List<Modulo> modelo) {
		this.modelo = modelo;
	}


	public void cambioModulo(ActionEvent event ){
		System.out.println("Entrando a cambiando modulos");
		String idModulo = (String) getRequestParameter("idModulo");
		cargarApplet = idModulo.equals("1") ? true : false;
		
		//if(idModulo.equals("1")){
			//System.out.println("-----ES CAJAS-----");
		//}
		 
		Map<Integer, Object> mapaNavegacion = (Map<Integer, Object>) getManagedBean("#{aplicacion.mapaNavegacion}", Map.class);
		
        TreeNode raiz = (DefaultTreeNode) mapaNavegacion.get(new Integer(idModulo) );
        
        System.out.println("idPagina -> " + ((Pagina)raiz.getData()).getId());
        System.out.println("nombre -> " + ((Pagina)raiz.getData()).getDescripcion());
        System.out.println("idPadre -> " + ((Pagina)raiz.getData()).getIdPadre());
        System.out.println("idModulo -> " + ((Pagina)raiz.getData()).getIdModulo());
        System.out.println("raiz.children -> " + raiz.getChildren());
        System.out.println("raiz.children.size -> " + raiz.getChildren().size());
        
        Pagina pagina = (Pagina)raiz.getChildren().get(0).getData();
        
        System.out.println("pagina.nombre -> " + pagina.getNombre());
        System.out.println("pagina.url -> " + pagina.getUrlPantalla());
        
        setValueToManagedBean(pagina, "#{aplicacion.pagina}");
        
        setValueToManagedBean(raiz, "#{aplicacion.navegacion.modelo}");
		
        List<String> rutaNavegacion = new ArrayList<String>();
        
        //System.out.println("Nodos hijos -> " + raiz.getChildCount());
        
        rutaNavegacion.add(raiz.getChildren().get(0).toString());
        
        setValueToManagedBean(rutaNavegacion, "#{aplicacion.rutaNavegacion}");
		
		idModulo= null;
		mapaNavegacion = null;
		raiz = null;
		rutaNavegacion = null;
		System.out.println("Saliendo de cambiando modulos");
	}


	/*public void preparaModelo(){
		
		modelo = new ArrayList<Modulo>();
		
		Modulo m1 = new Modulo();
		
		m1.setId(1);
		m1.setNombre("CAJAS");
		m1.setPagina(new Pagina(0, "Modulo de Cajas", "Se ha ingresado al modulo de CAJAS", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m1);
		
		
		Modulo m4 = new Modulo();
		
		m4.setId(4);
		m4.setNombre("FACTIBILIDAD");
		m4.setPagina(new Pagina(0, "Modulo de Factibilidades", "Se ha ingresado al modulo de Factibilidades", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m4);	
		
		Modulo m5 = new Modulo();
		
		m5.setId(5);
		m5.setNombre("PADRON");
		m5.setPagina(new Pagina(0, "Modulo de Padron de Usuarios", "Se ha ingresado al modulo de Padron de Usuarios", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m5);	
		
		
		Modulo m6 = new Modulo();
		
		m6.setId(6);
		m6.setNombre("ATENCION USUARIOS");
		m6.setPagina(new Pagina(0, "Modulo de Padron de Usuarios", "Se ha ingresado al modulo de Padron de Usuarios", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m6);
		
		Modulo m7 = new Modulo();
		
		m7.setId(7);
		m7.setNombre("EJECUCION FISCAL");
		m7.setPagina(new Pagina(0, "Modulo de Padron de Usuarios", "Se ha ingresado al modulo de Padron de Usuarios", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m7);
		
		
		
		Modulo m8 = new Modulo();
		
		m8.setId(8);
		m8.setNombre("USO EFICIENTE AGUA");
		m8.setPagina(new Pagina(0, "Modulo de Padron de Usuarios", "Se ha ingresado al modulo de Padron de Usuarios", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m8);
		
		Modulo m9 = new Modulo();
		
		m9.setId(9);
		m9.setNombre("ALTOS CONSUMIDORES");
		m9.setPagina(new Pagina(0, "Modulo de Altos Consumidores", "Se ha ingresado al modulo de Altos Consumidores", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m9);	
		
		
		Modulo m10 = new Modulo();
		
		m10.setId(10);
		m10.setNombre("FACTURACION");
		m10.setPagina(new Pagina(0, "Modulo de Facturacion", "Se ha ingresado al modulo de Facturacion", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m10);	
		
		
		
		
		
		Modulo m3 = new Modulo();
		
		m3.setId(3);
		m3.setNombre("CONSULTA GRAL");
		m3.setPagina(new Pagina(0, "Modulo de Consulata General", "Se ha ingresado al modulo de Consulta General", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));

		
		modelo.add(m3);
		
		
		
		Modulo m2 = new Modulo();
		
		m2.setId(2);
		m2.setNombre("SIS ADMIN");
		m3.setPagina(new Pagina(0, "Modulo de Administracion del Sistema", "Se ha ingresado al modulo de Administracion del sistema", "WEB-INF/web/contenido/pantallas/modulo-pantalla.xhtml","#", "#",null));
		
		modelo.add(m2);		
		
		
	}*/
	
	
	
	
}
