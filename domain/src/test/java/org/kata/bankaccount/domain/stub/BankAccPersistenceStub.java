package org.kata.bankaccount.domain.stub;

import org.kata.bankaccount.domain.model.Operation;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

import java.util.ArrayList;
import java.util.List;

public class BankAccPersistenceStub implements BankAccPersistencePort {

    private final List<Operation> operationList;

    public BankAccPersistenceStub() {
        this.operationList = new ArrayList<>();
    }

    @Override
    public List<Operation> loadOperations() {
        return new ArrayList<>(operationList);
    }

    @Override
    public Operation saveOperation(Operation operation) {
        operationList.add(operation);
        return operation;
    }
}
