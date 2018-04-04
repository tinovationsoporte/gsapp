package com.servicios.app.catalogos;

import java.util.SortedMap;

import com.core.app.modelo.Entidad;
import com.objetos.transf.datos.app.catalogo.ActualizarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalagoRespuesta;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.CrearCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.EliminarCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoRespuesta;
import com.servicios.excepcion.ExcepcionServicioFachada;

public interface ServicioCatalogo {

	void crearCatalogo (CrearCatalogoPeticion peticion) throws ExcepcionServicioFachada;
	void eliminarCatalogo(EliminarCatalogoPeticion peticion) throws ExcepcionServicioFachada;
	void actualizarCatalogo(ActualizarCatalogoPeticion peticion) throws ExcepcionServicioFachada;	
	ConsultarCatalagoRespuesta consultarCatalogo(ConsultarCatalogoPeticion peticion) throws ExcepcionServicioFachada;
	ObtenerCatalogoRespuesta obtenerCatalogo(ObtenerCatalogoPeticion peticion) throws ExcepcionServicioFachada;

}
