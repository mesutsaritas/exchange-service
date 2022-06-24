package com.exchange.model;

import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author msaritas
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTION")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_TRANSACTION", allocationSize = 1)
@Getter
@Setter
public class Transaction extends AbstractEntity<Long> {

  private static final long serialVersionUID = 1L;

  @Column(name = "SOURCE")
  private String source;

  @Column(name = "AMOUNT")
  private BigDecimal amount;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "transaction", cascade = CascadeType.ALL)
  private Set<TransactionDetail> transactionDetail;
}
