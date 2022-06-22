package com.exchange.web.resources;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author msaritas
 */
@Builder
@Getter
@Setter
public class TransactionResource extends RepresentationModel<TransactionResource> {

    private String source;

    private String target;

    private BigDecimal exhangeRate;

    private BigDecimal amount;

    private BigDecimal calculatedAmount;

    private Date transactionDate;

}
