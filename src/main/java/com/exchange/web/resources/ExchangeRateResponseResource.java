package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author msaritas
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ExchangeRateResponseResource implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "Source is mandatory")
  private String source;

  @NotEmpty(message = "Target List is mandatory")
  private List<String> targets;

  private Map<String, BigDecimal> exchangeRates;
}
