package com.app.mbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.excepcion.ExcepcionServicioFachada;

public abstract class CatalogoAbstractoBean extends MBeanAbstracto {
	
	private Entidad entidad;	
	
	//@ManagedProperty(value = "#{listaExlusionesCatalogos}")
	//private List<String> listaExclusiones;
	
	
	@ManagedProperty(value = "#{servicioCatalogo}")
	private ServicioCatalogo servicioCatalogo;			
	
	@ManagedProperty(value = "#{comun}")
	private ServicioComun fachadaComun;	
	
	private ListDataModel  modelo;
		
	private boolean accionBuscar = !true;
	
	private boolean accionLimpiar = !true;

	private boolean accionRegistrar = !true;
	
	private boolean accionActualizar = !false;
	
	private Integer idCatalogo;		
	
	public static class Item{
		private Integer idItem;
	    private String descripcion;
		  
		private Item(Integer idItem, String descripcion) {
			// TODO Auto-generated constructor stub
			this.setIdItem(idItem);
			this.setDescripcion(descripcion);
		}

		public Integer getIdItem() {
			return idItem;
		}

		public void setIdItem(Integer idItem) {
			this.idItem = idItem;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	}
	
	public static Item getItemInstance(Integer idItem, String descripcion){
		return new Item(idItem, descripcion);
	}
	
	
	private boolean entidadRegistrada = false;
	
	private boolean entidadActualizada = false;
	
	public boolean isEntidadActualizada() {
		return entidadActualizada;
	}

	public void setEntidadActualizada(boolean entidadActualizada) {
		this.entidadActualizada = entidadActualizada;
	}

	public boolean isEntidadRegistrada() {
		return entidadRegistrada;
	}

	public void setEntidadRegistrada(boolean entidadRegistrada) {
		this.entidadRegistrada = entidadRegistrada;
	}

	public ListDataModel getModelo() {
		return modelo;
	}

	public void setModelo(ListDataModel modelo) {
		this.modelo = modelo;
	}

	public Integer getIdCatalogo() {
		return idCatalogo;
	}

	public void setIdCatalogo(Integer idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	
	
	/*public List<String> getListaExclusiones() {
		return listaExclusiones;
	}

	public void setListaExclusiones(List<String> listaExclusiones) {
		this.listaExclusiones = listaExclusiones;
	}*/

	public ServicioCatalogo getServicioCatalogo() {
		return servicioCatalogo;
	}

	public void setServicioCatalogo(ServicioCatalogo servicioCatalogo) {
		this.servicioCatalogo = servicioCatalogo;
	}

	public ServicioComun getFachadaComun() {
		return fachadaComun;
	}

	public void setFachadaComun(ServicioComun fachadaComun) {
		this.fachadaComun = fachadaComun;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}
	
	public boolean isAccionBuscar() {
		return accionBuscar;
	}

	public void setAccionBuscar(boolean accionBuscar) {
		this.accionBuscar = accionBuscar;
	}

	public boolean isAccionLimpiar() {
		return accionLimpiar;
	}

	public void setAccionLimpiar(boolean accionLimpiar) {
		this.accionLimpiar = accionLimpiar;
	}

	public boolean isAccionRegistrar() {
		return accionRegistrar;
	}

	public void setAccionRegistrar(boolean accionRegistrar) {
		this.accionRegistrar = accionRegistrar;
	}

	public boolean isAccionActualizar() {
		return accionActualizar;
	}

	public void setAccionActualizar(boolean accionActualizar) {
		this.accionActualizar = accionActualizar;
	}

	public void registrarCatalogo(ActionEvent e) throws ExcepcionAplicacion{
		
		registrarEntidad();
		
		if(isEntidadRegistrada()){
			limpiarCatalogo(null);			
			consultarCatalogo(null);			
			setAccionActualizar(!false);
			setAccionRegistrar(!true);
			setAccionBuscar(!true);
		}
				
	}
	
	public void eliminarCatalogo(ActionEvent e)throws ExcepcionAplicacion{
		eliminarEntidad();
		ligarEntidad();
		consultarCatalogo(null);
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);
	}
	
	public void actualizarCatalogo(ActionEvent e)throws ExcepcionAplicacion{
		
		actualizarEntidad();
		
		if (isEntidadActualizada()){
			limpiarCatalogo(null);		
			consultarCatalogo(null);
			
		}
		
	}	
	
	
	public void limpiarCatalogo(ActionEvent e)throws ExcepcionAplicacion{
		
		inicilizarEntidad();
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);
	}
	
	public void seleccionarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
		
		// System.out.println("El id de la entidad a actualizar es -> " + getRequestContext().get("idCatalogo"));
		System.out.println("Entrando a seleccionar Catalogo");
		this.idCatalogo = Integer.valueOf( getRequestContext().get("idCatalogo") );
		
		
		this.obtenerEntidad(this.idCatalogo);
		
		setAccionActualizar(!true);
		setAccionRegistrar(!false);
		setAccionBuscar(!false);
		
	}
	
	public void consultarCatalogo(ActionEvent e) throws ExcepcionAplicacion {		
		
		setModelo(consultarEntidad( getEntidad() ) );		
	}
	
	
	
	
	protected abstract ListDataModel<? extends Entidad> consultarEntidad(Entidad entidad) throws ExcepcionAplicacion;	
	
	protected abstract void ligarEntidad(); //En este metodo se va asignar la clase del catalogo a la entidad	
	protected abstract void inicilizarEntidad();	
	protected abstract void cargarSubCatalogos()throws ExcepcionAplicacion;
	
	protected abstract boolean validarCampos(Entidad entidad) ;	
	protected abstract boolean validarExistente(Entidad entidad);	
	protected abstract boolean validarExistenteActualizar(Entidad entidad);
	
	@PostConstruct
	public void cargar() {
		try{
			System.out.println("Antes de cargar");
			ligarEntidad();
			cargarSubCatalogos();
			consultarCatalogo(null);
			System.out.println("Despues de cargar");
		} catch (ExcepcionAplicacion e) {
			System.out.println("Lanzando excepcion desde catalogoabstractobean");
			e.printStackTrace();
		} 
	}

	public void registrarEntidad()throws ExcepcionAplicacion{
		
		CrearCatalogoPeticion peticion = new CrearCatalogoPeticion();
		peticion.setEntidad(getEntidad());

		try {
			
			if(validarCampos(getEntidad()) && !validarExistente(getEntidad() )  ){
					servicioCatalogo.crearCatalogo(peticion);
					//limpiarCatalogo(null);
					//consultarCatalogo(null); 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","El registro fue almacenado correctamente."));				
					setEntidadRegistrada(true);
			}else{
				setEntidadRegistrada(false);
				
			}
			
		} catch (ExcepcionServicioFachada e) {
			setEntidadRegistrada(false);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ocurrio un problema al guardar la informacion, verifique e intente de nuevo."));
			//e.printStackTrace();
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo registrarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
		}
	}
	
	

	

	public void eliminarEntidad()throws ExcepcionAplicacion{
				
		this.obtenerEntidad(getIdCatalogo());
		
		EliminarCatalogoPeticion peticion = new EliminarCatalogoPeticion();
		
		peticion.setEntidad(getEntidad());

		try {
			
			servicioCatalogo.eliminarCatalogo(peticion);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","El registro fue eliminado correctamente."));	
			
		} catch (ExcepcionServicioFachada e) {
			
			if(e.getClave()!= null &&  e.getClave().equals("101")){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:",e.getMessage()));
				
			}else{
				
				throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo eliminarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());	
			}
		}
		
	}
	
	public void obtenerEntidad(Integer id)throws ExcepcionAplicacion{
	
		ObtenerCatalogoPeticion peticion = new ObtenerCatalogoPeticion();
		peticion.setClase( getEntidad().getClass() );
		peticion.setIdEntidad( getIdCatalogo() );
		
		try {
		
			 ObtenerCatalogoRespuesta respuesta = servicioCatalogo.obtenerCatalogo(peticion);		
			 setEntidad(respuesta.getEntidad());			 
			 
		} catch (ExcepcionServicioFachada e) {
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo obtenerEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			//e.printStackTrace();
		}
		
	}	
	
	public void actualizarEntidad()throws ExcepcionAplicacion{
		
		if(getEntidad()!= null){
			ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
			peticion.setEntidad(getEntidad());
			try {
				
				if(validarCampos(getEntidad()) && !validarExistenteActualizar(getEntidad())){
					servicioCatalogo.actualizarCatalogo(peticion);
					setEntidadActualizada(true);
					//limpiarCatalogo(null);
					//consultarCatalogo(null); 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","El registro fue actualizado correctamente."));
					setAccionActualizar(!false);
					setAccionRegistrar(!true);
					setAccionBuscar(!true);								
				}
				else{
					setEntidadActualizada(false);
					//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","La informacion ingresada en la descripcion no puede contener consecutivamente el caracter '\"' o '.'."));
				}
			} catch (ExcepcionServicioFachada e) {
				setEntidadActualizada(false);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ocurrio un problema al actualizar el registro, verifique e intente nuevamente."));
				//e.printStackTrace();
				throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo actualizarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			}			
		}
		
	}			

}
