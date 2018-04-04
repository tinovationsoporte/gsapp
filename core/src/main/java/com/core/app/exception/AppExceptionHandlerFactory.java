/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.app.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author RSANROMAN
 */
public class AppExceptionHandlerFactory extends ExceptionHandlerFactory{

    private ExceptionHandlerFactory parent;

    
    public AppExceptionHandlerFactory(ExceptionHandlerFactory parent) {

        
        this.parent = parent;
        
    }
    
    
    
    
    @Override
    public ExceptionHandler getExceptionHandler() {

        ExceptionHandler handler = new AppExceptionHandler(parent.getExceptionHandler());
        
        return handler;
    
    }
    
}
