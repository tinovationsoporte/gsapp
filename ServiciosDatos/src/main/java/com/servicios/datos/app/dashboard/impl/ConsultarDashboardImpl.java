package com.servicios.datos.app.dashboard.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.DashboardMapper;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;
import com.servicios.datos.app.dashboard.ConsultarDashboard;

public class ConsultarDashboardImpl implements ConsultarDashboard {

	private DashboardMapper dashboardMapper;
	
	@Override
	public ConsultarDashboardRespuesta ejecutar(ConsultarDashboardPeticion peticion) throws ExcepcionServicio {
		
		ConsultarDashboardRespuesta respuesta = new ConsultarDashboardRespuesta();
		
		respuesta.setDashboardList( dashboardMapper.consultarDashboard(peticion));
		
		return respuesta;
	}

	public DashboardMapper getDashboardMapper() {
		return dashboardMapper;
	}

	public void setDashboardMapper(DashboardMapper dashboardMapper) {
		this.dashboardMapper = dashboardMapper;
	}
	
	

}
