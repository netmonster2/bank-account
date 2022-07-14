package org.kata.bankaccount.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class History {

    @Getter
    private final List<Operation> operationList;

    public History() {
        this.operationList = new ArrayList<>();
    }

    public Operation getLastOperation() {
        return operationList.get(operationList.size() - 1);
    }

    int calculateBalance() {
        return operationList.stream().mapToInt(Operation::getAmount).sum();
    }

    public int addWithdrawal(int amount) {
        addOperation(Operation.Type.WITHDRAW, -1 * amount);
        return calculateBalance();
    }

    public int addDeposit(int amount) {
        addOperation(Operation.Type.DEPOSIT, amount);
        return calculateBalance();
    }

    private void addOperation(Operation.Type operationType, int amount) {
        operationList.add(Operation.builder()
                .amount(amount)
                .balance(calculateBalance() + amount)
                .type(operationType)
                .build());
    }
}
