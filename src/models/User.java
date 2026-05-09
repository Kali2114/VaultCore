package models;

import java.util.List;
import java.util.ArrayList;

import utils.IdGenerator;

public class User{
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private List<BankAccount> accounts;
    private int pin;

    public User(String first_name, String last_name, String email, int pin){
        this.id = IdGenerator.generate_id();
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.accounts = new ArrayList<>();
        this.pin = pin;
    }

    public String get_id(){
        return this.id;
    }

    public String get_first_name(){
        return this.first_name;
    }

    public String get_last_name(){
        return this.last_name;
    }

    public String get_email(){
        return this.email;
    }

    public List<BankAccount> get_accounts(){
        return this.accounts;
    }

    public int get_pin() { return this.pin; }

    public void add_account(BankAccount account){
        this.accounts.add(account);
    }
}