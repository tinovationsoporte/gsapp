package com.modelo.datos.estructuras;

import java.util.Date;

public class PartidasPresupuesto {

	private Integer idPresupuesto;
	private Integer idPartida;
	private Double cantidad;
	private String usuario;
	private Date fecha;
	private Double pu;
	private String concepto;
	private Double total;
	private Double totalPartida;
		
	public Double getTotalPartida() {
		return totalPartida;
	}
	public void setTotalPartida(Double totalPartida) {
		this.totalPartida = totalPartida;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	
	public Integer getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Double getPu() {
		return pu;
	}
	public void setPu(Double pu) {
		this.pu = pu;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}	
	
}
