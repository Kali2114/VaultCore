import java.util.UUID;


abstract class BankAccount{
    private String account_number;
    private User owner;
    private double balance;
    private boolean is_active;

    public BankAccount(User owner){
        this.account_number = generate_account_number();
        this.owner = owner;
        this.balance = 0;
        this.is_active = true;
    }

    private String generate_account_number(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String get_account_number(){
        return this.account_number;
    }

    public User get_owner(){
        return this.owner;
    }

    public double get_balance(){
        return this.balance;
    }

    public boolean get_is_active(){
        return this.is_active;
    }

    public void deposit(double value){
        this.balance += value;
    }

    public void withdraw(double value){
        if (value < 0){
            System.out.println("Wrong value");
            return;
        }
        else if (value > this.balance){
            System.out.println("Not enough money");
            return;
        }
        this.balance -= value;
    }

}

