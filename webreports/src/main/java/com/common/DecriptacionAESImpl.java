package com.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;



public class DecriptacionAESImpl {

	
	public String decode(String mensaje, String llave) throws Exception {
		String texto = mensaje;		
		byte[] desencriptado = null;
		try {
			
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			
			keyGenerator.init(128);
			
			Key key = keyGenerator.generateKey();
			
			key = new SecretKeySpec(llave.getBytes(),  0, 16, "AES");
			      
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

			aes.init(Cipher.DECRYPT_MODE, key);			
			
			desencriptado = aes.doFinal( Hex.decodeHex(texto.toCharArray()));//Base64.decodeBase64(texto));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Ocurrio un error en el servicio DecriptacionAES", e);
		}	
		
		return new String (desencriptado);
	}

}

