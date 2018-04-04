package com.sentencias.sql.sistema;

import java.sql.PreparedStatement;

import com.core.app.bd.ExcepcionQuery;
import com.core.app.bd.NamedParameterStatement;
import com.core.app.bd.query.Query;
import com.core.app.bd.query.Select;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaPeticion;

public class ObtenerUsuarioSistemaSQL extends Select {

	public ObtenerUsuarioSistemaSQL() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String defineSELECT() {
		StringBuilder select = new StringBuilder();
		
		select.append("SELECT us.idUsuarioSistema,")
			  .append("us.fechaCaptura,us.login,")
			  .append("us.password,")
			  .append("us.nombre,")
			  .append("us.status,")
			  .append("us.idRol,")
			  .append("r.clave,")
			  .append("r.descripcion ");
			  	
		return select.toString();
	}

	@Override
	protected String defineFROM() {
		
		StringBuilder from = new StringBuilder();

		from.append("FROM usuariosistema us ");
		from.append("JOIN rol r on us.idRol = r.idRol ");
		  
		return from.toString();
	}

	@Override
	protected String defineWHERE(Object parametros) {
		
		StringBuilder where = new StringBuilder();
		
		where.append("WHERE us.login = :login ")
		     .append("AND us.password = :password");
		
		return where.toString();
		
	}
	
	
	
	@Override
	public PreparedStatement prepararParametros(Object parametros,
			NamedParameterStatement namedParameterStatement) throws ExcepcionQuery {

		if(parametros instanceof ObtenerUsuarioSistemaPeticion){			
			
			try {
				
				namedParameterStatement.setString("login", ((ObtenerUsuarioSistemaPeticion)parametros).getNombreUsuario() );
				namedParameterStatement.setString("password", ((ObtenerUsuarioSistemaPeticion)parametros).getContrasena() );

			} catch (Exception e) {
				throw new ExcepcionQuery("Ocurrio un erro al preparar los parametros en ObtenerUsuarioSistemaSQL. -" + e.getMessage(), e.getCause());
			}
			
		}
		
		
		return namedParameterStatement.getPreparedStatement();
	}
	
	
	public static void main(String [] args){
			
		Query sql = new ObtenerUsuarioSistemaSQL();
		//System.out.println( sql.crearDeclaracion(peticion) );	
	
	}

	
	

}
