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

@Entity(name = "Categoria")
@Table(name = "tccategoria")
@Access(AccessType.FIELD)
public class Categoria extends Entidad implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtccategoria")
	@Basic(optional=false)
	private Integer idCategoria;
	
	@Column(name="idtccategoria", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name = "descripcion")
	@Basic(optional = false)
	private String descripcion;
	
	@Column(name = "clave")
	@Basic(optional = false)
	private String clave;

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
