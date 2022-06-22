package com.exchange.web.exception;


import com.exchange.web.resources.ErrorResource;

/**
 * @author msaritas
 */
public class ServiceUnavailableException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public ServiceUnavailableException() {
        this.errorResource = new ErrorResource("Currency conversion is currently not available!");
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }

}
