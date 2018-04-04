package com.mybatis.mapper;

import java.util.List;

import com.modelo.datos.estructuras.Catalogos;
import com.objetos.transf.datos.app.catalogo.ConsultarCatalogoPeticion;

public interface CatalogosMapper {

	
	List<Catalogos> consultarStatus(ConsultarCatalogoPeticion peticion);
	
	List<Catalogos> consultarPrioridades(ConsultarCatalogoPeticion peticion);

	List<Catalogos> consultarAreas(ConsultarCatalogoPeticion peticion);

	List<Catalogos> consultarColonias(ConsultarCatalogoPeticion peticion);

	List<Catalogos> consultarUsuarioArea(ConsultarCatalogoPeticion peticion);
	
	List<Catalogos> consultarOperador(ConsultarCatalogoPeticion peticion);


}
