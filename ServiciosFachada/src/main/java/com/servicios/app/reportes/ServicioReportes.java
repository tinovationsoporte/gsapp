package com.servicios.app.reportes;

import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaRespuesta;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;

public interface ServicioReportes {
	
	
	ConsultarReportesPorFechaRespuesta consultarReportesPorFecha(ConsultarReportesPorFechaPeticion peticion) throws ExcepcionServicioFachada;
	ConsultarReportesPorFechaRespuesta consultarFechasReportes(ConsultarReportesPorFechaPeticion peticion) throws ExcepcionServicioFachada;
	ConsultarTotalReportesRespuesta consultarTotalReportes(ConsultarTotalReportesPeticion peticion) throws ExcepcionServicioFachada;

}
