package com.exchange.web.resources;


import com.exchange.model.Transaction;
import com.exchange.web.controller.ExchangeController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * @author msaritas
 */
public class TransactionResourceAssembler extends RepresentationModelAssemblerSupport<Transaction, TransactionResource> {

    public TransactionResourceAssembler() {
        super(ExchangeController.class, TransactionResource.class);
    }

    @Override
    public TransactionResource toModel(Transaction entity) {
        return TransactionResource.builder().amount(entity.getAmount()).calculatedAmount(entity.getCalculatedAmount()).transactionDate(entity.getCreatedDate()).source(entity.getSource()).target(entity.getTarget()).build();
    }

}
