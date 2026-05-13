package storage;

import java.io.Serializable;
import java.util.List;
import models.User;
import models.BankAccount;
import models.Transaction;


public class BankData implements Serializable {
    private List<User> users;
    private List<BankAccount> accounts;
    private List<Transaction> transactions;

    public BankData(
        List<User> users,
        List<BankAccount> accounts,
        List<Transaction> transactions
    ){
        this.users = users;
        this.accounts = accounts;
        this.transactions = transactions;
    }

    public List<User> get_users() {
        return this.users;
    }

    public List<BankAccount> get_accounts() {
        return this.accounts;
    }

    public List<Transaction> get_transactions() {
        return this.transactions;
    }
}

