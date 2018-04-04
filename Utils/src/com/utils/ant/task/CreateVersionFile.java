package com.utils.ant.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class CreateVersionFile extends Task {
	private String deployDate;
	private String version;
	private String dirFile;
	
	public void execute() throws BuildException{
		Properties prop = new Properties();
		 
    	try {
    		//set the properties value
    		prop.setProperty("fechaDeploy", deployDate);
    		prop.setProperty("version", version);    		
 
    		//save properties to project root folder
    		prop.store(new FileOutputStream(dirFile.concat("/version.properties")), null);
    		log("Archivo Version Creado.", Project.MSG_INFO);
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		log("Error al crear el archivo: " + ex, Project.MSG_ERR);
        }
	}

	public String getDeployDate() {
		return deployDate;
	}

	public void setDeployDate(String deployDate) {
		this.deployDate = deployDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDirFile() {
		return dirFile;
	}

	public void setDirFile(String dirFile) {
		this.dirFile = dirFile;
	}
	
	
}
