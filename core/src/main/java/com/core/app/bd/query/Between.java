package com.core.app.bd.query;

public class Between {

	
	private String field;
	
	private Object from;
	
	private Object to;

	
	
	
	public Between() {
		super();
	}

	public Between(String field, Object from, Object to) {
		super();
		this.field = field;
		this.from = from;
		this.to = to;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getFrom() {
		return from;
	}

	public void setFrom(Object from) {
		this.from = from;
	}

	public Object getTo() {
		return to;
	}

	public void setTo(Object to) {
		this.to = to;
	}
	
	
	
}
