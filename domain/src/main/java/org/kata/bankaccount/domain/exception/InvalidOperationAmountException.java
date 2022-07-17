package org.kata.bankaccount.domain.exception;

/**
 * Thrown to indicate that the current operation amount is incorrect.
 */
public class InvalidOperationAmountException extends RuntimeException {

    public InvalidOperationAmountException(String operation, int amount, String reason) {
        super(String.format("The amount '%s' for the operation %s is invalid: %s", amount, operation, reason));
    }
}
