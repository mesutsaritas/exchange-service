package com.exchange.dataloader;

import com.exchange.model.Transaction;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeRateResponseResource;
import com.exchange.web.resources.TransactionDetailResource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author msaritas
 */
public class ExchangeDataLoader {

  public static ExchangeRateResponseResource getExchangeRateResource() {

    Map<String, BigDecimal> maps = new HashMap<>();
    maps.put("EUR", BigDecimal.TEN);
    ExchangeRateResponseResource exchangeRateResource = new ExchangeRateResponseResource();
    exchangeRateResource.setExchangeRates(maps);
    exchangeRateResource.setSource("TRY");
    exchangeRateResource.setTargets(List.of("USD"));
    return exchangeRateResource;
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

  public static Transaction getTransaction() {
    return Transaction.builder()
        .id(1L)
        .source("USD")
        .amount(BigDecimal.ONE)
        .build();
  }
}
