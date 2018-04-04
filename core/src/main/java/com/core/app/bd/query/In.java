package com.core.app.bd.query;

public class In {


	private boolean isNotIn;
	
	private String field;
	
	private Object[] values;

	
	public In (){
		super();
	}
	
	
	public In(boolean isNotIn, String field, Object[] values) {
		super();
		this.isNotIn = isNotIn;
		this.field = field;
		this.values = values;
	}

	public boolean isNotIn() {
		return isNotIn;
	}

	public void setNotIn(boolean isNotIn) {
		this.isNotIn = isNotIn;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	


}
