package org.kata.bankaccount.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The operation DTO
 */
@Getter
@Setter
public class Operation {

    private Type type;
    private int balance;
    private int amount;
    private Date date;

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
