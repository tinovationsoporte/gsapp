package com.core.app.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MapeadorRegistro <E>{
	public void mapear(ResultSet rs, E entidad ) throws ExcepcionMapeo;
}
