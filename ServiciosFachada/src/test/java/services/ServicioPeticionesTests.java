package services;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.modelo.datos.app.Archivo;
import com.modelo.datos.app.Peticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.ConsultarPeticionesRespuesta;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesPeticion;
import com.objetos.transf.datos.app.peticion.RegistrarPeticionesRespuesta;
import com.servicios.app.peticion.ServicioPeticiones;
import com.servicios.excepcion.ExcepcionServicioFachada;

import parent.SpringAbstractTest;

public class ServicioPeticionesTests extends SpringAbstractTest{

	public ServicioPeticionesTests(){
		
		super.initSpringContext();
	}
	
	private ServicioPeticiones servicioPeticiones;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		servicioPeticiones = (ServicioPeticiones) getBean("servicioPeticiones");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegistrarPeticion() {
	
		RegistrarPeticionesPeticion peticion = new RegistrarPeticionesPeticion();
		peticion.setSolicitante("Pedro Chavez");
		peticion.setDireccion("Rio Colorado 2 Col. Revolucion");
		peticion.setDescripcion("Se requiere material de curacion en consultorios de apoyo");
		peticion.setIdArea(1);
		peticion.setIdCategoria(1);
		peticion.setIdEntidadMpal(1);
		peticion.setIdPrioridad(5);
		peticion.setIdTipoMedioContacto(1);
		peticion.setMedioConcato("2299803256");
		peticion.setObservaciones("Tampoco hay antibioticos");
		peticion.setIdUsuario(2);
		peticion.setRequierePresupuesto(1);
		
		
		RegistrarPeticionesRespuesta respuesta = null;
		try {
			
			peticion.setListaArchivos(crearListaArchivos());
			respuesta = servicioPeticiones.registrarPeticiones(peticion);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue("respuesta es null", respuesta != null);
		assertTrue("idPeticion es null", respuesta.getIdPeticion() != null);
		assertTrue("folio es null", respuesta.getFolio() != null);
		assertTrue("fecha captura es null", respuesta.getFechaCaptura() != null);
		
	}

	
	@Test
	public void consultarPeticionesTest(){
		
		
		ConsultarPeticionesRespuesta respuesta = null;
		try{
			ConsultarPeticionesPeticion peticion = new ConsultarPeticionesPeticion();
			respuesta = servicioPeticiones.consultarPeticiones(peticion );
		}catch(Exception e ){
			e.printStackTrace();
		}
		assertTrue("respuesta es null", respuesta != null);
		assertTrue("dasboardList.size es 0", respuesta.getPeticiones().size() != 0);	
		
		System.out.println("peticionesList.size -> " + respuesta.getPeticiones().size());
	}
	
	
	private List<Archivo> crearListaArchivos() throws Exception{
		
		List<Archivo> listaArchivos = new ArrayList<Archivo>();
		Archivo archivo = new Archivo();
		
		archivo.setNombre("imageTest1.jpg");
		archivo.setRuta("NA");
		archivo.setBlob(Files.readAllBytes(new File("C:\\imagenes\\imagenTest1.jpg").toPath()));
		listaArchivos.add(archivo);
		
		archivo = new Archivo();
		
		archivo.setNombre("MPCEIK.pdf");
		archivo.setRuta("NA");
		archivo.setBlob(Files.readAllBytes(new File("C:\\imagenes\\MPCEIK.pdf").toPath()));
		listaArchivos.add(archivo);	 
		 
		
		return listaArchivos;
	}
}
