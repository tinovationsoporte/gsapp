package com.core.app.bd.query;

import java.sql.PreparedStatement;

import com.core.app.bd.ExcepcionQuery;
import com.core.app.bd.NamedParameterStatement;
import com.core.app.bd.query.Query.QueryType;

public abstract class Callable extends Query{

	@Override
	public String crearDeclaracion(Object parametros) {
        setResultadoQuery(new StringBuilder()) ;
		
		setParametros(parametros);
		
		getResultadoQuery()
		.append(defineCallable());
		
		
		setDeclaracionCreada(true);
		setQueryType( QueryType.SQL_CALLABLE);
		
		return getResultadoQuery().toString();
	}
	
	protected abstract String defineCallable();

}
