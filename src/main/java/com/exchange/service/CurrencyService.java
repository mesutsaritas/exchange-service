package com.exchange.service;

import com.exchange.client.CurrencyClient;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.resources.CurrenyServiceResponseResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author msaritas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

  @Value("${currency-service.base-url}")
  private String currencyLayerUrl;

  @Value("${currency-service.api-key}")
  private String currencyServiceApiKey;

  private final CurrencyClient fixerClient;

  public Map<String, BigDecimal> callCurrencyLayerLiveService(String source, List<String> targets)
      throws ServiceUnavailableException {

    String currencies = targets.stream().map(String::valueOf).collect(Collectors.joining(","));

    final var uriComponents =
        UriComponentsBuilder.fromHttpUrl(currencyLayerUrl)
            .queryParam("access_key", currencyServiceApiKey)
            .queryParam("source", source)
            .queryParam("currencies", currencies)
            .build();
    CurrenyServiceResponseResource fixerResponseResource =
        fixerClient.callCurrenyApi(uriComponents.toUri());
    if (!fixerResponseResource.getSuccess()) {
      log.warn("[Getting error from Currency Service! description:{}]", fixerResponseResource.getError().getInfo());
      throw new ServiceUnavailableException();
    }
    return fixerResponseResource.getQuotes();
  }
}
