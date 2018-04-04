package com.modelo.datos.app;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.core.app.modelo.Entidad;

@Entity
@Table(name = "tcgeneral")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "referencia")
public class Catalogo extends Entidad{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtcgeneral")
	@Basic(optional=false)
	private Integer idtcgeneral;
	
	@Column(name="id")
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="valor")
	@Basic(optional = false)
	private String valor;

	public Integer getIdtcgeneral() {
		return idtcgeneral;
	}

	public void setIdtcgeneral(Integer idtcgeneral) {
		this.idtcgeneral = idtcgeneral;
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
	
	
	

}
