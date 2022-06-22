package com.exchange.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author msaritas
 *
 */
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TRANSACTION")
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_TRANSACTION", allocationSize = 1)
@Getter
@Setter
public class Transaction extends AbstractEntity<Long> {


    private static final long serialVersionUID = 1L;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "TARGET")
    private String target;

    @Column(name = "EXCHANGE_RATE")
    private BigDecimal exhangeRate;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CALCULATED_AMOUNT")
    private BigDecimal calculatedAmount;



}
