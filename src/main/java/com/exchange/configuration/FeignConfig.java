package com.exchange.configuration;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author msaritas
 */
@Configuration
public class FeignConfig {

    @Bean
    public OkHttpClient otpServiceClient() {
        return new OkHttpClient();
    }

}
