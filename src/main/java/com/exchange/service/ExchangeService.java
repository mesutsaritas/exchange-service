package com.exchange.service;

import com.exchange.enums.CurrencyType;
import com.exchange.model.Transaction;
import com.exchange.model.TransactionDetail;
import com.exchange.web.exception.EmptyParametersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeRateResponseResource;
import com.exchange.web.resources.TransactionDetailResource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author msaritas
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ExchangeService {

  private final TransactionService transactionService;

  private final CurrencyService currencyService;

  public ExchangeRateResponseResource exchangeRate(String source, List<String> targets)
      throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
    checkCurrencyType(source, targets);
    Map<String, BigDecimal> pairExchangeList = currencyService.callCurrencyLayerLiveService(source,
        targets);
    return ExchangeRateResponseResource.builder().exchangeRates(pairExchangeList).build();
  }

  public ConversionResponseResource exchangeConversion(BigDecimal amount, String source, List<String> targets)
      throws ServiceUnavailableException, UnsupportedCurrencyTypeException {

    checkCurrencyType(source, targets);

    Map<String, BigDecimal> exchangeRateMap = currencyService.callCurrencyLayerLiveService(source,
        targets);

    Transaction transaction = Transaction.builder().source(source).amount(amount).build();
    List<TransactionDetail> transactionDetails = getTransactionDetails(amount, exchangeRateMap,
        transaction);

    transaction.setTransactionDetail(new HashSet<>(transactionDetails));
    Transaction transactionFromDB = transactionService.save(transaction);

    List<TransactionDetailResource> calculatedAmountList = transactionDetails.stream().map(this::convertTransactionDetail)
        .collect(Collectors.toList());

    return ConversionResponseResource.builder().transactionId(transactionFromDB.getId()).calculatedAmountList(calculatedAmountList).build();
  }

  @Transactional(readOnly = true)
  public List<Transaction> exchangeList(Long transactionId, Date conversionDate) throws EmptyParametersException {
    if (transactionId == null && conversionDate == null) {
      throw new EmptyParametersException();
    }
    log.info("Transaction service queried with these parameters transactionId:{} and conversionDate:{}",
        transactionId, conversionDate);
    return transactionService.findByIdOrCreatedDate(transactionId, conversionDate);
  }

  private List<TransactionDetail> getTransactionDetails(BigDecimal amount,
      Map<String, BigDecimal> exchangeRateMap, Transaction transaction) {
    List<TransactionDetail> transactionDetails = new ArrayList<>();

    exchangeRateMap.forEach((key, value) -> {
      TransactionDetail transactionDetail = TransactionDetail.builder().exchangeRate(value)
          .calculatedAmount(value.multiply(amount)).target(key).transaction(transaction).build();
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
