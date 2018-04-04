package com.core.app.bd;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class NamedParameterStatement implements PreparedStatement{
    /** The statement this object is wrapping. */
    private PreparedStatement preparedStatement;
    private CallableStatement callableStatement;
    private Map<String, Integer> mapaParametros;
    //private DataSource dataSource;

    /** Maps parameter names to arrays of ints which are the parameter indices. 
*/
    private final Map indexMap;
 
    public static final int SQL_SELECT = 0;
    public static final int SQL_INSERT = 1;
    public static final int SQL_UPDATE = 2;
    public static final int SQL_DELETE = 3; 
    public static final int SQL_CALLABLE = 4;

    /**
     * Creates a NamedParameterStatement.  Wraps a call to
     * c.{@link Connection#prepareStatement(java.lang.String) 
prepareStatement}.
     * @param connection the database connection
     * @param query      the parameterized query
     * @throws SQLException if the statement could not be created
     */
    public NamedParameterStatement(Connection connection, String query) throws 
SQLException {
        indexMap=new HashMap();
        
        String parsedQuery=parse(query, indexMap);
        preparedStatement=connection.prepareStatement(parsedQuery);
    }
    
    public NamedParameterStatement(Connection connection, String query, int operationType)throws SQLException{
    	
    	indexMap = new HashMap();
    	
    	String parsedQuery = parse(query, indexMap); 
    	
    	switch(operationType){
    		case SQL_SELECT: // ligado con executeQuery
    		  preparedStatement = connection.prepareStatement(parsedQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	break;
    		case SQL_INSERT: // ligado con execute
      		  preparedStatement = connection.prepareStatement(parsedQuery, PreparedStatement.RETURN_GENERATED_KEYS);
          	break;
    		case SQL_UPDATE: // ligado con executeUpdate
      		  preparedStatement = connection.prepareStatement(parsedQuery);
          	break;
    		case SQL_DELETE: // ligado con execute
      		  preparedStatement = connection.prepareStatement(parsedQuery);      		  
          	break;
    		case SQL_CALLABLE:
    		  callableStatement = connection.prepareCall(parsedQuery);
    		  preparedStatement = callableStatement;    		  
    		break; 
          	default:
              throw new IllegalArgumentException("Tipo SQL Incorrecto");          	
    	}        
    }
    
   /*public NamedParameterStatement(String query, int operationType)throws SQLException{
    	indexMap = new HashMap();
    	String parsedQuery=parse(query, indexMap); 
    	switch(operationType){
    		case SQL_SELECT: // ligado con executeQuery
    		  preparedStatement=dataSource.getConnection().prepareStatement(parsedQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	break;
    		case SQL_INSERT: // ligado con execute
      		  preparedStatement=dataSource.getConnection().prepareStatement(parsedQuery, PreparedStatement.RETURN_GENERATED_KEYS);
          	break;
    		case SQL_UPDATE: // ligado con executeUpdate
      		  preparedStatement=dataSource.getConnection().prepareStatement(parsedQuery);
          	break;
    		case SQL_DELETE: // ligado con execute
      		  preparedStatement=dataSource.getConnection().prepareStatement(parsedQuery);      		  
          	break;
          	default:
              throw new IllegalArgumentException("Tipo SQL Incorrecto");          	
    	}        
    }*/


    /**
     * Parses a query with named parameters.  The parameter-index mappings are 
put into the map, and the
     * parsed query is returned.  DO NOT CALL FROM CLIENT CODE.  This 
method is non-private so JUnit code can
     * test it.
     * @param query    query to parse
     * @param paramMap map to hold parameter-index mappings
     * @return the parsed query
     */
    static final String parse(String query, Map paramMap) {
        // I was originally using regular expressions, but they didn't work well for ignoring
        // parameter-like strings inside quotes.
        int length=query.length();
        StringBuffer parsedQuery=new StringBuffer(length);
        boolean inSingleQuote=false;
        boolean inDoubleQuote=false;
        int index=1;

        for(int i=0;i<length;i++) {
            char c=query.charAt(i);
            if(inSingleQuote) {
                if(c=='\'') {
                    inSingleQuote=false;
                }
            } else if(inDoubleQuote) {
                if(c=='"') {
                    inDoubleQuote=false;
                }
            } else {
                if(c=='\'') {
                    inSingleQuote=true;
                } else if(c=='"') {
                    inDoubleQuote=true;
                } else if(c==':' && i+1<length &&
                        Character.isJavaIdentifierStart(query.charAt(i+1))) {
                    int j=i+2;
                    while(j<length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name=query.substring(i+1,j);
                    c='?'; // replace the parameter with a question mark
                    i+=name.length(); // skip past the end if the parameter

                    List indexList=(List)paramMap.get(name);
                    if(indexList==null) {
                        indexList=new LinkedList();
                        paramMap.put(name, indexList);
                    }
                    indexList.add(new Integer(index));

                    index++;
                }
            }
            parsedQuery.append(c);
        }

        // replace the lists of Integer objects with arrays of ints
        for(Iterator itr=paramMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry=(Map.Entry)itr.next();
            List list=(List)entry.getValue();
            int[] indexes=new int[list.size()];
            int i=0;
            for(Iterator itr2=list.iterator(); itr2.hasNext();) {
                Integer x=(Integer)itr2.next();
                indexes[i++]=x.intValue();
            }
            entry.setValue(indexes);
        }

        return parsedQuery.toString();
    }


    /**
     * Returns the indexes for a parameter.
     * @param name parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException if the parameter does not exist
     */
    private int[] getIndexes(String name) {
        int[] indexes=(int[])indexMap.get(name);
        if(indexes==null) {
            throw new IllegalArgumentException("Parameter not found: "+name);
        }
        return indexes;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(String name, Object value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setObject(indexes[i], value);
        }
    }


    /** 
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(String name, String value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setString(indexes[i], value);
        }
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setInt(String name, int value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setInt(indexes[i], value);
        }
    }
    
    public void registerOutParameter(String name, int tipo) throws SQLException {
        int[] indexes=getIndexes(name);
        if(mapaParametros == null)
           mapaParametros = new HashMap<String, Integer>();
        
        for(int i=0; i < indexes.length; i++) {
           callableStatement.registerOutParameter(indexes[i], tipo);
           mapaParametros.put(name, indexes[i]);
        }
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setLong(String name, long value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setLong(indexes[i], value);
        }
    }
    
    public void setFloat(String name, float value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setFloat(indexes[i], value);
        }
    }
    
    public void setDouble(String name, double value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setDouble(indexes[i], value);
        }
    }

    public void setBigDecimal(String name, BigDecimal value) throws SQLException {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setBigDecimal(indexes[i], value);
        }
    }

    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException 
    {
        int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {
        	preparedStatement.setTimestamp(indexes[i], value);
        }
    }

    public void setNull(String name, int sqlType)throws SQLException{
    	int[] indexes=getIndexes(name);
        for(int i=0; i < indexes.length; i++) {        	
        	preparedStatement.setNull(indexes[i], sqlType);
        }
    }
    
    public void setDate(String name, java.util.Date date)throws SQLException{
    	int[] indexes=getIndexes(name);
    	Date fechaSql = new Date(date.getTime()); 
    	System.out.println("fechaSQL: "+fechaSql);
        for(int i=0; i < indexes.length; i++) {        	
        	preparedStatement.setDate(indexes[i], fechaSql);
        }
    }

    /**
     * Returns the underlying statement.
     * @return the statement
     */
    public PreparedStatement getStatement() {
        return preparedStatement;
    }


    /**
     * Executes the statement.
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {    	
        return preparedStatement.execute();
    }


    /**
     * Executes the statement, which must be a query.
     * @return the query results
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        return preparedStatement.executeQuery();
    }


    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE 
statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        return preparedStatement.executeUpdate();
    }


    /**
     * Closes the statement.
     * @throws SQLException if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException {
    	preparedStatement.close();
    }


    /**
     * Adds the current set of parameters as a batch entry.
     * @throws SQLException if something went wrong
     */
    public void addBatch() throws SQLException {
    	preparedStatement.addBatch();
    }


    /**
     * Executes all of the batched statements.
     * 
     * See {@link Statement#executeBatch()} for details.
     * @return update counts for each statement
     * @throws SQLException if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return preparedStatement.executeBatch();
    }
    
    public ResultSet getResultSet() throws SQLException{
    	return preparedStatement.getResultSet();
    }

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
        
		return preparedStatement.executeQuery(sql);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {		
		return preparedStatement.executeUpdate(sql);
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return preparedStatement.getMaxFieldSize();		
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		preparedStatement.setMaxFieldSize(max);
	}

	@Override
	public int getMaxRows() throws SQLException {
		return preparedStatement.getMaxRows();
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		preparedStatement.setMaxRows(max);
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		preparedStatement.setEscapeProcessing(enable);		
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return preparedStatement.getQueryTimeout();
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		preparedStatement.setQueryTimeout(seconds);		
	}

	@Override
	public void cancel() throws SQLException {
		preparedStatement.cancel();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {		
		return preparedStatement.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		preparedStatement.clearWarnings();		
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		preparedStatement.setCursorName(name);		
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		return preparedStatement.execute(sql);
	}

	@Override
	public int getUpdateCount() throws SQLException {		
		return preparedStatement.getUpdateCount();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return preparedStatement.getMoreResults();		
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		preparedStatement.setFetchDirection(direction);
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return preparedStatement.getFetchDirection();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		preparedStatement.setFetchSize(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		return preparedStatement.getFetchSize();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return preparedStatement.getResultSetConcurrency();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return preparedStatement.getResultSetType();
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBatch() throws SQLException {
		preparedStatement.clearBatch();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return preparedStatement.getConnection();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return preparedStatement.getMoreResults(current);
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return preparedStatement.getGeneratedKeys();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return preparedStatement.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return preparedStatement.executeUpdate(sql, columnIndexes);		
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return preparedStatement.executeUpdate(sql, columnNames);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return preparedStatement.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return preparedStatement.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return preparedStatement.execute(sql, columnNames);
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return preparedStatement.getResultSetHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return preparedStatement.isClosed();		
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		preparedStatement.setPoolable(poolable);
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return preparedStatement.isPoolable();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return preparedStatement.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return preparedStatement.isWrapperFor(iface);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		preparedStatement.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		preparedStatement.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		preparedStatement.setByte(parameterIndex, x);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		preparedStatement.setShort(parameterIndex, x);
		
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		preparedStatement.setInt(parameterIndex, x);	
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		preparedStatement.setLong(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		preparedStatement.setFloat(parameterIndex, x);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		preparedStatement.setDouble(parameterIndex, x);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		preparedStatement.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		preparedStatement.setString(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		preparedStatement.setBytes(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		preparedStatement.setDate(parameterIndex, x);	
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		preparedStatement.setTime(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		preparedStatement.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		preparedStatement.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearParameters() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public CallableStatement getCallableStatement() {
		return callableStatement;
	}

	public void setCallableStatement(CallableStatement callableStatement) {
		this.callableStatement = callableStatement;
	}

	public Map<String, Integer> getMapaParametros() {
		return mapaParametros;
	}

	public void setMapaParametros(Map<String, Integer> mapaParametros) {
		this.mapaParametros = mapaParametros;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	/*public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/
}
