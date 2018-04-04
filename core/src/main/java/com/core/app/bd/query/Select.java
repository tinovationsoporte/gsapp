package com.core.app.bd.query;

import java.sql.Connection;

public abstract class Select extends Query {

	@Override
	public String crearDeclaracion(Object parametros) {
		
		setResultadoQuery(new StringBuilder()) ;		
		
		setParametros(parametros);
		
		getResultadoQuery()
		.append(defineSELECT()).append(" ")
		.append(defineFROM()).append(" ")
		.append(defineWHERE(parametros));		
		
		setDeclaracionCreada(true);
		setQueryType( QueryType.SQL_SELECT);
		return getResultadoQuery().toString();
	}

	protected abstract String defineSELECT();
	protected abstract String defineFROM();
	protected abstract String defineWHERE(Object parametros);
	
	

}
