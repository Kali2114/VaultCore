package models;

import contracts.MonthlyFee;

public class BusinessAccount extends BankAccount implements MonthlyFee{

    public BusinessAccount(User owner){
        super(owner);
    }

    @Override
    public void apply_interest(){
        if (get_balance() < 5000){
            withdraw(500);
        }
    }

    @Override
    public void monthly_update(){
        apply_interest();
    }
}
