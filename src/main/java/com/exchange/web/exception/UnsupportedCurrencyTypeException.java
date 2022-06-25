package com.exchange.web.exception;


import com.exchange.web.resources.ErrorResource;

/**
 * @author msaritas
 */
public class UnsupportedCurrencyTypeException extends Exception {

  private static final long serialVersionUID = 1L;

  private final ErrorResource errorResource;

  public UnsupportedCurrencyTypeException(String message) {
    this.errorResource = new ErrorResource(400, message);
  }

  public ErrorResource getErrorResource() {
    return errorResource;
  }

}
