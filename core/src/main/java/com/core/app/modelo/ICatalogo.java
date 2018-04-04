package com.core.app.modelo;

public interface ICatalogo {

	
	
	//Attr idTabla
	Integer getIdCatalogo();
	void setIdCatalogo(Integer id);	
	
	//Attr id
	Integer getId();
	void setId(Integer id);
	
	//Attr clave
	String getClave();
	void setClave(String clave);

	//Attr descripcion
	String getDescripcion();
	void setDescripcion(String descripcion );
	
	Class getClazz();	
	
	Entidad getEntidad();

}
