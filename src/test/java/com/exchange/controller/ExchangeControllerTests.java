package com.exchange.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.exchange.dataloader.ExchangeDataLoader;
import com.exchange.model.Transaction;
import com.exchange.service.ExchangeService;
import com.exchange.web.controller.ExchangeController;
import com.exchange.web.exception.EmptyParametersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeRateResponseResource;
import com.exchange.web.resources.TransactionResponseResource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

/**
 * @author msaritas
 */
@ExtendWith(MockitoExtension.class)
class ExchangeControllerTests {

  @InjectMocks
  ExchangeController exchangeController;

  @Mock
  ExchangeService exchangeService;

  InOrder inOrder;


  @BeforeEach
  void setUp() {
    inOrder = Mockito.inOrder(exchangeService);
  }

  @DisplayName("Test Rate API Scenario")
  @Test
  void rate() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    final var exchangeRateResponse = ExchangeDataLoader.getExchangeRateResource();

    //given
    when(exchangeService.exchangeRate(any(), any())).thenReturn(exchangeRateResponse);

    //when
    final ResponseEntity<ExchangeRateResponseResource> exchangeRateResourceApi = exchangeController.rate("USD", List.of("TRY", "USD"));

    //then
    inOrder.verify(exchangeService).exchangeRate(any(), any());
    inOrder.verifyNoMoreInteractions();

    assertNotNull(exchangeRateResourceApi.getBody());
    assertThat(exchangeRateResourceApi.getBody().getExchangeRates()).isNotNull();

  }

  @DisplayName("Test Conversion API Scenario")
  @Test
  void conversion() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

    final var exchangeConversionResponse = ExchangeDataLoader.getConversionResponseResource();

    //given
    when(exchangeService.exchangeConversion(any(), any(), any())).thenReturn(exchangeConversionResponse);

    //when
    final ResponseEntity<ConversionResponseResource> exchangeRateResourceApi = exchangeController.conversion(BigDecimal.TEN, "USD",
        List.of("EUR", "GBP"));

    // then
    inOrder.verify(exchangeService).exchangeConversion(any(), any(), any());
    inOrder.verifyNoMoreInteractions();
    assertNotNull(exchangeRateResourceApi.getBody());
  }

  @DisplayName("Test List API Scenario")
  @Test
  void list() throws EmptyParametersException {

    Long transactionId = 1L;
    Date conversionDate = new Date();

    Transaction listResponse = ExchangeDataLoader.getTransaction();

    //given
    when(exchangeService.exchangeList(any(), any())).thenReturn(List.of(listResponse));

    //when
    final ResponseEntity<CollectionModel<TransactionResponseResource>> transactionResponse = exchangeController.list(transactionId,
        conversionDate);

    //then
    inOrder.verify(exchangeService).exchangeList(transactionId, conversionDate);
    inOrder.verifyNoMoreInteractions();
    assertNotNull(transactionResponse.getBody());

  }

}
