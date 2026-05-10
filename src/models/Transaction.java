package models;

import java.time.LocalDateTime;
import utils.IdGenerator;

public class Transaction {
    private String transaction_id;
    private TransactionType type;
    private String source_account_number;
    private String target_account_number;
    private double amount;
    private LocalDateTime created_at;
    private String description;

    public Transaction(
            TransactionType type,
            String source_account_number,
            String target_account_number,
            double amount,
            String description
    ){
        this.transaction_id = IdGenerator.generate_id();
        this.type = type;
        this.source_account_number = source_account_number;
        this.target_account_number = target_account_number;
        this.amount = amount;
        this.created_at = LocalDateTime.now();
        this.description = description;
    }

    public String get_transaction_id(){
        return this.transaction_id;
    }

    public TransactionType get_type(){
        return this.type;
    }

    public String get_source_account_number(){
        return this.source_account_number;
    }

    public String get_target_account_number(){
        return this.target_account_number;
    }

    public double get_amount(){
        return this.amount;
    }

    public LocalDateTime get_created_at(){
        return this.created_at;
    }

    public String get_description(){
        return this.description;
    }
}


