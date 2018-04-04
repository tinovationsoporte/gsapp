package com.app.mbeans.peticiones;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.app.mbeans.AplicacionBean;
import com.core.app.mbeans.MBeanAbstracto;
import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.ProcesoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;


@ManagedBean(name ="imagenBean")
@ViewScoped
public class ImagenBean extends MBeanAbstracto {

	private String URL_REPORTES;
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;

	
	private String urlImagen;
	
	@PostConstruct
	private void init(){		
		
		
		AplicacionBean app = (AplicacionBean) getManagedBean("#{aplicacion}", AplicacionBean.class);
		
		URL_REPORTES =  app.getUrlImagenes();
		
		//setUrlImagen(URL_REPORTES + "http://localhost:8080/webapp-1.0-SNAPSHOT/ImagenesServlet?idArchivo="+ getRequestParameter("idArchivo").toString());
		setUrlImagen(URL_REPORTES + "idArchivo="+ getRequestParameter("idArchivo").toString());
		
	}
	
	
	public String getUrlImagen() {
		return urlImagen;
	}


	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}


	public StreamedContent getStreamedImage(){		
		
		if (getCurrentFacesContext().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			System.out.println("\n\n+++ImagenBean RENDER_RESPONSE phase\n\n");

			return new DefaultStreamedContent();
		} else {	
		
			System.out.println("\n\n+++ImagenBean.getStreamedImage()\n\n");
			Integer idArchivo = Integer.parseInt(getRequestParameter("idArchivo").toString());
			System.out.println("\n\n+++idArchivo -> " + idArchivo +"\n\n");

			
			
			ObtenerCatalogoPeticion peticion = new ObtenerCatalogoPeticion();
			peticion.setClase(Archivo.class);
			peticion.setIdEntidad(idArchivo);
			
			Archivo archivo = null;
			try {
			
				archivo = (Archivo) servicioCatalogo.obtenerCatalogo(peticion).getEntidad();
			
				System.out.println("\n\narchivo.bytes -> " + archivo.getBlob() +"\n\n");
			} catch (Exception e) {
				e.printStackTrace(); 
			
				FacesContext.getCurrentInstance().addMessage("imagen_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al obtener el archivo. " + e.getMessage(),"" ));
			}		
			
			InputStream is = new ByteArrayInputStream(archivo.getBlob());
			
			return new DefaultStreamedContent(is,"image/jpeg");
		}		
		
	}

	
	public List<StreamedContent> getStreamedImages(){
		
		
		//if (getCurrentFacesContext().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			//return new ArrayList<StreamedContent>();
		//} else  {	
			
			List<StreamedContent> imagesList = new  ArrayList<StreamedContent>();
			
			try {
			
				System.out.println("\n\n*****CarruselBean.getListaImagenes()\n\n");		
				
				Integer idProcesoPeticion = new Integer(getRequestParameter("idProcesoPeticion").toString());
			
				System.out.println("\n\nidProcesoPeticion -> " + idProcesoPeticion + "\n\n");
				
				ProcesoPeticion procesoPeticion = new ProcesoPeticion();
				procesoPeticion.setId(idProcesoPeticion);			
				
				System.out.println("\n\n*****CarruselBean.ConsultandoPeticion\n\n");		

				ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
				peticion.setClase(ProcesoPeticion.class);
				peticion.setEntidad(procesoPeticion);			
				
				ConsultarCatalagoRespuesta respuesta = servicioCatalogo.consultarCatalogo(peticion);
				procesoPeticion = (ProcesoPeticion)respuesta.getEntidades().get(0);
				
				System.out.println("\n\n*****CarruselBean.ConsultandoArchivos\n\n");					
				List<Archivo> listaArchivos = procesoPeticion.getListaArchivo();
				
				System.out.println("\n\n*****listaArchivos.size -> " +listaArchivos.size() + "\n\n");	

				
				if(listaArchivos == null || listaArchivos.isEmpty()){
					
					FacesContext.getCurrentInstance().addMessage("carrusel_msges", 
							new FacesMessage(FacesMessage.SEVERITY_WARN,"No hay archivos para la peticion" ,"" ));				
					
					return imagesList;
				}
				
				
				System.out.println("\n\n*****CarruselBean.ASignandoArchivos\n\n");	
				
				
				
				for(Archivo archivo : listaArchivos){
					
					System.out.println("\n\n*****Procesando  Archivo " + archivo.getNombre() + " | " 
						+ archivo.getBlob() + 	"\n\n");
					
					InputStream is = new ByteArrayInputStream(archivo.getBlob());
					
					imagesList.add(new DefaultStreamedContent(is,"image/jpeg"));
				}
				
				
				
			} catch (ExcepcionServicioFachada e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
				FacesContext.getCurrentInstance().addMessage("carrusel_msges", 
						new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio  un error al obtener los archivos. " + e.getMessage(),"" ));
			}
			
			
			return imagesList;
		//}
	}
	
	
	
	
	
	
	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}
	
	
	
}
