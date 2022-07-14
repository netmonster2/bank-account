package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

import java.util.List;

/**
 * The operations history model
 */
public class History {

    private List<Operation> operationList;

    private final BankAccPersistencePort bankAccPersistencePort;

    public History(BankAccPersistencePort bankAccPersistencePort) {
        this.bankAccPersistencePort = bankAccPersistencePort;
        loadOperationsList();
    }

    /**
     * Refresh the operations list from the persistence port
     */
    private void loadOperationsList() {
        operationList = bankAccPersistencePort.loadOperations();
    }

    /**
     * Get the account operations list
     *
     * @return The list
     */
    public List<Operation> getOperationList() {
        loadOperationsList();
        return operationList;
    }

    /**
     * Get the latest operation according to the order returned from the persistence port.
     * The last one from the list is returned
     *
     * @return The latest operation
     */
    public Operation getLatestOperation() {
        loadOperationsList();
        if (operationList == null || operationList.size() == 0)
            return null;
        else {
            return operationList.get(operationList.size() - 1);
        }
    }

    /**
     * Calculate the current balance according to the history
     *
     * @return The actual balance
     */
    int calculateBalance() {
        loadOperationsList();
        return operationList.stream().mapToInt(Operation::getAmount).sum();
    }

    /**
     * Add a withdrawal operations to the history
     *
     * @param amount The amount of the withdrawal
     * @return The withdrawal operation
     */
    public Operation addWithdrawal(int amount) {
        return addOperation(Operation.Type.WITHDRAW, -1 * amount);
    }

    /**
     * Add a deposit operations to the history
     *
     * @param amount The amount of the deposit
     * @return The deposit operation
     */
    public Operation addDeposit(int amount) {
        return addOperation(Operation.Type.DEPOSIT, amount);
    }

    /**
     * Add an operation to the history
     *
     * @param amount The amount of the operation
     * @return The operation
     */
    private Operation addOperation(Operation.Type operationType, int amount) {
        Operation operation = Operation.builder()
                .amount(amount)
                .balance(calculateBalance() + amount)
                .type(operationType)
                .build();
        return bankAccPersistencePort.saveOperation(operation);
    }
}
