package com.servicios.datos.app.dashboard;

import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.ServicioPeticionRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;

public interface ConsultarDashboard extends ServicioPeticionRespuesta<ConsultarDashboardPeticion, ConsultarDashboardRespuesta>{

	@Override
	public ConsultarDashboardRespuesta ejecutar(ConsultarDashboardPeticion peticion) throws ExcepcionServicio;
	
}
