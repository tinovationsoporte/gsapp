import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.servicios.negocio.app.comun.Codificar;
import com.servicios.negocio.app.comun.Decodificar;
import com.servicios.negocio.app.comun.impl.DecriptacionAESImpl;
import com.servicios.negocio.app.comun.impl.EncriptacionAESImpl;

public class EncriptacionTest {

	
	
	public static void main(String ...args){
		
		try {
			Codificar codificar = new EncriptacionAESImpl();
			
			
			Decodificar decodificar = new DecriptacionAESImpl();
			
			String llave = "estaeslallavemagica...";
			
			CodificarPeticion codificarPeticion = new CodificarPeticion();
			codificarPeticion.setLlave(llave);
			codificarPeticion.setMensaje("rptName=rptFolio.rpt&idFolio=1&key=AAABB5564");
			 
			CodificarRespuesta codificarRespuesta = null;
			 
			
			codificarRespuesta = codificar.ejecutar(codificarPeticion);
		
			System.out.println("codificarPeticion.llave -> "+ codificarPeticion.getLlave());
			System.out.println("codificarPeticion.mensaje -> " + codificarPeticion.getMensaje());
			System.out.println("codificarRespuesta.codificado -> " + codificarRespuesta.getCodificado());
			
			
			System.out.println("\n\n**************************************************************************************\n\n");

			CodificarPeticion decodificarPeticion = new CodificarPeticion();
			decodificarPeticion.setLlave(llave);
			decodificarPeticion.setMensaje(codificarRespuesta.getCodificado());
			CodificarRespuesta decodificarRespuesta = decodificar.ejecutar(decodificarPeticion);
			
			System.out.println("decodificarPeticion.llave -> "+ decodificarPeticion.getLlave());
			System.out.println("decodificarPeticion.mensaje -> " + decodificarPeticion.getMensaje());
			System.out.println("decodificarRespuesta.codificado -> " + decodificarRespuesta.getCodificado());
			
		} catch (ExcepcionServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
