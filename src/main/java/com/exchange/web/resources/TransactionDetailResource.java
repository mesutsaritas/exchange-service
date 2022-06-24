package com.exchange.web.resources;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * @author msaritas
 */
@Builder
@Data
public class TransactionDetailResource {

  private BigDecimal calculatedAmount;

  private String target;
}
