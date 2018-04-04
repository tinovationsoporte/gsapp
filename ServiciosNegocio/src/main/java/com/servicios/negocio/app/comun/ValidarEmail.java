package com.servicios.negocio.app.comun;

import com.core.app.servicios.ServicioPeticionRespuesta;
import com.objetos.transf.datos.app.comun.ValidarEmailPeticion;
import com.objetos.transf.datos.app.comun.ValidarEmailRespuesta;

public interface ValidarEmail extends
		ServicioPeticionRespuesta<ValidarEmailPeticion, ValidarEmailRespuesta> {

}
