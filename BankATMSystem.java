import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountID;
    private String accountName;
    private String password;
    private double balance;

    public BankAccount(String accountID, String accountName, String password, double balance) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.password = password;
        this.balance = balance;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void displayAccountInfo() {
        System.out.println("Account ID: " + accountID);
        System.out.println("Account Name: " + accountName);
        System.out.println("Balance: " + balance);
    }
}

class ATM {
    private ArrayList<BankAccount> accounts;

    public ATM() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public BankAccount authenticate(String accountID, String password) {
        for (BankAccount account : accounts) {
            if (account.getAccountID().equals(accountID) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public void atmMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("ATM ComputerThanyaburi Bank");
            System.out.print("Account ID: ");
            String accountID = scanner.nextLine();
            System.out.print("Account Password: ");
            String password = scanner.nextLine();

            BankAccount account = authenticate(accountID, password);

            if (account != null) {
                System.out.println("Menu Service");
                System.out.println("1. Account Balance");
                System.out.println("2. Withdrawal");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
                int choice = scanner.nextInt();
            
                switch (choice) {
                    case 1:
                        System.out.println("Your balance is: " + account.getBalance());
                        break;
            
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double amount = scanner.nextDouble();
                        if (amount <= account.getBalance()) {
                            account.setBalance(account.getBalance() - amount);
                            System.out.println("Withdrawal successful! Remaining balance: " + account.getBalance());
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                        break;
            
                    case 3:
                        System.out.println("Thank you for using our ATM!");
                        return;
            
                    default:
                        // กรณีเลือกเมนูที่ไม่ถูกต้อง
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } else {
                System.out.println("Invalid account ID or password. Please try again.");
            }
            
        }

    }
}

public class BankATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Step 1. Enter amount of all account: ");
        int numAccounts = scanner.nextInt();
        while (numAccounts < 1 || numAccounts > 5) {
            System.out.println("Invalid number of accounts. Please enter a number 1 - 5");
            System.out.print("Step 1. Enter amount of all account: ");
            numAccounts = scanner.nextInt();
        }
        scanner.nextLine();
        System.out.println("Step 2. Enter detail of each account.");

        for (int i = 1; i <= numAccounts; i++) {
            System.out.println("No." + i);

            String accountID;
            do {
                System.out.print("Account ID: ");
                accountID = scanner.nextLine();
                if (accountID.length() != 13 || !accountID.matches("\\d+")) {
                    System.out.println("Invalid Account ID. It must be 13 digits.");
                }
            } while (accountID.length() != 13 || !accountID.matches("\\d+"));

            String accountName;
            do {
                System.out.print("Account Name: ");
                accountName = scanner.nextLine();
                if (accountName.length() > 50) {
                    System.out.println("Invalid Account Name. It must not exceed 50 characters.");
                }
            } while (accountName.length() > 50);

            String password;
            do {
                System.out.print("Password: ");
                password = scanner.nextLine();
                if (password.length() != 4 || !password.matches("\\d+")) {
                    System.out.println("Invalid Password. It must be 4 digits.");
                }
            } while (password.length() != 4 || !password.matches("\\d+"));

            double balance;
            do {
                System.out.print("Balance: ");
                balance = scanner.nextDouble();
                if (balance < 0 || balance > 1000000) {
                    System.out.println("Invalid Balance. It must be between 0 and 1000000.");
                }
            } while (balance < 0 || balance > 1000000);
            scanner.nextLine();

            BankAccount account = new BankAccount(accountID, accountName, password, balance);
            atm.addAccount(account);
        }

        atm.atmMenu();
        scanner.close();
    }
}


