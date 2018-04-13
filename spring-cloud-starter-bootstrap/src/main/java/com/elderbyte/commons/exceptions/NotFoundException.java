package com.elderbyte.commons.exceptions;

/**
 * Thrown when an entity is not found.
 */
@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public NotFoundException(String message) {
        super(message);
    }


}
