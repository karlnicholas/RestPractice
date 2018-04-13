package com.elderbyte.commons.exceptions;


/**
 * Thrown when a functionality is not supported.
 */
@SuppressWarnings("serial")
public class NotSupportedException extends RuntimeException {

    public NotSupportedException() {}
    public NotSupportedException(String message){
        super(message);
    }
    public NotSupportedException(String message, Throwable e){
        super(message, e);
    }

}
