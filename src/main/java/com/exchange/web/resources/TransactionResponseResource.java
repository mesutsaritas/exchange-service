package com.exchange.web.resources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author msaritas
 */
@Builder
@Getter
@Setter
public class TransactionResponseResource extends RepresentationModel<TransactionResponseResource> {

  private Long transactionId;

  private String source;

  private BigDecimal amount;

  private Date transactionDate;

  private List<TransactionDetailResource> transactionDetails;

}
