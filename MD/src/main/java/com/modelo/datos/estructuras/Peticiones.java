package com.modelo.datos.estructuras;

public class Peticiones {
	
	private String idPeticion; 		
	private String folio;		    
	private String statusActual;    
	private String categoria;  		
	private String area;  			
	private String prioridad;		
	private String colonia;			
	private String fechaCaptura; 	
	private String stausSiguiente;
	private String solicitante;	
	private Integer idStatusActual;
	private String descripcion;
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getIdStatusActual() {
		return idStatusActual;
	}
	public void setIdStatusActual(Integer idStatusActual) {
		this.idStatusActual = idStatusActual;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getStatusActual() {
		return statusActual;
	}
	public void setStatusActual(String statusActual) {
		this.statusActual = statusActual;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(String fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public String getStausSiguiente() {
		return stausSiguiente;
	}
	public void setStausSiguiente(String stausSiguiente) {
		this.stausSiguiente = stausSiguiente;
	}		
}
