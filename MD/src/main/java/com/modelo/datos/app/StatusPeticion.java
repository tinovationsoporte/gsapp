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

@Entity(name = "StatusPeticion" )
@Table(name = "tpstatuspeticion")
@Access(AccessType.FIELD)
public class StatusPeticion extends Entidad implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtpstatuspeticion")
	@Basic(optional=false)
	private Integer idStatusPeticion;
	
	@Column(name="idtpstatuspeticion", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="valor", updatable = false, insertable = false)
	@Basic(optional = false)
	private String valor;
	
	@Column(name="idstatussiguiente", updatable = false, insertable = false)
	@Basic(optional = false)	
	private Integer idStatusSiguiente;
	
	@Column(name="idstatusanterior", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer idStatusAnterior;

	public Integer getIdStatusPeticion() {
		return idStatusPeticion;
	}

	public void setIdStatusPeticion(Integer idStatusPeticion) {
		this.idStatusPeticion = idStatusPeticion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getIdStatusSiguiente() {
		return idStatusSiguiente;
	}

	public void setIdStatusSiguiente(Integer idStatusSiguiente) {
		this.idStatusSiguiente = idStatusSiguiente;
	}

	public Integer getIdStatusAnterior() {
		return idStatusAnterior;
	}

	public void setIdStatusAnterior(Integer idStatusAnterior) {
		this.idStatusAnterior = idStatusAnterior;
	}
	
	//private String condicion;
	
	//private String elementoUI;
	
	

}
