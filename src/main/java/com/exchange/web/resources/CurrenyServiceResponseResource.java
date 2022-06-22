package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author msaritas
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CurrenyServiceResponseResource implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("base")
    private String base;

    @JsonProperty("error")
    private ServiceResponseErrorResource error;

    @JsonProperty("quotes")
    private Map<String, BigDecimal> quotes;

}
