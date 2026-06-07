package models;

import contracts.InterestBearing;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingAccount extends BankAccount implements InterestBearing {
    private BigDecimal interest_rate;

    public SavingAccount(User owner, BigDecimal interest_rate) {
        super(owner);
        this.interest_rate = interest_rate;
    }

    public BigDecimal get_interest_rate() {
        return this.interest_rate;
    }

    @Override
    public void apply_interest() {
        BigDecimal interest = get_balance()
                .multiply(this.interest_rate)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        if (interest.compareTo(BigDecimal.ZERO) > 0) {
            deposit(interest);
        }
    }

    @Override
    public void monthly_update() {
        apply_interest();
    }
}