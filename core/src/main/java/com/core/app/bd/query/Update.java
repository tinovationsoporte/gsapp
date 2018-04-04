package com.core.app.bd.query;

import com.core.app.bd.query.Query.QueryType;

public abstract class Update extends Query {

	
	public String crearDeclaracion(Object parametros) {
		
		setResultadoQuery(new StringBuilder()) ;
		
		setParametros(parametros);
		
		getResultadoQuery()
		.append(defineUPDATE()).append(" ")
		.append(defineSET()).append(" ")
		.append(defineWHERE(parametros));
		
		setDeclaracionCreada(true);
		setQueryType( QueryType.SQL_UPDATE);
		
		return getResultadoQuery().toString();
	}

	protected abstract String defineUPDATE();
	protected abstract String defineSET();
	protected abstract String defineWHERE(Object parametros);
}
