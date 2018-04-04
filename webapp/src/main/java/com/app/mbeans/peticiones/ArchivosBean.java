package com.app.mbeans.peticiones;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.ProcesoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="archivosBean")
@RequestScoped
public class ArchivosBean extends MBeanAbstracto {

	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;
	
	

	
	public List<Archivo>  getListaArchivos(){
		System.out.println("\n\n*****ArchivosBean.getListaImagenes()\n\n");		
		
		Integer idProcesoPeticion = new Integer(getRequestParameter("idProcesoPeticion").toString());
	
		System.out.println("\n\nidProcesoPeticion -> " + idProcesoPeticion + "\n\n");
		
		ProcesoPeticion procesoPeticion = new ProcesoPeticion();
		procesoPeticion.setId(idProcesoPeticion);
		
		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		peticion.setClase(ProcesoPeticion.class);
		peticion.setEntidad(procesoPeticion);		
			
		List<Archivo> listaArchivos = null;
		
		try {
		
			ConsultarCatalagoRespuesta respuesta = servicioCatalogo.consultarCatalogo(peticion);
			procesoPeticion = (ProcesoPeticion)respuesta.getEntidades().get(0);
			
			listaArchivos = procesoPeticion.getListaArchivo();			
				
		
		} catch (ExcepcionServicioFachada e) {			
			
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("carrusel_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al obtener los archivos. " + e.getMessage(),"" ));			
		}	
		
		return listaArchivos;
	}
	
	public List<Archivo>  getListaUnicoArchivo(){
		System.out.println("\n\n*****ArchivosBean.getListaImagenes()\n\n");		
		
		Integer idProcesoPeticion = new Integer(getRequestParameter("idProcesoPeticion").toString());
	
		System.out.println("\n\nidProcesoPeticion -> " + idProcesoPeticion + "\n\n");
		
		ProcesoPeticion procesoPeticion = new ProcesoPeticion();
		procesoPeticion.setId(idProcesoPeticion);
		
		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		peticion.setClase(ProcesoPeticion.class);
		peticion.setEntidad(procesoPeticion);		
			
		List<Archivo> listaArchivos = null;
		
		try {
		
			ConsultarCatalagoRespuesta respuesta = servicioCatalogo.consultarCatalogo(peticion);
			procesoPeticion = (ProcesoPeticion)respuesta.getEntidades().get(0);
			
			listaArchivos = procesoPeticion.getListaArchivo();			
				
		
		} catch (ExcepcionServicioFachada e) {			
			
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("carrusel_msges", 
					new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al obtener los archivos. " + e.getMessage(),"" ));			
		}	
		
		return listaArchivos;
	}
	
	
	
	
	


	
	
	
	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}
	
	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	
	
	
	
	/*public List<CarruselImage> getListaImagenes() {
		return listaImagenes;
	}*/


	public class CarruselImage{
		
		private String nombre;
		private String descripcion;
		private byte[] bytes;
		private StreamedContent streamedImage;
		
		
		
		public byte[] getBytes() {
			return bytes;
		}
		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public StreamedContent getStreamedImage() {
			return streamedImage;
		}
		public void setStreamedImage(StreamedContent streamedImage) {
			this.streamedImage = streamedImage;
		}
		
		
		
	}
}
