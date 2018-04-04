package com.objetos.transf.datos.app.comun;

import java.io.InputStream;

public class ModificarTamanoImagenPeticion {
	
	
	private byte[] imageBytes;
	private String imageStringB64;
	private InputStream imageInputStream;
	private Integer tamanoAComprimir;
	private String tipoImagen;
	
	
	
	public String getTipoImagen() {
		return tipoImagen;
	}
	public void setTipoImagen(String tipoImagen) {
		this.tipoImagen = tipoImagen;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	public String getImageStringB64() {
		return imageStringB64;
	}
	public void setImageStringB64(String imageStringB64) {
		this.imageStringB64 = imageStringB64;
	}
	public InputStream getImageInputStream() {
		return imageInputStream;
	}
	public void setImageInputStream(InputStream imageInputStream) {
		this.imageInputStream = imageInputStream;
	}
	public Integer getTamanoAComprimir() {
		return tamanoAComprimir;
	}
	public void setTamanoAComprimir(Integer tamanoAComprimir) {
		this.tamanoAComprimir = tamanoAComprimir;
	}

}
