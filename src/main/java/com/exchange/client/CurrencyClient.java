package com.exchange.client;

import com.exchange.web.resources.CurrencyServiceResponseResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author msaritas
 */
@FeignClient(name = "currency-client", url = "${currency-service.base-url}")
public interface CurrencyClient {

  @GetMapping
  CurrencyServiceResponseResource callCurrencyApi(@RequestParam(value = "access_key") String accessKey,
      @RequestParam(value = "source") String source,
      @RequestParam(value = "currencies") String currencies,
      @RequestParam(value = "format") String format);
}