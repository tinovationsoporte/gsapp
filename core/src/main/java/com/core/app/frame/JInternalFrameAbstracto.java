package com.core.app.frame;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JInternalFrame;

import org.springframework.context.ApplicationContext;

import com.core.app.ExcepcionAplicacion;
import com.core.app.report.URLReports;

public class JInternalFrameAbstracto extends JInternalFrame {

	
	
	private ApplicationContext applicationContext;
	
	private Map<String,Object> info;
	 
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public JInternalFrameAbstracto(ApplicationContext applicationContext,
			Map<String, Object> info) throws HeadlessException {
		super();
		this.applicationContext = applicationContext;
		this.info = info;
	}

	
    public String getAppProperty(String property ){
		
		Properties prop = new Properties();
		InputStream input = null;
		String result = null;
		
		try {
	 
			String filename = "config.properties";
            input = JInternalFrameAbstracto.class.getClassLoader().getResourceAsStream(filename);
            
            if(input==null){
                 throw new IOException("Unable to find file config.properties");
                            
            }

            //load a properties file from class path, inside static method
            prop.load(input);
 			
			result = prop.getProperty(property);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
                            try {
                                input.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
			}
		}
		
		return result;
	}

    
    public void openReportInBrowser(URLReports urlReports) {   
    	
    
    	
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(urlReports.createURL()));

            } catch (URISyntaxException e) {
                
                e.printStackTrace();
                
            } catch (IOException ex) {
                
            	ex.printStackTrace();
            }
        }else{
        	
        	try {
				throw new ExcepcionAplicacion("Desktop not Supported", null);
			} catch (ExcepcionAplicacion e) {
				e.printStackTrace();
			}
        	
        }

    }
 
    public static void main(String args[]){  
        Runtime rt = Runtime.getRuntime();  
        try{  
        Process clientProcess = rt.exec(new String[] {"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe","-new-window" ,"coderanch.com"});  
        clientProcess.waitFor();  
        } catch (Exception e){  
            e.printStackTrace();  
        }  
    }  
    
    
}
