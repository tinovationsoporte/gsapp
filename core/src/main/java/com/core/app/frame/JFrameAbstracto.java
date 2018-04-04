package com.core.app.frame;

import java.awt.HeadlessException;
import java.util.Map;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;

public abstract class JFrameAbstracto extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -557607955217584585L;

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

	public JFrameAbstracto(ApplicationContext applicationContext,
			Map<String, Object> info) throws HeadlessException {
		super();
		this.applicationContext = applicationContext;
		this.info = info;
	}
	
	public JFrameAbstracto(ApplicationContext applicationContext) throws HeadlessException {
		super();
		this.applicationContext = applicationContext;
		
	}
	
	public JFrameAbstracto(){
		super();
	}
	
	
	public abstract void cargarMenu();
	public abstract void cargarInfo();
	public abstract void cerrar();
	public abstract void abrir();
}
