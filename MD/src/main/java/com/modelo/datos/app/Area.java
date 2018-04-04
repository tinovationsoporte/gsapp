package com.modelo.datos.app;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.app.modelo.Entidad;

@Entity(name = "Area")
@Table(name = "tcarea")
@Access(AccessType.FIELD)
public class Area extends Entidad implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtcarea")
	@Basic(optional=false)
	private Integer idArea;
	
	@Column(name="idtcarea", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="nombre")
	@Basic(optional = false)
	private String nombre;
	
	@Column(name="responsable")
	@Basic(optional = false)
	private String descripcion;

	@Column(name="status")
	@Basic(optional = false)
	private Integer status;
	
	@Column(name="telefono")
	@Basic(optional = false)
	private String telefono;
	
	@Column(name="email")
	@Basic(optional = false)
	private String email;
	
	@Column(name="direccion")
	@Basic(optional = false)
	private String direccion;
	
	@Column(name="esDefault")
	@Basic(optional = false)
	private Integer esDefault;
	
	@Column(name="esPresupuesto")
	@Basic(optional = false)
	private Integer esPresupuesto;

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getEsDefault() {
		return esDefault;
	}

	public void setEsDefault(Integer esDefault) {
		this.esDefault = esDefault;
	}

	public Integer getEsPresupuesto() {
		return esPresupuesto;
	}

	public void setEsPresupuesto(Integer esPresupuesto) {
		this.esPresupuesto = esPresupuesto;
	}
	
	
}
