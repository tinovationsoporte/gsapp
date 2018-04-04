package com.mybatis.mapper;

import java.util.Date;
import java.util.List;

import com.modelo.datos.estructuras.ReportesActividades;
import com.modelo.datos.estructuras.TotalReportes;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaPeticion;
import com.objetos.transf.datos.app.reporte.ConsultarReportesPorFechaRespuesta;
import com.objetos.transf.datos.app.reporte.ConsultarTotalReportesPeticion;



public interface ReporteActividadMapper {
	
	
	List<ReportesActividades>consultarFechasReportes(ConsultarReportesPorFechaPeticion peticion);
	List<ReportesActividades>consultarReportesPorFecha(ConsultarReportesPorFechaPeticion peticion);
	List<TotalReportes> consultarTotalReportes(ConsultarTotalReportesPeticion peticion);
	
}

