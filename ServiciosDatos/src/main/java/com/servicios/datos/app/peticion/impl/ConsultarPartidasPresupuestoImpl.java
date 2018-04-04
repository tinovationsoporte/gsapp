package com.servicios.datos.app.peticion.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.PeticionMapper;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoRespuesta;
import com.servicios.datos.app.peticion.ConsultarPartidasPresupuesto;

public class ConsultarPartidasPresupuestoImpl implements ConsultarPartidasPresupuesto {

	private PeticionMapper peticionMapper;

	
	@Override
	public ConsultarPartidasPresupuestoRespuesta ejecutar(ConsultarPartidasPresupuestoPeticion peticion)
			throws ExcepcionServicio {
		
		ConsultarPartidasPresupuestoRespuesta respuesta = new ConsultarPartidasPresupuestoRespuesta();
		
		respuesta.setListaPartidasPresupuesto(peticionMapper.consultarPartidasPresupuesto(peticion));
		
		return respuesta;
	}


	public PeticionMapper getPeticionMapper() {
		return peticionMapper;
	}


	public void setPeticionMapper(PeticionMapper peticionMapper) {
		this.peticionMapper = peticionMapper;
	}
	
	

}
