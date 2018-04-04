package com.app.modelo;

public class Pagina {

	private int id;
	private String nombre;
	private String descripcion;
	private String urlPantalla;
	private String urlAyuda;
	private String urlCss;
	private String nombreBean;
	private String claseCatalogo;
	
	
	
	
	
	public Pagina(int id, String nombre, String descripcion,
			String urlPantalla, String urlAyuda, String urlCss, String nombreBean) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.urlPantalla = urlPantalla;
		this.urlAyuda = urlAyuda;
		this.urlCss = urlCss;
		this.nombreBean = nombreBean;
		//this.nombreCatalogo = nombreCatalogo;
	}
	
	
	
	
	
	
	public Pagina(int id, String nombre, String descripcion,
			String urlPantalla, String urlAyuda, String urlCss, String nombreBean, String claseCatalogo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.urlPantalla = urlPantalla;
		this.urlAyuda = urlAyuda;
		this.urlCss = urlCss;
		this.nombreBean = nombreBean;
		this.claseCatalogo = claseCatalogo;
	}
	



	public String getClaseCatalogo() {
		return claseCatalogo;
	}






	public void setClaseCatalogo(String claseCatalogo) {
		this.claseCatalogo = claseCatalogo;
	}






	public String getNombreBean() {
		return nombreBean;
	}




	public void setNombreBean(String nombreBean) {
		this.nombreBean = nombreBean;
	}




	public String getUrlCss() {
		return urlCss;
	}


	public void setUrlCss(String urlCss) {
		this.urlCss = urlCss;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getUrlPantalla() {
		return urlPantalla;
	}
	public void setUrlPantalla(String urlPantalla) {
		this.urlPantalla = urlPantalla;
	}
	public String getUrlAyuda() {
		return urlAyuda;
	}
	public void setUrlAyuda(String urlAyuda) {
		this.urlAyuda = urlAyuda;
	}
	
	
	
	public String toString() {
		
		return this.nombre.toString();
	}
	
	




}
