package com.app.mbeans.peticiones;

import java.io.Serializable;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.app.mbeans.dashboard.DashboardBean;
import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Peticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="consultaDetalleBean")
@ViewScoped
public class ConsultarPeticionDetalleBean extends MBeanAbstracto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String RPT_PETICION = "folioPeticionRpt";
	private String RPT_FORMAT_PDF = "pdf";
	private String urlReportePeticion = null;
	private String URL_REPORTES = "http://localhost:8080/webreports/JasperReportServlet?";
	
	
	//@ManagedProperty(value = "#{datosSolicitanteBean}")
	private DatosPeticionBean datosPeticion;
	private AutorizarPeticionBean autorizarPeticion;
	private PresupuestoBean asignarPresupesto;
	private AsignarOperadorBean asignarOperador;
	private GestionBean gestion;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	@ManagedProperty(value = "#{servicioPeticiones}")
	private ServicioPeticiones servicioPeticiones;	
		
	@ManagedProperty(value = "#{servicioComun}")
	private ServicioComun servicioComun;
	
	private String anclaTabSeleccionado;
		
	private Peticion peticion;
		
	private Integer idPeticion;
	
	private String origen;
	
	@PostConstruct
	public void inicializar(){
		
		System.out.println("consultaDetalleBean.inicializar()");
		
		idPeticion = getRequestParameter("idPeticion") != null ? Integer.valueOf( getRequestParameter("idPeticion").toString() ) : 0;		
		
		origen =  getRequestParameter("origen") != null ? getRequestParameter("origen").toString() : "";
		
		
		System.out.println("idPeticion -> " + idPeticion);
		System.out.println("idOrigen -> " + origen);
		

		
		//if(idPeticion != 0){
			try {
				
				Peticion peticion = consultarPeticion(idPeticion);
				
				datosPeticion = new DatosPeticionBean();
				datosPeticion.setServicioPeticiones(getServicioPeticiones());
				datosPeticion.setServicioComun(getServicioComun());
				datosPeticion.setPeticion(peticion);
				datosPeticion.inicializar();
				
				autorizarPeticion = new AutorizarPeticionBean();
//				autorizarPeticion.setServicioCatalogo(getServicioCatalogo());
				autorizarPeticion.setServicioPeticiones(getServicioPeticiones());
				autorizarPeticion.setPeticion(peticion);
				autorizarPeticion.inicializar();
			
				asignarPresupesto = new PresupuestoBean();
//				asignarPresupesto.setServicioCatalogo(getServicioCatalogo());
				asignarPresupesto.setServicioPeticiones(getServicioPeticiones());				
				asignarPresupesto.setPeticion(peticion);
				asignarPresupesto.inicializar();
				
				
				asignarOperador= new AsignarOperadorBean();
//				asignarOperador.setServicioCatalogo(getServicioCatalogo());
				asignarOperador.setServicioPeticiones(getServicioPeticiones());
				asignarOperador.setPeticion(peticion);
				asignarOperador.inicializar();
				
				gestion = new GestionBean();
				gestion.setServicioCatalogo(getServicioCatalogo());
				gestion.setServicioPeticiones(getServicioPeticiones());				
				gestion.setPeticion(peticion);
				gestion.inicializar();
				
				peticion = null;
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Mensaje del Sistema. Ocurrio un error al consultar la Peticion." + e.getMessage(),"" ));
			}

			
		//}
		
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
		
//		ObtenerCatalogoRespuesta respuesta = null;
//		try{
//			
//		 ObtenerCatalogoPeticion _peticion = new ObtenerCatalogoPeticion();
//		 _peticion.setClase(Peticion.class);
//		 _peticion.setIdEntidad(idPeticion);
//		 respuesta = servicioCatalogo.obtenerCatalogo(_peticion );
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return (Peticion)respuesta.getEntidad();
	}
	
	
	@Override
	public void resetBean(ActionEvent e) throws ExcepcionAplicacion {
		
		if( origen.equals("dashboard") ){
			
			DashboardBean dashboardBean = (DashboardBean) getViewContext().get("dashboardBean");
			System.out.println("\n\n\n\t **** dashboardBean.BuscarFolio ->" + dashboardBean);
			dashboardBean.buscarFolio(e);;			
		}
		
		System.out.println("\n\n\n\t **** resetando consultaDetalleBean");

		
		super.resetBean(e);
	}
	
	
	public GestionBean getGestion() {
		return gestion;
	}

	public void setGestion(GestionBean gestion) {
		this.gestion = gestion;
	}

	public AsignarOperadorBean getAsignarOperador() {
		return asignarOperador;
	}

	public void setAsignarOperador(AsignarOperadorBean asignarOperador) {
		this.asignarOperador = asignarOperador;
	}

	private List consultarCatalogo(Class clazz, Entidad entidad) throws ExcepcionServicioFachada {
		
		ConsultarCatalogoPeticion consultarCatalogoPeticion = new ConsultarCatalogoPeticion();
		consultarCatalogoPeticion.setClase(clazz);
		consultarCatalogoPeticion.setEntidad(entidad);
	
		ConsultarCatalagoRespuesta consultarCatalogoRespuesta = servicioCatalogo.consultarCatalogo(consultarCatalogoPeticion);
		
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta );
		//System.out.println("consultarCatalogoRespuesta ->" + consultarCatalogoRespuesta.getEntidades() );
		return consultarCatalogoRespuesta.getEntidades();			
	}
	
	public AutorizarPeticionBean getAutorizarPeticion() {
		return autorizarPeticion;
	}

	public void setAutorizarPeticion(AutorizarPeticionBean autorizarPeticion) {
		this.autorizarPeticion = autorizarPeticion;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public String getAnclaTabSeleccionado() {
		return anclaTabSeleccionado;
	}

	public void setAnclaTabSeleccionado(String anclaTabSeleccionado) {
		this.anclaTabSeleccionado = anclaTabSeleccionado;
	}

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public DatosPeticionBean getDatosPeticion() {
		return datosPeticion;
	}

	public void setDatosPeticion(DatosPeticionBean datosPeticion) {
		this.datosPeticion = datosPeticion;
	}
	
	public ServicioPeticiones getServicioPeticiones() {
		return servicioPeticiones;
	}

	public void setServicioPeticiones(ServicioPeticiones servicioPeticiones) {
		this.servicioPeticiones = servicioPeticiones;
	}

	public PresupuestoBean getAsignarPresupesto() {
		return asignarPresupesto;
	}

	public void setAsignarPresupesto(PresupuestoBean asignarPresupesto) {
		this.asignarPresupesto = asignarPresupesto;
	}

	public ServicioComun getServicioComun() {
		return servicioComun;
	}

	public void setServicioComun(ServicioComun servicioComun) {
		this.servicioComun = servicioComun;
	}	
	
}
