package models;

import exceptions.InvalidAmountException;
import exceptions.InsufficientFundsException;
import utils.IdGenerator;

public abstract class BankAccount{
    private String account_number;
    private User owner;
    private double balance;
    private boolean is_active;

    public BankAccount(User owner){
        this.account_number = IdGenerator.generate_id();
        this.owner = owner;
        this.balance = 0;
        this.is_active = true;
    }

    public String get_account_number(){
        return this.account_number;
    }

    public User get_owner(){
        return this.owner;
    }

    public double get_balance(){
        return this.balance;
    }

    public boolean get_is_active(){
        return this.is_active;
    }

    public void deposit(double value){
        if (value <= 0){
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        this.balance += value;
    }

    public void withdraw(double value){
        if (value <= 0){
            throw new InvalidAmountException("Amount must be greater than zero");
        }
        else if (value > this.balance){
            throw new InsufficientFundsException("Not enough funds.");
        }
        this.balance -= value;
    }

}

