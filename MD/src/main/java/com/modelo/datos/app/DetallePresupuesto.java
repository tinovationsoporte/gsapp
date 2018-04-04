package com.modelo.datos.app;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "DetallePresupuesto" )
@Table(name = "tdpresupuesto")
@Access(AccessType.FIELD)
public class DetallePresupuesto extends Entidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5743165342161435594L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtdpresupuesto")
	@Basic(optional=false)
	private Integer idDetallePresupuesto;
	
	
	@Column(name="idtdpresupuesto", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="cantidad")
	@Basic(optional = false)
	private Double cantidad;
	
	@Column(name="pu")
	@Basic(optional = false)
	private Double pu;
	
	@Column(name="fecha")
	@Temporal(TemporalType.DATE)
	@Basic(optional = false)
	private Date fecha;
	
	@Column(name="concepto")
	@Basic(optional = false)
	private String concepto;
	
	@JoinColumn(name="idusuario", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuario;
	
	
	@JoinColumn(name="idtmpresupuesto", referencedColumnName="idtmpresupuesto")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private Presupuesto presupuesto;
	
	
	//private double total;
	
	
	
	
	/*public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}*/

	public Integer getIdDetallePresupuesto() {
		return idDetallePresupuesto;
	}

	public void setIdDetallePresupuesto(Integer idDetallePresupuesto) {
		this.idDetallePresupuesto = idDetallePresupuesto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	
}
