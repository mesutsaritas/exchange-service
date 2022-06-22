package com.exchange.web.resources;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author msaritas
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ConversionResponseResource implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long transactionId;
    private BigDecimal amount;


}
