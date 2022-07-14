package org.kata.bankaccount.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class Operation {

    private final Type type;
    private final int balance;
    private final int amount;
    private final Date date;

    @Builder
    public Operation(Type type, int balance, int amount) {
        this.type = type;
        this.balance = balance;
        this.amount = (type == Type.WITHDRAW && amount > 0) ? -1 * amount : amount;
        this.date = new Date();
    }

    public enum Type {
        WITHDRAW,
        DEPOSIT
    }
}
