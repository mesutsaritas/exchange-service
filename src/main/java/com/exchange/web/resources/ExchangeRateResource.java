package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author msaritas
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ExchangeRateResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Source is mandatory")
    private String source;

    @NotBlank(message = "Target is mandatory")
    private String target;

    private BigDecimal exchangeRate;

}
