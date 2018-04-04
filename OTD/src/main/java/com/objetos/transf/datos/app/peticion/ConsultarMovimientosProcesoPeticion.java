package com.objetos.transf.datos.app.peticion;

import java.util.ArrayList;
import java.util.List;

public class ConsultarMovimientosProcesoPeticion {

	private Integer idPeticion;
	private Integer idProcesoPeticion;
	private String movimiento;
	private List<Integer> listaIdsStatusPeticion = new ArrayList<Integer>();

	
	public Integer getIdPeticion() {
		return idPeticion;
	}
	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}
	public Integer getIdProcesoPeticion() {
		return idProcesoPeticion;
	}
	public void setIdProcesoPeticion(Integer idProcesoPeticion) {
		this.idProcesoPeticion = idProcesoPeticion;
	}
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}	
	public void addIdStatusPeticion(Integer idProcesoPeticion){
		listaIdsStatusPeticion.add(idProcesoPeticion);
	}
	public List<Integer> getListaIdsStatusPeticion() {
		return listaIdsStatusPeticion;
	}
	public void setListaIdsStatusPeticion(List<Integer> listaIdsStatusPeticion) {
		this.listaIdsStatusPeticion = listaIdsStatusPeticion;
	}
	
	
}
