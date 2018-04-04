package com.app.mbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

//import com.app.modelo.Pagina;

import com.core.app.ExcepcionAplicacion;
import com.core.app.mbeans.MBeanAbstracto;
import com.core.app.modelo.Entidad;
import com.core.app.modelo.ICatalogo;
import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresRespuesta;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosPeticion;
import com.objetos.transf.datos.app.comun.ValidarCaracteresPermitidosRespuesta;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.app.comun.ServicioComun;
import com.servicios.excepcion.ExcepcionServicioFachada;
import com.site.md.Pagina;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;



@ManagedBean(name = "catalogoBean")
@ViewScoped
public class CatalogoBean extends MBeanAbstracto implements Serializable {
	
	private static final long serialVersionUID = 661493510229088870L;
	
	@ManagedProperty(value = "#{listaExlusionesCatalogos}")
	private List<String> listaExclusiones;
	
	@ManagedProperty(value = "#{fcatalogo}")
	private ServicioCatalogo fCatalogo;			
	
	@ManagedProperty(value = "#{comun}")
	private ServicioComun fachadaComun;	
	
	private ICatalogo  catalogo;		
	
	private String clazz;
	
	private ListDataModel  modelo;	
	
	private boolean accionBuscar = !true;
	
	private boolean accionLimpiar = !true;

	private boolean accionRegistrar = !true;
	
	private boolean accionActualizar = !false;
	
	private String lblBtnRegistrar = "Registrar";
	
	private Integer idCatalogo;
	
	private String nombreCatalogo;
	
	public List<String> getListaExclusiones() {
		return listaExclusiones;
	}

	public void setListaExclusiones(List<String> listaExclusiones) {
		this.listaExclusiones = listaExclusiones;
	}

	public ServicioComun getFachadaComun() {
		return fachadaComun;
	}

	public void setFachadaComun(ServicioComun fachadaComun) {
		this.fachadaComun = fachadaComun;
	}

	public String getNombreCatalogo() {
		return nombreCatalogo;
	}

	public void setNombreCatalogo(String nombreCatalogo) {
		this.nombreCatalogo = nombreCatalogo;
	}

	public Integer getIdCatalogo() {
		return idCatalogo;
	}

	public void setIdCatalogo(Integer idCatalogo) {
		this.idCatalogo = idCatalogo;
	}
	
	public String getLblBtnRegistrar() {
		return lblBtnRegistrar;
	}

	public void setLblBtnRegistrar(String lblBtnRegistrar) {
		this.lblBtnRegistrar = lblBtnRegistrar;
	}

	public boolean isAccionActualizar() {
		return accionActualizar;
	}

	public void setAccionActualizar(boolean accionActualizar) {
		this.accionActualizar = accionActualizar;
	}

	public boolean isAccionBuscar() {
		return accionBuscar;
	}

	public void setAccionBuscar(boolean accionBuscar) {
		this.accionBuscar = accionBuscar;
	}

	public boolean isAccionRegistrar() {
		return accionRegistrar;
	}

	public void setAccionRegistrar(boolean accionRegistrar) {
		this.accionRegistrar = accionRegistrar;
	}
	
	public boolean isAccionLimpiar() {
		return accionLimpiar;
	}

	public void setAccionLimpiar(boolean accionLimpiar) {
		this.accionLimpiar = accionLimpiar;
	}

	public ListDataModel getModelo() {
		return modelo;
	}

	public void setModelo(ListDataModel modelo) {
		this.modelo = modelo;
	}

	public ServicioCatalogo getfCatalogo() {
		return fCatalogo;
	}

	public void setfCatalogo(ServicioCatalogo fCatalogo) {
		this.fCatalogo = fCatalogo;
	}	
	
	public ICatalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(ICatalogo catalogo) {
		this.catalogo = catalogo;
	}

	public void registrarEntidad()throws ExcepcionAplicacion{
			
		CrearCatalogoPeticion peticion = new CrearCatalogoPeticion();
		peticion.setEntidad(getCatalogo().getEntidad());

		try {
			if(!validarCaracteresDescripcion(getCatalogo().getDescripcion())){
				if(validarCampos() && validarExistente(getCatalogo().getClave(), getCatalogo().getDescripcion())){
					getfCatalogo().crearCatalogo(peticion);
					limpiarCatalogo(null);
					consultarEntidad(); 
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","El registro fue almacenado correctamente."));
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","La informacion ingresada en la descripcion no puede contener consecutivamente el caracter '\"' o '.'."));
			}
		} catch (ExcepcionServicioFachada e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ocurrio un problema al guardar la informacion, verifique e intente de nuevo."));
			//e.printStackTrace();
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo registrarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
		}
	}
	
	public void eliminarEntidad()throws ExcepcionAplicacion{
				
		// System.out.println("El id de la entidad a eliminar es -> " + getIdCatalogo());
		
		//this.idCatalogo = Integer.valueOf( getRequestContext().get("idCatalogo") );
				
		this.obtenerEntidad(getIdCatalogo());
		
		EliminarCatalogoPeticion peticion = new EliminarCatalogoPeticion();
		
		peticion.setEntidad(getCatalogo().getEntidad());

		try {
			
			getfCatalogo().eliminarCatalogo(peticion);
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
		peticion.setClase(getCatalogo().getClazz());
		peticion.setIdEntidad(getIdCatalogo());
		
		try {
		
			 ObtenerCatalogoRespuesta respuesta = getfCatalogo().obtenerCatalogo(peticion);
		
			 catalogo = (ICatalogo) respuesta.getEntidad();			 
			
			 // System.out.println("Entidad obtenida -> " + catalogo.getEntidad());
			 
		} catch (ExcepcionServicioFachada e) {
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo obtenerEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			//e.printStackTrace();
		}
		
	}	
	
	public void actualizarEntidad()throws ExcepcionAplicacion{
		
		if(getCatalogo()!= null){
			ActualizarCatalogoPeticion peticion = new ActualizarCatalogoPeticion();
			peticion.setEntidad(getCatalogo().getEntidad());
			try {
				if(!validarCaracteresDescripcion(getCatalogo().getDescripcion())){
					if(validarCampos() && validarExistenteActualizar(getCatalogo().getDescripcion())){
						getfCatalogo().actualizarCatalogo(peticion);
						limpiarCatalogo(null);
						consultarEntidad(); 
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","El registro fue actualizado correctamente."));
						setAccionActualizar(!false);
						setAccionRegistrar(!true);
						setAccionBuscar(!true);
					}
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","La informacion ingresada en la descripcion no puede contener consecutivamente el caracter '\"' o '.'."));
				}
			} catch (ExcepcionServicioFachada e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ocurrio un problema al actualizar el registro, verifique e intente nuevamente."));
				//e.printStackTrace();
				throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo actualizarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			}			
		}
		
	}	
	
	public void consultarEntidad()throws ExcepcionAplicacion{

		// System.out.println("Se ingreso a consultarEntidad()");
		String respaldoDescripcion = "";
		ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		
		
		
		if(getCatalogo().getDescripcion()!= null){
			
			if(getCatalogo().getDescripcion().equals("") || getCatalogo().getDescripcion().equals(" ")){
				getCatalogo().setDescripcion(null);
				 
			}else{
				respaldoDescripcion = getCatalogo().getDescripcion();
				getCatalogo().setDescripcion("%"+getCatalogo().getDescripcion()+"%");
			}
			
		}
		
		if(getCatalogo().getClave() != null){
			if(getCatalogo().getClave().trim().equals("")){
				getCatalogo().setClave(null);
			}
		}
		
		peticion.setEntidad(getCatalogo().getEntidad());
		peticion.setClase(getCatalogo().getClass());
		peticion.setEnablesLike(true);
		
		
		// System.out.println("informacion a buscar -> " +  getCatalogo().getEntidad() );
		
		try {		
			
			ConsultarCatalagoRespuesta respuesta = getfCatalogo().consultarCatalogo(peticion);
			getCatalogo().setDescripcion(respaldoDescripcion);	
			// System.out.println("respuesta -> " + respuesta);
			
			if(respuesta != null){
				setModelo( respuesta.getListaEntidades() );
			}else{
				
				setModelo(null);
			}
			
			
			
		} catch (ExcepcionServicioFachada e) {
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo consultarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			//e.printStackTrace();
		}

	}
	
	@PostConstruct
	public void cargarPagina(){
		
		 // System.out.println("SE INGRESO AL CONSTRUCTOR DE CatBeanAbstracto");
		 
		 Pagina pagina = (Pagina)getManagedBean("#{aplicacion.pagina}", Pagina.class);
		 
		 this.clazz = pagina.getClaseCatalogo();	 
		 
		 this.nombreCatalogo = pagina.getNombre().toLowerCase();
		 
		 try { 
			
			 catalogo = (ICatalogo)Class.forName(clazz).newInstance();
		
			 consultarEntidad();
			 
			 //llenarTabla();
		 
		 
		 } catch (Exception e) {
				///throw new ExcepcionAplicacion("Ocurrio un problema inicializar la pantalla bean CatalogoBean.-" + e.getMessage(), e.getCause());
				e.printStackTrace();
		 } catch (ExcepcionAplicacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void limpiarCatalogo(ActionEvent evento){
		
		//catalogo.setId(null);
		//catalogo.setClave(null);
		//catalogo.setDescripcion(null);
		
		try {
			catalogo = (ICatalogo)Class.forName(clazz).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);
			
	}  
	
	public void seleccionarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
		
		// System.out.println("El id de la entidad a actualizar es -> " + getRequestContext().get("idCatalogo"));
		
		this.idCatalogo = Integer.valueOf( getRequestContext().get("idCatalogo") );
		
		
		this.obtenerEntidad(this.idCatalogo);
		
		setAccionActualizar(!true);
		setAccionRegistrar(!false);
		setAccionBuscar(!false);
		
	}
	
	public void actualizarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
		
		actualizarEntidad();
		
		/*
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);
		*/
		
	}
	
	public void registrarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
		
		
		registrarEntidad();
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);		
	}
	
	public void eliminarCatalogo (ActionEvent evento)throws ExcepcionAplicacion{
		
		eliminarEntidad();
		
		limpiarCatalogo(null);
		
		consultarEntidad();
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);		

	}
	
	
	
	public void buscarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
	
		try{	
			consultarEntidad();
		}catch(NullPointerException e){
			e.printStackTrace();
			
		}
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);		
	}
	
	private boolean validarCampos(){
		boolean resultado = false;
		if(getCatalogo().getClave() != null && !getCatalogo().getClave().equals("") && getCatalogo().getClave().length() <= 10){ 
			if(getCatalogo().getDescripcion() != null  && !getCatalogo().getDescripcion().equals("")){
				resultado = true;
			}else{
				resultado = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo descripcion es obligatorio."));
			}
		}else{
			resultado = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo clave es obligatorio y debe tener una longitud de 1 a 10 caracteres."));
		}
		return resultado;
	}
	
	private boolean validarExistente(String clave, String descripcion)throws ExcepcionAplicacion{
		boolean resultado = true;
		boolean claveIgual = false;
		ValidarCadenasSimilaresPeticion validarCadenasSimilaresPeticion = null;
		ValidarCadenasSimilaresRespuesta validarCadenasSimilaresRespuesta = null;
		
		try{
			
			for(Object entidad : getModelo()){
				if( ((ICatalogo)entidad).getClave().equals(clave) ){
					claveIgual = true;
					resultado = false;
					break;
				}
			}
			
			if(!claveIgual){
				if(!listaExclusiones.contains(this.clazz.substring(this.clazz.lastIndexOf(".")+1, this.clazz.length()))){
					validarCadenasSimilaresPeticion = new ValidarCadenasSimilaresPeticion();
					validarCadenasSimilaresPeticion.setCadenaNueva(descripcion); 
					validarCadenasSimilaresPeticion.setCatalogo(getModelo());
					validarCadenasSimilaresPeticion.setUmbral(5);
					validarCadenasSimilaresRespuesta = fachadaComun.validarCadenasSimilares(validarCadenasSimilaresPeticion);
					if( validarCadenasSimilaresRespuesta.getExisteCadenaSimilar() ){
						resultado = false;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ya existe un registro con una descripcion muy similar a la ingresada."));
					}
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ya existe un registro con la clave ingresada."));
			}
			
		}catch(ExcepcionServicio e){
			e.printStackTrace();
		}finally{
			validarCadenasSimilaresPeticion = null;
			validarCadenasSimilaresRespuesta = null;
		}
		return resultado;
	}
	
	private boolean validarExistenteActualizar(String descripcion)throws ExcepcionAplicacion{
		boolean resultado = true;
		ValidarCadenasSimilaresPeticion validarCadenasSimilaresPeticion = null;
		ValidarCadenasSimilaresRespuesta validarCadenasSimilaresRespuesta = null;
		
		try{
			
			if(!listaExclusiones.contains(this.clazz.substring(this.clazz.lastIndexOf(".")+1, this.clazz.length()))){
				validarCadenasSimilaresPeticion = new ValidarCadenasSimilaresPeticion();
				validarCadenasSimilaresPeticion.setCadenaNueva(descripcion); 
				validarCadenasSimilaresPeticion.setCatalogo(getModelo());
				validarCadenasSimilaresPeticion.setUmbral(5);
				validarCadenasSimilaresRespuesta = fachadaComun.validarCadenasSimilares(validarCadenasSimilaresPeticion);
				if( validarCadenasSimilaresRespuesta.getExisteCadenaSimilar() ){
					resultado = false;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ya existe un registro con una descripcion muy similar a la ingresada."));
				}
			}
			
			
		}catch(ExcepcionServicio e){
			e.printStackTrace();
		}finally{
			validarCadenasSimilaresPeticion = null;
			validarCadenasSimilaresRespuesta = null;
		}
		return resultado;
	}
	
	private boolean validarCaracteresDescripcion(String cadenaValidar)throws ExcepcionAplicacion{
		ValidarCaracteresPermitidosPeticion validarCaracteresPermitidosPeticion = null;
		ValidarCaracteresPermitidosRespuesta validarCaracteresPermitidosRespuesta = null;
		String [] caracteresValidar = {"\"", "."};
		boolean resultado = false;
		
		try {
			
			validarCaracteresPermitidosPeticion = new ValidarCaracteresPermitidosPeticion();
			validarCaracteresPermitidosPeticion.setCadenaValidar(cadenaValidar);
			validarCaracteresPermitidosPeticion.setCaracteres(caracteresValidar);
			validarCaracteresPermitidosRespuesta = fachadaComun.validarCaracteresPermitidos(validarCaracteresPermitidosPeticion);
			resultado = validarCaracteresPermitidosRespuesta.getResultadoValidacion();
			
		} catch (ExcepcionServicio e) {
			throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo validarCaracteresDescripcionDel bean CatalogoBean.-" + e.getMessage(), e.getCause());
		} finally{
			validarCaracteresPermitidosPeticion = null;
			validarCaracteresPermitidosRespuesta = null;
			cadenaValidar = null;
			caracteresValidar = null;
		}
		
		return resultado;
	}

}

