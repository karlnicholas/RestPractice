package com.elderbyte.commons.exceptions;

/**
 * Thrown when an illegal format is at hand.
 */
@SuppressWarnings("serial")
public class IllegalFormatException extends IllegalArgumentException {

    public IllegalFormatException(String message, Throwable e){
        super(message, e);
    }

    public IllegalFormatException(String message){
        super(message);
    }

}
