package com.objetos.transf.datos.app.comun;

public class ValidarCadenasSimilaresRespuesta {
	private Integer resultadoComputo;
	private boolean existeCadenaSimilar; // Solo aplica cuando se valido un catalogo completo
	
	public Integer getResultadoComputo() {
		return resultadoComputo;
	}

	public void setResultadoComputo(Integer resultadoComputo) {
		this.resultadoComputo = resultadoComputo;
	}

	public boolean getExisteCadenaSimilar() {
		return existeCadenaSimilar;
	}

	public void setExisteCadenaSimilar(boolean existeCadenaSimilar) {
		this.existeCadenaSimilar = existeCadenaSimilar;
	}
	
}
