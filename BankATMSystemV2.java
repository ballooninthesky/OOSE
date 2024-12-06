import java.util.ArrayList;
import java.util.Scanner;

// Base class Person
class Person {
    private String nationalID;
    private String name;
    private String gender;

    public Person(String nationalID, String name, String gender) {
        this.nationalID = nationalID;
        this.name = name;
        this.gender = gender;
    }

    public String getNationalID() {
        return nationalID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}

// Manager class inheriting from Person
class Manager extends Person {
    private String username;
    private String password;

    public Manager(String nationalID, String name, String gender, String username, String password) {
        super(nationalID, name, gender);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// BankAccount class
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

// Interface for ATM actions
interface ATMAction {
    void checkBalance(BankAccount account);

    void withdraw(BankAccount account, double amount);

    void deposit(BankAccount account, double amount);

    void transfer(BankAccount fromAccount, BankAccount toAccount, double amount);
}

// ATM class implementing ATMAction
class ATM implements ATMAction {
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private Manager manager; // Manager reference

    public void setManager(Manager manager) {
        this.manager = manager; // Assign manager
    }

    public boolean authenticateManager(String username, String password) {
        return manager != null && manager.getUsername().equals(username) && manager.getPassword().equals(password);
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

    @Override
    public void checkBalance(BankAccount account) {
        System.out.println("Current balance: " + account.getBalance());
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        if (amount <= account.getBalance()) {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    @Override
    public void deposit(BankAccount account, double amount) {
        account.setBalance(account.getBalance() + amount);
        System.out.println("Deposit successful. New balance: " + account.getBalance());
    }

    @Override
    public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (amount <= fromAccount.getBalance()) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            System.out.println("Transfer successful. New balance for sender: " + fromAccount.getBalance());
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    public BankAccount findAccountByID(String accountID) {
        for (BankAccount account : accounts) {
            if (account.getAccountID().equals(accountID)) {
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
                System.out.println("1. Check Balance");
                System.out.println("2. Withdrawal");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        checkBalance(account);
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        withdraw(account, withdrawalAmount);
                        break;

                    case 3:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        deposit(account, depositAmount);
                        break;

                    case 4:
                        System.out.print("Enter recipient Account ID: ");
                        String recipientID = scanner.nextLine();
                        BankAccount recipient = findAccountByID(recipientID); // ใช้ findAccountByID
                        if (recipient != null) {
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            if (transferAmount > 0) { // ตรวจสอบว่าจำนวนเงินที่โอนต้องมากกว่า 0
                                transfer(account, recipient, transferAmount);
                            } else {
                                System.out.println("Transfer amount must be greater than 0.");
                            }
                        } else {
                            System.out.println("Recipient account not found.");
                        }

                        break;

                    case 5:
                        System.out.println("Thank you for using our ATM!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } else {
                System.out.println("Invalid account ID or password. Please try again.");
            }
        }
    }
}

// Main class
public class BankATMSystemV2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        // Manager setup
        System.out.println("No manager set. Create a manager.");
        System.out.print("National ID: ");
        String nationalID = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Manager manager = new Manager(nationalID, name, gender, username, password);
        atm.setManager(manager);

        // Manager login
        System.out.println("Manager login required.");
        while (true) {
            System.out.print("Username: ");
            String managerUsername = scanner.nextLine();
            System.out.print("Password: ");
            String managerPassword = scanner.nextLine();

            if (atm.authenticateManager(managerUsername, managerPassword)) {
                System.out.println("Manager authenticated.");
                break;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }

        // Account creation
        System.out.print("Step 1. Enter amount of all accounts: ");
        int numAccounts = scanner.nextInt();
        scanner.nextLine();
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

            String accountPassword;
            do {
                System.out.print("Password: ");
                accountPassword = scanner.nextLine();
                if (accountPassword.length() != 4 || !accountPassword.matches("\\d+")) {
                    System.out.println("Invalid Password. It must be 4 digits.");
                }
            } while (accountPassword.length() != 4 || !accountPassword.matches("\\d+"));

            double balance;
            do {
                System.out.print("Balance: ");
                balance = scanner.nextDouble();
                if (balance < 0 || balance > 1000000) {
                    System.out.println("Invalid Balance. It must be between 0 and 1000000.");
                }
            } while (balance < 0 || balance > 1000000);
            scanner.nextLine();
            atm.addAccount(new BankAccount(accountID, accountName, accountPassword, balance));
        }

        // ATM menu
        atm.atmMenu();
        scanner.close();
    }
}
