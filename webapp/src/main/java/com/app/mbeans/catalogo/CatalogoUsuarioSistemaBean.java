package com.app.mbeans.catalogo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import com.app.mbeans.CatalogoAbstractoBean;
import com.core.app.ExcepcionAplicacion;
import com.core.app.modelo.Entidad;
import com.modelo.datos.app.Area;
import com.modelo.datos.app.Rol;

import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresPeticion;
import com.objetos.transf.datos.app.comun.ValidarCadenasSimilaresRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;

@ManagedBean(name="catalogoUsuarioSistemaBean")
@ViewScoped
public class CatalogoUsuarioSistemaBean extends CatalogoAbstractoBean{

	private UsuarioSistema usuarioSistema;
	private String password1;
	private String password2;
	
	private Integer[] mostrarPassword1;
	private Integer[] mostrarPassword2;
	
	public Integer[] getMostrarPassword1() {
		return mostrarPassword1;
	}

	public void setMostrarPassword1(Integer[] mostrarPassword1) {
		this.mostrarPassword1 = mostrarPassword1;
	}

	public Integer[] getMostrarPassword2() {
		return mostrarPassword2;
	}

	public void setMostrarPassword2(Integer[] mostrarPassword2) {
		this.mostrarPassword2 = mostrarPassword2;
	}

	private List<String> listaStatus;
	private List<Rol> listaRoles;
	private List<Area> listaAreas;
	
	private Boolean opcionBuscar = false;
	private Boolean validarPasswords = false;
	private Boolean habilitarLike = false;
	
	public void limpiarCatalogo(ActionEvent e)throws ExcepcionAplicacion{
		this.mostrarPassword1 = null;
		this.mostrarPassword2 = null;
		this.password1 = "";
		this.password2 = "";
		super.limpiarCatalogo(null);
			
	}
	
	public void buscarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{		
		System.out.println("Buscar catalogo.");
		this.setOpcionBuscar(true);
		ListDataModel respuesta = null;
		UsuarioSistema usuarioSistema = null;
		try{	
			
			if(getUsuarioSistema().getPassword() != null ){
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El password no es un campo valido de busqueda"));
			}else{
				
				getUsuarioSistema().getRol().setId(getUsuarioSistema().getRol().getIdRol());
				
				usuarioSistema = obtenerClonUsuario(getUsuarioSistema());
				
				if (usuarioSistema.getNombre().trim().isEmpty()) 
					usuarioSistema.setNombre(null); 
				else usuarioSistema.setNombre( "%".concat(usuarioSistema.getNombre()).concat("%") );
				if (usuarioSistema.getLogin().trim().isEmpty()) usuarioSistema.setLogin(null);
				else usuarioSistema.setLogin( "%".concat(usuarioSistema.getLogin()).concat("%") );
				
				usuarioSistema.setPassword(null);
				
				this.habilitarLike = true;
				respuesta = consultarEntidad(usuarioSistema);
				setModelo( respuesta );
				if(respuesta == null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","No se encontro informacion con los parametros indicados"));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO ,"Info:","Se encontraron ".concat(String.valueOf( respuesta.getRowCount() )).concat(" registros con los parametros indicados(No se considero el PASSWORD)") ));
				}
				
		
				
			}
			
		}catch(NullPointerException e){
			e.printStackTrace();			
		}
		
		setAccionActualizar(!false);
		setAccionRegistrar(!true);
		setAccionBuscar(!true);		
	}
	
	public void seleccionarCatalogo(ActionEvent evento)throws ExcepcionAplicacion{
		super.seleccionarCatalogo(null);
		
		this.usuarioSistema = (UsuarioSistema) getEntidad();
		this.password1 = this.usuarioSistema.getPassword();
		this.password2 = this.usuarioSistema.getPassword();
		System.out.println("Password: "+usuarioSistema.getPassword());
	}
	
		
	@Override
	protected ListDataModel<? extends Entidad> consultarEntidad(Entidad entidad)
			throws ExcepcionAplicacion {
		System.out.println("Entrando a consultarEntidad");
		if(entidad != null){
			ConsultarCatalogoPeticion peticion = new ConsultarCatalogoPeticion();
		
			peticion.setEntidad(entidad);	
			peticion.setClase(entidad.getClass());
			peticion.setEnablesLike(habilitarLike);
			
		 	System.out.println("informacion a buscar -> " +  entidad );
			ConsultarCatalagoRespuesta respuesta = null;
			try {		
			
				
				
				respuesta = getServicioCatalogo().consultarCatalogo(peticion);
				
				System.out.println("despues de consultarCatalogo peticion: "+respuesta);
				
				if(respuesta != null)
					System.out.println("despues de consultarCatalogo peticion: "+ respuesta.getListaEntidades().getRowCount());
			
			} catch (ExcepcionServicioFachada e) {
				throw new ExcepcionAplicacion("Ocurrio un problema al ejecutar el metodo consultarEntidad del bean CatalogoBean.-" + e.getMessage(), e.getCause());
			
			}
			
			return respuesta== null? null:respuesta.getListaEntidades();
		}else 
			return null;	
	}
	
	public UsuarioSistema obtenerClonUsuario(UsuarioSistema usuarioSistema){
		UsuarioSistema iUsuarioSistema = new UsuarioSistema();
		Rol rol = new Rol();
		rol.setIdRol(usuarioSistema.getRol().getIdRol());
		rol.setId(rol.getIdRol());
		iUsuarioSistema.setLogin(usuarioSistema.getLogin());
		iUsuarioSistema.setNombre(usuarioSistema.getNombre());
		iUsuarioSistema.setPassword(usuarioSistema.getPassword());
		iUsuarioSistema.setRol(rol);
		iUsuarioSistema.setStatus(usuarioSistema.getStatus());
		return iUsuarioSistema;
	}

	@Override
	protected void ligarEntidad() {
		// TODO Auto-generated method stub
		inicilizarEntidad();
	}

	@Override
	protected void inicilizarEntidad() {
		// TODO Auto-generated method stub
		setUsuarioSistema(new UsuarioSistema());
		getUsuarioSistema().setRol(new Rol());
		getUsuarioSistema().setArea(new Area());
		setEntidad(getUsuarioSistema());
		
		System.out.println("*******************************  usuarioSis: "+getUsuarioSistema());
	}

	@Override
	protected void cargarSubCatalogos() throws ExcepcionAplicacion {
		//Status status = new Status();
		//status.setReferencia("UsuarioSistema");
		listaStatus = new ArrayList<String>();//(List<Status>) consultarEntidad(status).getWrappedData();
		listaRoles = (List<Rol>) consultarEntidad(new Rol()).getWrappedData();		
		listaAreas=(List<Area>)consultarEntidad(new Area()).getWrappedData();
	}

	public void registrarCatalogo(ActionEvent e) throws ExcepcionAplicacion{
		this.usuarioSistema.setPassword(getPassword1());
		this.usuarioSistema.setFechaCaptura(new Date());
		registrarEntidad();
		
		if(isEntidadRegistrada()){
			limpiarCatalogo(null);			
			consultarCatalogo(null);			
			setAccionActualizar(!false);
			setAccionRegistrar(!true);
			setAccionBuscar(!true);
		}
				
	}
	
	public void actualizarCatalogo(ActionEvent e)throws ExcepcionAplicacion{	
		this.usuarioSistema.setPassword(getPassword1());
		super.actualizarCatalogo(null);	
	}
	
	public void eliminarCatalogo(ActionEvent e)throws ExcepcionAplicacion{		
		super.eliminarCatalogo(null);
		limpiarCatalogo(null);
	}
	
	@Override
	protected boolean validarCampos(Entidad entidad) {
	   boolean resultado = false;
		
	   resultado = esNombreValido() && esLoginValido() && esPasswordValido() && esConfirmarPWValido();
	   
	   if(resultado){
		    resultado = false;
			if(getUsuarioSistema().getStatus() == null ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe de seleccionar un status."));
			
			}else if(getUsuarioSistema().getRol() == null || getUsuarioSistema().getRol().getIdRol() == null ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe de seleccionar un Rol."));			
			}else if(!opcionBuscar && (getPassword1().compareTo(getPassword2()) != 0)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo PASSWORD no coincide con el campo CONFIRMAR PW."));
			}else if(getUsuarioSistema().getArea() == null || getUsuarioSistema().getArea().getIdArea() == null ){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Debe seleccionar un Area"));
			}else{
				resultado = true;			
			}
	   }
		
		System.out.println("Valor de validar Campos: "+resultado);
		opcionBuscar = false;
		return resultado;		
	}
	
	public Boolean esNombreValido(){
		boolean nombreTieneValor = !(getUsuarioSistema().getNombre() == null || getUsuarioSistema().getNombre().trim().isEmpty());
		boolean resultado = false;
		if(nombreTieneValor ){
			//resultado = valorValido(getUsuarioSistema().getNombre(), "^([a-zA-Z])+|[ ]([a-zA-Z])+$");
			//if(!resultado) //no contiene letras y espacios
			//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Nombre solo permite letras y espacios."));
			resultado = true;
		}else{
			if(!opcionBuscar){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Nombre es obligatorio."));
			}else
				resultado = true;
		}
		return resultado;
	}
	
	public Boolean esLoginValido(){
		boolean loginTieneValor = !(getUsuarioSistema().getLogin() == null || getUsuarioSistema().getLogin().trim().isEmpty());
		boolean resultado = false;
		if(loginTieneValor ){
			//resultado = valorValido(getUsuarioSistema().getLogin(), "^([a-zA-Z0-9])+|[ ]([a-zA-Z0-9])+$");
			//if(!resultado) //no contiene letras y espacios
			//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Login solo permite letras, numeros y espacios."));
			resultado = true;
		}else{
			if(!opcionBuscar){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Login es obligatorio."));				
			}else
				resultado = true;
		}
		return resultado;
	}
	
	public Boolean esPasswordValido(){
		boolean pass1TieneValor = !(getPassword1() == null || getPassword1().trim().isEmpty());
		boolean resultado = false;
		if(pass1TieneValor ){
			//resultado = valorValido(getPassword1(), "^([a-zA-Z0-9])+|[ ]([a-zA-Z0-9])+$");
			//if(!resultado) //no contiene letras y espacios
			//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Password solo permite letras, numeros y espacios."));
			resultado = true;
		}else{
			if(!opcionBuscar){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Password es obligatorio."));
			}else
				resultado = true;
			
		}
		return resultado;
	}
	
	public Boolean esConfirmarPWValido(){
		boolean pass2TieneValor = !(getPassword2() == null || getPassword2().trim().isEmpty());
		boolean resultado = true;
		if(pass2TieneValor ){
			//resultado = valorValido(getPassword1(), "^([a-zA-Z0-9])+|[ ]([a-zA-Z0-9])+$");
			//if(!resultado) //no contiene letras y espacios
			//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Confirmar PW solo permite letras, numeros y espacios."));
			resultado = true;
		}else{
			if(!opcionBuscar){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","El campo Confirmar PW es obligatorio."));
			}else
				resultado = true;
		}
		return resultado;
	}
	
	public Boolean valorValido(String valor, String expresionRegular){
		  Pattern pat = Pattern.compile(expresionRegular);
		  Matcher mat = pat.matcher(valor);
		  return mat.matches();		         
	}

	@Override
	protected boolean validarExistente(Entidad entidad) {
		return false;//existenCadenasSimilares(this.usuarioSistema.getNombre(), null) || validarRegistroExistente(getUsuarioSistema(), 1);	
	}
	
	public UsuarioSistema obtenerClonUsuarioSistema(UsuarioSistema usuarioSistema, Boolean conId){
		UsuarioSistema iUsuarioSistema = new UsuarioSistema();
		
		iUsuarioSistema.setIdUsuarioSistema(usuarioSistema.getIdUsuarioSistema());
		if(conId){
			iUsuarioSistema.setId(iUsuarioSistema.getIdUsuarioSistema());			
		}
		
		iUsuarioSistema.setLogin(usuarioSistema.getLogin());
		iUsuarioSistema.setNombre(usuarioSistema.getNombre());
		
		return iUsuarioSistema;
	}
	
	public Boolean validarRegistroExistente(Entidad entidad, Integer tipo){
		ListDataModel<UsuarioSistema> listaModelo = null;
		Boolean registroExistente = true;
		UsuarioSistema usuarioSistema = obtenerClonUsuarioSistema(getUsuarioSistema(), false);
		   
		try{
			listaModelo = (ListDataModel<UsuarioSistema>)consultarEntidad(usuarioSistema);
			registroExistente = listaModelo != null && listaModelo.getRowCount() > 0;
			System.out.println("registroExistente: "+registroExistente );
			System.out.println("listamodelo: "+listaModelo);
			if(listaModelo!=null)
			System.out.println("Cantidad de registros: "+listaModelo.getRowCount() );
			if(registroExistente && tipo == 2){
				System.out.println("es tipo 2, vamos a validar si es la misma tarifa");
				System.out.println(listaModelo.getRowData().getIdUsuarioSistema());
				System.out.println(usuarioSistema.getIdUsuarioSistema());
				if(listaModelo.getRowCount() == 1 && listaModelo.getRowData().getIdUsuarioSistema().compareTo(usuarioSistema.getIdUsuarioSistema()) == 0 ){
					System.out.println("********** Los IDS son iguales.");
					registroExistente = false;
				}
			}
			
			if(registroExistente)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,
					"Info:","Ya existe un registro con la misma informacion(No se considero el IMPORTE para esta validacion)."));
		}catch(Throwable e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,
					"Info:","Sucedio un error al Validar si existe el registro: "+e.getMessage()));
			//mandamos true para que no contiene con la ejecucion de los metodos
			registroExistente = true;
		}
		return registroExistente;
	}

	public boolean existenCadenasSimilares(String valor, Integer idRegistro){
		ValidarCadenasSimilaresPeticion validarCadenasSimilaresPeticion = new ValidarCadenasSimilaresPeticion();
		ValidarCadenasSimilaresRespuesta validarCadenasSimilaresRespuesta = null;
		Boolean resultado = false;
		Integer umbral = 5;
		
		ListDataModel<UsuarioSistema> listaModelo = null;
		
		try{
			
			listaModelo = (ListDataModel<UsuarioSistema>)consultarEntidad(new UsuarioSistema());
			validarCadenasSimilaresPeticion = new ValidarCadenasSimilaresPeticion();
			validarCadenasSimilaresPeticion.setCadenaNueva(valor); 
			validarCadenasSimilaresPeticion.setUmbral(umbral);
			System.out.println("Registros nulos: "+listaModelo);
			System.out.println("Registros encontrados: "+listaModelo.getRowCount());
			
			for(UsuarioSistema us: listaModelo){
				validarCadenasSimilaresPeticion.setCadenaExistente(us.getNombre());
				validarCadenasSimilaresRespuesta = getFachadaComun().validarCadenasSimilares(validarCadenasSimilaresPeticion);
				resultado = validarCadenasSimilaresRespuesta.getResultadoComputo() < umbral;
				System.out.println("Validando:"+us.getNombre()+", "+this.usuarioSistema.getNombre());
				System.out.println(resultado);
				if(resultado){
					if(idRegistro!=null && idRegistro == us.getIdUsuarioSistema() ){
						resultado = false;
						break;
					}
					break;
				}
			}
			
			if( resultado ){				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Ya existe un registro con un NOMBRE DE USUARIO muy similar al ingresado."));
			}
		}catch(Throwable t){
			t.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN ,"Info:","Sucedio un error al validar si existe el registro:"+t.getMessage()));
		}
		return resultado;
	}
	
	@Override
	protected boolean validarExistenteActualizar(Entidad entidad) {
		// TODO Auto-generated method stub
		return false;//existenCadenasSimilares(this.usuarioSistema.getNombre(), this.usuarioSistema.getIdUsuarioSistema()) || validarRegistroExistente(getUsuarioSistema(), 2);
	}

	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public List<String> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<String> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public List<Rol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
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

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Boolean getValidarPasswords() {
		return validarPasswords;
	}

	public void setValidarPasswords(Boolean validarPasswords) {
		this.validarPasswords = validarPasswords;
	}

	public List<Area> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}
	

}
