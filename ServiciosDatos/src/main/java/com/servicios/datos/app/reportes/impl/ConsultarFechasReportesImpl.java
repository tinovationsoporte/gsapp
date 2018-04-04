package com.servicios.datos.app.reportes.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.ReporteActividadMapper;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaRespuesta;
import com.servicios.datos.app.reportes.ConsultarFechasReportes;

public class ConsultarFechasReportesImpl implements ConsultarFechasReportes {

	private ReporteActividadMapper reporteActividadMapper;
	
	@Override
	public ConsultarReportesPorFechaRespuesta ejecutar(ConsultarReportesPorFechaPeticion peticion)
			throws ExcepcionServicio {
		
		ConsultarReportesPorFechaRespuesta respuesta = new ConsultarReportesPorFechaRespuesta();
		
		respuesta.setListaReportes(reporteActividadMapper.consultarFechasReportes(peticion));
		
		return respuesta;
	}

	public ReporteActividadMapper getReporteActividadMapper() {
		return reporteActividadMapper;
	}

	public void setReporteActividadMapper(ReporteActividadMapper reporteActividadMapper) {
		this.reporteActividadMapper = reporteActividadMapper;
	}

	
	
}
