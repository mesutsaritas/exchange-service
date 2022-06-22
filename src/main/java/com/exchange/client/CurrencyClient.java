package com.exchange.client;

import com.exchange.configuration.FeignConfig;
import com.exchange.web.resources.CurrenyServiceResponseResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;


/**
 * @author msaritas
 */
@FeignClient(name = "currency-client", configuration = FeignConfig.class)
public interface CurrencyClient {

    @GetMapping
    CurrenyServiceResponseResource callCurrenyApi(URI baseUri);

}
