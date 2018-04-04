package com.core.app.bd.query;

import com.core.app.bd.query.Query.QueryType;

public abstract class Insert extends Query{


	public String crearDeclaracion(Object parametros) {
		
		setResultadoQuery(new StringBuilder()) ;		
		setParametros(parametros);
		
		getResultadoQuery()
		.append(defineINSERT()).append(" ")
		.append(defineINTO()).append(" ")
		.append(defineVALUES(parametros));		
		
		setDeclaracionCreada(true);
		setQueryType( QueryType.SQL_INSERT);
		return getResultadoQuery().toString();
	}

	protected abstract String defineINSERT();
	protected abstract String defineINTO();
	protected abstract String defineVALUES(Object parametros);
	
	
	
}
