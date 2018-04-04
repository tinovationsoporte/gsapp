package com.servicios.datos.app.dashboard.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.DashboardMapper;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolRespuesta;
import com.servicios.datos.app.dashboard.ConsultarStatusPeticionPorRol;

public class ConsultarStatusPeticionPorRolImpl implements ConsultarStatusPeticionPorRol {

	private DashboardMapper dashboardMapper;
	
	
	@Override
	public ConsultarStatusPeticionesPorRolRespuesta ejecutar(ConsultarStatusPeticionesPorRolPeticion peticion)
			throws ExcepcionServicio {
		
		ConsultarStatusPeticionesPorRolRespuesta respuesta = new ConsultarStatusPeticionesPorRolRespuesta();
		respuesta.setListaStatusPeticiones(dashboardMapper.consultarStatusPeticionPorRol(peticion));		
		return respuesta;
	}


	public DashboardMapper getDashboardMapper() {
		return dashboardMapper;
	}


	public void setDashboardMapper(DashboardMapper dashboardMapper) {
		this.dashboardMapper = dashboardMapper;
	}

	
}
