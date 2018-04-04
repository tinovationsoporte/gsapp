package com.core.app.report;

import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

class HTMLParams {
	
	private String separator;		

	private Map<String,String> paramMap;
	
	public HTMLParams(String separator) {
		this.separator = separator;
		paramMap = new HashMap<String, String>();
	}
	
	public void addParam(String name, String value){		
		this.addToMap(name, value, 2);	
	}
	
	public void addAttribute(String name, String value){
		this.addToMap(name, value, 1);		
	}
	
	private void addToMap(String key, String value, int type){
		
		switch (type) {
		case 1:			
			key = "rpt_" + key;
			break;
		case 2:
			key = "prm_" + key;
			break;		
		default:
			break;
		}
		
		if(paramMap.containsKey(key)){
			paramMap.remove(key);
		}
		
		paramMap.put(key, value);
	
	}
	
	
	public String generateParams(){
		
		StringBuilder generatedParams = new StringBuilder();
		Iterator<String> iMap = this.paramMap.keySet().iterator();
		while(iMap.hasNext()){			 
			String key = iMap.next();			 
			String value = paramMap.get(key);			 
			generatedParams.append(key)			   
						   .append("=")
						   .append(value);			   
			if(iMap.hasNext()){
				generatedParams.append(this.separator);
			}
		}	
		return generatedParams.toString();
	}

	
	public void clearParams(){		
		paramMap.clear();
	}
	
	public void clearParam(String param){		
		paramMap.remove(param);
	}
	
	
	public static void main(String [] args){
		
		HTMLParams params= new HTMLParams("&amp;");
		
		params.addAttribute("name", "Pagos.rpt");
		params.addAttribute("folder", "cajas");
		
		params.addParam("usuario", "rod");
		params.addParam("cuenta", "1422008319");
		
		System.out.println("Generando parametros 1 ... " + params.generateParams());
		
	
		params.addAttribute("name", "Otro.rpt");
		params.addAttribute("folder", "admon");
		
		params.addParam("usuario", "jose");
		params.addParam("cuenta", "1111");
		
		System.out.println("Generando parametros 2 ... " + params.generateParams());
		
	
	
	}

}

