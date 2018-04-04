package com.servicios.datos.app.sistema.impl;

import java.util.List;

import com.core.app.otd.ObtenerEntidadesPorCriterioPeticion;
import com.core.app.otd.ObtenerEntidadesPorCriterioRespuesta;
import com.core.app.servicios.ExcepcionServicio;
import com.core.app.servicios.crud.ServicioObtenerEntidadesPorCriterio;
import com.modelo.datos.app.UsuarioSistema;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaPeticion;
import com.objetos.transf.datos.app.sistema.ObtenerUsuarioSistemaRespuesta;
import com.servicios.datos.app.sistema.ObtenerUsuarioSistema;
import com.servicios.datos.excepcion.ExcepcionServicioDatos;

public class ObtenerUsuarioSistemaJPAImpl implements ObtenerUsuarioSistema{

	private ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio;
	
	@Override
	public ObtenerUsuarioSistemaRespuesta ejecutar(ObtenerUsuarioSistemaPeticion peticion) throws ExcepcionServicio {
		
		ObtenerEntidadesPorCriterioPeticion obtenerEntidadesPeticion = new ObtenerEntidadesPorCriterioPeticion();
		obtenerEntidadesPeticion.setClase(UsuarioSistema.class);
		
		UsuarioSistema usuarioSistema = new UsuarioSistema();
		usuarioSistema.setLogin(peticion.getNombreUsuario());
		usuarioSistema.setPassword(peticion.getContrasena());
		obtenerEntidadesPeticion.setEntidad(usuarioSistema);
		
		ObtenerEntidadesPorCriterioRespuesta obtenerEntidadesRespuesta = obtenerEntidadesPorCriterio.ejecutar(obtenerEntidadesPeticion);
		
		List<UsuarioSistema> listaUsuarios = obtenerEntidadesRespuesta.getListaEntidades();
		
		ObtenerUsuarioSistemaRespuesta respuesta = new ObtenerUsuarioSistemaRespuesta();
		
		if(listaUsuarios != null && listaUsuarios.size() > 1){
			
			throw new ExcepcionServicioDatos("Existe mas de un usuario con las mismo nombre y contraseña.", null);
		}		
		
		if(listaUsuarios != null && listaUsuarios.size() == 1){
			respuesta.setUsuarioSistema(listaUsuarios.get(0));
		}		
		
		return respuesta;
	}

	public ServicioObtenerEntidadesPorCriterio getObtenerEntidadesPorCriterio() {
		return obtenerEntidadesPorCriterio;
	}

	public void setObtenerEntidadesPorCriterio(ServicioObtenerEntidadesPorCriterio obtenerEntidadesPorCriterio) {
		this.obtenerEntidadesPorCriterio = obtenerEntidadesPorCriterio;
	}
	
	

}
