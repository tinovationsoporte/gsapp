package com.app.mbeans;

import java.io.FileInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
//import com.app.modelo.Pagina;
import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.UsuarioSistema;
import com.site.md.Pagina;

@ManagedBean(name = "aplicacion")
@SessionScoped
public class AplicacionBean extends MBeanAbstracto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serverUrl;
	private String reportsAppName;
	private String appName;
	
	private UsuarioSistema usuario;

	private String fecha;
	
	private String direccionReporte;

	private NavegacionBean navegacion;

	private ModulosBean modulos;
	
	private OpcionesUsuarioBean opcionesUsuario;
	
	private Pagina pagina;
	
	private Pagina paginaNivel1;
	
	private Pagina paginaNivel2;
	
	private Pagina paginaNivel3;
	
	private Map <Integer, Object> mapaNavegacion;
	
	private List <String> rutaNavegacion;
	
	private String mensaje;
	
	private String urlReportes;	
	
	private String macCliente;
	
	private String macsCliente;
	
	private String version;
	
	private String urlAyuda;
	
	private String paginaAyuda;
	
	private String directorioImagenes;
	
	private String urlImagenes;
	
	
	
	public String getUrlImagenes() {
		return urlImagenes;
	}


	public void setUrlImagenes(String urlImagenes) {
		this.urlImagenes = urlImagenes;
	}


	public String getDirectorioImagenes() {
		return directorioImagenes;
	}


	public void setDirectorioImagenes(String directorioImagenes) {
		this.directorioImagenes = directorioImagenes;
	}


	public String getPaginaAyuda() {
		return paginaAyuda;
	}


	public void setPaginaAyuda(String paginaAyuda) {
		this.paginaAyuda = paginaAyuda;
	}


	public String getUrlAyuda() {
		return urlAyuda;
	}


	public void setUrlAyuda(String urlAyuda) {		
		this.urlAyuda = urlAyuda;
	}


	public String getDireccionReporte() {
		return direccionReporte;
	}


	public void setDireccionReporte(String direccionReporte) {
		this.direccionReporte = direccionReporte;
	}


	public Pagina getPaginaNivel3() {
		return paginaNivel3;
	}


	public void setPaginaNivel3(Pagina paginaNivel3) {
		this.paginaNivel3 = paginaNivel3;
	}


	public String getMacCliente() {
		return macCliente;
	}


	public void setMacCliente(String macCliente) {
		this.macCliente = macCliente;
	}


	public String getUrlReportes() {
		return urlReportes;
	}


	public void setUrlReportes(String urlReportes) {
		this.urlReportes = urlReportes;
	}


	public Pagina getPaginaNivel2() {
		return paginaNivel2;
	}


	public void setPaginaNivel2(Pagina paginaNivel2) {
		this.paginaNivel2 = paginaNivel2;
	}


	public Pagina getPaginaNivel1() {
		return paginaNivel1;
	}


	public void setPaginaNivel1(Pagina paginaNivel1) {
		this.paginaNivel1 = paginaNivel1;
	}


	public Map<Integer, Object> getMapaNavegacion() {
		return mapaNavegacion;
	}


	public void setMapaNavegacion(Map<Integer, Object> mapaNavegacion) {
		this.mapaNavegacion = mapaNavegacion;
	}


	public List<String> getRutaNavegacion() {
		return rutaNavegacion;
	}


	public void setRutaNavegacion(List<String> rutaNavegacion) {
		this.rutaNavegacion = rutaNavegacion;
	}


	public Pagina getPagina() {
		return pagina;
	}


	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}


	public UsuarioSistema getUsuario() {
		return usuario;
	}

	
	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public NavegacionBean getNavegacion() {
		return navegacion;
	}

	public void setNavegacion(NavegacionBean navegacion) {
		this.navegacion = navegacion;
	}

	public ModulosBean getModulos() {
		return modulos;
	}

	public void setModulos(ModulosBean modulos) {
		this.modulos = modulos;
	}

	
	
	
	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public void cargar() { 

		System.out.println("\nCargando sitio ...\n");
		
		//serverUrl = "http://localhost:8080/";		
		//serverUrl = "http://gestionsocial.cloudapp.net/";
		serverUrl = "http://gsalamo.cloudapp.net/";
		//appName ="webapp-1.0-SNAPSHOT/";
		appName ="operacion/";		
		reportsAppName ="webreports/";
		
		
		this.setModulos(new ModulosBean());
		this.setNavegacion(new NavegacionBean());
		this.setOpcionesUsuario( new OpcionesUsuarioBean() );
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'DE' MMMM 'DE' yyyy"); 
		this.setFecha(sdf.format(new Date() ).toUpperCase() );
				
		this.crearRutaNavegacion();
		
		this.setPagina(new Pagina(0, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/inicio-pantalla.xhtml", "#","#",null));
		
		this.setPaginaNivel1(new Pagina(0, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/pagina-construccion-pantalla.xhtml", "#","#",null));
		this.setPaginaNivel2(new Pagina(0, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/pagina-construccion-pantalla.xhtml", "#","#",null));
		this.setPaginaNivel3(new Pagina(0, "Inicio", "Inicio", "/WEB-INF/web/contenido/pantallas/pagina-construccion-pantalla.xhtml", "#","#",null));
		
		//this.setUrlReportes("http://gestionsocial.cloudapp.net/webreports/JasperReportServlet?");
		//this.setUrlReportes("http://localhost:8080/webreports/JasperReportServlet?");
		this.setUrlReportes(serverUrl + reportsAppName + "JasperReportServlet?");
		
		//this.setUrlImagenes("http://gestionsocial.cloudapp.net/operacion/ImagenesServlet?");
		//this.setUrlImagenes("http://localhost:8080/webapp-1.0-SNAPSHOT/ImagenesServlet?");
		this.setUrlImagenes(serverUrl + appName + "ImagenesServlet?");
		
		//http://localhost:8080/webapp-1.0-SNAPSHOT/ayuda/SGS_Guia_Rapida_de_Usuario_V1.htm
		//this.setUrlAyuda("http://localhost:8080/webapp/ayuda/");
		this.setUrlAyuda(serverUrl + appName + "ayuda/");
		
		this.setPaginaAyuda("GuiaRapidaDeUsuario.htm");
		
		this.setMensaje("La aplicacion ha sido inicializada");
		
		this.setDirectorioImagenes("C:\\imagenes\\");
		
		
		/*
		try{
		    //System.out.println(obtenerFechaVersion());
		    //System.out.println(obtenerPropiedadesBuildAnt());
			String ruta = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("WEB-INF/classes/recursos");
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream(ruta.concat("/version.properties")));			
		    this.setVersion(propiedades.getProperty("fechaDeploy").concat("_").concat(propiedades.getProperty("version")));
		}catch(Throwable e){
			e.printStackTrace();
		}
		*/
	}
	
	private void crearRutaNavegacion(){
		
		this.rutaNavegacion = new ArrayList<String>();
		
		this.rutaNavegacion.add("Bienvenido");
	}
	
	public void cerrarSession(ActionEvent e){
		
		getExternalContext().invalidateSession();		
		setRedireccionar("cerrar");
				
	}

	public void mostrarAyuda(ActionEvent e){
		
		RequestContext.getCurrentInstance().execute("openHelp('"+ getUrlAyuda() + getPaginaAyuda() + "')");
	}

	
	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getMacsCliente() {
		return macsCliente;
	}


	public void setMacsCliente(String macsCliente) {
		this.macsCliente = macsCliente;
	}

	public OpcionesUsuarioBean getOpcionesUsuario() {
		return opcionesUsuario;
	}


	public void setOpcionesUsuario(OpcionesUsuarioBean opcionesUsuario) {
		this.opcionesUsuario = opcionesUsuario;
	}	

}
