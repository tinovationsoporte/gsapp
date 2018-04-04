package com.modelo.datos.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "Presupuesto")
@Table(name = "tmpresupuesto")
@Access(AccessType.FIELD)
public class Presupuesto extends Entidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816276771926287657L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtmpresupuesto")
	@Basic(optional=false)
	private Integer idPresupuesto;	

	@Column(name="idtmpresupuesto", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;	
	
	@Column(name="fechacaptura")
	@Temporal(TemporalType.DATE)
	@Basic(optional = false)
	private Date fechaCaptura;
	
	@Column(name="fechacierre")
	@Temporal(TemporalType.DATE)
	@Basic(optional = false)
	private Date fechaCierre;
	
	@JoinColumn(name="idusuario", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuarioSistema;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="idtmpeticion")
	private Peticion peticion;
	
	@JoinColumn(name="idstatuspresupuesto", referencedColumnName="idtcgeneral")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private StatusPresupuesto statusPresupuesto;
	
	@OneToMany(orphanRemoval=true, mappedBy = "presupuesto", fetch=FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	@NotFound (action = NotFoundAction.IGNORE)
	private List<DetallePresupuesto> listaDetallesPresupuesto = new ArrayList<DetallePresupuesto>();
	
	public int getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}
	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}
	public StatusPresupuesto getStatusPresupuesto() {
		return statusPresupuesto;
	}
	public void setStatusPresupuesto(StatusPresupuesto statusPresupuesto) {
		this.statusPresupuesto = statusPresupuesto;
	}
	public Peticion getPeticion() {
		return peticion;
	}
	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}
	public List<DetallePresupuesto> getListaDetallesPresupuesto() {
		return listaDetallesPresupuesto;
	}
	public void setListaDetallesPresupuesto(List<DetallePresupuesto> listaDetallesPresupuesto) {
		this.listaDetallesPresupuesto = listaDetallesPresupuesto;
	}
	
	public void addDetallePresupuesto(DetallePresupuesto detallePresupuesto){
		detallePresupuesto.setPresupuesto(this);
		getListaDetallesPresupuesto().add(detallePresupuesto);
	}
}
