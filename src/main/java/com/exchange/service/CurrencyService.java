package com.exchange.service;

import com.exchange.client.CurrencyClient;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.resources.CurrencyServiceResponseResource;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author msaritas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {


  @Value("${currency-service.api-key}")
  private String currencyServiceApiKey;


  private final CurrencyClient currencyClient;

  public Map<String, BigDecimal> callCurrencyLayerLiveService(String source, List<String> targets)
      throws ServiceUnavailableException {
    String currencies = targets.stream().map(String::valueOf).collect(Collectors.joining(","));

    CurrencyServiceResponseResource currencyServiceResponseResource =
        currencyClient.callCurrencyApi(currencyServiceApiKey, source, currencies, "1");

    if (!currencyServiceResponseResource.getSuccess()) {
      log.warn(
          "Getting error from Currency Service! description:{}",
          currencyServiceResponseResource.getError().getInfo());
      throw new ServiceUnavailableException();
    }

    LinkedHashMap<String, BigDecimal> resultMap = new LinkedHashMap<>();
    return currencyServiceResponseResource.getQuotes();
  }
}
