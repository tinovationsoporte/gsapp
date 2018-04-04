package com.servicios.app.dashboard.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarResumenDashboardRespuesta;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarStatusPeticionesPorRolRespuesta;
import com.servicios.app.dashboard.ServicioDashboard;
import com.servicios.datos.app.dashboard.ConsultarDashboard;
import com.servicios.datos.app.dashboard.ConsultarResumenDashboard;
import com.servicios.datos.app.dashboard.ConsultarStatusPeticionPorRol;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class ServicioDashboardImpl implements ServicioDashboard {
	
	private ConsultarDashboard consultarDashboard;
	private ConsultarStatusPeticionPorRol consultarStatusPeticionPorRol;
	private ConsultarResumenDashboard consultarResumenDashboard;
	
	
	@Override
	public ConsultarDashboardRespuesta consultarDashboard(ConsultarDashboardPeticion peticion)
			throws ExcepcionServicioFachada {
		
		ConsultarDashboardRespuesta respuesta = null;
		
		try {
		
			respuesta = consultarDashboard.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada("Ocurrio un error en la operacion consultarDashboard",e.getCause());
		}
		
		return respuesta;
	}

	@Override
	public ConsultarStatusPeticionesPorRolRespuesta consultarStatusPeticionPorRol(ConsultarStatusPeticionesPorRolPeticion peticion)
			throws ExcepcionServicioFachada {
		
		ConsultarStatusPeticionesPorRolRespuesta respuesta = null;
		
		try {
		
			respuesta = consultarStatusPeticionPorRol.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada("Ocurrio un error en la operacion consultarStatusPeticionPorRol",e.getCause());
		}
		
		return respuesta;
	}

	@Override
	public ConsultarResumenDashboardRespuesta consultarResumenDashboard(ConsultarResumenDashboardPeticion peticion)
			throws ExcepcionServicioFachada {
		ConsultarResumenDashboardRespuesta respuesta = null;
		
		try {
		
			respuesta = consultarResumenDashboard.ejecutar(peticion);
		
		} catch (ExcepcionServicio e) {
			throw new ExcepcionServicioFachada("Ocurrio un error en la operacion consultarResumenDashboard",e.getCause());
		}
		
		return respuesta;
	}
	
	
	
	public ConsultarDashboard getConsultarDashboard() {
		return consultarDashboard;
	}

	public void setConsultarDashboard(ConsultarDashboard consultarDashboard) {
		this.consultarDashboard = consultarDashboard;
	}

	public ConsultarStatusPeticionPorRol getConsultarStatusPeticionPorRol() {
		return consultarStatusPeticionPorRol;
	}

	public void setConsultarStatusPeticionPorRol(ConsultarStatusPeticionPorRol consultarStatusPeticionPorRol) {
		this.consultarStatusPeticionPorRol = consultarStatusPeticionPorRol;
	}

	public ConsultarResumenDashboard getConsultarResumenDashboard() {
		return consultarResumenDashboard;
	}

	public void setConsultarResumenDashboard(ConsultarResumenDashboard consultarResumenDashboard) {
		this.consultarResumenDashboard = consultarResumenDashboard;
	}

	
	
	

}
