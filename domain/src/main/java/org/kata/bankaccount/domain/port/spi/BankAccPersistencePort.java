package org.kata.bankaccount.domain.port.spi;

import org.kata.bankaccount.domain.model.Operation;

import java.util.List;

public interface BankAccPersistencePort {
    /**
     * Load bank account operations
     *
     * @return List of operations
     */
    List<Operation> loadOperations();

    /**
     * Persist the operation details
     *
     * @param operation Operation to save
     * @return The saved operation
     */
    Operation saveOperation(Operation operation);
}
