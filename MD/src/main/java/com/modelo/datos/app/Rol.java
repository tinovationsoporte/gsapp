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
import com.core.app.modelo.ICatalogo;

/**
 * @author webingeniuz
 * @version 1.0
 * @created 10-mar-2013 12:39:34 a.m.
 */

@Entity(name="Rol")
@Table(name="Rol")
@Access(AccessType.FIELD)

public class Rol extends Entidad implements Serializable, ICatalogo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idRol")
	@Basic(optional = false)
	private Integer idRol;
	
	@Column(name="idRol", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="clave")
	@Basic(optional = true)
	private String clave;
	
	@Column(name="descripcion")
	@Basic(optional = true)
	private String descripcion;

	public Rol(){

	}
	


	public String getClave() {
		return clave;
	}



	public void setClave(String clave) {
		this.clave = clave;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Integer getIdRol() {
		return idRol;
	}



	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public void finalize() throws Throwable {

	}



	@Override
	public Integer getIdCatalogo() {
		return getIdRol();
	}



	@Override
	public void setIdCatalogo(Integer id) {
		setIdRol(id);
	}



	@Override
	public Class getClazz() {
		return this.getClass();
	}



	@Override
	public Entidad getEntidad() {
		return this;
	}

}