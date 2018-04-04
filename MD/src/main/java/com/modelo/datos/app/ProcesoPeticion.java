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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "ProcesoPeticion" )
@Table(name = "tppeticion")
@Access(AccessType.FIELD)
public class ProcesoPeticion extends Entidad implements Serializable {

	public enum Movimiento{
		
		CAPTURA,
		VALIDACION,
		ASIGNACION,
		PRESUPUESTO,
		GESTION,
		EVIDENCIA
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8100943624066305874L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtppeticion") 
	@Basic(optional=false)
	private Integer idProcesoPeticion;
	
	@Column(name="idtppeticion", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name="fecha")
	@Temporal(TemporalType.DATE)
	@Basic(optional = false)
	private Date fecha;
	
	@JoinColumn(name="idtmpeticion", referencedColumnName="idtmpeticion")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private Peticion peticion;
	
	
	@JoinColumn(name="idusuario", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuarioSistema;
	
	//private TipoMovimientoPeticion tipoMovimientoPeticion;
	
	@Column(name="comentarios")
	@Basic(optional = true)
	private String comentarios;
	
	@JoinColumn(name="idtpstatuspeticion", referencedColumnName="idtpstatuspeticion")
	@ManyToOne (fetch=FetchType.LAZY, optional = false)
	@NotFound (action = NotFoundAction.IGNORE)
	private StatusPeticion statusPeticion;

	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "procesoPeticion",fetch=FetchType.EAGER)
	@NotFound (action = NotFoundAction.IGNORE)
	private List<Archivo> listaArchivo = new ArrayList<Archivo>();
	
	@Column(name="movimiento")
	@Basic(optional = true)
	private String movimiento;
	
	
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}

	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	
	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}

	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public StatusPeticion getStatusPeticion() {
		return statusPeticion;
	}

	public void setStatusPeticion(StatusPeticion statusPeticion) {
		this.statusPeticion = statusPeticion;
	}	
	
	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}

	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}

	public void addArchivo(Archivo archivo){
		archivo.setProcesoPeticion(this);
		listaArchivo.add(archivo);
	}
	
}
