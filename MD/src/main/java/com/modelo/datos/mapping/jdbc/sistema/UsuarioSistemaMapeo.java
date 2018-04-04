package com.modelo.datos.mapping.jdbc.sistema;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.app.bd.ExcepcionMapeo;
import com.core.app.bd.MapeadorRegistro;
import com.modelo.datos.app.Rol;
import com.modelo.datos.app.UsuarioSistema;

public class UsuarioSistemaMapeo implements MapeadorRegistro<UsuarioSistema> {

	@Override
	public void mapear(ResultSet rs, UsuarioSistema entidad)
			throws ExcepcionMapeo {
		// TODO Auto-generated method stub
		try {		
	      
			  Rol rol = new Rol();	   
			  
			  entidad.setIdUsuarioSistema(rs.getInt("idUsuarioSistema"));
			  entidad.setLogin(rs.getString("login"));
			  entidad.setPassword(rs.getString("password"));
			  entidad.setFechaCaptura(rs.getDate("fechaCaptura"));
			  entidad.setStatus(rs.getInt("status"));
			  entidad.setNombre(rs.getString("nombre"));
		      
		      rol.setIdRol(rs.getInt("idRol"));
		      rol.setDescripcion(rs.getString("descripcion"));
		      rol.setClave(rs.getString("clave"));
		      entidad.setRol(rol);
			
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ExcepcionMapeo("Ocurrio un error en el mapeo de UsuarioSistemaMapeo. -" + e.getMessage(), e.getCause());
		}
		
	}

}
