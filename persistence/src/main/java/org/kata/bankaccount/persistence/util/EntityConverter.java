package org.kata.bankaccount.persistence.util;

import org.kata.bankaccount.domain.model.Operation;
import org.kata.bankaccount.persistence.entity.Transaction;

public class EntityConverter {

    /**
     * Converts an Operation model to a Transaction Entity
     *
     * @param operation {@link Operation} model
     * @return An {@link Transaction} object
     */
    public static Transaction fromOperationModel(Operation operation) {
        return Transaction.builder()
                .balance(operation.getBalance())
                .type(operation.getType() == Operation.Type.DEPOSIT ?
                        Transaction.DEPOSIT_OPERATION_INT : Transaction.WITHDRAWAL_OPERATION_INT)
                .amount(operation.getAmount())
                .date(operation.getDate())
                .build();
    }

    /**
     * Converts a Transaction Entity to an Operation model
     *
     * @param transaction {@link Transaction} object
     * @return An {@link Operation} model
     */
    public static Operation toOperationModel(Transaction transaction) {
        Operation operation = Operation.builder()
                .type(transaction.getType() == Transaction.DEPOSIT_OPERATION_INT ?
                        Operation.Type.DEPOSIT : Operation.Type.WITHDRAW)
                .amount(transaction.getAmount())
                .build();
        operation.setBalance(transaction.getBalance());
        operation.setDate(transaction.getDate());

        return operation;
    }
}
