package com.core.app.bd.query;

import com.core.app.bd.query.Query.QueryType;

public abstract class Delete extends Query{
	
	
	
	public String crearDeclaracion(Object parametros) {
		
		setResultadoQuery(new StringBuilder()) ;		
		
		setParametros(parametros);
		
		getResultadoQuery()
		.append(defineDELETE()).append(" ")
		.append(defineFROM()).append(" ")
		.append(defineWHERE(parametros));		
		
		setDeclaracionCreada(true);
		setQueryType( QueryType.SQL_DELETE);
		
		return getResultadoQuery().toString();
	}

	protected abstract String defineDELETE();
	protected abstract String defineFROM();
	protected abstract String defineWHERE(Object parametros);


}
