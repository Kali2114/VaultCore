package contracts

public interface Transferable{
    void transfer(BankAccount target_account, double amount);
}