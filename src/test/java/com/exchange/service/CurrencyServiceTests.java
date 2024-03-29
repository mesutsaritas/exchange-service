package com.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.exchange.client.CurrencyClient;
import com.exchange.util.TestUtil;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.resources.CurrencyServiceResponseResource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
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
class CurrencyServiceTests {

  @InjectMocks
  CurrencyService currencyService;

  @Mock
  CurrencyClient currencyClient;

  InOrder inOrder;

  private EasyRandom easyRandom;

  @BeforeEach
  void setUp() {
    inOrder = Mockito.inOrder(currencyClient);
    easyRandom = new EasyRandom(TestUtil.getEasyRandomParameters());
  }

  @DisplayName("Test callCurrencyLayerLiveService Success Scenario")
  @Test
  void callCurrencyLayerLiveService() throws ServiceUnavailableException {

    CurrencyServiceResponseResource mockCurrencyServiceResponseResource = easyRandom.nextObject(CurrencyServiceResponseResource.class);
    mockCurrencyServiceResponseResource.setSuccess(Boolean.TRUE);
    //given
    when(currencyClient.callCurrencyApi(any(), any(), any(), any())).thenReturn(mockCurrencyServiceResponseResource);

    //when
    Map<String, BigDecimal> serviceResult = currencyService.callCurrencyLayerLiveService("TRY", Collections.singletonList("EUR"));

    //then
    inOrder.verify(currencyClient).callCurrencyApi(any(), any(), any(), any());
    inOrder.verifyNoMoreInteractions();

    assertThat(serviceResult).isNotNull();

  }


  @DisplayName("Test callCurrencyLayerLiveService ServiceUnavailableException Scenario")
  @Test
  void callCurrencyLayerLiveService_when_ServiceUnavailableException() {
    CurrencyServiceResponseResource mockCurrencyServiceResponseResource = easyRandom.nextObject(CurrencyServiceResponseResource.class);
    mockCurrencyServiceResponseResource.setSuccess(Boolean.FALSE);
    //given
    when(currencyClient.callCurrencyApi(any(), any(), any(), any())).thenReturn(mockCurrencyServiceResponseResource);

    //when
    Throwable throwable = catchThrowable(() -> currencyService.callCurrencyLayerLiveService("TRY", Collections.singletonList("EUR")));

    //then
    assertThat(throwable).isNotNull().isExactlyInstanceOf(ServiceUnavailableException.class);

  }


}
