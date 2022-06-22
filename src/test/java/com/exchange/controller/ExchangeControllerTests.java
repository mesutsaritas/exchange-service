package com.exchange.controller;

import com.exchange.model.Transaction;
import com.exchange.service.ExchangeService;
import com.exchange.util.TestUtil;
import com.exchange.web.controller.ExchangeController;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.*;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        inOrder = Mockito.inOrder(exchangeService);
        easyRandom = new EasyRandom(TestUtil.getEasyRandomParameters());
    }

    @DisplayName("Test Rate API Scenario")
    @Test
    void rate() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

        final var exchangeRateRequest = easyRandom.nextObject(ExchangeRateResource.class);
        final var exchangeRateResponse = easyRandom.nextObject(ExchangeRateResource.class);

        //given
        when(exchangeService.exchangeRate(exchangeRateRequest)).thenReturn(exchangeRateResponse);

        //when
        final ResponseEntity<ExchangeRateResource> exchangeRateResourceApi = exchangeController.rate(exchangeRateRequest);


        //then
        assertNotNull(exchangeRateResourceApi.getBody());
        assertThat(exchangeRateResourceApi.getBody().getExchangeRate()).isNotNull();

        inOrder.verify(exchangeService).exchangeRate(exchangeRateRequest);
        inOrder.verifyNoMoreInteractions();

    }

    @DisplayName("Test Conversion API Scenario")
    @Test
    void conversion() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

        final var exchangeConversionRequest = easyRandom.nextObject(ExchangeConversionResource.class);
        final var exchangeConversionResponse = easyRandom.nextObject(ConversionResponseResource.class);

        //given
        when(exchangeService.exchangeConversion(exchangeConversionRequest)).thenReturn(exchangeConversionResponse);

        //when
        final ResponseEntity<ConversionResponseResource> exchangeRateResourceApi = exchangeController.conversion(exchangeConversionRequest);

        // then
        assertNotNull(exchangeRateResourceApi.getBody());
        assertThat(exchangeRateResourceApi.getBody().getAmount()).isNotNull();

        inOrder.verify(exchangeService).exchangeConversion(exchangeConversionRequest);
        inOrder.verifyNoMoreInteractions();

    }


    @Test
    void list() throws ServiceUnavailableException, EmptyParamatersException {

        final var listRequest = easyRandom.nextObject(ExchangeListResource.class);
        List<Transaction> listResponse = easyRandom.objects(Transaction.class, 5).collect(Collectors.toList());

        //given
        when(exchangeService.exchangeList(listRequest)).thenReturn(listResponse);

        // when
        final ResponseEntity<CollectionModel<TransactionResource>> exchangeRateResourceApi = exchangeController.list(listRequest);

        // then
        assertNotNull(exchangeRateResourceApi.getBody());

        inOrder.verify(exchangeService).exchangeList(listRequest);
        inOrder.verifyNoMoreInteractions();

    }


}
