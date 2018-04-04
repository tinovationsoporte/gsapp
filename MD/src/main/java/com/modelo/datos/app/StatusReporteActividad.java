package com.modelo.datos.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="StatusReporteActividad")
@DiscriminatorValue("status_reporte_actividad")
public class StatusReporteActividad extends Catalogo {

}
