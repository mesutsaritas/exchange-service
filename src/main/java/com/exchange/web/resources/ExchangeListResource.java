package com.exchange.web.resources;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author msaritas
 */
@Setter
@Getter
public class ExchangeListResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long transactionId;

    private Date conversionDate;

}
