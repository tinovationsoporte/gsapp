package com.core.app.bd.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.core.app.bd.ExcepcionQuery;
import com.core.app.bd.NamedParameterStatement;

	

public abstract class Query {
	
	
	protected enum QueryType {
		
		SQL_SELECT(0),
	    SQL_INSERT(1),
	    SQL_UPDATE(2),
	    SQL_DELETE(3),
	    SQL_CALLABLE(4); 
		
		
		public final int type;
		
		QueryType(int type){
			this.type = type;
		}
		
		
	   
	}
	
	
	private QueryType queryType;
	private NamedParameterStatement namedParameterStatement;
	private Map<String, Integer> mapaParametros;
	private PreparedStatement statement;
	
	private StringBuilder resultadoQuery;
	
	protected Object parametros;
	private boolean declaracionCreada;
	
	public abstract String crearDeclaracion(Object parametros);

	public abstract PreparedStatement prepararParametros(Object parametros,
			NamedParameterStatement namedParameterStatement) throws ExcepcionQuery;
	
	public  void prepararDeclaracion(Connection conexion, Object parametros) throws ExcepcionQuery{
		
		if(conexion != null){
			
			if(isDeclaracionCreada()){
			
				try {
				
					NamedParameterStatement namedParameterStatement= new NamedParameterStatement(conexion, getResultadoQuery().toString(), getQueryType().type);
					setStatement(prepararParametros(parametros, namedParameterStatement));
					if(queryType == QueryType.SQL_CALLABLE){
					   setMapaParametros(namedParameterStatement.getMapaParametros());
					}
					
				} catch (SQLException e) {
					throw new ExcepcionQuery("Ocurrio un error al crear la declaracion. - " + e.getMessage(), e.getCause());
				}
			
			}else {
				
				throw new ExcepcionQuery("No existe una declaracion creada.", null);
			}
		}else {
			
			throw new ExcepcionQuery("No existe una referencia asignada del objeto Connection.", null);
		}
		
	}
 	
	
	
	
	
	
	


	protected final StringBuilder getResultadoQuery() {
		return resultadoQuery;
	}
	
	protected final void setResultadoQuery(StringBuilder resultadoQuery) {
		this.resultadoQuery = resultadoQuery;
	}

	protected Object getParametros() {
		return parametros;
	}
	
	protected void setParametros(Object parametros){
		
		this.parametros = parametros;
	}

	public PreparedStatement getStatement() {
		return statement;
	}

	protected void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	protected boolean isDeclaracionCreada() {
		return declaracionCreada;
	}

	protected void setDeclaracionCreada(boolean declaracionCreada) {
		this.declaracionCreada = declaracionCreada;
	}

	protected QueryType getQueryType() {
		return queryType;
	}

	protected void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public Map<String, Integer> getMapaParametros() {
		return mapaParametros;
	}

	public void setMapaParametros(Map<String, Integer> mapaParametros) {
		this.mapaParametros = mapaParametros;
	}
	
	

}
