package com.exchange.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author msaritas
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACTION_DETAIL")
@SequenceGenerator(
    name = "default_gen",
    sequenceName = "SEQ_TRANSACTION_DETAIL",
    allocationSize = 1)
@Getter
@Setter
public class TransactionDetail extends Auditable<String, Long> {

  private static final long serialVersionUID = 1L;

  @Column(name = "TARGET")
  private String target;

  @Column(name = "EXCHANGE_RATE")
  private BigDecimal exchangeRate;

  @Column(name = "CALCULATED_AMOUNT")
  private BigDecimal calculatedAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "ID")
  private Transaction transaction;
}
