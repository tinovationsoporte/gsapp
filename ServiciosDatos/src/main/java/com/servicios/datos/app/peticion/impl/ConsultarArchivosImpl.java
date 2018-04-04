package com.servicios.datos.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.PeticionMapper;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosRespuesta;
import com.servicios.datos.app.peticion.ConsultarArchivos;

public class ConsultarArchivosImpl implements ConsultarArchivos {

	private PeticionMapper peticionMapper;

	
	@Override
	public ConsultarArchivosRespuesta ejecutar(ConsultarArchivosPeticion peticion) throws ExcepcionServicio {
		
		ConsultarArchivosRespuesta respuesta = new ConsultarArchivosRespuesta();
		
		respuesta.setListaArchivos(peticionMapper.consultarArchivos(peticion));
		
		return respuesta;
	}


	public PeticionMapper getPeticionMapper() {
		return peticionMapper;
	}


	public void setPeticionMapper(PeticionMapper peticionMapper) {
		this.peticionMapper = peticionMapper;
	}

	
}
