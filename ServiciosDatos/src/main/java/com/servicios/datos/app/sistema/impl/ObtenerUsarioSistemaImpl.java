package com.servicios.datos.app.sistema.impl;

import java.sql.ResultSet;



import com.core.app.bd.ConexionExcepcion;
import com.core.app.bd.ConexionJDBC;
import com.core.app.bd.query.Query;
import com.modelo.datos.app.UsuarioSistema;
import com.modelo.datos.mapping.jdbc.sistema.UsuarioSistemaMapeo;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaRespuesta;
import com.servicios.datos.app.sistema.ObtenerUsuarioSistema;
import com.servicios.datos.excepcion.ExcepcionServicioDatos;

public class ObtenerUsarioSistemaImpl implements ObtenerUsuarioSistema {

	
	
	private Query query;
	
	private ConexionJDBC conexion;		

	public ObtenerUsuarioSistemaRespuesta ejecutar(
			ObtenerUsuarioSistemaPeticion peticion) throws ExcepcionServicioDatos{

		ObtenerUsuarioSistemaRespuesta respuesta = null;
		try{
		
		
		conexion.abrir();
		
		query.crearDeclaracion(peticion);
		
		query.prepararDeclaracion(conexion.getConnection(), peticion);
		
		conexion.crearPreparedStatement(query);
		conexion.ejecutarPreparedStamentQuery(query);
		
		ResultSet resultSet = conexion.getResultSet(); 
		
		
		UsuarioSistemaMapeo usuarioSistemaMapeo = new UsuarioSistemaMapeo();
		UsuarioSistema usuarioSistema = null;
		
		while(resultSet.next()){
			usuarioSistema = new UsuarioSistema();
			usuarioSistemaMapeo.mapear(resultSet, usuarioSistema );
		}
		
		
		System.out.println("Imprimiendo informacion de usuario sistema -> " + usuarioSistema);
		
		 respuesta = new ObtenerUsuarioSistemaRespuesta();
		 respuesta.setUsuarioSistema(usuarioSistema);					
				
		
		}catch(Throwable e){
			e.printStackTrace();
			throw new ExcepcionServicioDatos("Ocurrio un error en el servicio ObtenerUsuarioSistema.-" + e.getMessage(), e.getCause()); 
			
		}finally{
			
			try {
				this.conexion.cerrar();
			} catch (ConexionExcepcion e) {
				// TODO Auto-generated catch block
				throw new ExcepcionServicioDatos("Ocurrio un error en el servicio ObtenerUsuarioSistema.-" + e.getMessage(), e.getCause()); 
			}
		}
		
		
		
				
		return respuesta;
	}





	public ConexionJDBC getConexion() {
		return conexion;
	}





	public void setConexion(ConexionJDBC conexion) {
		this.conexion = conexion;
	}





	public Query getQuery() {
		return query;
	}





	public void setQuery(Query query) {
		this.query = query;
	}





	


	
	

}
