package service;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class BankService {
    private List<User> users;
    private List<BankAccount> accounts;
    private List<Transaction> transactions;

    public BankService() {
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public List<User> get_users(){
        return this.users;
    }

    public List<BankAccount> get_accounts(){
        return this.accounts;
    }

    public List<Transaction> get_transaction(){
        return this.transactions;
    }

    public User create_user(String first_name, String last_name, String email) {
        return new User(first_name, last_name, email);
    }

    public void add_user(User user) {
        this.users.add(user);
    }

    public SavingAccount create_saving_account(User owner, double interest_rate){
        return new SavingAccount(owner, interest_rate);
    }

    public CheckingAccount create_checking_account(User owner){
        return new CheckingAccount(owner);
    }

    public BusinessAccount create_business_account(User owner){
        return new BusinessAccount(owner);
    }
    
    public void add_account(BankAccount account){
        this.accounts.add(account);
        account.get_owner().add_account(account);
    }


    public void deposit(BankAccount account, double amount){
        account.deposit(amount);
        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                null,
                account.get_account_number(),
                amount,
                "deposit"
        );
        transactions.add(transaction);
    }

    public void withdraw(BankAccount account, double amount){
        account.withdraw(amount);
        Transaction transaction = new Transaction(
                TransactionType.WITHDRAW,
                account.get_account_number(),
                null,
                amount,
                "withdraw"
        );
        transactions.add(transaction);
    }

    public void transfer(BankAccount source_account, BankAccount target_account, double amount){
        source_account.transfer(target_account, amount);
        Transaction transaction = new Transaction(
                TransactionType.TRANSFER,
                source_account.get_account_number(),
                target_account.get_account_number(),
                amount,
                "transfer"
        );
        transactions.add(transaction);
    }

    public void apply_monthly_update(){
        for (BankAccount account : this.accounts){
            account.monthly_update();
        }
    }
}
