package models;

import java.io.Serializable;
import java.math.BigDecimal;

import exceptions.InvalidAmountException;
import exceptions.InsufficientFundsException;
import utils.IdGenerator;

public abstract class BankAccount implements Serializable {
    private String account_number;
    private User owner;
    private BigDecimal balance;
    private boolean is_active;

    public BankAccount(User owner) {
        this.account_number = IdGenerator.generate_account_id();
        this.owner = owner;
        this.balance = BigDecimal.ZERO;
        this.is_active = true;
    }

    public String get_account_number() {
        return this.account_number;
    }

    public User get_owner() {
        return this.owner;
    }

    public BigDecimal get_balance() {
        return this.balance;
    }

    public boolean get_is_active() {
        return this.is_active;
    }

    public void deposit(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        this.balance = this.balance.add(value);
    }

    public void withdraw(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        } else if (value.compareTo(this.balance) > 0) {
            throw new InsufficientFundsException("Not enough funds.");
        }

        this.balance = this.balance.subtract(value);
    }

    public void transfer(BankAccount target_account, BigDecimal amount) {
        withdraw(amount);
        target_account.deposit(amount);
    }

    public abstract void monthly_update();
}