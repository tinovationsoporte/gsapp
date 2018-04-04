package com.servicios.negocio.app.comun.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.CodificarPeticion;
import com.objetos.transf.datos.app.comun.CodificarRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.comun.Codificar;

public class EncriptacionAESImpl implements Codificar {

	@Override
	public CodificarRespuesta ejecutar(CodificarPeticion peticion) throws ExcepcionServicioNegocio {
		
		CodificarRespuesta respuesta = null;
		String texto = peticion.getMensaje();
		byte[] bytes = peticion.getMensajeBytes();
		
		
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			Key key = keyGenerator.generateKey();
			      
			// Alternativamente, una clave que queramos que tenga al menos 16 bytes
			// y nos quedamos con los bytes 0 a 15
			//key = new SecretKeySpec("una clave de 16 bytes".getBytes(),  0, 16, "AES");
			key = new SecretKeySpec(peticion.getLlave().getBytes(),  0, 16, "AES");
			      
			// Ver como se puede guardar esta clave en un fichero y recuperarla
			// posteriormente en la clase RSAAsymetricCrypto.java

			// Texto a encriptar
			//String texto =peticion.getMensaje(); //"rptName=rptFolio.rpt&idFolio=1&key=AAABB5564";

			// Se obtiene un cifrador AES
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// Se inicializa para encriptacion y se encripta el texto,
			// que debemos pasar como bytes.
			aes.init(Cipher.ENCRYPT_MODE, key);
			
			//byte[] encriptado = aes.doFinal(texto.getBytes());
			byte[] encriptado = null; 
					
			if(texto != null){
				encriptado = aes.doFinal(texto.getBytes());
			}else{
				if(bytes !=null){
					
					encriptado = aes.doFinal(bytes);
				}else{
					throw new IllegalArgumentException("No se agrego nigun mensaje valido");
				}
			}		
					
		
		    
			String hexEncription = Hex.encodeHexString(encriptado); //Base64.encodeBase64String(encriptado);

			respuesta = new CodificarRespuesta();
			respuesta.setCodificado(hexEncription);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExcepcionServicioNegocio("Ocurrio un error en el servicio EncriptacionAES", e);
		}	
		
		return respuesta;
	}

}
