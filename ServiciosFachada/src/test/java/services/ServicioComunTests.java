package services;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.objetos.transf.datos.app.comun.RegistrarImagenPeticion;
import com.servicios.app.comun.ServicioComun;
import com.servicios.app.dashboard.ServicioDashboard;
import com.servicios.excepcion.ExcepcionServicioFachada;

import parent.SpringAbstractTest;

public class ServicioComunTests extends SpringAbstractTest{

	private ServicioComun servicioComun;
	
	public ServicioComunTests(){
		
		super.initSpringContext();
	}
	
	
	@Before
	public void setUp() throws Exception {
	
		servicioComun = (ServicioComun) getBean("servicioComun");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRegistrarImagen() {
			
		try {
		
			RegistrarImagenPeticion peticion = new RegistrarImagenPeticion();
			
			File imagenInicial = new File("C:\\imagenes\\imagenTest1.jpg");
			
			
			BufferedImage originalImage = ImageIO.read(imagenInicial);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ImageIO.write( originalImage, "jpg", baos );
			
			baos.flush();
			
			byte[] imageEnBytes = baos.toByteArray();
			
			baos.close();
			
			
			peticion.setFolderDestino("C:\\imagenes\\");
			
			peticion.setNombreImagen("imagenTest2.jpg");
			
			peticion.setImagenBytes(imageEnBytes);	
			
			servicioComun.RegistrarImagen(peticion);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail("Ocurrio un error");
		}
	
	
	}

}
