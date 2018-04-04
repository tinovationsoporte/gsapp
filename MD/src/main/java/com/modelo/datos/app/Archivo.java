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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.core.app.modelo.Entidad;


@Entity(name = "Archivo")
@Table(name = "tparchivo")
@Access(AccessType.FIELD)
public class Archivo extends Entidad implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "idtparchivo") 
	@Basic(optional=false)
	private Integer idArchivo;
	
	@Column(name="idtparchivo", updatable = false, insertable = false)
	@Basic(optional = false)
	private Integer id;
	
	
	@JoinColumn(name="idtppeticion", referencedColumnName="idtppeticion")
	@ManyToOne (fetch = FetchType.LAZY, optional = false )
	@NotFound (action = NotFoundAction.IGNORE)
	private ProcesoPeticion procesoPeticion;
	
	
	@Column(name="ruta")
	@Basic(optional = true)
	private String ruta;
	
	
    @Column(name = "binario", nullable = false)	 
	private byte[] blob;

    @Column(name = "nombre", nullable = false)	 
	private String nombre;  
    
    
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getIdArchivo() {
		return this.idArchivo;
	}


	public void setIdArchivo(Integer idArchivo) {
		this.idArchivo = idArchivo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public ProcesoPeticion getProcesoPeticion() {
		return procesoPeticion;
	}


	public void setProcesoPeticion(ProcesoPeticion procesoPeticion) {
		this.procesoPeticion = procesoPeticion;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public byte[] getBlob() {
		return blob;
	}


	public void setBlob(byte[] blob) {
		this.blob = blob;
	}
    
    
    

}
