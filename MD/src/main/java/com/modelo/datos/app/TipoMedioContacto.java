package com.modelo.datos.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="TipoMedioContacto")
@DiscriminatorValue("medio_contacto")
public class TipoMedioContacto extends Catalogo {	

}
