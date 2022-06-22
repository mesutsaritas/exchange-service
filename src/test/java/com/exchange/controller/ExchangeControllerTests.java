package com.exchange.controller;

import com.exchange.dataloader.ExchangeDataLoader;
import com.exchange.model.Transaction;
import com.exchange.service.ExchangeService;
import com.exchange.web.controller.ExchangeController;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeRateResource;
import com.exchange.web.resources.TransactionResource;
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


    @BeforeEach
    void setUp() {
        inOrder = Mockito.inOrder(exchangeService);
    }

    @DisplayName("Test Rate API Scenario")
    @Test
    void rate() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

        final var exchangeRateRequest = ExchangeDataLoader.getExchangeRateResource();
        final var exchangeRateResponse = ExchangeDataLoader.getExchangeRateResource();

        //given
        when(exchangeService.exchangeRate(exchangeRateRequest)).thenReturn(exchangeRateResponse);

        //when
        final ResponseEntity<ExchangeRateResource> exchangeRateResourceApi = exchangeController.rate(exchangeRateRequest);

        //then
        inOrder.verify(exchangeService).exchangeRate(exchangeRateRequest);
        inOrder.verifyNoMoreInteractions();

        assertNotNull(exchangeRateResourceApi.getBody());
        assertThat(exchangeRateResourceApi.getBody().getExchangeRate()).isNotNull();


    }

    @DisplayName("Test Conversion API Scenario")
    @Test
    void conversion() throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

        final var exchangeConversionRequest = ExchangeDataLoader.getExchangeConversionResource();
        final var exchangeConversionResponse = ExchangeDataLoader.getConversionResponseResource();

        //given
        when(exchangeService.exchangeConversion(exchangeConversionRequest)).thenReturn(exchangeConversionResponse);

        //when
        final ResponseEntity<ConversionResponseResource> exchangeRateResourceApi = exchangeController.conversion(exchangeConversionRequest);

        // then
        inOrder.verify(exchangeService).exchangeConversion(exchangeConversionRequest);
        inOrder.verifyNoMoreInteractions();
        assertNotNull(exchangeRateResourceApi.getBody());
        assertThat(exchangeRateResourceApi.getBody().getAmount()).isNotNull();
    }

    @DisplayName("Test List API Scenario")
    @Test
    void list() throws EmptyParamatersException {

        final var exchangeListResource = ExchangeDataLoader.getExchangeListResource();
        List<Transaction> listResponse = ExchangeDataLoader.getTransaction();

        //given
        when(exchangeService.exchangeList(exchangeListResource)).thenReturn(listResponse);

        //when
        final ResponseEntity<CollectionModel<TransactionResource>> exchangeRateResourceApi = exchangeController.list(exchangeListResource);

        //then
        inOrder.verify(exchangeService).exchangeList(exchangeListResource);
        inOrder.verifyNoMoreInteractions();
        assertNotNull(exchangeRateResourceApi.getBody());

    }


}
