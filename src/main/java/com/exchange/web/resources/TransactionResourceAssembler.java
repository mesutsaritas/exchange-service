package com.exchange.web.resources;

import com.exchange.model.Transaction;
import com.exchange.model.TransactionDetail;
import com.exchange.web.controller.ExchangeController;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.util.CollectionUtils;

/**
 * @author msaritas
 */
public class TransactionResourceAssembler
    extends RepresentationModelAssemblerSupport<Transaction, TransactionResponseResource> {

  public TransactionResourceAssembler() {
    super(ExchangeController.class, TransactionResponseResource.class);
  }

  @Override
  public TransactionResponseResource toModel(Transaction entity) {

    var resource = TransactionResponseResource.builder();
    resource.transactionDate(entity.getCreatedDate());
    resource.source(entity.getSource());
    resource.amount(entity.getAmount());
    if (!CollectionUtils.isEmpty(entity.getTransactionDetail())) {
      List<TransactionDetailResource> transactionResourceList =
          entity.getTransactionDetail().stream().map(this::getTransactionDetail).collect(Collectors.toList());
      resource.transactionDetails(transactionResourceList);
    }
    return resource.build();
  }

  private TransactionDetailResource getTransactionDetail(TransactionDetail transactionDetail) {
    return TransactionDetailResource.builder()
        .calculatedAmount(transactionDetail.getCalculatedAmount()).target(transactionDetail.getTarget()).build();
  }
}
