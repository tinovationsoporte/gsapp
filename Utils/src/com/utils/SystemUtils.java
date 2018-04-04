package com.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SystemUtils {
	
	public static String sep = ":";
	 public static String format = "%02X";

	  public static String macToString( NetworkInterface ni ) throws SocketException
	  {
	        return macToString( ni, SystemUtils.sep,  SystemUtils.format );
	  }

	    /**
	     * getMacAddress - return the first mac address found
	     *
	     * @param ni - the network interface
	     * @param separator - byte seperator default ":"
	     * @param format - byte formatter default "%02X"
	     * @return String - the mac address as a string
	     * @throws SocketException - pass it on
	     */
	  public static String macToString( NetworkInterface ni, String separator, String format ) throws SocketException
	  {
	      byte mac [] = ni.getHardwareAddress();

	        if( mac != null ) {
	            StringBuffer macAddress = new StringBuffer( "" );
	            String sep = "";
	            for( byte o : mac ) {
	                macAddress.append( sep ).append( String.format( format, o ) );
	                sep = separator;
	            }
	            return macAddress.toString();
	        }

	        return null;
	    }

	        /**
	         * getMacAddressInternal - return the first mac address found
	         *
	         * @return the mac address or undefined
	         */
	        public String obtenerDireccionesMAC()
	        {
	                try {
	                	  
	                        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

	                        // not all interface will have a mac address for instance loopback on windows
	                        while( nis.hasMoreElements() ) {
	                        	   
	                                String mac = macToString( nis.nextElement() );
	                                if( mac != null && mac.length() > 0 )
	                                        return mac;
	                        }
	                } catch( SocketException ex ) {
	                        System.err.println( "SocketException:: " + ex.getMessage() );
	                        ex.printStackTrace();
	                } catch( Exception ex ) {
	                        System.err.println( "Exception:: " + ex.getMessage() );
	                        ex.printStackTrace();
	                }

	                return "undefined";
	        }
	        
	        public String getMacAddressEthernetActivas(){
	        	return null;
	        }

	    /**
	     * getMacAddressesJSON - return all mac addresses found
	     *
	     * @return a JSON array of strings (as a string)
	     */
	    public static String obtenerDireccionesMAC_JSON()
	    {
	        try {
	            List<String> macs = obtenerListaMACS();

	            String sep = "|";
	            StringBuffer macArray = new StringBuffer();
	            for(String mac:macs) {
	                macArray.append(sep).append(mac);
	                sep = "|";
	            }
	            return macArray.toString();
	        } catch(Exception ex) {
	            System.err.println("Exception:: "+ex.getMessage());
	            ex.printStackTrace();
	        }
	        return "";
	    }
	    
	    /**
	     * getMacAddresses - return all mac addresses found
	     *
	     * @return array of strings (mac addresses) empty if none found
	     */
	    public static List<String> obtenerListaMACS(){
	        List<String> lista = new ArrayList<String>();
	        int counter = 0;
	        for(String x:getInets()){
	        	if(counter>0){
	        		if((x.length()==17)&&(!lista.contains(x))){
	                	lista.add(x);
	            	}
	        	}else{
	        		lista.add(x);
	        	}
	        	counter = counter + 1;
	        }
	        return lista;
	    }
	    
	    
	    public static String [] obtenerArregloDireccionesMAC()
	    {
	        try {
	            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
	           
	            ArrayList<String> macs = new ArrayList<String>();
	            while( nis.hasMoreElements() ) {
	                String mac = macToString( nis.nextElement() );
	                // not all interface will have a mac address for instance loopback on windows
	                if( mac != null ) {
	                	System.out.println("MAc: "+mac);
	                    macs.add( mac );
	                }
	            }
	            return macs.toArray( new String[macs.size()] );
	        } catch( SocketException ex ) {
	            System.err.println( "SocketException:: " + ex.getMessage() );
	            ex.printStackTrace();
	        } catch( Exception ex ) {
	            System.err.println( "Exception:: " + ex.getMessage() );
	            ex.printStackTrace();
	        }

	        return new String[0];
	    }

	    /**
	     * getMacAddresses - return all mac addresses found
	     *
	     * @param sep - use a different separator
	     */
	    public  void setSep( String sep )
	    {
	        try {
	            SystemUtils.sep = sep;
	        } catch( Exception ex ) {
	            //  don't care
	        }
	    }

	    /**
	     * getMacAddresses - return all mac addresses found
	     *
	     * @param format - the output format string for bytes that can be overridden default hex.
	     */
	    public  void setFormat( String format )
	    {
	        try {
	            SystemUtils.format = format;
	        } catch( Exception ex ) {
	            //  don't care
	        }
	    }

	      /**
	       * Wrap the privilege access to our internal method
	       */
	      private final class ServicioObtenerDireccionesMAC implements PrivilegedAction<String>
	      {
	          public String run()
	          {
	              return obtenerDireccionesMAC();
	          }
	      }

	      /**
	       * Wrap the privilege access to our internal method
	       */
	     private final static class ServicioObtenerDireccionesMAC_JSON implements PrivilegedAction<String>
	     {
	          public String run()
	          {
	              return obtenerDireccionesMAC_JSON();
	          }
	     }
	     
	     private final static class ServicioObtenerArregloDireccionesMAC implements PrivilegedAction<String[]>
	     {
	          public String[] run()
	          {
	              return obtenerArregloDireccionesMAC();
	          }
	     }


	     /**
	      * getMacAddress - return the first mac address found
	      *
	      * @return the mac address or undefined
	      * @throws java.security.PrivilegedActionException ex
	      */
	     public String obtenerMACS() throws PrivilegedActionException
	     {
	           return AccessController.doPrivileged( new ServicioObtenerDireccionesMAC() );
	     }

	     /**
	      * getMacAddress - return the first mac address found
	      *
	      * @return the mac address or undefined
	      * @throws java.security.PrivilegedActionException ex
	      */
	     public static String obtenerMACS_JSON() throws PrivilegedActionException
	     {
	         return AccessController.doPrivileged( new ServicioObtenerDireccionesMAC_JSON() );
	     }	
	     
	     public static String[] obtenerArregloMACS(){
	    	 return AccessController.doPrivileged( new ServicioObtenerArregloDireccionesMAC() );
	     }
	     
	     public static List<String> getInets(){
	    	 InetAddress addr;
             List<String> macs = new ArrayList<String>();
	         try{
	             addr = InetAddress.getLocalHost();
	             macs.add(addr.getHostName().toString());
	             macs.add(addr.getHostName());
	             
	             InetAddress[] nets = InetAddress.getAllByName(addr.getHostName().toString());
	             for(InetAddress net:nets){
	                 NetworkInterface a = NetworkInterface.getByInetAddress(net);
	                 if((!a.getDisplayName().contains("virtual"))&&
	                         (!a.getDisplayName().contains("Virtual"))&&
	                         (!a.getDisplayName().contains("VMware"))&&
	                         (!a.getName().contains("net"))){
	                     byte[] mac = a.getHardwareAddress();
	                     StringBuilder sb = new StringBuilder();
	                     for (int i = 0; i < mac.length; i++) {
	                         sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
	                     }
	                     macs.add(sb.toString());
	                 }
	             }
	         } catch (Exception e){
	             e.printStackTrace();
	         }
	         return macs;
	     }
	     
	     public static void main (String... args){
	    	 for(String mac: SystemUtils.obtenerArregloDireccionesMAC()){
	    		 System.out.println("MAac: "+mac);
	    	 }
	     }
}
