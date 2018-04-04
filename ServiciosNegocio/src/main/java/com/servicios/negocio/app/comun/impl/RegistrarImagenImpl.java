package com.servicios.negocio.app.comun.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.core.app.servicios.ExcepcionServicio;
import com.mysql.jdbc.integration.jboss.ExtendedMysqlExceptionSorter;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenPeticion;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenRespuesta;
import com.objetos.transf.datos.app.comun.RegistrarImagenPeticion;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.comun.ModificarTamanoImagen;
import com.servicios.negocio.app.comun.RegistrarImagen;

public class RegistrarImagenImpl implements RegistrarImagen {

	private ModificarTamanoImagen modificarTamanoImagen;	
	
	public ModificarTamanoImagen getModificarTamanoImagen() {
		return modificarTamanoImagen;
	}

	public void setModificarTamanoImagen(ModificarTamanoImagen modificarTamanoImagen) {
		this.modificarTamanoImagen = modificarTamanoImagen;
	}

	@Override
	public void ejecutar(RegistrarImagenPeticion peticion) throws ExcepcionServicioNegocio {
	
		try {
		
			ModificarTamanoImagenPeticion modificarImagenPeticion = new ModificarTamanoImagenPeticion();
			
			modificarImagenPeticion.setImageInputStream(peticion.getImagenInputStream());
			modificarImagenPeticion.setImageBytes(peticion.getImagenBytes());
			
			
			modificarImagenPeticion.setTamanoAComprimir(750);
			
			modificarImagenPeticion.setTipoImagen("jpg");
	
			
			ModificarTamanoImagenRespuesta modificarImagenRespuesta = modificarTamanoImagen.ejecutar(modificarImagenPeticion);
		
		
			
			String archivo = peticion.getFolderDestino() + peticion.getNombreImagen();
			
			FileOutputStream fos = new FileOutputStream(archivo);
			
			fos.write(modificarImagenRespuesta.getImageBytes());
			
			fos.flush();
			fos.close();
			
		} catch (Exception e) {
			throw new ExcepcionServicioNegocio("Ocurrio un error al ejecutar el servicio RegistrarImagen", e);			
		}

	}

}
