package com.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.exchange.dataloader.ExchangeDataLoader;
import com.exchange.model.Transaction;
import com.exchange.util.TestUtil;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;
import java.math.BigDecimal;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * @author msaritas
 */
@ExtendWith(MockitoExtension.class)
class ExchangeServiceTests {

  @InjectMocks
  ExchangeService exchangeService;

  @Mock
  TransactionService transactionService;

  @Mock
  CurrencyService currencyService;

  InOrder inOrder;

  private EasyRandom easyRandom;


  @BeforeEach
  void setUp() {
    inOrder = Mockito.inOrder(transactionService, currencyService);
    easyRandom = new EasyRandom(TestUtil.getEasyRandomParameters());
  }

  @DisplayName("Test Exchange Rate Service Scenario")
  @Test
  void exchangeRate() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    final var exchangeRateResource = ExchangeDataLoader.getExchangeRateResource();

    // given
    when(currencyService.callCurrencyLayerLiveService(any(), any())).thenReturn(BigDecimal.TEN);

    //when
    final var response = exchangeService.exchangeRate(exchangeRateResource);

    //then
    inOrder.verify(currencyService).callCurrencyLayerLiveService(any(), any());
    inOrder.verifyNoMoreInteractions();

    assertThat(response.getExchangeRates()).isNotNull();

  }


  @DisplayName("Test Exchange Rate Service UnsupportedCurrencyTypeException Scenario")
  @Test
  void exchangeRate_throwsException_when_UnsupportedCurrencyTypeException() {
    final var exchangeRateResource = easyRandom.nextObject(ExchangeRateResource.class);

    //when
    Throwable throwable = catchThrowable(() -> exchangeService.exchangeRate(exchangeRateResource));

    //then
    assertThat(throwable).isNotNull().isExactlyInstanceOf(UnsupportedCurrencyTypeException.class);

  }

  @DisplayName("Test Exchange Conversion Service Scenario")
  @Test
  void exchangeConversion() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    final var transaction = ExchangeDataLoader.getTransaction().get(0);
    final var exchangeConversionResource = ExchangeDataLoader.getExchangeConversionResource();

    //given
    when(currencyService.callCurrencyLayerLiveService(any(), any())).thenReturn(BigDecimal.TEN);

    when(transactionService.save(any())).thenReturn(transaction);

    //when
    final var response = exchangeService.exchangeConversion(exchangeConversionResource);

    //then
    inOrder.verify(currencyService).callCurrencyLayerLiveService(any(), any());
    inOrder.verify(transactionService).save(any());
    inOrder.verifyNoMoreInteractions();

    assertThat(response.getTransactionId()).isNotNull();

  }

  @DisplayName("Test Exchange Conversion Service UnsupportedCurrencyTypeException Scenario")
  @Test
  void exchangeConversion_throwsException_when_UnsupportedCurrencyTypeException() {
    final var exchangeConversionResource = easyRandom.nextObject(ExchangeConversionResource.class);

    //when
    Throwable throwable = catchThrowable(() -> exchangeService.exchangeConversion(exchangeConversionResource));

    //then
    assertThat(throwable).isNotNull().isExactlyInstanceOf(UnsupportedCurrencyTypeException.class);
  }


  @DisplayName("Test Exchange List Service Scenario")
  @Test
  void exchangeList() throws EmptyParamatersException {
    List<Transaction> transactions = ExchangeDataLoader.getTransaction();
    final var exchangeListResource = ExchangeDataLoader.getExchangeListResource();

    //given
    when(transactionService.findAllByConversionDate(any(), any())).thenReturn(transactions);

    //when
    final var response = exchangeService.exchangeList(exchangeListResource);

    //then
    inOrder.verify(transactionService).findAllByConversionDate(any(), any());
    inOrder.verifyNoMoreInteractions();

    assertThat(response.get(0).getAmount()).isNotNull();
  }


  @DisplayName("Test Exchange List Service EmptyParamatersException Scenario")
  @Test
  void exchangeList_throwsException_when_EmptyParamatersException() {
    final var exchangeListResource = easyRandom.nextObject(ExchangeListResource.class);
    exchangeListResource.setTransactionId(null);
    exchangeListResource.setConversionDate(null);

    //when
    Throwable throwable = catchThrowable(() -> exchangeService.exchangeList(exchangeListResource));

    //then
    assertThat(throwable).isNotNull().isExactlyInstanceOf(EmptyParamatersException.class);
  }


}
