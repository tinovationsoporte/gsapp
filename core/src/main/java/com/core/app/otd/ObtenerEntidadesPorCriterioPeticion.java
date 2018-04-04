package com.core.app.otd;

import com.core.app.bd.LockMode;
import com.core.app.bd.query.Between;
import com.core.app.bd.query.In;
import com.core.app.modelo.Entidad;

public class ObtenerEntidadesPorCriterioPeticion {

	private Entidad entidad;
	
	private Class clase;
	
	private String orderByAsc;

	private String orderByDesc;
	
	private Between between;
	
	private String [] nullables;
	
	private boolean enablesLike = true;
	
	private In in;
	
	private LockMode lockMode;	

	private String distinct;
	
	
	public String getDistinct() {
		return distinct;
	}

	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}

	public LockMode getLockMode() {
		return lockMode;
	}

	public void setLockMode(LockMode lockMode) {
		this.lockMode = lockMode;
	}

	public In getIn() {
		return in;
	}

	public void setIn(In in) {
		this.in = in;
	}

	public Between getBetween() {
		return between;
	}

	public boolean isEnablesLike() {
		return enablesLike;
	}

	public void setEnablesLike(boolean enablesLike) {
		this.enablesLike = enablesLike;
	}

	public String[] getNullables() {
		return nullables;
	}

	public void setNullables(String[] nullables) {
		this.nullables = nullables;
	}

	public void setBetween(Between between) {
		this.between = between;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Class getClase() {
		return clase;
	}

	public void setClase(Class clase) {
		this.clase = clase;
	}

	public String getOrderByAsc() {
		return orderByAsc;
	}

	public void setOrderByAsc(String orderByAsc) {
		this.orderByAsc = orderByAsc;
	}

	public String getOrderByDesc() {
		return orderByDesc;
	}

	public void setOrderByDesc(String orderByDesc) {
		this.orderByDesc = orderByDesc;
	}

	
	
	

}
