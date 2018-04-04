package com.mybatis.mapper;

import java.util.List;

import com.modelo.datos.estructuras.Dashboard;
import com.modelo.datos.estructuras.ResumenDashboard;
import com.modelo.datos.estructuras.StatusPeticiones;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolPeticion;

public interface DashboardMapper {	
	List<Dashboard> consultarDashboard(ConsultarDashboardPeticion peticion);
	List<StatusPeticiones> consultarStatusPeticionPorRol(ConsultarStatusPeticionesPorRolPeticion peticion);
	List<ResumenDashboard> consultarResumenDashboard(ConsultarResumenDashboardPeticion peticion);	
}
