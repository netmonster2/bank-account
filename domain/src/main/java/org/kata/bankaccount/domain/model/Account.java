package org.kata.bankaccount.domain.model;

import org.kata.bankaccount.domain.exception.InsufficientBalanceException;
import org.kata.bankaccount.domain.exception.InvalidOperationAmountException;
import org.kata.bankaccount.domain.port.spi.BankAccPersistencePort;

/**
 * The bank account model
 */
public class Account {
    private final History history;

    public Account(BankAccPersistencePort bankAccPersistencePort) {
        this.history = new History(bankAccPersistencePort);
    }


    /**
     * Make a deposit in the account
     *
     * @param depositAmount The amount to deposit
     * @return The deposit operation
     */
    public Operation deposit(int depositAmount) {
        if (depositAmount <= 0)
            throw new InvalidOperationAmountException
                    (Operation.Type.DEPOSIT.name(), depositAmount, "Operation amount cannot be 0 or less");
        return history.addDeposit(depositAmount);
    }

    /**
     * Withdrawal from the account
     *
     * @param withdrawalAmount The amount to withdraw
     * @return The withdrawal operation
     * @throws InsufficientBalanceException Thrown when the account balance is insufficient
     */
    public Operation withdraw(int withdrawalAmount) {
        if (withdrawalAmount == 0)
            throw new InvalidOperationAmountException
                    (Operation.Type.WITHDRAW.name(), withdrawalAmount, "Operation amount cannot be 0");
        int currentBalance = getBalance();
        if (withdrawalAmount > currentBalance)
            throw new InsufficientBalanceException(currentBalance);

        return history.addWithdrawal(withdrawalAmount);
    }

    /**
     * Get the current account balance (calculated from the history)
     *
     * @return The balance value
     */
    public int getBalance() {
        return history.calculateBalance();
    }

    /**
     * Get the history domain model to manage operations history
     *
     * @return The model
     */
    public History getHistory() {
        return history;
    }
}
