package org.kata.bankaccount.domain.model;

import java.util.ArrayList;
import java.util.List;

public class History {

    private final List<Operation> operationList;

    public History() {
        this.operationList = new ArrayList<>();
    }

    public void addOperation(Operation operation) {
        operationList.add(operation);
    }

    public Operation getLastOperation() {
        return operationList.get(operationList.size() - 1);
    }
}
