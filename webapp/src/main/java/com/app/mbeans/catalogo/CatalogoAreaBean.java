package com.app.mbeans.catalogo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import com.app.mbeans.CatalogoAbstractoBean;
import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.EntidadMpal;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.TipoEntidadMpal;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="catalogoAreaBean")
@ViewScoped
public class CatalogoAreaBean extends MBeanAbstracto{
	
		
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	private Area area;
	private Area areaAux;
	
	
	private Boolean opcionBuscar = false;
	private Boolean habilitarLike = false;
	
	private Boolean accionActualizar;
	private Boolean accionRegistrar;
	private Boolean accionBuscar;
	private Boolean accionLimpiar;
	
	
	ListDataModel<? extends Entidad> listaAreas;
	
	@PostConstruct
	public void inicializar(){
		
		//colonia = new EntidadMpal();
		//colonia.setTipoEntidadMpal(new TipoEntidadMpal());
		
		area = new Area();
		//area.setEsDefault(0);	
		
		try {
			listaAreas = consultarEntidad(area);
			//cargarSubCatalogos();
			
			setAccionBuscar(!true);
			setAccionLimpiar(!true);
			setAccionRegistrar(!true);
			setAccionActualizar(!false);
		
		} catch (ExcepcionAplicacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_FATAL ,"Info:","Ocurrio un error al consultar el catalogo " + e.getMessage()));
		}		
	}
	
	
	
	public void limpiarCatalogo(ActionEvent e)throws ExcepcionAplicacion{
		
		//colonia = new EntidadMpal();
		//colonia.setTipoEntidadMpal(new TipoEntidadMpal());
		
		area = new Area();
		setAccionRegistrar(!true);
		setAccionActualizar(!false);
	
	}
	
	public void buscarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{		
		System.out.println("Buscar catalogo.");
		
		
		  this.setOpcionBuscar(true);			
			
			try{				
				
				Area _area = obtenerClonArea(area);
				
				_area.setEsDefault(null);
				_area.setStatus(null);
				
				if (_area.getNombre().trim().isEmpty()) 
					_area.setNombre(null); 
				else _area.setNombre("%".concat(_area.getNombre()).concat("%"));
				if(_area.getDireccion().trim().isEmpty()) _area.setDireccion(null);
				else _area.setDireccion( "%".concat(_area.getDireccion()).concat("%") );
				if (_area.getDescripcion().trim().isEmpty()) _area.setDescripcion(null);
				else _area.setDescripcion( "%".concat(_area.getDescripcion()).concat("%") );
				if (_area.getEmail().trim().isEmpty()) _area.setEmail(null);
				else _area.setEmail( "%".concat(_area.getDescripcion()).concat("%") );
				if (_area.getTelefono().trim().isEmpty()) _area.setTelefono(null);
				else _area.setTelefono( "%".concat(_area.getTelefono()).concat("%") );
				
				
				
				
				this.habilitarLike = true;
				listaAreas = consultarEntidad(_area);			
						
				if(listaAreas.getRowCount() == 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","No se encontro informacion con los parametros indicados"));
				
					setAccionActualizar(!false);
					setAccionRegistrar(!false);
					
					
				}else{
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se encontraron ".concat(String.valueOf( listaAreas.getRowCount() )).concat(" registros con los parametros indicados")));
					
					setAccionActualizar(!false);
					setAccionRegistrar(!true);
					
				}			
				
			}catch(Exception e){
				e.printStackTrace();	
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL ,"Info:","Ocurrio un error al consultar el catalogo " + e.getMessage()));
			}
			
	}
		
	public void registrarCatalogo(ActionEvent e) throws ExcepcionAplicacion{
		
		
		if(validarCaptura()){
			CrearCatalogoPeticion peticion = new CrearCatalogoPeticion();
			area.setEsDefault(0);
			peticion.setEntidad(area);
			
			
			try {
				servicioCatalogo.crearCatalogo(peticion);
				
				inicializar();			
			
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se registro la informacion. "));
				
			} catch (ExcepcionServicioFachada ex) {
				// TODO Auto-generated catch block
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Info:","Ocurriono un error al registrar la informacion. " + ex.getMessage()));
				ex.printStackTrace();			
			}			
		}	
	}
	
	
	public void seleccionarCatalogo(Area area)throws ExcepcionAplicacion{
		this.area = area;
		setAccionRegistrar(!false);
		setAccionActualizar(!true);
	}
	
	public void actualizarCatalogo(ActionEvent e)throws ExcepcionAplicacion{	
		
		ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
		peticion.setEntidad(area);
		try {
			
			servicioCatalogo.actualizarCatalogo(peticion );
			inicializar();			
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se actualizo la informacion. "));
		} catch (ExcepcionServicioFachada ex) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Info:","Ocurriono un error al registrar la informacion. " + ex.getMessage()));
			ex.printStackTrace();
		}
	}
		
	public void eliminarCatalogo(Area area){
		
		areaAux = area;
	}

	public void eliminarCatalogo(ActionEvent e)throws ExcepcionAplicacion{		
		
		EliminarCatalogoPeticion peticion = new EliminarCatalogoPeticion();
		
		peticion.setEntidad(areaAux);
		
		try {
			servicioCatalogo.eliminarCatalogo(peticion );
			inicializar();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se elimino la informacion. "));
		} catch (ExcepcionServicioFachada ex) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Info:","Ocurriono un error al registrar la informacion. " + ex.getMessage()));
			ex.printStackTrace();
		}
		
		//limpiarCatalogo(null);
	}
	
	private ListDataModel<? extends Entidad> consultarEntidad(Entidad entidad) throws ExcepcionAplicacion {
		System.out.println("Entrando a consultarEntidad");	
			
		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		
		peticion.setEntidad(entidad);	
		peticion.setClase(entidad.getClass());
		peticion.setEnablesLike(true);
			
		System.out.println("informacion a buscar -> " +  entidad );
		
		ConsultarCatalagoRespuesta respuesta = null;
		
		try {					
			respuesta = getServicioCatalogo().consultarCatalogo(peticion);
				
				//System.out.println("despues de consultarCatalogo peticion: "+respuesta);
				
				//if(respuesta != null)
					//System.out.println("despues de consultarCatalogo peticion: "+ respuesta.getListaEntidades().getRowCount());
			
		} catch (ExcepcionServicioFachada e) {
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo consultarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			
		}
			
		return respuesta.getListaEntidades();			
	}

	
	
	
	
	private Area obtenerClonArea(Area area){
		Area _area = new Area();
		
		_area.setDescripcion(area.getDescripcion());
		_area.setDireccion(area.getDireccion());
		_area.setEmail(area.getEmail());
		_area.setEsDefault(area.getEsDefault());
		_area.setNombre(area.getNombre());
		_area.setStatus(area.getStatus());
		_area.setTelefono(area.getTelefono());
		
		return _area;
		
	}
	
	
	private  void cargarSubCatalogos() throws ExcepcionAplicacion {
	
		//listaTipoEntidadMpal = (List<TipoEntidadMpal>)consultarEntidad(new TipoEntidadMpal()).getWrappedData();
	}

	
	
	private boolean validarBusqueda(EntidadMpal colonia){	
		
		if(colonia.getNombre() != null && !colonia.getNombre().isEmpty() ) return true;
		
		if(colonia.getClave() != null && !colonia.getClave().isEmpty() ) return true;
		
		if(colonia.getDescripcion() != null && !colonia.getDescripcion().isEmpty() ) return true;	
		
		if(colonia.getTipoEntidadMpal() != null && colonia.getTipoEntidadMpal().getIdtcgeneral() != null ) return true;	
				
		return false;		
	}
	
	
	private boolean validarCaptura() {
		boolean resultado = false;   
	   
	  	if(getArea().getNombre() == null || getArea().getNombre().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe de ingresar un Nombre"));			
		}else if(getArea().getDescripcion() == null || getArea().getDescripcion().isEmpty() ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe ingresar una Clave"));			
		}else if(getArea().getDireccion() == null || getArea().getDireccion().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe  igresar un Lider/Jefe"));
		}else if(getArea().getEmail() == null || getArea().getEmail().isEmpty() ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe seleccionar el tipo"));					
		}else if(getArea().getTelefono() == null || getArea().getTelefono().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe  igresar un Lider/Jefe"));
		}else{
			resultado = true;			
		}
	   
		
		System.out.println("Valor de validar Campos: "+resultado);
		opcionBuscar = false;
		return resultado;		
	}

	

	public Boolean getOpcionBuscar() {
		return opcionBuscar;
	}

	public void setOpcionBuscar(Boolean opcionBuscar) {
		this.opcionBuscar = opcionBuscar;
	}

	public Boolean getHabilitarLike() {
		return habilitarLike;
	}

	public void setHabilitarLike(Boolean habilitarLike) {
		this.habilitarLike = habilitarLike;
	}

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	/*public EntidadMpal getColonia() {
		return colonia;
	}

	public void setColonia(EntidadMpal colonia) {
		this.colonia = colonia;
	}*/
	
	

	public Boolean getAccionActualizar() {
		return accionActualizar;
	}



	public Area getArea() {
		return area;
	}



	public void setArea(Area area) {
		this.area = area;
	}



	public void setAccionActualizar(Boolean accionActualizar) {
		this.accionActualizar = accionActualizar;
	}



	public Boolean getAccionRegistrar() {
		return accionRegistrar;
	}



	public void setAccionRegistrar(Boolean accionRegistrar) {
		this.accionRegistrar = accionRegistrar;
	}



	public Boolean getAccionBuscar() {
		return accionBuscar;
	}



	public void setAccionBuscar(Boolean accionBuscar) {
		this.accionBuscar = accionBuscar;
	}

	
	


	/*public ListDataModel<? extends Entidad> getListaColonias() {
		return listaColonias;
	}



	public void setListaColonias(ListDataModel<? extends Entidad> listaColonias) {
		this.listaColonias = listaColonias;
	}



	public List<TipoEntidadMpal> getListaTipoEntidadMpal() {
		return listaTipoEntidadMpal;
	}



	public void setListaTipoEntidadMpal(List<TipoEntidadMpal> listaTipoEntidadMpal) {
		this.listaTipoEntidadMpal = listaTipoEntidadMpal;
	}*/



	public ListDataModel<? extends Entidad> getListaAreas() {
		return listaAreas;
	}



	public void setListaAreas(ListDataModel<? extends Entidad> listaAreas) {
		this.listaAreas = listaAreas;
	}



	public Boolean getAccionLimpiar() {
		return accionLimpiar;
	}



	public void setAccionLimpiar(Boolean accionLimpiar) {
		this.accionLimpiar = accionLimpiar;
	}
	

}
