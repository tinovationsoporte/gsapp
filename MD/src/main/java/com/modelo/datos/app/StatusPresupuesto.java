package com.modelo.datos.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="StatusPresupuesto")
@DiscriminatorValue("status_presupuesto")
public class StatusPresupuesto extends Catalogo {

	

}
