package com.servicios.app.dashboard;

import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;

public interface ServicioDashboard {
	
	ConsultarDashboardRespuesta consultarDashboard(ConsultarDashboardPeticion peticion) throws ExcepcionServicioFachada;
	ConsultarStatusPeticionesPorRolRespuesta consultarStatusPeticionPorRol(ConsultarStatusPeticionesPorRolPeticion peticion) throws ExcepcionServicioFachada;
	ConsultarResumenDashboardRespuesta consultarResumenDashboard(ConsultarResumenDashboardPeticion peticion) throws ExcepcionServicioFachada;


}
