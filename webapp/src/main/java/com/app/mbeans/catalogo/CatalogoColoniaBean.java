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

@ManagedBean(name="catalogoColoniaBean")
@ViewScoped
public class CatalogoColoniaBean extends MBeanAbstracto{//CatalogoAbstractoBean{

	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;	
	
	private EntidadMpal colonia;
	private EntidadMpal coloniaAux;
		
	private Boolean opcionBuscar = false;
	private Boolean habilitarLike = false;
	
	private Boolean accionActualizar;
	private Boolean accionRegistrar;
	private Boolean accionBuscar;
	private Boolean accionLimpiar;
	
	
	ListDataModel<? extends Entidad> listaColonias;
	
	List<TipoEntidadMpal> listaTipoEntidadMpal;
	
	@PostConstruct
	public void inicializar(){
		
		colonia = new EntidadMpal();
		colonia.setTipoEntidadMpal(new TipoEntidadMpal());
		
		try {
			listaColonias = consultarEntidad(colonia);
			cargarSubCatalogos();
			
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
		
		colonia = new EntidadMpal();
		colonia.setTipoEntidadMpal(new TipoEntidadMpal());
		
		setAccionRegistrar(!true);
		setAccionActualizar(!false);
	
	}
	
	public void buscarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{		
		System.out.println("Buscar catalogo.");
		
		
		if(validarBusqueda(colonia)){
			
			this.setOpcionBuscar(true);			
			
			try{				
				
				EntidadMpal _colonia = obtenerClonColonia(colonia);
				
				if (_colonia.getNombre().trim().isEmpty()) 
					_colonia.setNombre(null); 
				else _colonia.setNombre("%".concat(_colonia.getNombre()).concat("%"));
				if (_colonia.getClave().trim().isEmpty()) _colonia.setClave(null);
				else _colonia.setClave( "%".concat(_colonia.getClave()).concat("%") );
				if (_colonia.getDescripcion().trim().isEmpty()) _colonia.setDescripcion(null);
				else _colonia.setClave( "%".concat(_colonia.getDescripcion()).concat("%") );
						
				this.habilitarLike = true;
				listaColonias = consultarEntidad(_colonia);			
						
				if(listaColonias.getRowCount() == 0){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","No se encontro informacion con los parametros indicados"));
				
					setAccionActualizar(!false);
					setAccionRegistrar(!false);
					
					
				}else{
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se encontraron ".concat(String.valueOf( listaColonias.getRowCount() )).concat(" registros con los parametros indicados")));
					
					setAccionActualizar(!false);
					setAccionRegistrar(!true);
					
				}	
				
				
				
			}catch(Exception e){
				e.printStackTrace();	
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_FATAL ,"Info:","Ocurrio un error al consultar el catalogo " + e.getMessage()));
			}
			
		}else{
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe ingresar almenos un campo para realizar la busqueda "));
		}
	}
		
	public void registrarCatalogo(ActionEvent e) throws ExcepcionAplicacion{
		
		
		if(validarCaptura(colonia)){
			CrearCatalogoPeticion peticion = new CrearCatalogoPeticion();
			peticion.setEntidad(colonia);
			
			
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
	
	
	public void seleccionarCatalogo(EntidadMpal colonia)throws ExcepcionAplicacion{
		this.colonia = colonia;
		setAccionRegistrar(!false);
		setAccionActualizar(!true);
	}
	
	public void actualizarCatalogo(ActionEvent e)throws ExcepcionAplicacion{	
		
		ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
		peticion.setEntidad(colonia);
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
		
	public void eliminarCatalogo(EntidadMpal colonia){
		
		coloniaAux = colonia;
	}

	public void eliminarCatalogo(ActionEvent e)throws ExcepcionAplicacion{		
		
		EliminarCatalogoPeticion peticion = new EliminarCatalogoPeticion();
		
		peticion.setEntidad(coloniaAux);
		
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

	
	
	
	
	private EntidadMpal obtenerClonColonia(EntidadMpal colonia){
		EntidadMpal _colonia = new EntidadMpal();
		
		_colonia.setNombre(colonia.getNombre());
		_colonia.setDescripcion(colonia.getDescripcion());
		_colonia.setClave(colonia.getClave());
		_colonia.setTipoEntidadMpal(colonia.getTipoEntidadMpal());	
		
		return _colonia;
		
	}
	
	
	private  void cargarSubCatalogos() throws ExcepcionAplicacion {
	
		listaTipoEntidadMpal = (List<TipoEntidadMpal>)consultarEntidad(new TipoEntidadMpal()).getWrappedData();
	}

	
	
	private boolean validarBusqueda(EntidadMpal colonia){	
		
		if(colonia.getNombre() != null && !colonia.getNombre().isEmpty() ) return true;
		
		if(colonia.getClave() != null && !colonia.getClave().isEmpty() ) return true;
		
		if(colonia.getDescripcion() != null && !colonia.getDescripcion().isEmpty() ) return true;	
		
		if(colonia.getTipoEntidadMpal() != null && colonia.getTipoEntidadMpal().getIdtcgeneral() != null ) return true;	
				
		return false;		
	}
	
	
	private boolean validarCaptura(EntidadMpal entidad) {
		boolean resultado = false;   
	   
	  	if(getColonia().getNombre() == null || getColonia().getNombre().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe de ingresar un Nombre"));			
		}else if(getColonia().getClave() == null || getColonia().getClave().isEmpty() ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe ingresar una Clave"));			
		}else if(getColonia().getDescripcion() == null || getColonia().getDescripcion().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe  igresar un Lider/Jefe"));
		}else if(getColonia().getTipoEntidadMpal() == null || getColonia().getTipoEntidadMpal().getIdtcgeneral() == 0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe seleccionar el tipo"));
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

	public EntidadMpal getColonia() {
		return colonia;
	}

	public void setColonia(EntidadMpal colonia) {
		this.colonia = colonia;
	}

	public Boolean getAccionActualizar() {
		return accionActualizar;
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



	public ListDataModel<? extends Entidad> getListaColonias() {
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
	}



	public Boolean getAccionLimpiar() {
		return accionLimpiar;
	}



	public void setAccionLimpiar(Boolean accionLimpiar) {
		this.accionLimpiar = accionLimpiar;
	}
	
	

}
