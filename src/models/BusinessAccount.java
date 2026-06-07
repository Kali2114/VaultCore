package models;

import contracts.MonthlyFee;
import java.math.BigDecimal;

public class BusinessAccount extends BankAccount implements MonthlyFee {

    public BusinessAccount(User owner) {
        super(owner);
    }

    @Override
    public void withdraw(BigDecimal value) {
        BigDecimal fee = new BigDecimal("10.00");
        BigDecimal total_amount = value.add(fee);

        super.withdraw(total_amount);
    }

    @Override
    public void apply_interest() {
        if (get_balance().compareTo(new BigDecimal("5000")) < 0) {
            withdraw(new BigDecimal("500"));
        }
    }

    @Override
    public void monthly_update() {
        apply_interest();
    }
}