package com.servicios.negocio.app.comun;

import com.core.app.servicios.ServicioPeticionRespuesta;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.objetos.transf.datos.app.comun.DecodificarPeticion;
import com.objetos.transf.datos.app.comun.DecodificarRespuesta;

public interface Decodificar extends ServicioPeticionRespuesta<CodificarPeticion, CodificarRespuesta> {

}
