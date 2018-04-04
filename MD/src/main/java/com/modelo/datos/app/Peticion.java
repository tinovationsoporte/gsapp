package com.modelo.datos.app;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;

@Entity(name = "Peticion")
@Table(name = "tmpeticion")
@Access(AccessType.FIELD)
public class Peticion extends Entidad implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtmpeticion")
	@Basic(optional=false)
	private Integer idPeticion;
	
	@Column(name="idtmpeticion", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	@Column(name = "folio")
	@Basic(optional = false)
	private String folio;
	
	@Column(name = "direccion")
	@Basic(optional = false)
	private String direccion;
		
	@JoinColumn(name="idtpstatuspeticion", referencedColumnName="idtpstatuspeticion")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private StatusPeticion statusPeticion;
	
	@Column(name = "requierepresupuesto")
	@Basic(optional = true)
	private Integer requierePresupuesto;	
	
	@Column(name = "solicitante")
	@Basic(optional = false)
	private String solicitante;
	
	@JoinColumn(name="idtcentidadmpal", referencedColumnName="idtcentidadmunicipal")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private EntidadMpal entidadMpal;
	
	@JoinColumn(name="idtipomediocontacto", referencedColumnName="idtcgeneral")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private TipoMedioContacto tipoMedioContacto;
	
	@Column(name = "mediocontacto")
	@Basic(optional = false)
	private String medioContacto;
	
	@Column(name = "descripcion")
	@Basic(optional = false)	
	private String descripcion;
	
	@JoinColumn(name="idtccategoria", referencedColumnName="idtccategoria")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Categoria categoria;

	@JoinColumn(name="idprioridad", referencedColumnName="idtcgeneral")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Prioridad prioridad;
	
	@Column(name = "observaciones")
	@Basic(optional = true)
	private String observaciones;
	
	@Column(name="requiereEvidencia")
	@Basic(optional = false)
	private Integer requiereEvidencia;
	
	@Column(name="claveExterna")
	@Basic(optional = true)
	private Integer claveExterna;
	
	@JoinColumn(name="idtcarea", referencedColumnName="idtcarea")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Area area;
	
	@JoinColumn(name="idtcareapresupuesto", referencedColumnName="idtcarea")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Area areaPresupuesto;
	
	@JoinColumn(name="idtcareaadicional", referencedColumnName="idtcarea")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Area areaAdicional;	
	
	
	@OneToMany(orphanRemoval=true, mappedBy = "peticion", fetch=FetchType.LAZY)
	@Cascade({org.hibernate.annotations.CascadeType.PERSIST})
	@NotFound (action = NotFoundAction.IGNORE)
	private List<ProcesoPeticion> listaProcesoPeticion = new ArrayList<ProcesoPeticion>();
	
	@JoinColumn(name="idusuarioasignado", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuarioAsignado;
	
	@JoinColumn(name="idusuariopresupuesto", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuarioPresupuesto;
	
	@JoinColumn(name="idusuarioAdicional", referencedColumnName="idUsuarioSistema")
	@ManyToOne (fetch=FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private UsuarioSistema usuarioAdicional;
	
	
	@OneToOne(mappedBy = "peticion", cascade = CascadeType.PERSIST, 
              fetch = FetchType.LAZY, optional = true)
	@NotFound (action = NotFoundAction.IGNORE)
	private Presupuesto presupuesto;
	
	@Column(name = "descripcionEntrega")
	@Basic(optional = false)	
	private String descripcionEntrega;
		
	public String getDescripcionEntrega() {
		return descripcionEntrega;
	}

	public void setDescripcionEntrega(String descripcionEntrega) {
		this.descripcionEntrega = descripcionEntrega;
	}

	public Integer getRequiereEvidencia() {
		return requiereEvidencia;
	}

	public void setRequiereEvidencia(Integer requiereEvidencia) {
		this.requiereEvidencia = requiereEvidencia;
	}

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public StatusPeticion getStatusPeticion() {
		return statusPeticion;
	}

	public void setStatusPeticion(StatusPeticion statusPeticion) {
		this.statusPeticion = statusPeticion;
	}

	public Integer getRequierePresupuesto() {
		return requierePresupuesto;
	}

	public void setRequierePresupuesto(Integer requierePresupuesto) {
		this.requierePresupuesto = requierePresupuesto;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public EntidadMpal getEntidadMpal() {
		return entidadMpal;
	}

	public void setEntidadMpal(EntidadMpal entidadMpal) {
		this.entidadMpal = entidadMpal;
	}

	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}

	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

	public String getMedioContacto() {
		return medioContacto;
	}

	public void setMedioContacto(String medioContacto) {
		this.medioContacto = medioContacto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Prioridad prioridad) {
		this.prioridad = prioridad;
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

	public void addProcesoPeticion(ProcesoPeticion procesoPeticion){		
		procesoPeticion.setPeticion(this);
		listaProcesoPeticion.add(procesoPeticion);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<ProcesoPeticion> getListaProcesoPeticion() {
		return listaProcesoPeticion;
	}

	public void setListaProcesoPeticion(List<ProcesoPeticion> listaProcesoPeticion) {
		this.listaProcesoPeticion = listaProcesoPeticion;
	}

	public Area getAreaPresupuesto() {
		return areaPresupuesto;
	}

	public void setAreaPresupuesto(Area areaPresupuesto) {
		this.areaPresupuesto = areaPresupuesto;
	}

	public Area getAreaAdicional() {
		return areaAdicional;
	}

	public void setAreaAdicional(Area areaAdicional) {
		this.areaAdicional = areaAdicional;
	}

	public UsuarioSistema getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(UsuarioSistema usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public UsuarioSistema getUsuarioPresupuesto() {
		return usuarioPresupuesto;
	}

	public void setUsuarioPresupuesto(UsuarioSistema usuarioPresupuesto) {
		this.usuarioPresupuesto = usuarioPresupuesto;
	}

	public UsuarioSistema getUsuarioAdicional() {
		return usuarioAdicional;
	}

	public void setUsuarioAdicional(UsuarioSistema usuarioAdicional) {
		this.usuarioAdicional = usuarioAdicional;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Integer getClaveExterna() {
		return claveExterna;
	}

	public void setClaveExterna(Integer claveExterna) {
		this.claveExterna = claveExterna;
	}
	
	
}
