package com.exchange.repository;

import com.exchange.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author msaritas
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByIdOrCreatedDate(Long Id, Date conversionDate);

}
