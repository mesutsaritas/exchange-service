package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author msaritas
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ExchangeRateResource implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "Source is mandatory")
  private String source;

  @NotEmpty(message = "Target List is mandatory")
  private List<String> targets;

  private Map<String, BigDecimal> exchangeRates;
}
