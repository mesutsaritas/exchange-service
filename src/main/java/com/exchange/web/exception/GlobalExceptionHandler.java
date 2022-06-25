package com.exchange.web.exception;

import com.exchange.web.resources.ErrorResource;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author msaritas
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(value = ServiceUnavailableException.class)
  public @ResponseBody
  ResponseEntity<Object> serviceUnavailableException(ServiceUnavailableException e) {
    return getExceptionResponseEntity(e.getErrorResource().getErrorMessage(), e.getErrorResource().getErrorCode(),
        HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);

  }


  @ExceptionHandler(UnsupportedCurrencyTypeException.class)
  public @ResponseBody
  ResponseEntity<Object> unsupportedCurrencyTypeException(UnsupportedCurrencyTypeException e) {
    return getExceptionResponseEntity(e.getErrorResource().getErrorMessage(), e.getErrorResource().getErrorCode(), HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(EmptyParametersException.class)
  public ResponseEntity<Object> emptyParametersException(EmptyParametersException e) {
    return getExceptionResponseEntity(e.getErrorResource().getErrorMessage(), 400, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    String exceptionMessage =
        exception.getBindingResult().getAllErrors().stream()
            .map(fieldError -> ((FieldError) fieldError).getField() + "=>" + fieldError.getDefaultMessage()).collect(
                Collectors.joining(", "));
    return getExceptionResponseEntity(exceptionMessage, 400, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException exception) {
    String exceptionMessage = exception.getMessage();
    return getExceptionResponseEntity(exceptionMessage, 400, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
    String exceptionMessage =
        exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
    return getExceptionResponseEntity(exceptionMessage, 400, HttpStatus.BAD_REQUEST);
  }


  private static ResponseEntity<Object> getExceptionResponseEntity(String exceptionMessage, int errorCode, HttpStatus status) {
    ErrorResource errorResource = ErrorResource.builder().errorCode(errorCode).errorMessage(exceptionMessage).build();
    return new ResponseEntity<>(errorResource, status);
  }

}
