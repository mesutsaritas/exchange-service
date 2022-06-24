package com.exchange.service;

import com.exchange.enums.CurrencyType;
import com.exchange.model.Transaction;
import com.exchange.model.TransactionDetail;
import com.exchange.web.exception.EmptyParametersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;
import com.exchange.web.resources.TransactionDetailResource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author msaritas
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ExchangeService {

  private final TransactionService transactionService;

  private final CurrencyService currencyService;

  public ExchangeRateResource exchangeRate(ExchangeRateResource exchangeRateResource)
      throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    checkCurrencyType(exchangeRateResource.getSource(), exchangeRateResource.getTargets());

    Map<String, BigDecimal> pairExchangeList = currencyService.callCurrencyLayerLiveService(exchangeRateResource.getSource(),
        exchangeRateResource.getTargets());

    return ExchangeRateResource.builder().exchangeRates(pairExchangeList).build();
  }

  public ConversionResponseResource exchangeConversion(ExchangeConversionResource exchangeConversionResource)
      throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    String sourceCurrencies = exchangeConversionResource.getSource();
    List<String> targetCurrencies = exchangeConversionResource.getTargets();
    BigDecimal amount = exchangeConversionResource.getAmount();

    checkCurrencyType(exchangeConversionResource.getSource(), exchangeConversionResource.getTargets());

    Map<String, BigDecimal> exchangeRateMap = currencyService.callCurrencyLayerLiveService(sourceCurrencies,
        exchangeConversionResource.getTargets());

    Transaction transaction = Transaction.builder().source(sourceCurrencies).amount(amount).build();
    List<TransactionDetail> transactionDetails = getTransactionDetails(sourceCurrencies, targetCurrencies, amount, exchangeRateMap,
        transaction);

    transaction.setTransactionDetail(new HashSet<>(transactionDetails));
    Transaction transactionFromDB = transactionService.save(transaction);

    List<TransactionDetailResource> calculatedAmountList = transactionDetails.stream().map(this::convertTransactionDetail)
        .collect(Collectors.toList());

    return ConversionResponseResource.builder().transactionId(transactionFromDB.getId()).calculatedAmountList(calculatedAmountList).build();
  }

  @Transactional(readOnly = true)
  public List<Transaction> exchangeList(ExchangeListResource exchangeListResource) throws EmptyParametersException {
    if (ObjectUtils.isEmpty(exchangeListResource.getTransactionId()) && ObjectUtils.isEmpty(exchangeListResource.getConversionDate())) {
      throw new EmptyParametersException();
    }
    log.info("Transaction service queried with these parameters transactionId:{} and conversionDate:{}",
        exchangeListResource.getTransactionId(), exchangeListResource.getConversionDate());
    return transactionService.findByIdOrCreatedDate(exchangeListResource.getTransactionId(), exchangeListResource.getConversionDate());
  }

  private List<TransactionDetail> getTransactionDetails(String sourceCurrencies, List<String> targetCurrencies, BigDecimal amount,
      Map<String, BigDecimal> exchangeRateMap, Transaction transaction) {
    List<TransactionDetail> transactionDetails = new ArrayList<>();

    targetCurrencies.forEach(currency -> {
      String findCurrency = sourceCurrencies + currency;
      BigDecimal exchangeRate = exchangeRateMap.get(findCurrency);
      TransactionDetail transactionDetail = TransactionDetail.builder().exchangeRate(exchangeRate)
          .calculatedAmount(exchangeRate.multiply(amount)).target(currency).transaction(transaction).build();
      transactionDetails.add(transactionDetail);
    });

    return transactionDetails;
  }

  private TransactionDetailResource convertTransactionDetail(TransactionDetail transactionDetail) {
    return TransactionDetailResource.builder().calculatedAmount(transactionDetail.getCalculatedAmount())
        .target(transactionDetail.getTarget()).build();
  }

  private static void checkCurrencyType(String source, List<String> targets) throws UnsupportedCurrencyTypeException {
    List<String> currencies = new ArrayList<>(targets);
    currencies.add(source);
    for (String currency : currencies) {
      CurrencyType.getByValue(currency);
    }
  }
}
