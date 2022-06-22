package com.exchange.web.controller;


import com.exchange.service.ExchangeService;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author msaritas
 *
 */
@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {


    private final ExchangeService exchangeService;

    private TransactionResourceAssembler transactionResourceAssembler;

    @Operation(summary = "Currency Pair To Retrieve The Exchange Rate")
    @PostMapping("/rate")
    public ResponseEntity<ExchangeRateResource> rate(@Valid @RequestBody ExchangeRateResource exchangeRateResource)
                    throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
        return ResponseEntity.ok().body(exchangeService.exchangeRate(exchangeRateResource));

    }

    @Operation(summary = "Conversion between currencies")
    @PostMapping("/conversion")
    public ResponseEntity<ConversionResponseResource> conversion(
                    @Valid @RequestBody ExchangeConversionResource exchangeConversionResource)
                    throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
        return ResponseEntity.ok().body(exchangeService.exchangeConversion(exchangeConversionResource));


    }

    @Operation(summary = "Listing an Exchange By Transaction Id or Conversion Date")
    @PostMapping("/list")
    public ResponseEntity<CollectionModel<TransactionResource>> list(
                    @Valid @RequestBody ExchangeListResource exchangeListResource) throws EmptyParamatersException {
        transactionResourceAssembler = new TransactionResourceAssembler();
        return ResponseEntity.ok().body(transactionResourceAssembler
                        .toCollectionModel(exchangeService.exchangeList(exchangeListResource)));


    }

}
