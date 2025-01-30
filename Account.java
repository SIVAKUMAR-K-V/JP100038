package week1;



public class Account {
    private String account_Number;
    private String accountHolder_Name;
    private double balance;

    public Account(String account_Number, String accountHolder_Name, double Balance) {
        this.account_Number = account_Number;
        this.accountHolder_Name = accountHolder_Name;
        this.balance = Balance;
    }

    public String AccountNumber() {
        return account_Number;
    }
    public double Balance() {
        return balance;
    }

    public String AccountHolderName() {
        return accountHolder_Name;
    }


    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Available balance: " + balance);
        } else {
            System.out.println(" Please enter a valid amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Available balance: " + balance);
        } else if (amount <= 0) {
            System.out.println("Please enter a valid amount.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    
    public void transfer(Account recipientAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipientAccount.deposit(amount);
            System.out.println("Transfer successful.");
        } else if (amount <= 0) {
            System.out.println("Please enter a valid amount.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
