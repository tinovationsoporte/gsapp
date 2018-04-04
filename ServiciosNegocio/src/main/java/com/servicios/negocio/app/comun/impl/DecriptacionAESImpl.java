package com.servicios.negocio.app.comun.impl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.comun.Decodificar;

public class DecriptacionAESImpl implements Decodificar{

	@Override
	public CodificarRespuesta ejecutar(CodificarPeticion peticion) throws ExcepcionServicio {
		CodificarRespuesta respuesta = null;
		String texto = peticion.getMensaje();
		byte[] bytes = peticion.getMensajeBytes();
		
		
		KeyGenerator keyGenerator;
		try {
			
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			Key key = keyGenerator.generateKey();
			
			key = new SecretKeySpec(peticion.getLlave().getBytes(),  0, 16, "AES");
			      
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

			aes.init(Cipher.DECRYPT_MODE, key);			
			
			byte[] desencriptado = null; 
					
			if(texto != null){				
				desencriptado = aes.doFinal(Hex.decodeHex(texto.toCharArray()) );//Base64.decodeBase64(texto));
			}else{
				if(bytes !=null){
					
					desencriptado = aes.doFinal(bytes);
				}else{
					throw new IllegalArgumentException("No se agrego nigun mensaje valido");
				}
			}		

			respuesta = new CodificarRespuesta();
			respuesta.setCodificado(new String (desencriptado));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio EncriptacionAES", e);
		}	
		
		return respuesta;
	}

}

