package com.servicios.app.reportes.impl;

import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaRespuesta;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesRespuesta;
import com.servicios.app.reportes.ServicioReportes;
import com.servicios.datos.app.reportes.ConsultarFechasReportes;
import com.servicios.datos.app.reportes.ConsultarReportesPorFecha;
import com.servicios.datos.app.reportes.ConsultarTotalReportes;
import com.servicios.excepcion.ExcepcionServicioFachada;

public class ServicioReportesImpl implements ServicioReportes {

	private ConsultarReportesPorFecha consultarReportesPorFecha;
	private ConsultarFechasReportes consultarFechasReportes;
	private ConsultarTotalReportes consultarTotalReportes;

	
	
	
	@Override
	public ConsultarReportesPorFechaRespuesta consultarReportesPorFecha(ConsultarReportesPorFechaPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarReportesPorFechaRespuesta respuesta = new ConsultarReportesPorFechaRespuesta();
		
		try{
			respuesta = consultarReportesPorFecha.ejecutar(peticion);
		}catch(Exception e){
			
			throw new ExcepcionServicioFachada("Ocurrion un error en la operacion consultarReportesPorFecha.- " + e.getMessage(), e);
		}
		
		
		return respuesta;
	}

	@Override
	public ConsultarReportesPorFechaRespuesta consultarFechasReportes(ConsultarReportesPorFechaPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarReportesPorFechaRespuesta respuesta = new ConsultarReportesPorFechaRespuesta();
		
		try{
			respuesta = consultarFechasReportes.ejecutar(peticion);
		}catch(Exception e){			
			
			throw new ExcepcionServicioFachada("Ocurrion un error en la operacion consultarFechasReportes.- " + e.getMessage(), e);
		}
		
		
		return respuesta;
	}
	
	@Override
	public ConsultarTotalReportesRespuesta consultarTotalReportes(ConsultarTotalReportesPeticion peticion) throws ExcepcionServicioFachada {
		ConsultarTotalReportesRespuesta respuesta = new ConsultarTotalReportesRespuesta();
		
		try{
			respuesta = consultarTotalReportes.ejecutar(peticion);
		}catch(Exception e){			
			
			throw new ExcepcionServicioFachada("Ocurrion un error en la operacion consultarTotalReportes.- " + e.getMessage(), e);
		}
		
		
		return respuesta;
	}

	
	
	public ConsultarTotalReportes getConsultarTotalReportes() {
		return consultarTotalReportes;
	}

	public void setConsultarTotalReportes(ConsultarTotalReportes consultarTotalReportes) {
		this.consultarTotalReportes = consultarTotalReportes;
	}

	public ConsultarReportesPorFecha getConsultarReportesPorFecha() {
		return consultarReportesPorFecha;
	}

	public void setConsultarReportesPorFecha(ConsultarReportesPorFecha consultarReportesPorFecha) {
		this.consultarReportesPorFecha = consultarReportesPorFecha;
	}

	public ConsultarFechasReportes getConsultarFechasReportes() {
		return consultarFechasReportes;
	}

	public void setConsultarFechasReportes(ConsultarFechasReportes consultarFechasReportes) {
		this.consultarFechasReportes = consultarFechasReportes;
	}
	
	
	

}
