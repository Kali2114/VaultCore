package models;

import contracts.InterestBearing;
import contracts.Transferable;

public class SavingAccount extends BankAccount implements InterestBearing, Transferable{
    private double interest_rate;

    public SavingAccount(User owner, double interest_rate){
        super(owner);
        this.interest_rate = interest_rate;
    }

    public double get_interest_rate(){
        return this.interest_rate;
    }

    @Override
    public void apply_interest(){
        double interest = (get_balance() * this.interest_rate / 100);
        deposit(interest);
    }

    @Override
    public void transfer(BankAccount target_account, double amount){
        withdraw(amount);
        target_account.deposit(amount);
    }
}