package com.modelo.datos.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="TipoEntidadMpal")
@DiscriminatorValue("tipo_entidad_mapl")
public class TipoEntidadMpal extends Catalogo{

	public TipoEntidadMpal() {
		// TODO Auto-generated constructor stub
	}

}
