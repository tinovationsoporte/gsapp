package com.servicios.negocio.app.comun.impl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import com.core.app.servicios.ExcepcionServicio;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenPeticion;
import com.objetos.transf.datos.app.comun.ModificarTamanoImagenRespuesta;
import com.servicios.negocio.ExcepcionServicioNegocio;
import com.servicios.negocio.app.comun.ModificarTamanoImagen;

public class ModificarTamanoImagenImpl implements ModificarTamanoImagen {

	@Override
	public ModificarTamanoImagenRespuesta ejecutar(ModificarTamanoImagenPeticion peticion) throws ExcepcionServicioNegocio{
		ModificarTamanoImagenRespuesta respuesta = new ModificarTamanoImagenRespuesta();
		
		
		try {
			InputStream is = convertirAInputStream(peticion);
			
			int size = peticion.getTamanoAComprimir();// size of the new image.
            //take the file as inputstream.
            InputStream imageStream = convertirAInputStream(peticion);
            //read the image as a BufferedImage.
            BufferedImage image = javax.imageio.ImageIO.read(imageStream); 
            //cal the sacleImage method.
            BufferedImage newImage = scaleImage(image, size);
           
          
            ByteArrayOutputStream baos  = new ByteArrayOutputStream();
            
            ImageIO.write(newImage, peticion.getTipoImagen(), baos);
            
            respuesta.setImageBytes(baos.toByteArray());
            
            baos.close();
            
            
            /*String path = getServletContext().getRealPath("/image");
            //write file.
            File file = new File(path, "testimage.jpg");
            ImageIO.write(newImage, "JPG", file);
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
	}

	private InputStream convertirAInputStream(ModificarTamanoImagenPeticion peticion) throws Exception{
		InputStream result = null;
		
		System.out.println("inputstream -> " + peticion.getImageInputStream());
		System.out.println("bytes -> " + peticion.getImageBytes());
		System.out.println("b64 -> " + peticion.getImageStringB64());
		
		if(peticion.getImageInputStream()  != null){
			
			result =  peticion.getImageInputStream();
		}else{
			
			if(peticion.getImageBytes() != null){
				
				result = new ByteArrayInputStream(peticion.getImageBytes());  
			}else{
				
				if(peticion.getImageStringB64() != null){
					
					byte[] bytes = Base64.decodeBase64(peticion.getImageStringB64());
					result = new ByteArrayInputStream(bytes);
				
				}else{
						throw new Exception("Los parametros de la peticion ModificiarTamanoArchivo son nulos.");
				}
			}
		}
	
		return result;
	}
	
	private BufferedImage scaleImage(BufferedImage bufferedImage, int size) {
		double boundSize = size;
           int origWidth = bufferedImage.getWidth();
           int origHeight = bufferedImage.getHeight();
           double scale;
           if (origHeight > origWidth)
               scale = boundSize / origHeight;
           else
               scale = boundSize / origWidth;
            //* Don't scale up small images.
           if (scale > 1.0)
               return (bufferedImage);
           int scaledWidth = (int) (scale * origWidth);
           int scaledHeight = (int) (scale * origHeight);
           Image scaledImage = bufferedImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
           // new ImageIcon(image); // load image
           // scaledWidth = scaledImage.getWidth(null);
           // scaledHeight = scaledImage.getHeight(null);
           BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
           Graphics2D g = scaledBI.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           g.drawImage(scaledImage, 0, 0, null);
           g.dispose();
           return (scaledBI);
   }
}
