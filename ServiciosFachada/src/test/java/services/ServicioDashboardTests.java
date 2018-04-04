package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.objetos.transf.datos.app.dashboard.ConsultarDashboardPeticion;
import com.objetos.transf.datos.app.dashboard.ConsultarDashboardRespuesta;
import com.servicios.app.dashboard.ServicioDashboard;
import com.servicios.excepcion.ExcepcionServicioFachada;

import parent.SpringAbstractTest;

public class ServicioDashboardTests extends SpringAbstractTest{

	public ServicioDashboardTests(){
		
		super.initSpringContext();
	}
	
	private ServicioDashboard servicioDashboard;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		servicioDashboard = (ServicioDashboard) getBean("servicioDashboard");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConsultarDashboard() {

	    ConsultarDashboardPeticion peticion = new ConsultarDashboardPeticion();
	    peticion.setIdRol(3);
	    //peticion.setIdArea(1);
	
		ConsultarDashboardRespuesta respuesta = null;
		try {
			respuesta = servicioDashboard.consultarDashboard(peticion);	
			
		} catch (ExcepcionServicioFachada e) {
			e.printStackTrace();
		}
		
		assertTrue("respuesta es null", respuesta != null);
		assertTrue("dasboardList.size es 0", respuesta.getDashboardList().size() != 0);	
		
		System.out.println("dasboardList.size -> " + respuesta.getDashboardList().size());
	}

}
