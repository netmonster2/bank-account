package org.kata.bankaccount.domain.port.spi;

import org.kata.bankaccount.domain.model.Operation;

import java.util.List;

public interface BankAccPersistencePort {
    List<Operation> loadOperations();

    Operation saveOperation(Operation operation);
}
