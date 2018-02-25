package cbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import uod.gla.io.Storage;
import uod.gla.menu.Finalisable;
import uod.gla.menu.MenuBuilder;
import uod.gla.menu.MenuItem;
import uod.gla.util.Reader;

public class App implements Finalisable {

    static List<Transaction> ledger = new ArrayList<>();
    static List<Account> accounts = new ArrayList<>();

    static App app = new App();

    public static void main(String[] args) {
        ArrayList<Transaction> savedLedger
                = Storage.<ArrayList<Transaction>>retrieve("ledger", true);
        ArrayList<Account> savedAccounts
                = Storage.<ArrayList<Account>>retrieve("accounts", true);
        if (savedLedger != null) {
            App.ledger = savedLedger;
        }
        if (savedAccounts != null) {
            App.accounts = savedAccounts;
        }
        MenuItem c = new MenuItem("C", "Create New Account", app, "createNewAccount");
        MenuItem m = new MenuItem("M", "Modify Account Details", app, "modifyAccountDetails");
        MenuItem d = new MenuItem("D", "Deposit Cash", app, "depositCash");
        MenuItem w = new MenuItem("W", "Withdraw Cash", app, "withdrawCash");
        MenuItem t = new MenuItem("T", "Transfer Funds", app, "transferFunds");
        MenuItem b = new MenuItem("B", "Balance Inquiry", app, "balanceInquiry");
        MenuItem s = new MenuItem("S", "View Account Statement", app, "viewAccountStatement");
        MenuItem a = new MenuItem("A", "Modify Account Status", app, "modifyAccountStatus");
        MenuItem l = new MenuItem("L", "Liability Report", app, "liabilityReport");
        MenuBuilder.displayMenu(app, c, m, d, w, t, b, s, a, l);
        app.finalise();
    }

    @Override
    public void finalise() {
        Storage.save((Serializable) ledger, "ledger", true);
        Storage.save((Serializable) accounts, "accounts", true);
    }

    public static void createNewAccount() {
        MenuItem p = new MenuItem("P", "Personal Account", app, "createPersonalAccount");
        MenuItem b = new MenuItem("B", "Business Account", app, "createBusinessAccount");
        MenuBuilder.displayMenuOnce("Personal or Business Account?", p, b);
    }

    public static void createPersonalAccount() {
        System.out.println("Please fill out the following details");
        PersonalAccount pa = new PersonalAccount(
                Reader.readNameString("First Name"),
                Reader.readNameString("Last Name"),
                new Address(Reader.readLine("Address Line 1"),
                        Reader.readLine("Address Line 2"),
                        Reader.readLine("Post Code")),
                Reader.readNumberString("Phone", 11),
                Reader.readEmailString("Email"),
                Reader.readEnum("Please select a currency", Currency.class));
        accounts.add(pa);
        System.out.println("\nAccount successfully created!");
        System.out.println("Your account number is " + pa.getAcctNumber());
    }

    public static void createBusinessAccount() {
        System.out.println("Please fill out the following details");
        BusinessAccount ba = new BusinessAccount(
                Reader.readNameString("Business Name"),
                Reader.readNameString("Signatory's First Name"),
                Reader.readNameString("Signatory's Last Name"),
                new Address(Reader.readLine("Business Address (Line 1)"),
                        Reader.readLine("Business Address (Line 2)"),
                        Reader.readLine("Post Code")),
                Reader.readNumberString("Business Phone Number", 11),
                Reader.readEmailString("Business Email"),
                Reader.readEnum("Please select a currency", Currency.class));
        accounts.add(ba);
        System.out.println("\nAccount successfully created!");
        System.out.println("Your account number is " + ba.getAcctNumber());
    }

    public static void modifyAccountDetails() {
        MenuItem p = new MenuItem("P", "Personal Account", app, "modifyPersonalAccount");
        MenuItem b = new MenuItem("B", "Business Account", app, "modifyBusinessAccount");
        MenuBuilder.displayMenuOnce("Personal or Business Account?", p, b);
    }

    public static void modifyPersonalAccount() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        } else if (!(acct instanceof PersonalAccount)) {
            System.err.println("Not a personal account!");
            return;
        }
        boolean modified = false;
        if (Reader.readBoolean("Do you want to modify the first name?")) {
            acct.setFirstName(Reader.readNameString("New First Name"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the last name?")) {
            acct.setLastName(Reader.readNameString("New Last Name"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the address?")) {
            acct.getAddress().setLine1(Reader.readLine("Enter new Address Line 1"));
            acct.getAddress().setLine2(Reader.readLine("Enter new Address Line 2"));
            acct.getAddress().setPostCode(Reader.readLine("Enter new post code"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the phone number?")) {
            acct.setPhone(Reader.readNumberString("New phone number"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the email?")) {
            acct.setEmail(Reader.readEmailString("New email"));
            modified = true;
        }
        if (modified) {
            System.out.println("Account successfully modified!");
        } else {
            System.out.println("No detail was modified!");
        }
    }

    public static void modifyBusinessAccount() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        } else if (!(acct instanceof BusinessAccount)) {
            System.err.println("Not a business account!");
            return;
        }
        boolean modified = false;
        if (Reader.readBoolean("Do you want to modify the business name?")) {
            ((BusinessAccount) acct)
                    .setBusinessName(Reader.readNameString("New Business Name"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the signatory's first name?")) {
            acct.setFirstName(Reader.readNameString("New First Name"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the signatory's last name?")) {
            acct.setLastName(Reader.readNameString("New Last Name"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the business address?")) {
            acct.getAddress().setLine1(Reader.readLine("Enter new Address Line 1"));
            acct.getAddress().setLine2(Reader.readLine("Enter new Address Line 2"));
            acct.getAddress().setPostCode(Reader.readLine("Enter new post code"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the business phone number?")) {
            acct.setPhone(Reader.readNumberString("New business phone number"));
            modified = true;
        }
        if (Reader.readBoolean("Do you want to modify the business email?")) {
            acct.setEmail(Reader.readEmailString("New business email"));
            modified = true;
        }
        if (modified) {
            System.out.println("Account successfully modified!");
        } else {
            System.out.println("No detail was modified!");
        }

    }

    private static Account search() {
        boolean found = false;
        Account acct = null;
        while (!found) {
            String acctNumber = Reader.readNumberString("Enter the account number");
            for (Account a : accounts) {
                if (a.getAcctNumber().equals(acctNumber)) {
                    acct = a;
                    break;
                }
            }
            if (acct != null) {
                System.out.println(acct.getAccountName());
                found = Reader.readBoolean("Is this the correct account?");
            } else {
                return null;
            }
        }
        return acct;
    }

    public static void depositCash() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        BigDecimal value = BigDecimal.valueOf(Reader.readDouble("Enter deposit amount"));
        boolean deposited = acct.deposit(value,
                Reader.readNumberString("Enter 4-digit PIN", 4));
        if (deposited) {
            Transaction t = new Transaction(acct.getAcctNumber(),
                    TranType.DEP, "Cash Deposit", value, acct.getBalance());
            ledger.add(t);
        }
    }

    public static void withdrawCash() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        BigDecimal value = BigDecimal.valueOf(Reader.readDouble("Enter withdrawal amount"));
        boolean withdrawn = acct.withdraw(value,
                Reader.readNumberString("Enter 4-digit PIN", 4));
        if (withdrawn) {
            Transaction t = new Transaction(acct.getAcctNumber(),
                    TranType.WDR, "Cash Withdrawal", value, acct.getBalance());
            ledger.add(t);
        }
    }

    public static void transferFunds() {

    }

    public static void balanceInquiry() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        acct.printBalance();
    }

    public static void viewAccountStatement() {

    }

    public static void modifyAccountStatus() {

    }

    public static void liabilityReport() {

    }

}
