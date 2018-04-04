package com.servicios.datos.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.PeticionMapper;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoRespuesta;
import com.servicios.datos.app.peticion.ConsultarMovimientosProceso;

public class ConsultarUltimoMovimientoValidoImpl implements ConsultarMovimientosProceso {

	private PeticionMapper peticionMapper;

	
	@Override
	public ConsultarMovimientosProcesoRespuesta ejecutar(ConsultarMovimientosProcesoPeticion peticion)
			throws ExcepcionServicio {
		
		ConsultarMovimientosProcesoRespuesta respuesta = new ConsultarMovimientosProcesoRespuesta();
		
		respuesta.setListaMovimientosProceso(peticionMapper.consultarUltimoMovimientoValidoPeticion(peticion));
		
		return respuesta;
	}


	public PeticionMapper getPeticionMapper() {
		return peticionMapper;
	}


	public void setPeticionMapper(PeticionMapper peticionMapper) {
		this.peticionMapper = peticionMapper;
	}
	
	

}
