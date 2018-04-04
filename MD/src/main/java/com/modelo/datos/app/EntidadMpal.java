package com.modelo.datos.app;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "EntidadMpal")
@Table(name = "tcentidadmunicipal")
@Access(AccessType.FIELD)
public class EntidadMpal extends Entidad implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtcentidadmunicipal")
	@Basic(optional=false)
	private Integer idEntidadMpal;
	
	@Column(name="idtcentidadmunicipal", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name = "clave")
	@Basic(optional = true)
	private String clave;
	
	@Column(name = "nombre")
	@Basic(optional = false)
	private String nombre;
	
	@Column(name = "descripcion")
	@Basic(optional = true)
	private String descripcion;
	
	@JoinColumn(name="idtipoentidadmunicipal", referencedColumnName="idtcgeneral")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private TipoEntidadMpal tipoEntidadMpal;

	public Integer getIdEntidadMpal() {
		return idEntidadMpal;
	}

	public void setIdEntidadMpal(Integer idEntidadMpal) {
		this.idEntidadMpal = idEntidadMpal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

	public TipoEntidadMpal getTipoEntidadMpal() {
		return tipoEntidadMpal;
	}

	public void setTipoEntidadMpal(TipoEntidadMpal tipoEntidadMpal) {
		this.tipoEntidadMpal = tipoEntidadMpal;
	}
	
	
	

}
