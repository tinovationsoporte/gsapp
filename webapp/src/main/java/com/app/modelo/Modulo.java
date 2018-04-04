package com.app.modelo;

public class Modulo {

	private int id;
	private String nombre;
	private Pagina Pagina;
	
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

	public Pagina getPagina() {
		return Pagina;
	}

	public void setPagina(Pagina pagina) {
		Pagina = pagina;
	}

}
