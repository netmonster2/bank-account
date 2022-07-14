package org.kata.bankaccount.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    private Type type;
    private int balance;
    private int amount;

    public enum Type {
        WITHDRAW,
        DEPOSIT
    }
}
