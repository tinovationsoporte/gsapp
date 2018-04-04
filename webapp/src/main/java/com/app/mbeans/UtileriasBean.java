package com.app.mbeans;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.core.app.mbeans.MBeanAbstracto;

@ManagedBean(name="utileriasBean")
@ViewScoped
public class UtileriasBean extends MBeanAbstracto {

	private String urlGuiaDoc;
	private String urlGuiaHtml;
	
	@PostConstruct
	public void inicializar(){
		
		AplicacionBean app = (AplicacionBean)getManagedBean("#{aplicacion}", AplicacionBean.class);
		
		urlGuiaHtml = app.getUrlAyuda() + "SGS_Guia_Rapida_de_Usuario_V1.htm";
		urlGuiaDoc = app.getUrlAyuda() + "SGS_Guia_Rapida_de_Usuario_V1.docx";
	}
	
	public void descargarGuiaRapida(ActionEvent e){
		RequestContext.getCurrentInstance().execute("openHelp('"+ urlGuiaDoc +"')");

	}
	
	public void verGuiaRapida(ActionEvent e){
		RequestContext.getCurrentInstance().execute("openHelp('"+ urlGuiaHtml +"')");
	}
}
