package models;

import contracts.Transferable;

public class CheckingAccount extends BankAccount implements Transferable{

    public CheckingAccount(User owner){
        super(owner);
    }

    @Override
    public void transfer(BankAccount target_account, double amount){
        withdraw(amount);
        target_account.deposit(amount);
    }

    @Override
    public void monthly_update(){
        // No monthly operation for checking amount.
    }
}