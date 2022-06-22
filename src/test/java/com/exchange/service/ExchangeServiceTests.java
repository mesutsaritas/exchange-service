package com.exchange.service;

import com.exchange.model.Transaction;
import com.exchange.util.TestUtil;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
        final var exchangeRateResource = easyRandom.nextObject(ExchangeRateResource.class);

        // given
        when(currencyService.callCurrencyLayerLiveService(any(), any())).thenReturn(BigDecimal.TEN);

        //when
        final var response = exchangeService.exchangeRate(exchangeRateResource);
        // then
        assertThat(response.getExchangeRate()).isNotNull();

    }

    @DisplayName("Test Exchange Conversion Service Scenario")
    @Test
    void exchangeConversion() throws ServiceUnavailableException, EmptyParamatersException, UnsupportedCurrencyTypeException {
        final var transaction = easyRandom.nextObject(Transaction.class);
        final var exchangeConversionResource = easyRandom.nextObject(ExchangeConversionResource.class);

        // when
        when(currencyService.callCurrencyLayerLiveService(any(), any())).thenReturn(BigDecimal.TEN);

        when(transactionService.save(any())).thenReturn(transaction);

        final var response = exchangeService.exchangeConversion(exchangeConversionResource);

        assertThat(response.getAmount()).isNotNull();

    }

    @DisplayName("Test Exchange List Service Scenario")
    @Test
    void exchangeList() throws ServiceUnavailableException, EmptyParamatersException {
        List<Transaction> transactions =
                easyRandom.objects(Transaction.class, 5).collect(Collectors.toList());
        final var exchangeListResource = easyRandom.nextObject(ExchangeListResource.class);

        // given
        when(transactionService.findAllByConversionDate(any(), any())).thenReturn(transactions);

        // when
        final var response = exchangeService.exchangeList(exchangeListResource);

        // then
        assertThat(response.get(0).getAmount()).isNotNull();
    }


}
