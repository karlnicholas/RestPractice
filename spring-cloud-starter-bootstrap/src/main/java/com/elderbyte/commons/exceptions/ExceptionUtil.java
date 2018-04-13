package com.elderbyte.commons.exceptions;

/**
 * Utility methods for exception handling.
 */
public class ExceptionUtil {

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Aggregates the messages of the while stack trace
     *
     * @param exception the exception
     * @return string containing all messages of the stack trace
     */
    public static String aggregateMessages(Exception exception) {

        StringBuilder sb = new StringBuilder();

        Throwable throwable = exception;
        while(throwable !=null) {
            sb.append(throwable.getMessage());
            sb.append(System.lineSeparator());

            throwable = throwable.getCause();
        }

        return sb.toString();
    }

}
