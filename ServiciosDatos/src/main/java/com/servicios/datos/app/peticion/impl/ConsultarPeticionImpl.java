package com.servicios.datos.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.PeticionMapper;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesRespuesta;
import com.servicios.datos.app.peticion.ConsultarPeticion;
import com.servicios.datos.excepcion.ExcepcionServicioDatos;

public class ConsultarPeticionImpl implements ConsultarPeticion{

	private PeticionMapper peticionMapper;
	
	@Override
	public ConsultarPeticionesRespuesta ejecutar(ConsultarPeticionesPeticion peticion) throws ExcepcionServicio {
		
		ConsultarPeticionesRespuesta respuesta = new ConsultarPeticionesRespuesta();
		
		try{
			respuesta.setPeticiones( peticionMapper.consultarPeticion(peticion));
		}catch (Exception e){
			throw new ExcepcionServicioDatos("Ocurrio un error el invocar al Operacion ConsultarPeticion.ejecutar()", e);
		}
				
		
		return respuesta;
	}

	public PeticionMapper getPeticionMapper() {
		return peticionMapper;
	}

	public void setPeticionMapper(PeticionMapper peticionMapper) {
		this.peticionMapper = peticionMapper;
	}

	
}
