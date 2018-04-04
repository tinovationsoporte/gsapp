package com.objetos.transf.datos.app.comun;

import java.io.InputStream;

public class RegistrarImagenPeticion {

	private String folderDestino;
	private String nombreImagen;
	
	private byte[] imagenBytes;
	private InputStream imagenInputStream;
	
	
	
	
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	public String getFolderDestino() {
		return folderDestino;
	}
	public void setFolderDestino(String folderDestino) {
		this.folderDestino = folderDestino;
	}
	public byte[] getImagenBytes() {
		return imagenBytes;
	}
	public void setImagenBytes(byte[] imagenBytes) {
		this.imagenBytes = imagenBytes;
	}
	public InputStream getImagenInputStream() {
		return imagenInputStream;
	}
	public void setImagenInputStream(InputStream imagenInputStream) {
		this.imagenInputStream = imagenInputStream;
	}	
	
	
	
	
}
