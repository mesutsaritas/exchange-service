package com.exchange.web.exception;

import com.exchange.web.resources.ErrorResource;

/**
 * @author msaritas
 */
public class EmptyParametersException extends Exception {

  private static final long serialVersionUID = 1L;

  private final ErrorResource errorResource;

  public EmptyParametersException() {
    this.errorResource =
        new ErrorResource(400, "At least one of the service parameters must be filled.");
  }

  public ErrorResource getErrorResource() {
    return errorResource;
  }
}
