package atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// User class to represent each user of the ATM
class User {
    private String userID;
    private String userPIN;
    private double accountBalance;
    private List<String> transactionHistory;

    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

// ATM class encapsulating ATM functionalities
class ATM {
    private User currentUser;

    public boolean authenticateUser(String userID, String userPIN, User[] users) {
        for (User user : users) {
            if (user.getUserID().equals(userID) && user.getUserPIN().equals(userPIN)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + currentUser.getAccountBalance());
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= currentUser.getAccountBalance()) {
            currentUser.setAccountBalance(currentUser.getAccountBalance() - amount);
            currentUser.addTransaction("Withdrawal: -$" + amount);
            System.out.println("Withdrawal successful. Remaining balance: $" + currentUser.getAccountBalance());
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            currentUser.setAccountBalance(currentUser.getAccountBalance() + amount);
            currentUser.addTransaction("Deposit: +$" + amount);
            System.out.println("Deposit successful. New balance: $" + currentUser.getAccountBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Sample user data
        User[] users = {
            new User("123456", "7890", 1000.0),
            new User("987654", "0987", 500.0)
        };

        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("Welcome to the ATM!");

        // User authentication
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPIN = scanner.nextLine();

        if (atm.authenticateUser(userID, userPIN, users)) {
            System.out.println("Authentication successful. What would you like to do?");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. View Transaction History"); // New option

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 4: // New case to view transaction history
                    System.out.println("Transaction History:");
                    for (String transaction : users[0].getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Authentication failed. Please try again.");
        }

        scanner.close();
    }
}
