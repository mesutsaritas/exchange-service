package com.exchange.web.resources;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author msaritas
 */
@NoArgsConstructor
@Setter
@Getter
public class ExchangeConversionResource implements Serializable {

  private static final long serialVersionUID = 1L;

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 5, fraction = 2)
  @NotNull(message = "Amount is mandatory")
  private BigDecimal amount;

  @NotBlank(message = "Source is mandatory")
  private String source;

  @NotEmpty(message = "Target List is mandatory")
  private List<String> targets;
}
