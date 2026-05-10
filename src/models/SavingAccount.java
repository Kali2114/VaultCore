package models;

import contracts.InterestBearing;

public class SavingAccount extends BankAccount implements InterestBearing{
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
        if (interest > 0) {
            deposit(interest);
        }
    }

    @Override
    public void monthly_update(){
        apply_interest();
    }
}