package com.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.exchange.model.Transaction;
import com.exchange.repository.TransactionRepository;
import com.exchange.util.TestUtil;
import java.util.Date;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author msaritas
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTests {

  @InjectMocks
  TransactionService transactionService;

  @Mock
  TransactionRepository transactionRepository;

  InOrder inOrder;

  private EasyRandom easyRandom;

  @BeforeEach
  void setUp() {
    inOrder = Mockito.inOrder(transactionRepository);
    easyRandom = new EasyRandom(TestUtil.getEasyRandomParameters());
  }

  @DisplayName("Test createTransaction Success Scenario")
  @Test
  void createTransaction() {
    Transaction mockTransaction = easyRandom.nextObject(Transaction.class);

    //given
    when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(mockTransaction);

    //when
    final var transaction = transactionService.save(mockTransaction);

    //then
    inOrder.verify(transactionRepository).save(Mockito.any(Transaction.class));
    inOrder.verifyNoMoreInteractions();

    assertThat(transaction.getAmount()).isNotNull();
  }

  @DisplayName("Test findByIdOrCreatedDate Success Scenario")
  @Test
  void findByIdOrCreatedDate() {
    Transaction mockTransaction = easyRandom.nextObject(Transaction.class);

    //given
    when(transactionRepository.findByIdOrCreatedDate(any(), any())).thenReturn(List.of(mockTransaction));

    //when
    final var transaction = transactionService.findByIdOrCreatedDate(1L, new Date());

    //then
    inOrder.verify(transactionRepository).findByIdOrCreatedDate(any(), any());
    inOrder.verifyNoMoreInteractions();

    assertThat(transaction).isNotNull();
    assertThat(transaction).isNotNull();
  }


}
