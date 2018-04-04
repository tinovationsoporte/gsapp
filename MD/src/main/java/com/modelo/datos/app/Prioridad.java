package com.modelo.datos.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Prioridad")
@DiscriminatorValue("prioridad")
public class Prioridad extends Catalogo{

}
