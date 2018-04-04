package com.modelo.datos.app;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;
import javax.persistence.Access;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

import static javax.persistence.AccessType.FIELD;

/**
 * @author webingeniuz
 * @version 1.0
 * @created 10-mar-2013 12:39:26 a.m.
 */
@Entity(name = "UsuarioSistema")
@Table(name = "UsuarioSistema")
@Access(FIELD)

public class UsuarioSistema extends Entidad implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuarioSistema")
	@Basic(optional = false)
	private Integer idUsuarioSistema;
	
	@Column(name="idUsuarioSistema", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;

	@Column(name = "fechaCaptura")
    @Temporal(TemporalType.DATE)
	@Basic(optional = true)
	private Date fechaCaptura;

	@Column(name = "login")
	@Basic(optional = true)
	private String login;
	
	@Column(name = "nombre")
	@Basic(optional = true)
	private String nombre;
	
	@Column(name = "password")
	@Basic(optional = true)
	private String password;
	/**
	 * Indica si está activo=1 o inactivo=0
	 */
	@Column(name = "status")
	@Basic(optional = true)
	private Integer status;
	
	@JoinColumn(name = "idRol", referencedColumnName="idRol")
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private Rol rol;

	@JoinColumn(name = "idtcarea", referencedColumnName="idtcarea")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private Area area;

	@Column(name = "reporteActividad")
	@Basic(optional = false)
	private Integer reporteActividad;
	
	
	/*
	@ManyToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	@JoinTable(name = "",
		joinColumns={@JoinColumn(name="idUsuarioSistema",referencedColumnName="idUsuarioSistema")},
		inverseJoinColumns={@JoinColumn(name="idtcarea",referencedColumnName="idtcarea")}
			)
	private List<Area> listaAreas = new ArrayList<Area>();
	*/
	public UsuarioSistema(){

	}


	public Date getFechaCaptura() {
		return fechaCaptura;
	}


	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}


	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}


	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Rol getRol() {
		return rol;
	}


	public void setRol(Rol rol) {
		this.rol = rol;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void finalize()
	  throws Throwable{

	}
	
	/*
	public List<Area> getListaAreas() {
		return listaAreas;
	}


	public void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}


	public void addArea(Area area){
		listaAreas.add(area);
	}*/


	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}


	public Integer getReporteActividad() {
		return reporteActividad;
	}


	public void setReporteActividad(Integer reporteActividad) {
		this.reporteActividad = reporteActividad;
	}

	

}