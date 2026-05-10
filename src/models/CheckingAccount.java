package models;

public class CheckingAccount extends BankAccount{

    public CheckingAccount(User owner){
        super(owner);
    }

    @Override
    public void monthly_update(){
        // No monthly operation for checking amount.
    }
}