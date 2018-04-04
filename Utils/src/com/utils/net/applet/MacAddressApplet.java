package com.utils.net.applet;


import java.security.PrivilegedActionException;

import java.applet.Applet;


import com.utils.SystemUtils;

public class MacAddressApplet extends Applet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SystemUtils utils = new SystemUtils();
	
    public void init(){
    	
    }
    
    public String obtenerMACS() throws PrivilegedActionException{
    	return utils.obtenerMACS();
    }
    
    public String obtenerMACS_JSON() throws PrivilegedActionException{
    	return utils.obtenerMACS_JSON();
    }

    public static void main( String... args )
    {
         
    }
}

