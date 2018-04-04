package com.modelo.datos.app;

import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "ReporteActividad")
@Table(name = "tpreporteactividad")
@Access(AccessType.FIELD)
public class ReporteActividad extends Entidad {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtpreporteactividad")
	@Basic(optional=false)
	private Integer idReporteActividad;
	
	@Column(name="idtpreporteactividad", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="fechaInicial")
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional = false)
	private Date fechaInicial;
	
	@Column(name="fechaFinal")
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional = false)
	private Date fechaFinal;
	
	
	@Column(name="contenido")
	@Basic(optional = true)
	private String contenido;
	
	@Column(name="descripcion")
	@Basic(optional = false)
	private String descripcion;	
	
	@Column(name="observaciones")
	@Basic(optional = true)
	private String observaciones;
	
	@JoinColumn(name = "idtcarea", referencedColumnName="idtcarea")
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Area area;

	
	
	
	@JoinColumn(name="idusuario", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuario;
	
	@JoinColumn(name="idStatusReporteActividad", referencedColumnName="idtcgeneral")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private StatusReporteActividad statusReporteActividad;

	public Integer getIdReporteActividad() {
		return idReporteActividad;
	}

	public void setIdReporteActividad(Integer idReporteActividad) {
		this.idReporteActividad = idReporteActividad;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public StatusReporteActividad getStatusReporteActividad() {
		return statusReporteActividad;
	}

	public void setStatusReporteActividad(StatusReporteActividad statusReporteActividad) {
		this.statusReporteActividad = statusReporteActividad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}	
	
	
}
