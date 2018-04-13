package com.elderbyte.commons.exceptions;

/**
 * Thrown when a method argument which is required (@NonNull) was Null!
 */
@SuppressWarnings("serial")
public class ArgumentNullException extends IllegalArgumentException {

    public ArgumentNullException(String argumentName) {
        super("The argument '" + argumentName + "' was NULL!");
    }

    public ArgumentNullException(String argumentName, Throwable cause) {
        super("The argument '" + argumentName + "' was NULL!", cause);
    }
}

