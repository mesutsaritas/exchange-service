package com.exchange.web.exception;

import javax.validation.ConstraintViolationException;


import com.exchange.web.resources.ErrorResource;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author msaritas
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ServiceUnavailableException.class)
    public @ResponseBody
    ResponseEntity<ErrorResource> serviceUnavailableException(ServiceUnavailableException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    /**
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = UnsupportedCurrencyTypeException.class)
    public @ResponseBody
    ResponseEntity<ErrorResource> unsupportedCurrencyTypeException(UnsupportedCurrencyTypeException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = EmptyParamatersException.class)
    public @ResponseBody
    ResponseEntity<ErrorResource> emptyParamatersException(EmptyParamatersException e) throws Exception {
        return new ResponseEntity<>(e.getErrorResource(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> validationErrors = exception.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    /**
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        List<String> validationErrors = exception.getConstraintViolations().stream().map(violation ->
                violation.getPropertyPath() + ": " + violation.getMessage()).collect(Collectors.toList());
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    /**
     * @param status
     * @param request
     * @param errors
     * @return
     */
    private ResponseEntity<Object> getExceptionResponseEntity(final HttpStatus status, WebRequest request, List<String> errors) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String errorsMessage = !CollectionUtils.isEmpty(errors) ? errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")) : status.getReasonPhrase();
        final String path = request.getDescription(false);
        body.put("Errors", errorsMessage);
        body.put("Path", path);
        body.put("Message", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }

}
