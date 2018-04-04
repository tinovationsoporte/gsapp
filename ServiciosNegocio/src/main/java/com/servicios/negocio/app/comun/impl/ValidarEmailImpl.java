package com.servicios.negocio.app.comun.impl;

import org.apache.commons.validator.routines.EmailValidator;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.ValidarEmailPeticion;
import com.objetos.transf.datos.app.comun.ValidarEmailRespuesta;
import com.servicios.negocio.app.comun.ValidarEmail;

public class ValidarEmailImpl implements ValidarEmail {

	@Override
	public ValidarEmailRespuesta ejecutar(ValidarEmailPeticion peticion)
			throws ExcepcionServicio {
		
		ValidarEmailRespuesta respuesta = new ValidarEmailRespuesta();
		
		respuesta.setResultado( EmailValidator.getInstance().isValid( peticion.getEmail() ));
		
		respuesta.setMsge( respuesta.getResultado() ? "e-mail válido." : "e-mail inválido");
		
		return respuesta;	
	}



	public static void main(String [] args){
		
		ValidarEmailPeticion peticion = new ValidarEmailPeticion();
		peticion.setEmail("ppsanr._.12@hotmail.com");
		
		ValidarEmailImpl validarEmail = new ValidarEmailImpl();
		try {
			ValidarEmailRespuesta respuesta = validarEmail.ejecutar(peticion);
			
			System.out.println("email -> " + peticion.getEmail());
			System.out.println("resultado -> " + respuesta.getResultado());
			System.out.println("mensaje -> " + respuesta.getMsge());		
		
		} catch (ExcepcionServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
