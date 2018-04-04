package com.core.app.bd.query;

public  class QueryUtil {

	public static boolean esNullOVacio(String cadena){	
		return ( cadena == null | cadena.equals("") ) ? true: false ;  
	}
	
	public static boolean esNull(Object objeto){		
		return objeto == null ? true: false ;
	}	
	
	public static String evaluaElementoYConcatenaACadena(Object evaluar, String cadenaAGenerar, String marcador1 , String marcador2){		
		boolean eval = false ;		
		eval = evaluar instanceof String ? QueryUtil.esNullOVacio((String)evaluar) : QueryUtil.esNull(evaluar);		
		return !eval ? cadenaAGenerar + marcador1 + evaluar.toString() + marcador2: "";
	}	
	
	public static String evaluaElementoYGeneraCadena(Object evaluar, String cadenaAGenerar){		
		boolean eval = false ;				
		eval = evaluar instanceof String ? QueryUtil.esNullOVacio((String)evaluar) : QueryUtil.esNull(evaluar);		
		return !eval ? cadenaAGenerar + evaluar.toString() : "";
	}
	
	public static String evaluaElementoYDevuelveCondicion(Object evaluar, String condicion){
		boolean eval = false ;				
		eval = evaluar instanceof String ? QueryUtil.esNullOVacio((String)evaluar) : QueryUtil.esNull(evaluar);		
		return !eval ? condicion : "";
	}
	
	public static String evaluaElementoYDevuelveCondicion(Object evaluar, String condicion, Boolean concatenaCadena){
		boolean eval = false ;
		String coma = "";
		eval = evaluar instanceof String ? QueryUtil.esNullOVacio((String)evaluar) : QueryUtil.esNull(evaluar);
		if(concatenaCadena)
			coma = ",";
		return !eval ? (coma + condicion) : "";
	}
	
	public static boolean elementoVacioONulo(Object evaluar){
		boolean eval = false ;				
		eval = evaluar instanceof String ? QueryUtil.esNullOVacio((String)evaluar) : QueryUtil.esNull(evaluar);		
		return eval;
	}
}
