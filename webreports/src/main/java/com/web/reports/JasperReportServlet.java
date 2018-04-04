package com.web.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.DecriptacionAESImpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;

/**
 * Servlet implementation class JasperReportServlet
 */
public class JasperReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public JasperReportServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try{
			processReport(request, response);
		}catch(Exception e){
			
			throw new ServletException("Ocurrion un error en el servlet de reportes. " + e.getMessage() , e);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		throw new ServletException("POST is not supported.");
	}
	
	
	
	private void processReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Connection conn = null;
		try{
			
			System.out.println("***\n\n\nprocessReport() ...");
			
			Map<String,Object> rptParams = null; 
			
			System.out.println("***\n\n\nENABLE_GET_ENCRIPTION ->" + getServletContext().getInitParameter("ENABLE_GET_ENCRIPTION").equals("TRUE"));
			
			if(getServletContext().getInitParameter("ENABLE_GET_ENCRIPTION").equals("TRUE")){
				rptParams = generateDecodedParameters(request);
			}else{
				rptParams = generateParameters(request);
			}		
					
					
			
			
			System.out.println("rptParams -> " + rptParams);
			
			JasperReport jasperReport = getCompiledFile(rptParams.get("rptName").toString(), request);
			
			conn = createConnection();
			
			System.out.println("conn -> " + conn);

			
			if(rptParams.get("rptFormat").equals("html")){
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, rptParams, conn);
				
				generateReportHtml(jasperPrint, request, response); // For HTML report
			}else{
				
				if(rptParams.get("rptFormat").equals("pdf")){
					
					generateReportPDF(response, rptParams, jasperReport, conn); // For PDF report
	
				}else{
					
					throw new Exception("El formato del reporte no es valido");
				}				
			}
		}catch(Exception e){
		
			throw new Exception("Ocurrio un error al procesar el reporte. " + e.getMessage(), e );
			
		}finally{			
			
			/*if(!conn.isClosed()){				
				conn.close();
			}*/
		}		
	}
	
	
	
	

	private JasperReport getCompiledFile(String fileName, HttpServletRequest request) throws JRException {
		
		System.out.println("fileName -> " + fileName);
		
		
		
		String reportsFolderPath = "\\WEB-INF\\classes\\reports\\";
		
		String realPath = request.getSession().getServletContext().getRealPath("/") + reportsFolderPath;
		
		String compildedReportPath = realPath + fileName + ".jasper";
		
		String jrxmlReportPath = realPath + fileName + ".jrxml";

		
		System.out.println("realPath -> " + realPath);
		
		System.out.println("compildedReportPath -> " + compildedReportPath);
		System.out.println("jrxmlReportPath -> " + jrxmlReportPath);
		
		
		File reportFile = new File( compildedReportPath);
		
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
               JasperCompileManager.compileReportToFile(jrxmlReportPath,compildedReportPath);
        }
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
       
	   return jasperReport;
    } 
 
    private void generateReportHtml( JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws IOException, JRException {
        HtmlExporter exporter=new HtmlExporter();
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(jasperPrint);
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput( new SimpleHtmlExporterOutput(resp.getWriter()));
        SimpleHtmlReportConfiguration configuration =new SimpleHtmlReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
 
    }
 
    private void generateReportPDF (HttpServletResponse resp, Map<String,Object> parameters, JasperReport jasperReport, Connection conn)throws JRException, NamingException, SQLException, IOException {
        byte[] bytes = null;
        bytes = JasperRunManager.runReportToPdf(jasperReport,parameters,conn);
        resp.reset();
        resp.resetBuffer();
        resp.setContentType("application/pdf");
        resp.setContentLength(bytes.length);
        ServletOutputStream ouputStream = resp.getOutputStream();
        ouputStream.write(bytes, 0, bytes.length);
        ouputStream.flush();
        ouputStream.close();
    } 
	
    
    private Map<String,Object> generateParameters(HttpServletRequest request){
    	
		System.out.println("***\n\n\ngenerateParameters() ...");

    	
    	Map<String, Object>resultMap = new HashMap<String, Object>();
    	
    	Enumeration<String> nombresParam = request.getParameterNames();
    	
    	while(nombresParam.hasMoreElements()){
    		
    		String parametro = nombresParam.nextElement();
    		
    		resultMap.put(parametro, request.getParameter(parametro));
    	}   	
    	
    	return resultMap;
    }
    
    private Map<String, Object> generateDecodedParameters(HttpServletRequest request) throws Exception {
		
		System.out.println("***\n\n\ngenerateDecodedParameters() ...");

    	
    	String encriptedMessage = request.getParameter("values");
		String key = getServletContext().getInitParameter("AESKEY");
		
		
		System.out.println("***\n\n\nencriptedMessage ->" + encriptedMessage);
		System.out.println("***\n\n\nkey ->" + key);
		
		DecriptacionAESImpl decrip = new DecriptacionAESImpl();
		
		String decriptedParams = decrip.decode(encriptedMessage, key);
		
		System.out.println("***\n\n\ndecriptedParams ->" + decriptedParams);

		
		String [] params = decriptedParams.split("&");
		
		Map<String,Object> procesedParams = new HashMap<String,Object>();
		
		for(String param:params){
			
			String values [] = param.split("=");
			
			procesedParams.put(values[0], values[1]);
		}
		
		
		
		return procesedParams;
	}

    
    private Connection createConnection() throws Exception{
    	
		Connection conn = null;

		try{
			
			Class.forName("com.mysql.jdbc.Driver");    	
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionsocial","admin","admin");
			
		}catch(Exception e){
			
			throw new Exception("Ocurrio un error al crear la conexion del reporte." + e.getMessage(), e);
		}	
    	
    	return conn;
    }

}
