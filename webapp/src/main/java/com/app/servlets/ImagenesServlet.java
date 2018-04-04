package com.app.servlets;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.modelo.datos.app.Archivo;
import com.objetos.transf.datos.app.catalogo.ObtenerCatalogoPeticion;
import com.servicios.app.catalogos.ServicioCatalogo;
import com.servicios.excepcion.ExcepcionServicioFachada;

/**
 * Servlet implementation class ImagenesServlet
 */
public class ImagenesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	private WebApplicationContext webApplicationContext;
	
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ImagenesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    @Override
    public void init() throws ServletException {
    	webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//response.getWriter().append("idArchivo -> ").append(request.getParameter("idArchivo"));
		
		try {
			
			Integer idArchivo  = Integer.parseInt( request.getParameter("idArchivo").toString() );
			
			ServicioCatalogo servicioCatalogo = (ServicioCatalogo) webApplicationContext.getBean("servicioCatalogo");
			 
			ObtenerCatalogoPeticion peticion = new ObtenerCatalogoPeticion();
			peticion.setClase(Archivo.class);
			peticion.setIdEntidad(idArchivo);
			
			
			
			Archivo archivo = (Archivo) servicioCatalogo.obtenerCatalogo(peticion ).getEntidad();
		
			//InputStream is = new  ByteArrayInputStream(archivo.getBlob());
			response.setContentType("application/octet-stream");
            response.setContentLength(archivo.getBlob().length);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", archivo.getNombre());
            response.setHeader(headerKey, headerValue);
			
			
			OutputStream os = response.getOutputStream();
			os.write(archivo.getBlob());
			os.close();			
			
		} catch (Exception e) {
			
			throw new ServletException("Ocurrio un error al generar la imagen." + e.getMessage(), e);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
