package com.core.app.bd;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.sql.DataSource;

import com.core.app.bd.query.Callable;
import com.core.app.bd.query.Insert;
import com.core.app.bd.query.Query;
import com.core.app.bd.query.Select;


public class ConexionJDBC {
	
	private static Map<String,String> connectionData;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private boolean openConnection = false;
    private boolean statementCreated = false;
    
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private Map<String, Integer> mapaParametros;
    
    private boolean isDataSource;
	private boolean preparedStatementCreated;
    
    private boolean statementExecuted = false; 
    
    
    public void abrir() throws ConexionExcepcion{
    	
        
    		try {
        	
        	
        	if(isDataSource)
        		
        		this.connection = dataSource.getConnection();
        		
        	
        	else{
        		
        		Class.forName(ConexionJDBC.getConnectionData().get("driver"));                
                this.connection = DriverManager.getConnection(ConexionJDBC.getConnectionData().get("url")
                		,ConexionJDBC.getConnectionData().get("username")
                		,ConexionJDBC.getConnectionData().get("password"));       
                        		
        	}
        	
            
            
        } catch (Exception ex) {
            
            throw new ConexionExcepcion("Error al abrir la conexion local y conectarse a la BD. - " + ex.getMessage(), ex);
            //ex.printStackTrace();
            
        }    	
    	
    
        this.openConnection = true;
    }

    public void cerrar() throws ConexionExcepcion {
    
                
          try{
            	
            this.openConnection = false;
            this.statementCreated = false;
       
            this.statement = null;
            this.preparedStatement = null;
            
            
	        this.resultSet = null;
	        statementExecuted = false;
            
	        if (!this.connection.isClosed()){
	        	
	        	this.connection.close();
	        }
            
            this.connection = null;
                
                
            }catch(Exception ex){
            	ex.printStackTrace();
                throw new ConexionExcepcion("Ocurrio un error al intentar cerrar la conexion . - " + ex.getMessage(), ex);
            }             
    
    }
    
    
    
    public void crearReadOnlyStatement() throws ConexionExcepcion{
       
	    try {	
	    	
	    	if(openConnection & !isDataSource){ 
	    		
	            this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            
	            statementCreated = true;
	            
	       }else{	           
	    	   
	    	   if(openConnection & isDataSource){
	    		   this.statement = this.connection.createStatement();
	    		   statementCreated = true;
	    		   
	    	   }else{
	    		   
	    		   throw new ConexionExcepcion("No hay una conexion valida abierta para obtener el objeto ReadOnlySatatemt o no existe un DataSource asignado.", null);   
	    		   
	    	   } 
	    	   
	       }
	    
	    } catch (Exception ex) {
	        throw new ConexionExcepcion("Error al obtentener el objeto ReadOnlySatatemt.- " + ex.getMessage(), ex.getCause());
	    }
	       
    }
    
   

    public void ejecutarStatementQuery(String query) throws ConexionExcepcion{        
        
    	if(statementCreated){
            try {
            	
                this.resultSet = this.statement.executeQuery(query);
                statementExecuted= true;
            } catch (Exception ex) {
                
                throw new ConexionExcepcion("El query [" + query +"] no pudo ser ejecutado exitosamente en el objeto Statement. - " + ex.getMessage(), ex);
                
            }
        }else{        
            
            throw new ConexionExcepcion("No existe un objeto ReadOnlySatatemt creado para ejecutar el query", null);        
        }    
    }   
    
 
    public void crearPreparedStatement(Query query) throws ConexionExcepcion {
    	
    	if(query != null){
    		
    		this.preparedStatement = query.getStatement();
    		preparedStatementCreated = true;
    		
    	}else{   		

            throw new ConexionExcepcion("No existe una referencia de tipo Query creada para crear el Objeto PreparedStatement", null);    		
    	}
    	
    }
    
    public void ejecutarPreparedStamentQuery(Query query) throws ConexionExcepcion {
    	
    	
    	  	
	    try {
	    	
	    	this.preparedStatement.execute();
            statementExecuted= true;
            
            if(query instanceof Callable){
               this.mapaParametros = query.getMapaParametros();
            }
	    	
	    	if(query instanceof Select || (query instanceof Callable) ){
	    	
	    		this.resultSet = this.preparedStatement.getResultSet();
	    	
	    	}else{
	    		
	    		if(query instanceof Insert ){
	    			
	    			this.resultSet = this.preparedStatement.getGeneratedKeys();
	    		}
	    		
	    	}    	
	
	    } catch (Exception ex) {
	    	
	    	throw new ConexionExcepcion("El query [" + query +"] no pudo ser ejecutado exitosamente en el PreparedStatement.- " + ex.getMessage(), ex);
	                
	    }
           
    }
    
        
    public ResultSet getResultSet(){
    
        return this.resultSet;   
        
    }
    
    public Map<String, Object> obtenerParametroSalida() throws SQLException{
    	Map<String, Object> mapaParametrosSalida = null;
    	Iterator iterMapaSalida = null;
    	Entry<String, Integer> entry = null;
    	CallableStatement callable = ((CallableStatement) this.preparedStatement); 
    	if(this.mapaParametros != null){
    		iterMapaSalida = mapaParametros.entrySet().iterator();
    		mapaParametrosSalida = new HashMap<String, Object>();
    		while(iterMapaSalida.hasNext()){
    		   entry = (Entry<String, Integer>) iterMapaSalida.next();
    		   mapaParametrosSalida.put(entry.getKey(), callable.getObject(entry.getValue()));
    		}
    	}
    	return mapaParametrosSalida;
    }
    
    public Result createResult() throws ConexionExcepcion{
    	
    	if(this.resultSet == null){
    		
    		throw new ConexionExcepcion("El objeto de tipo result no puede ser creado ya que no hay una referencia del objeto ResultSet." , null);
    	}
    	
    		
    	return ResultSupport.toResult(this.resultSet);
    	
    }
    
    
    public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.isDataSource = true;
	}   
    
    
    public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private static Map<String, String> getConnectionData(){
    
        if(connectionData != null){
            return connectionData;
        }    
        
        connectionData = new HashMap<String,String>();
        
        connectionData.put("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connectionData.put("url", "jdbc:sqlserver://192.168.1.235:1433;databaseName=cmasOperacion2");
        connectionData.put("username", "sa");
        connectionData.put("password", "F3C47rywsh3r3i");
        //connectionData.put("schema", "SITE");
    
        return connectionData;
    }
    
   
    
    
    
    
    
	public static void main(String [] args){
    
        ConexionJDBC sc = new ConexionJDBC();
        
        
        try {
            /*sc.abrir();
            sc.crearReadOnlyStatement();
           
            sc.ejecutarStatementQuery("SELECT * FROM dbo.UsuarioSistema");
            System.out.println("Resultado ->");            
            System.out.println(sc.createResult().getRows() );*/
            sc.cerrar();
        } catch (ConexionExcepcion ex) {
            ex.printStackTrace();
        
        }
    }

	public Map<String, Integer> getMapaParametros() {
		return mapaParametros;
	}

	public void setMapaParametros(Map<String, Integer> mapaParametros) {
		this.mapaParametros = mapaParametros;
	}
	
	

}