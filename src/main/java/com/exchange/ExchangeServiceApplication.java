package com.exchange;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author msaritas
 */

@OpenAPIDefinition(info = @Info(title = "Exchange Service API", version = "1.0"))
@EnableFeignClients
@SpringBootApplication
public class ExchangeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExchangeServiceApplication.class, args);
  }
}
