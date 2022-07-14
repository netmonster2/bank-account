package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

import java.util.List;

public class History {

    private List<Operation> operationList;

    private final BankAccPersistencePort bankAccPersistencePort;

    public History(BankAccPersistencePort bankAccPersistencePort) {
        this.bankAccPersistencePort = bankAccPersistencePort;
        loadOperationsList();
    }

    private void loadOperationsList() {
        operationList = bankAccPersistencePort.loadOperations();
    }

    public List<Operation> getOperationList() {
        loadOperationsList();
        return operationList;
    }

    public Operation getLastOperation() {
        loadOperationsList();
        if (operationList == null || operationList.size() == 0)
            return null;
        else {
            return operationList.get(operationList.size() - 1);
        }
    }

    int calculateBalance() {
        loadOperationsList();
        return operationList.stream().mapToInt(Operation::getAmount).sum();
    }

    public Operation addWithdrawal(int amount) {
        return addOperation(Operation.Type.WITHDRAW, -1 * amount);
    }

    public Operation addDeposit(int amount) {
        return addOperation(Operation.Type.DEPOSIT, amount);
    }

    private Operation addOperation(Operation.Type operationType, int amount) {
        Operation operation = Operation.builder()
                .amount(amount)
                .balance(calculateBalance() + amount)
                .type(operationType)
                .build();
        return bankAccPersistencePort.saveOperation(operation);
    }
}
