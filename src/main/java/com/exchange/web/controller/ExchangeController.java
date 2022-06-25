package com.exchange.web.controller;

import com.exchange.service.ExchangeService;
import com.exchange.web.exception.EmptyParametersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeRateResponseResource;
import com.exchange.web.resources.TransactionResponseResource;
import com.exchange.web.resources.TransactionResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author msaritas
 */
@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

  private final ExchangeService exchangeService;

  private TransactionResourceAssembler transactionResourceAssembler;

  @Operation(summary = "Currency Pair To Retrieve The Exchange Rate")
  @GetMapping("/rate")
  public ResponseEntity<ExchangeRateResponseResource> rate(@RequestParam(value = "source") String source,
      @RequestParam(value = "targets") List<String> targets)
      throws ServiceUnavailableException,
      UnsupportedCurrencyTypeException {
    return ResponseEntity.ok().body(exchangeService.exchangeRate(source, targets));
  }

  @Operation(summary = "Conversion between currencies")
  @GetMapping("/conversion")
  public ResponseEntity<ConversionResponseResource> conversion(
      @RequestParam(value = "amount") BigDecimal amount,
      @RequestParam(value = "source") String source,
      @RequestParam(value = "targets") List<String> targets
  )
      throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    return ResponseEntity.ok().body(exchangeService.exchangeConversion(amount, source, targets));
  }

  @Operation(summary = "Listing an Exchange By Transaction Id or Conversion Date")
  @GetMapping("/list")
  public ResponseEntity<CollectionModel<TransactionResponseResource>> list(
      @RequestParam(value = "transactionId", required = false) Long transactionId,
      @RequestParam(value = "conversionDate", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") Date conversionDate)
      throws EmptyParametersException {
    transactionResourceAssembler = new TransactionResourceAssembler();
    return ResponseEntity.ok()
        .body(transactionResourceAssembler.toCollectionModel(exchangeService.exchangeList(transactionId, conversionDate)));
  }
}
