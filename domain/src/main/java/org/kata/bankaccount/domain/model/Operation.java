package org.kata.bankaccount.domain.model;

import lombok.Getter;

@Getter
public class Operation {

    private Type type;
    private int balance;
    private int amount;

    public enum Type {
        WITHDRAW("Withdraw"),
        DEPOSIT("Deposit");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
