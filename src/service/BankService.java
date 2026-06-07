package service;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import exceptions.AccountNotFoundException;
import models.*;
import storage.BankData;

public class BankService {
    private List<User> users;
    private List<BankAccount> accounts;
    private List<Transaction> transactions;

    public BankService() {
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
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

    public User create_user(String first_name, String last_name, String email) {
        return new User(first_name, last_name, email);
    }

    public void add_user(User user) {
        this.users.add(user);
    }

    public SavingAccount create_saving_account(User owner, BigDecimal interest_rate) {
        return new SavingAccount(owner, interest_rate);
    }

    public CheckingAccount create_checking_account(User owner) {
        return new CheckingAccount(owner);
    }

    public BusinessAccount create_business_account(User owner) {
        return new BusinessAccount(owner);
    }

    public void add_account(BankAccount account) {
        this.accounts.add(account);
        account.get_owner().add_account(account);
    }

    public BankAccount find_account_by_number(String account_number) {
        for (BankAccount account : this.accounts) {
            if (account.get_account_number().equals(account_number)) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account not found");
    }

    private void deposit(BankAccount account, BigDecimal amount) {
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

    private void withdraw(BankAccount account, BigDecimal amount) {
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

    private void transfer(BankAccount source_account, BankAccount target_account, BigDecimal amount) {
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

    public void apply_monthly_update() {
        for (BankAccount account : this.accounts) {
            account.monthly_update();
        }
    }

    public void deposit_by_account_number(String account_number, BigDecimal amount) {
        BankAccount account = find_account_by_number(account_number);
        deposit(account, amount);
    }

    public void withdraw_by_account_number(String account_number, BigDecimal amount) {
        BankAccount account = find_account_by_number(account_number);
        withdraw(account, amount);
    }

    public void transfer_by_account_number(
            String source_number,
            String target_number,
            BigDecimal amount
    ) {
        BankAccount source_account = find_account_by_number(source_number);
        BankAccount target_account = find_account_by_number(target_number);

        transfer(source_account, target_account, amount);
    }

    public BankData export_data() {
        return new BankData(this.users, this.accounts, this.transactions);
    }

    public void import_data(BankData data) {
        this.users = data.get_users();
        this.accounts = data.get_accounts();
        this.transactions = data.get_transactions();
    }
}