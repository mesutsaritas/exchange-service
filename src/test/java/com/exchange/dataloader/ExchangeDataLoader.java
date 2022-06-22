package com.exchange.dataloader;

import com.exchange.model.Transaction;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author msaritas
 */
public class ExchangeDataLoader {


    public static ExchangeRateResource getExchangeRateResource() {
        ExchangeRateResource exchangeRateResource = new ExchangeRateResource();
        exchangeRateResource.setExchangeRate(BigDecimal.ZERO);
        exchangeRateResource.setSource("TRY");
        exchangeRateResource.setTarget("USD");
        return exchangeRateResource;

    }

    public static ExchangeConversionResource getExchangeConversionResource() {
        ExchangeConversionResource exchangeConversionResource = new ExchangeConversionResource();
        exchangeConversionResource.setAmount(BigDecimal.ZERO);
        exchangeConversionResource.setSource("TRY");
        exchangeConversionResource.setTarget("USD");
        return exchangeConversionResource;

    }

    public static ConversionResponseResource getConversionResponseResource() {
        ConversionResponseResource conversionResponseResource = new ConversionResponseResource();
        conversionResponseResource.setAmount(BigDecimal.ZERO);
        conversionResponseResource.setTransactionId(1L);
        return conversionResponseResource;

    }

    public static ExchangeListResource getExchangeListResource() {
        ExchangeListResource exchangeListResource = new ExchangeListResource();
        exchangeListResource.setTransactionId(1L);
        exchangeListResource.setConversionDate(new Date());
        return exchangeListResource;

    }

    public static List<Transaction> getTransaction() {
        Transaction transaction = Transaction.builder().calculatedAmount(BigDecimal.ZERO).source("USD").target("TRY").amount(BigDecimal.ONE).exhangeRate(BigDecimal.TEN).build();
        return List.of(transaction);

    }


}
