package contracts;

import models.BankAccount;

public interface Transferable{
    void transfer(BankAccount target_account, double amount);
}