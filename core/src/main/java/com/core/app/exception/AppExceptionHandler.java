/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.app.exception;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

//import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 *
 * @author RSANROMAN
 */
public class AppExceptionHandler extends ExceptionHandlerWrapper{
    
    
    private ExceptionHandler exceptionHandlerWrapped;

    public AppExceptionHandler(ExceptionHandler exceptionHandler) {
        
        this.exceptionHandlerWrapped = exceptionHandler; 
    }

    @Override
    public ExceptionHandler getWrapped() {    
        return this.exceptionHandlerWrapped;
    }
    
    
    @Override
    public void handle() throws FacesException {
    
        //System.out.println("\nhandle()...\n");        
        
    	final FacesContext fc = FacesContext.getCurrentInstance();
    	final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
    	final NavigationHandler nav = fc.getApplication().getNavigationHandler();
	 
    	final StringBuilder builder = new StringBuilder();	        
        
        
        //Uncoment this line if want default JSF error page.
        if(fc.isProjectStage(ProjectStage.Development)){       
            getWrapped().handle();       
        }
        
           
        
        final Iterator<ExceptionQueuedEvent> exceptions = (getUnhandledExceptionQueuedEvents().iterator().hasNext())? 
                                                            getUnhandledExceptionQueuedEvents().iterator(): 
                                                            getHandledExceptionQueuedEvents().iterator();
            
        if(exceptions.hasNext()){            
            
            String redirect = "/error";
         
            ExceptionQueuedEvent event = exceptions.next();        
            
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();	          
                 
            Throwable exception = context.getException();
                        	
            createMessageCauses(exception, builder);
                
            requestMap.put("exceptionTrace", builder.toString());            
               
            if(exception instanceof javax.faces.application.ViewExpiredException){               
            	redirect = "/sessionTimeOut";
            }                       
                
            nav.handleNavigation(fc, null, redirect);                     
            fc.renderResponse(); 
        }
        //parent handle	
        //getWrapped().handle();
    }
    

    private void createMessageCauses(Throwable throwable, StringBuilder messages){
    
    	
        if( throwable.getCause() == null){ 
            
        	messages.append(throwable.getClass().getCanonicalName() + "\n");
            
        	/*if(throwable instanceof com.microsoft.sqlserver.jdbc.SQLServerException){
            	
        		messages.append("errorCode: " + ((com.microsoft.sqlserver.jdbc.SQLServerException)throwable).getErrorCode() + "\n");
        		messages.append("SQLState: " + ((com.microsoft.sqlserver.jdbc.SQLServerException)throwable).getSQLState() + "\n");
            }*/
        	
        	
        	
        	messages.append(throwable.getCause() +": "+  throwable.getMessage()).append("\n");            
        
            for(StackTraceElement element: throwable.getStackTrace()){       
            
                messages.append(element).append("\n");        
        
            }           
            return;
        }
        createMessageCauses(throwable.getCause(),messages);
    
        
    
    }


    private Throwable getRootException(Throwable throwable){
    	
    	if(throwable.getCause() == null){
    		
    		return throwable;
    	}
    	
    	return getRootException(throwable.getCause());
    }

    
}
