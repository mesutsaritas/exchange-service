package com.exchange.dataloader;

import com.exchange.model.Transaction;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;
import com.exchange.web.resources.TransactionDetailResource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author msaritas
 */
public class ExchangeDataLoader {

  public static ExchangeRateResource getExchangeRateResource() {

    Map<String, BigDecimal> maps = new HashMap<>();
    maps.put("EUR", BigDecimal.TEN);
    ExchangeRateResource exchangeRateResource = new ExchangeRateResource();
    exchangeRateResource.setExchangeRates(maps);
    exchangeRateResource.setSource("TRY");
    exchangeRateResource.setTargets(List.of("USD"));
    return exchangeRateResource;
  }

  public static ExchangeConversionResource getExchangeConversionResource() {
    ExchangeConversionResource exchangeConversionResource = new ExchangeConversionResource();
    exchangeConversionResource.setAmount(BigDecimal.ZERO);
    exchangeConversionResource.setSource("TRY");
    exchangeConversionResource.setTargets(List.of("USD"));
    return exchangeConversionResource;
  }

  public static ConversionResponseResource getConversionResponseResource() {
    ConversionResponseResource conversionResponseResource = new ConversionResponseResource();
    conversionResponseResource.setTransactionId(1L);
    List<TransactionDetailResource> calculatedAmountList = new ArrayList<>();

    TransactionDetailResource transactionDetailResource =
        TransactionDetailResource.builder().target("TRY").calculatedAmount(new BigDecimal(10)).build();
    calculatedAmountList.add(transactionDetailResource);
    conversionResponseResource.setCalculatedAmountList(calculatedAmountList);

    return conversionResponseResource;
  }

  public static ExchangeListResource getExchangeListResource() {
    ExchangeListResource exchangeListResource = new ExchangeListResource();
    exchangeListResource.setTransactionId(1L);
    exchangeListResource.setConversionDate(new Date());
    return exchangeListResource;
  }

  public static List<Transaction> getTransaction() {
    Transaction transaction =
        Transaction.builder()
            .source("USD")
            .amount(BigDecimal.ONE)
            .build();
    return List.of(transaction);
  }
}
