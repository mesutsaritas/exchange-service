package com.exchange.web.resources;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author msaritas
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ConversionResponseResource implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long transactionId;
  private List<TransactionDetailResource> calculatedAmountList;
}
