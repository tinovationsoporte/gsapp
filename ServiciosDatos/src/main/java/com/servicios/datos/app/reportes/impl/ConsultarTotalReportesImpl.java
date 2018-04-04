package com.servicios.datos.app.reportes.impl;

import com.core.app.servicios.ExcepcionServicio;
import com.mybatis.mapper.ReporteActividadMapper;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesRespuesta;
import com.servicios.datos.app.reportes.ConsultarTotalReportes;

public class ConsultarTotalReportesImpl implements ConsultarTotalReportes {

	private ReporteActividadMapper reporteActividadMapper; 
	
	@Override
	public ConsultarTotalReportesRespuesta ejecutar(ConsultarTotalReportesPeticion peticion) throws ExcepcionServicio {
		ConsultarTotalReportesRespuesta respuesta = new ConsultarTotalReportesRespuesta();
		respuesta.setListaTotalesReportes(reporteActividadMapper.consultarTotalReportes(peticion));
		
		return respuesta;
	}

	public ReporteActividadMapper getReporteActividadMapper() {
		return reporteActividadMapper;
	}

	public void setReporteActividadMapper(ReporteActividadMapper reporteActividadMapper) {
		this.reporteActividadMapper = reporteActividadMapper;
	}

	
	
}
