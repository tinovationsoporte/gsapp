package com.servicios.negocio.app.comun.impl;

import java.util.Date;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.ValidarFechaPeticion;
import com.objetos.transf.datos.app.comun.ValidarFechaRespuesta;
import com.servicios.negocio.app.comun.ValidarFecha;

public class ValidarFechaImpl implements ValidarFecha{

	
	@Override
	public ValidarFechaRespuesta ejecutar(
			ValidarFechaPeticion peticion)throws ExcepcionServicio {
	
		ValidarFechaRespuesta respuesta = new ValidarFechaRespuesta();
		respuesta.setResultado(false);
		try{
			if(peticion.getFecha1()!=null && peticion.getFecha2()!=null && (peticion.getCondicion()!=null && !peticion.getCondicion().trim().isEmpty())){
			
				if(peticion.getCondicion().equals("==")){
					if(peticion.getFecha1().equals(peticion.getFecha2())){
						respuesta.setResultado(true);
				
					}
				}	
				if(peticion.getCondicion().equals("<")){
					if(peticion.getFecha1().before(peticion.getFecha2())){
						respuesta.setResultado(true);
					}	
				}
				if(peticion.getCondicion().equals(">")){
					if(peticion.getFecha1().after(peticion.getFecha2())){
						respuesta.setResultado(true);
					}
				}
				if(peticion.getCondicion().equals("<=") || peticion.getCondicion().equals(">=") || peticion.getCondicion().equals("!=")){
					int resultadoComparacion = peticion.getFecha1().compareTo(peticion.getFecha2());
					if(peticion.getCondicion().equals("<=")){
						if(resultadoComparacion <= 0){
							respuesta.setResultado(true);
						}
					}
					if(peticion.getCondicion().equals(">=")){
						if(resultadoComparacion >= 0){
							respuesta.setResultado(true);
						}
					}
					if(peticion.getCondicion().equals("!=")){
						if(resultadoComparacion != 0){
							respuesta.setResultado(true);
						}
					}
				}
			
			}else{
				respuesta.setResultado(false);
				System.out.println("Alguno de los valores no es valido");
			}
			
			
		}catch(NullPointerException e){
			System.out.println("No se ha ingresado una cantidad valida");
		}
		return respuesta;
	}

	public static void main(String[] args) {
		
		ValidarFechaImpl dato =new ValidarFechaImpl();
		ValidarFechaPeticion peticion=new ValidarFechaPeticion();
		peticion.setFecha1(new Date());
		peticion.setFecha2(new Date());
		peticion.setCondicion(" ");
		
		
		try {
			ValidarFechaRespuesta respuesta=dato.ejecutar(peticion);
			System.out.println(respuesta.getResultado());
		} catch (ExcepcionServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

