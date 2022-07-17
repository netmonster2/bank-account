package org.kata.bankaccount.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kata.bankaccount.persistence.entity.Transaction;
import org.kata.bankaccount.persistence.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void init() {
        Transaction transaction1 = Transaction.builder()
                .amount(500)
                .balance(500)
                .date(new Date()).type(Transaction.DEPOSIT_OPERATION_INT)
                .build();

        Transaction transaction2 = Transaction.builder()
                .amount(100)
                .balance(400)
                .date(new Date()).type(Transaction.WITHDRAWAL_OPERATION_INT)
                .build();

        Transaction transaction3 = Transaction.builder()
                .amount(200)
                .balance(600)
                .date(new Date()).type(Transaction.DEPOSIT_OPERATION_INT)
                .build();

        Arrays.asList(transaction1, transaction2, transaction3).forEach(tr -> transactionRepository.save(tr));
    }

    @DisplayName("When I have transactions in the DB, the returned list should have the same size")
    @Test
    public void getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(3, transactions.size(), "Transaction list size is incorrect");
    }

    @DisplayName("When I have transactions in the DB and I add a new one, the returned list should have a correct size")
    @Test
    public void saveTransactionListSizeIncrease() {
        int oldListSize = transactionRepository.findAll().size();

        Transaction transaction = Transaction.builder()
                .amount(100)
                .balance(600)
                .date(new Date()).type(Transaction.DEPOSIT_OPERATION_INT)
                .build();
        transactionRepository.save(transaction);
        List<Transaction> transactions = transactionRepository.findAll();
        assertEquals(oldListSize + 1, transactions.size(), "Transaction list size is incorrect");
    }
}
