package com.servicios.datos.app.dashboard.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.DashboardMapper;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardRespuesta;
import com.servicios.datos.app.dashboard.ConsultarResumenDashboard;

public class ConsultarResumenDashboardImpl implements ConsultarResumenDashboard {

	private DashboardMapper dashboardMapper;
	
	
	@Override
	public ConsultarResumenDashboardRespuesta ejecutar(ConsultarResumenDashboardPeticion peticion)
			throws ExcepcionServicio {
		
		ConsultarResumenDashboardRespuesta respuesta = new ConsultarResumenDashboardRespuesta();
		respuesta.setListaResumenDashboard(dashboardMapper.consultarResumenDashboard(peticion));
		return respuesta;

	
	}


	public DashboardMapper getDashboardMapper() {
		return dashboardMapper;
	}


	public void setDashboardMapper(DashboardMapper dashboardMapper) {
		this.dashboardMapper = dashboardMapper;
	}

	
	
	
}
