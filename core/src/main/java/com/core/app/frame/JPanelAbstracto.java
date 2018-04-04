package com.core.app.frame;

import java.awt.HeadlessException;
import java.util.Map;

import javax.swing.JPanel;

import org.springframework.context.ApplicationContext;

public class JPanelAbstracto extends JPanel {

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

	public JPanelAbstracto(ApplicationContext applicationContext,
			Map<String, Object> info) throws HeadlessException {
		super();
		this.applicationContext = applicationContext;
		this.info = info;
	}






}
