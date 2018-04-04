package com.servicios.datos.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.PeticionMapper;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.servicios.datos.app.peticion.ConsultarFechaMovimiento;

public class ConsultarFechaMovimientoImpl implements ConsultarFechaMovimiento {

	private PeticionMapper peticionMapper;
	
	@Override
	public ConsultarFechaMovimientoRespuesta ejecutar(ConsultarFechaMovimientoPeticion peticion)
			throws ExcepcionServicio {	
		
		return peticionMapper.consultarFechaMovimiento(peticion);
	}

	public PeticionMapper getPeticionMapper() {
		return peticionMapper;
	}

	public void setPeticionMapper(PeticionMapper peticionMapper) {
		this.peticionMapper = peticionMapper;
	}
	
	

}
