package com.elderbyte.commons.exceptions;

/**
 * Exception to throw if an argument must not be empty.
 */
@SuppressWarnings("serial")
public class ArgumentEmptyException extends IllegalArgumentException {

    public ArgumentEmptyException(String argument) {
        super(String.format("Argument '%s' must not be empty!", argument));
    }


    public ArgumentEmptyException(String argument, Throwable cause) {
        super(String.format("Argument '%s' must not be empty!", argument), cause);
    }
}
