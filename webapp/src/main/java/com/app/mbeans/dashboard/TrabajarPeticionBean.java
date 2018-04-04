package com.app.mbeans.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.TabChangeEvent;

import com.core.app.mbeans.MBeanAbstracto;

@ManagedBean(name="trabajarPeticionBean")
@ViewScoped
public class TrabajarPeticionBean extends MBeanAbstracto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private DatosSolicitanteBean datosSolicitante;
	//private DatosPeticionBean datosPeticionBean;
	//private AsignarAreaBean asignarAreaBean;
	//private AsignarPresupuestoBean asignarPresupesto;
	//private RegistrarGestionBean registrarGestionBean;
	
	//Datos del solicitante
	private String nombreSolicitante;
	private String coloniaSolicitante;
	private String direccionSolicitante;
	private String medioContacto;
	private String infoAdicionalContacto;
	
	//Datos de la peticion 
	private String folioPeticion;
	private Date fechaCapturaPeticion;
	private String descripcionPeticion;
	private String categoria;
	private String prioridad;
	private String observacionesPeticion;
	
	private String estatusPeticion;
	
	//Datos del Area de Captura
	private Date fechaAutorizacion;
	private List listaAreas;
	private Integer idAreaSeleccionada;
	private String nombreResponsable;
	private String direccionResponsable;
	private String telefonoResponsable;
	private List listaOpcionesPresupuesto;
	private String opcionPresupuestoSeleccionado;
	
	//Datos del Area de Presupuesto
	private Date fechaElaboracionPresupuesto;
	private String nombreProducto;
	private Integer cantidadProducto;
	private Double precioUnitario;
	private List listaPartidas;
	private Map partidaSeleccionada;
	private Double totalPartidas;
	
	//Datos del Area de Asignacion
	private Date fechaAsignacionOperador;
	private List listaOperadores;
	private String telefonoOperador;
	private List listaOpcionesConEvidencia;
	
	//Datos del Area de gestion
	private String nombreOperadorResGestion;
	private Date fechaMovimientoGestion;
	private String movimientoGestion;
	private String comentarioGestion;
	private List listaMovimientosGestion;
	private Map movimientoSeleccionado;
	
	private String anclaTabSeleccionado;
	
	@PostConstruct
	public void inicializar(){
		this.nombreSolicitante = "Emilio Gonzalez Garcia";
		this.coloniaSolicitante = "Agua Santa";
		this.direccionSolicitante = "Av. avila camacho #17, Col. Centro CP. 91000";
		this.medioContacto = "Telefono";
		this.infoAdicionalContacto = "734 223 5678";
		
		this.folioPeticion = "27JUN-0002";
		this.fechaCapturaPeticion = new Date();
		this.descripcionPeticion = "Requieren material medico para el modulo de salud <br/> Requieren laminas para el techo del modulo";
		this.categoria = "Salud";
		this.prioridad = "Alta";
		this.observacionesPeticion = "NA";
		
		this.fechaAutorizacion = new Date();
		this.nombreResponsable = "Casimiro Martinez Hernandez";
		this.direccionResponsable = "Domicilio Conocido Poblado de Arboledas";
		this.telefonoResponsable = "732 567 8950";
		this.estatusPeticion = "En Gestion";
		this.listaAreas = crearListaAreas();
		this.listaOpcionesPresupuesto = crearOpcionesSiNo();
		
		this.fechaElaboracionPresupuesto = new Date();
		this.listaPartidas = crearListaPartidas();
		this.totalPartidas = 190.0;
		
		this.fechaAsignacionOperador = new Date();
		this.listaOperadores = crearListaOperadores();
		this.telefonoOperador = "732 678 8978";
		this.listaOpcionesConEvidencia = crearOpcionesSiNo();
		
		this.nombreOperadorResGestion = "Gonzalo Martinez Garcia";
		this.listaMovimientosGestion = crearListaMovimientos();
		
	}
	
	public void onTabChange(TabChangeEvent event){
		System.out.println("Entrando a onTabChange");
		String tabSeleccionado = event.getTab().getId();
		
		if(tabSeleccionado.compareTo("m0p1s1_tabAccordionDatSolicitante") == 0){
			this.anclaTabSeleccionado = "anclaTabDatosSolicitante";
		}else if(tabSeleccionado.compareTo("m0p1s1_tabAccordionDatPeticion") == 0){
			this.anclaTabSeleccionado = "m0p1s1_divDatosPeticionEstatus";
		}else if(tabSeleccionado.compareTo("m0p1s1_tabAccordionAreaAtencion") == 0){
			this.anclaTabSeleccionado = "anclaTabAreaAtencion";
		}else if(tabSeleccionado.compareTo("m0p1s1_tabAccordionPresupuesto") == 0){
			this.anclaTabSeleccionado = "anclaTabPresupuesto";
		}else if(tabSeleccionado.compareTo("m0p1s1_tabAsignacionOperador") == 0){
			this.anclaTabSeleccionado = "anclaTabAsignacion";
		}else if(tabSeleccionado.compareTo("m0p1s1_tabGestion") == 0){
			this.anclaTabSeleccionado = "anclaTabGestion";
		}
		System.out.println("Tab seleccionado: "+event.getTab().getId());
		System.out.println("Ancla del tab: " + this.anclaTabSeleccionado);
	}
	
	public List crearListaAreas(){
		List listaAreas = new ArrayList();
		
		Map mapaArea = new HashMap();
		mapaArea.put("area", "DIF");
		mapaArea.put("idArea", 1);
		listaAreas.add(mapaArea);
		
		mapaArea = new HashMap();
		mapaArea.put("area", "SEV");
		mapaArea.put("idArea", 2);
		listaAreas.add(mapaArea);
		
		mapaArea = new HashMap();
		mapaArea.put("area", "SSA");
		mapaArea.put("idArea", 3);
		listaAreas.add(mapaArea);
		
		mapaArea = new HashMap();
		mapaArea.put("area", "SEDESOL");
		mapaArea.put("idArea", 4);
		listaAreas.add(mapaArea);
		
		return listaAreas;
	}
	
	public List crearOpcionesSiNo(){
		List listaOpcionesPresupuesto = new ArrayList();
		
		Map mapaPresupuesto = new HashMap();
		mapaPresupuesto.put("etiqueta", "No");
		mapaPresupuesto.put("clave", "N");
		listaOpcionesPresupuesto.add(mapaPresupuesto);
		
		mapaPresupuesto = new HashMap();
		mapaPresupuesto.put("etiqueta", "Si");
		mapaPresupuesto.put("clave", "S");
		listaOpcionesPresupuesto.add(mapaPresupuesto);
		
		return listaOpcionesPresupuesto;
	}
	
	public List crearListaPartidas(){
		List listaPartidas = new ArrayList();
		
		Map mapaPartida = new HashMap();
		mapaPartida.put("idPartida", 1);
		mapaPartida.put("nombreProducto", "Lamina 1.5 x 5");
		mapaPartida.put("cantidadProducto", "4");
		mapaPartida.put("unidadMedida", "PZA");
		mapaPartida.put("precioUnitario", 10.0);
		mapaPartida.put("totalPartida", 40.0);
		listaPartidas.add(mapaPartida);
		
		mapaPartida = new HashMap();
		mapaPartida.put("idPartida", 2);
		mapaPartida.put("nombreProducto", "Kit de Curación Medica");
		mapaPartida.put("cantidadProducto", "10");
		mapaPartida.put("unidadMedida", "PZA");
		mapaPartida.put("precioUnitario", 5.0);
		mapaPartida.put("totalPartida", 50.0);
		listaPartidas.add(mapaPartida);
		
		mapaPartida = new HashMap();
		mapaPartida.put("idPartida", 3);
		mapaPartida.put("nombreProducto", "Caja Antibioticos");
		mapaPartida.put("cantidadProducto", "15");
		mapaPartida.put("unidadMedida", "PZA");
		mapaPartida.put("precioUnitario", 2.0);
		mapaPartida.put("totalPartida", 100.0);
		listaPartidas.add(mapaPartida);
		
		return listaPartidas;
	}
	
	public List crearListaOperadores(){
		List listaOperadores = new ArrayList();
		
		Map mapaOperador = new HashMap();
		mapaOperador.put("nombre", "Gonzalo Martinez Garcia");
		mapaOperador.put("idOperador", 1);
		listaOperadores.add(mapaOperador);
		
		mapaOperador = new HashMap();
		mapaOperador.put("nombre", "Maria Ramirez Hernandez");
		mapaOperador.put("idOperador", 2);
		listaOperadores.add(mapaOperador);
		
		return listaOperadores;
	}
	
	public List crearListaMovimientos(){
		List listaMovimientos = new ArrayList();
		
		Map mapaMovimiento = new HashMap();
		mapaMovimiento.put("idMovimiento", 1);
		mapaMovimiento.put("fecha", new Date());
		mapaMovimiento.put("movimiento", "Carga de Entrega");
		mapaMovimiento.put("comentario", "Mercancia de Lista");
		mapaMovimiento.put("conEvidencia", "NA");
		listaMovimientos.add(mapaMovimiento);
		
		mapaMovimiento = new HashMap();
		mapaMovimiento.put("idMovimiento", 2);
		mapaMovimiento.put("fecha", new Date());
		mapaMovimiento.put("movimiento", "En Ruta");
		mapaMovimiento.put("comentario", "Entregando Mercancia");
		mapaMovimiento.put("conEvidencia", "NA");
		listaMovimientos.add(mapaMovimiento);
		
		return listaMovimientos;
	}
	
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getColoniaSolicitante() {
		return coloniaSolicitante;
	}
	public void setColoniaSolicitante(String coloniaSolicitante) {
		this.coloniaSolicitante = coloniaSolicitante;
	}
	public String getDireccionSolicitante() {
		return direccionSolicitante;
	}
	public void setDireccionSolicitante(String direccionSolicitante) {
		this.direccionSolicitante = direccionSolicitante;
	}
	public String getMedioContacto() {
		return medioContacto;
	}
	public void setMedioContacto(String medioContacto) {
		this.medioContacto = medioContacto;
	}
	public String getInfoAdicionalContacto() {
		return infoAdicionalContacto;
	}
	public void setInfoAdicionalContacto(String infoAdicionalContacto) {
		this.infoAdicionalContacto = infoAdicionalContacto;
	}
	public String getFolioPeticion() {
		return folioPeticion;
	}
	public void setFolioPeticion(String folioPeticion) {
		this.folioPeticion = folioPeticion;
	}
	public Date getFechaCapturaPeticion() {
		return fechaCapturaPeticion;
	}
	public void setFechaCapturaPeticion(Date fechaCapturaPeticion) {
		this.fechaCapturaPeticion = fechaCapturaPeticion;
	}
	public String getDescripcionPeticion() {
		return descripcionPeticion;
	}
	public void setDescripcionPeticion(String descripcionPeticion) {
		this.descripcionPeticion = descripcionPeticion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getObservacionesPeticion() {
		return observacionesPeticion;
	}
	public void setObservacionesPeticion(String observacionesPeticion) {
		this.observacionesPeticion = observacionesPeticion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEstatusPeticion() {
		return estatusPeticion;
	}

	public void setEstatusPeticion(String estatusPeticion) {
		this.estatusPeticion = estatusPeticion;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Integer getIdAreaSeleccionada() {
		return idAreaSeleccionada;
	}

	public void setIdAreaSeleccionada(Integer idAreaSeleccionada) {
		this.idAreaSeleccionada = idAreaSeleccionada;
	}

	public String getDireccionResponsable() {
		return direccionResponsable;
	}

	public void setDireccionResponsable(String direccionResponsable) {
		this.direccionResponsable = direccionResponsable;
	}

	public List getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(List listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List getListaOpcionesPresupuesto() {
		return listaOpcionesPresupuesto;
	}

	public void setListaOpcionesPresupuesto(List listaOpcionesPresupuesto) {
		this.listaOpcionesPresupuesto = listaOpcionesPresupuesto;
	}

	public String getOpcionPresupuestoSeleccionado() {
		return opcionPresupuestoSeleccionado;
	}

	public void setOpcionPresupuestoSeleccionado(String opcionPresupuestoSeleccionado) {
		this.opcionPresupuestoSeleccionado = opcionPresupuestoSeleccionado;
	}

	public String getNombreResponsable() {
		return nombreResponsable;
	}

	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	public String getTelefonoResponsable() {
		return telefonoResponsable;
	}

	public void setTelefonoResponsable(String telefonoResponsable) {
		this.telefonoResponsable = telefonoResponsable;
	}

	public Date getFechaElaboracionPresupuesto() {
		return fechaElaboracionPresupuesto;
	}

	public void setFechaElaboracionPresupuesto(Date fechaElaboracionPresupuesto) {
		this.fechaElaboracionPresupuesto = fechaElaboracionPresupuesto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Integer getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(Integer cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public List getListaPartidas() {
		return listaPartidas;
	}

	public void setListaPartidas(List listaPartidas) {
		this.listaPartidas = listaPartidas;
	}

	public Map getPartidaSeleccionada() {
		return partidaSeleccionada;
	}

	public void setPartidaSeleccionada(Map partidaSeleccionada) {
		this.partidaSeleccionada = partidaSeleccionada;
	}

	public Double getTotalPartidas() {
		return totalPartidas;
	}

	public void setTotalPartidas(Double totalPartidas) {
		this.totalPartidas = totalPartidas;
	}

	public Date getFechaAsignacionOperador() {
		return fechaAsignacionOperador;
	}

	public void setFechaAsignacionOperador(Date fechaAsignacionOperador) {
		this.fechaAsignacionOperador = fechaAsignacionOperador;
	}

	public List getListaOperadores() {
		return listaOperadores;
	}

	public void setListaOperadores(List listaOperadores) {
		this.listaOperadores = listaOperadores;
	}

	public String getTelefonoOperador() {
		return telefonoOperador;
	}

	public void setTelefonoOperador(String telefonoOperador) {
		this.telefonoOperador = telefonoOperador;
	}

	public List getListaOpcionesConEvidencia() {
		return listaOpcionesConEvidencia;
	}

	public void setListaOpcionesConEvidencia(List listaOpcionesConEvidencia) {
		this.listaOpcionesConEvidencia = listaOpcionesConEvidencia;
	}

	public String getNombreOperadorResGestion() {
		return nombreOperadorResGestion;
	}

	public void setNombreOperadorResGestion(String nombreOperadorResGestion) {
		this.nombreOperadorResGestion = nombreOperadorResGestion;
	}

	public Date getFechaMovimientoGestion() {
		return fechaMovimientoGestion;
	}

	public void setFechaMovimientoGestion(Date fechaMovimientoGestion) {
		this.fechaMovimientoGestion = fechaMovimientoGestion;
	}

	public String getMovimientoGestion() {
		return movimientoGestion;
	}

	public void setMovimientoGestion(String movimientoGestion) {
		this.movimientoGestion = movimientoGestion;
	}

	public String getComentarioGestion() {
		return comentarioGestion;
	}

	public void setComentarioGestion(String comentarioGestion) {
		this.comentarioGestion = comentarioGestion;
	}

	public List getListaMovimientosGestion() {
		return listaMovimientosGestion;
	}

	public void setListaMovimientosGestion(List listaMovimientosGestion) {
		this.listaMovimientosGestion = listaMovimientosGestion;
	}

	public Map getMovimientoSeleccionado() {
		return movimientoSeleccionado;
	}

	public void setMovimientoSeleccionado(Map movimientoSeleccionado) {
		this.movimientoSeleccionado = movimientoSeleccionado;
	}

	public String getAnclaTabSeleccionado() {
		return anclaTabSeleccionado;
	}

	public void setAnclaTabSeleccionado(String anclaTabSeleccionado) {
		this.anclaTabSeleccionado = anclaTabSeleccionado;
	}


}
