package com.core.app.report;

public class URLReports implements Cloneable{

	
	private String url;
	private String params;
	private String codedParams;
	private String decodedParams;
	
	HTMLParams htmlParams;
	
	public URLReports(String url) {
		this.url = url;
		this.htmlParams = new HTMLParams("&");
	}
	
	private void addAttribute(String name, String value){
		
		this.htmlParams.addAttribute(name, value);
	}
	
	public void addParam(String name, String value){
		
		this.htmlParams.addParam(name, value);
	}
	
	public void removeParam(String param){
		
		this.htmlParams.clearParam(param);
	}
	
	public void removeParams(){
		
		this.htmlParams.clearParams();
	}
	
	
	public void addReportName(String name){
		this.addAttribute("nombre", name);
	}
	
	public void addReportFolder(String folder){
		this.addAttribute("folder", folder);
	}
	
	
	public void addReportFormat(String name){
		this.addAttribute("formato", name);
	}
	
	
	public String createURL(){
		
		return this.url + this.htmlParams.generateParams();
	}
	
	public void codeParams(){}
	public void decodeParams(){}
	
	public static void main(String [] args){
		
		
		URLReports urlReports = new URLReports("http://localhost:9090/EjemploReporte/ReporteServlet?");
		
		urlReports.addReportName("Pagos.rpt");
		urlReports.addReportFolder("cajas");
		
		urlReports.addParam("usuario", "rod");
		urlReports.addParam("cuenta", "1422008319");
		
		System.out.println("Generando parametros 1 ... " + urlReports.createURL());
		
	
		urlReports.addReportName("Otro.rpt");
		urlReports.addReportFolder("admon");
		
		urlReports.addParam("usuario", "jose");
		urlReports.addParam("cuenta", "1111");
		
		System.out.println("Generando parametros 2 ... " + urlReports.createURL());
		
		
		
		urlReports.addReportName("MyReport.rpt");
		urlReports.addReportFolder("admon");
		urlReports.addReportFormat("pdf");
		
		urlReports.addParam("usuario", "psamr");
		urlReports.addParam("cuenta", "000000");
		
		System.out.println("Generando parametros 3 ... " + urlReports.createURL());

	}


}
