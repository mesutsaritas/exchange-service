package com.exchange.repository;

import com.exchange.model.Transaction;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author msaritas
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByIdOrCreatedDate(Long Id, Date conversionDate);

}
