package app;

import models.User;
import models.CheckingAccount;
import models.SavingAccount;
import service.BankService;

public class Main {
    public static void main(String[] args){
        BankService bank_service = new BankService();

        User user1 = bank_service.create_user("Jan", "Kowalski", "jan@test.pl");
        bank_service.add_user(user1);

        User user2 = bank_service.create_user("Anna", "Nowak", "anna@test.pl");
        bank_service.add_user(user2);

        CheckingAccount account1 = bank_service.create_checking_account(user1);
        SavingAccount account2 = bank_service.create_saving_account(user2, 3.0);

        bank_service.add_account(account1);
        bank_service.add_account(account2);

        System.out.println(account1.get_account_number());
        System.out.println(account2.get_account_number());

        bank_service.deposit_by_account_number(account1.get_account_number(), 1000);
        bank_service.transfer_by_account_number(
                account1.get_account_number(),
                account2.get_account_number(),
                200
        );

        System.out.println(account1.get_balance());
        System.out.println(account2.get_balance());
        System.out.println(bank_service.get_transactions().size());
        System.out.println(bank_service.get_transactions());

        bank_service.apply_monthly_update();
        System.out.println(account1.get_balance());
        System.out.println(account2.get_balance());
    }
}
