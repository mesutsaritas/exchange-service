package com.exchange.web.exception;


import com.exchange.web.resources.ErrorResource;

/**
 * @author msaritas
 */
public class EmptyParamatersException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public EmptyParamatersException() {
        this.errorResource = new ErrorResource("At least one of the service parameters must be filled.");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }

}
