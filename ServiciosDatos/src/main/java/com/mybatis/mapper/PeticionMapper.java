package com.mybatis.mapper;

import java.util.Date;
import java.util.List;

import com.modelo.datos.estructuras.Peticiones;
import com.modelo.datos.estructuras.Archivos;
import com.modelo.datos.estructuras.Dashboard;
import com.modelo.datos.estructuras.MovimientosProceso;
import com.modelo.datos.estructuras.PartidasPresupuesto;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarArchivosPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarFechaMovimientoRespuesta;
import com.objetos.transf.datos.app.peticion.ConsultarMovimientosProcesoPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesPeticion;
import com.objetos.transf.datos.app.presupuesto.ConsultarPartidasPresupuestoPeticion;

public interface PeticionMapper {	
	List<Peticiones> consultarPeticion(ConsultarPeticionesPeticion peticion);
	List<Archivos> consultarArchivos(ConsultarArchivosPeticion peticion);
	List<MovimientosProceso> consultarMovimientosProceso(ConsultarMovimientosProcesoPeticion peticion);
	List<MovimientosProceso> consultarUltimoMovimientoValidoPeticion(ConsultarMovimientosProcesoPeticion peticion);
	
	List<PartidasPresupuesto> consultarPartidasPresupuesto(ConsultarPartidasPresupuestoPeticion peticion);
	
	ConsultarFechaMovimientoRespuesta consultarFechaMovimiento(ConsultarFechaMovimientoPeticion peticion);
}
