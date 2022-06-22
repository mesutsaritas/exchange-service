package com.exchange.service;

import com.exchange.model.Transaction;
import com.exchange.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * @author msaritas
 */
@RequiredArgsConstructor
@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;


    /**
     * 
     * @return
     */
    public Transaction save(Transaction transaction) {
        Transaction transactionFromDB = transactionRepository.save(transaction);
        return transactionFromDB;
    }


    /**
     * 
     * @param transactionId
     * @param conversionDate
     * @return
     */
    public List<Transaction> findAllByConversionDate(Long transactionId, Date conversionDate) {
        return transactionRepository.findByIdOrCreatedDate(transactionId, conversionDate);
    }


}
