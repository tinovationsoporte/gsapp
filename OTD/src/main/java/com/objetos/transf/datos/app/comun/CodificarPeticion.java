package com.objetos.transf.datos.app.comun;

public class CodificarPeticion {
	
	private byte[] mensajeBytes;
	
	private String llave;
	
	private String mensaje;

	
	public byte[] getMensajeBytes() {
		return mensajeBytes;
	}

	public void setMensajeBytes(byte[] mensajeBytes) {
		this.mensajeBytes = mensajeBytes;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	
	
}
