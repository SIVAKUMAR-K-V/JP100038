package week1;

import java.util.Scanner;

public class BankingApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Account Number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double initialBalance = sc.nextDouble();
        sc.nextLine(); 

        Account account = new Account(accountNumber, accountHolderName, initialBalance);

        int choice;
        double amount;

        do {
            System.out.println("\nBanking Application Menu");
            System.out.println("1. Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("Balance: " + account.Balance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    amount = sc.nextDouble();
                    sc.nextLine(); 
                    account.deposit(amount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    amount = sc.nextDouble();
                    sc.nextLine(); 
                    account.withdraw(amount);
                    break;
                case 4:
                    System.out.println("Enter Recipient Account Number: ");
                    String recipientAccountNumber = sc.nextLine();
                    
                    System.out.println("Enter Recipient Account Holder Name: ");
                    String recipientName = sc.nextLine();
                    
                    Account recipientAccount = new Account(recipientAccountNumber, recipientName, 0.00);
                    
                    System.out.println("Enter transfer amount: ");
                    double amnt = sc.nextDouble();
                    sc.nextLine(); 
                    account.transfer(recipientAccount, amnt);
                    break;
                case 5:
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
