package com.core.app.modelo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public abstract class Entidad {

	/*
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	 
	*/
	@Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }


    @Override
    public boolean equals(Object object) {
    	// TODO Auto-generated method stub
    	return EqualsBuilder.reflectionEquals(this,object , true);
    }


}
