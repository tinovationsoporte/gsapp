package com.objetos.transf.datos.app.sistema;

public class ValidarUsuarioSistemaPeticion {

	private ObtenerUsuarioSistemaPeticion obtenerUsuarioSistemaPeticion;

	public ObtenerUsuarioSistemaPeticion getObtenerUsuarioSistemaPeticion() {
		
		if(obtenerUsuarioSistemaPeticion == null) {
			
			obtenerUsuarioSistemaPeticion = new ObtenerUsuarioSistemaPeticion();
		}
		
		return obtenerUsuarioSistemaPeticion;
	}

	public void setObtenerUsuarioSistemaPeticion(
			ObtenerUsuarioSistemaPeticion obtenerUsuarioSistemaPeticion) {
		this.obtenerUsuarioSistemaPeticion = obtenerUsuarioSistemaPeticion;
	}
	
	
	
		

	

}
