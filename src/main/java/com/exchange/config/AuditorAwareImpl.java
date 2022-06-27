package com.exchange.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 * @author msaritas
 */
public class AuditorAwareImpl implements AuditorAware<String> {


  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("ExchangeService");
  }
}
