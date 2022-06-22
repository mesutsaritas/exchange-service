package com.exchange.service;

import com.exchange.enums.CurrencyType;
import com.exchange.model.Transaction;
import com.exchange.web.exception.EmptyParamatersException;
import com.exchange.web.exception.ServiceUnavailableException;
import com.exchange.web.exception.UnsupportedCurrencyTypeException;
import com.exchange.web.resources.ConversionResponseResource;
import com.exchange.web.resources.ExchangeConversionResource;
import com.exchange.web.resources.ExchangeListResource;
import com.exchange.web.resources.ExchangeRateResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author msaritas
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ExchangeService {


    private final TransactionService transactionService;

    private final CurrencyService currencyService;


    public ExchangeRateResource exchangeRate(ExchangeRateResource exchangeRateResource) throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
        checkCurrencyType(List.of(exchangeRateResource.getSource(), exchangeRateResource.getTarget()));
        log.info("[ExchangeService][exchangeRate][Currency live service is called with parameters source:{} and target:{} ]", exchangeRateResource.getSource(), exchangeRateResource.getTarget());
        BigDecimal exchangeRate = currencyService.callCurrencyLayerLiveService(exchangeRateResource.getSource(), exchangeRateResource.getTarget());
        return ExchangeRateResource.builder().exchangeRate(exchangeRate).build();

    }

    public ConversionResponseResource exchangeConversion(ExchangeConversionResource exchangeConversionResource) throws ServiceUnavailableException, UnsupportedCurrencyTypeException {
        String source = exchangeConversionResource.getSource();
        String target = exchangeConversionResource.getTarget();
        BigDecimal amount = exchangeConversionResource.getAmount();

        checkCurrencyType(List.of(source, target));
        BigDecimal exchangeRate = currencyService.callCurrencyLayerLiveService(source, target);
        BigDecimal calculatedAmount = exchangeRate.multiply(amount);
        Transaction transaction = Transaction.builder().calculatedAmount(calculatedAmount).source(source).target(target).amount(amount).exhangeRate(exchangeRate).build();
        Transaction transactionFromDB = transactionService.save(transaction);
        log.info("[ExchangeService][exchangeConversion][Currency conversion service is called with parameters source:{} and target:{} and amount:{} ]", source, target, amount);
        return ConversionResponseResource.builder().amount(calculatedAmount).transactionId(transactionFromDB.getId()).build();
    }


    @Transactional(readOnly = true)
    public List<Transaction> exchangeList(ExchangeListResource exchangeListResource) throws EmptyParamatersException {
        if (ObjectUtils.isEmpty(exchangeListResource.getTransactionId()) && ObjectUtils.isEmpty(exchangeListResource.getConversionDate())) {
            throw new EmptyParamatersException();
        }
        log.info("[ExchangeService][exchangeList][Transaction service queried with these parameters transactionId:{} and conversionDate:{}]", exchangeListResource.getTransactionId(), exchangeListResource.getConversionDate());
        return transactionService.findAllByConversionDate(exchangeListResource.getTransactionId(), exchangeListResource.getConversionDate());
    }


    private static void checkCurrencyType(List<String> currencies) throws UnsupportedCurrencyTypeException {
        for (String currency : currencies) {
            CurrencyType.getByValue(currency);
        }
    }


}
