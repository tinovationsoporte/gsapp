package com.objetos.transf.datos.app.presupuesto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.modelo.datos.app.UsuarioSistema;

public class AgregarPartidaPresupuestoPeticion {

	private Double cantidad;
	private Double pu;
	private Date fecha;
	private String concepto;
	private Integer idUsuario;
	private Integer idPeticion;
	private Integer idPresupuesto;
	private Integer idUsuarioSistema;
	
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPu() {
		return pu;
	}
	public void setPu(Double pu) {
		this.pu = pu;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Integer getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
	
	
	
}
