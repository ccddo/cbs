package cbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import uod.gla.io.Storage;
import uod.gla.menu.Finalisable;
import uod.gla.menu.MenuBuilder;
import uod.gla.menu.MenuItem;
import uod.gla.util.Reader;

public class App implements Finalisable {

    // List to store transactions in the ledger
    static List<Transaction> ledger = new ArrayList<>();

    // List to store all account information, including balances
    static List<Account> accounts = new ArrayList<>();

    // Used by MenuItem and MenuBuilder (see the Utility API documentation)
    static App app = new App();

    public static void main(String[] args) {

        System.out.println("Welcome to CBS Banking Application");

        // Load the accounts and transaction ledger from disk and
        // Initialise the lists with these if the load was successfully done.
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

        // Create MenuItem objects representing each available menu option
        MenuItem c = new MenuItem("C", "Create New Account", app, "createNewAccount");
        MenuItem m = new MenuItem("M", "Modify Account Details", app, "modifyAccountDetails");
        MenuItem p = new MenuItem("P", "Change Account PIN", app, "changeAccountPIN");
        MenuItem d = new MenuItem("D", "Deposit Cash", app, "depositCash");
        MenuItem w = new MenuItem("W", "Withdraw Cash", app, "withdrawCash");
        MenuItem t = new MenuItem("T", "Transfer Funds", app, "transferFunds");
        MenuItem b = new MenuItem("B", "Balance Inquiry", app, "balanceInquiry");
        MenuItem s = new MenuItem("S", "View Account Statement", app, "viewAccountStatement");
        MenuItem a = new MenuItem("A", "Modify Account Status", app, "modifyAccountStatus");
        MenuItem l = new MenuItem("L", "Liability Report", app, "liabilityReport");

        // Build the user interface and display the menu
        MenuBuilder.displayMenu(app, c, m, p, d, w, t, b, s, a, l);

        // Run the finalise() method when the application is about to shutdown.
        app.finalise();
        System.out.println("Good bye!");
    }

    @Override // Saves the application data to disk, on shut down
    public void finalise() {
        Storage.save((Serializable) ledger, "ledger", true);
        Storage.save((Serializable) accounts, "accounts", true);
        Storage.save(AcctSequence.getCurrent(), "currSeq");
    }

    public static void createNewAccount() {
        MenuItem p = new MenuItem("P", "Personal Account", app, "createPersonalAccount");
        MenuItem b = new MenuItem("B", "Business Account", app, "createBusinessAccount");
        MenuBuilder.displayMenuOnce("What type of account do you want to create?\n"
                + "Personal or Business Account?", p, b);
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
        System.out.println("\nPersonal account successfully created!");
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
        System.out.println("\nBusiness account successfully created!");
        System.out.println("Your account number is " + ba.getAcctNumber());
    }

    public static void modifyAccountDetails() {
        MenuItem p = new MenuItem("P", "Personal Account", app, "modifyPersonalAccount");
        MenuItem b = new MenuItem("B", "Business Account", app, "modifyBusinessAccount");
        MenuBuilder.displayMenuOnce("What type of account do you want to modify?\n"
                + "Personal or Business Account?", p, b);
    }

    public static void modifyPersonalAccount() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        } else if (!(acct instanceof PersonalAccount)) {
            System.err.println("That's not a personal account!");
            return;
        }
        if (!acct.validatePIN(Reader.readNumberString("Enter PIN"))) {
            System.err.println("Incorrect PIN!");
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
            System.err.println("That's not a business account!");
            return;
        }
        if (!acct.validatePIN(Reader.readNumberString("Enter PIN"))) {
            System.err.println("Incorrect PIN!");
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

    // Used to search for an account.
    private static Account search() {
        boolean found = false;
        Account acct = null;
        while (!found) {
            String acctNumber = Reader.readNumberString("Enter account number");
            for (Account a : accounts) {
                if (a.getAcctNumber().equals(acctNumber)) {
                    acct = a;
                    break;
                }
            }
            if (acct != null) {
                System.out.println("Account Name: " + acct.getAccountName());
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
        boolean deposited = acct.deposit(value);
        if (deposited
                && Reader.readBoolean("Would you like to see your closing balance?")) {
            if (acct.validatePIN(Reader.readNumberString("Enter PIN"))) {
                acct.printBalance();
            } else {
                System.err.println("Incorrect PIN!");
            }
        }
        if (deposited) {
            Transaction t = new Transaction(acct.getAcctNumber(), TranType.DEP,
                    "Cash Deposit", value, acct.getBalance());
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
        boolean withdrawn = acct.withdraw(value, Reader.readNumberString("Enter PIN"));
        if (withdrawn) {
            Transaction t = new Transaction(acct.getAcctNumber(), TranType.WDR,
                    "Cash Withdrawal", value.negate(), acct.getBalance());
            ledger.add(t);
        }
    }

    public static void transferFunds() {
        System.out.println("===Source account search===");
        Account src = search();
        if (src == null) {
            System.err.println("Account not found!");
            return;
        }
        System.out.println("\n===Destination account search===");
        Account dest = search();
        if (dest == null) {
            System.err.println("Account not found!");
            return;
        }
        BigDecimal value = BigDecimal.valueOf(Reader.readDouble("\nEnter transfer amount"));
        boolean transferred = src.transfer(value, dest, Reader.readNumberString("Enter PIN"));
        if (transferred) {
            Transaction ts = new Transaction(src.getAcctNumber(),
                    TranType.TRF, "Cash Transfer to " + dest.getAccountName(), 
                    value.negate(), src.getBalance());
            Transaction td = new Transaction(dest.getAcctNumber(),
                    TranType.TRF, "Cash Transfer from " + src.getAccountName(), 
                    value, dest.getBalance());
            ledger.add(ts);
            ledger.add(td);
        }
    }

    public static void balanceInquiry() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        if (acct.validatePIN(Reader.readNumberString("Enter PIN"))) {
            acct.printBalance();
        } else {
            System.err.println("Incorrect PIN!");
        }
    }

    public static void changeAccountPIN() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        boolean changed = acct.changePIN(
                Reader.readNumberString("Enter current PIN"),
                Reader.readNumberString("Enter new PIN", 4));
        if (changed) {
            System.out.println("PIN change sucessful!");
        }
    }

    public static void viewAccountStatement() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        if (!acct.validatePIN(Reader.readNumberString("Enter PIN"))) {
            System.err.println("Incorrect PIN!");
            return;
        }
        Transaction.printHeader();
        for (int i = ledger.size() - 1; i >= 0; i--) {
            Transaction t = ledger.get(i);
            if (t.getAccountNumber().equals(acct.getAcctNumber())) {
                System.out.println(t);
            }
        }
    }

    public static void modifyAccountStatus() {
        Account acct = search();
        if (acct == null) {
            System.err.println("Account not found!");
            return;
        }
        AcctStatus status = Reader.readEnum("Select an account status", AcctStatus.class);
        acct.setStatus(status);
        System.out.println("Account status now set to " + status);
    }

    public static void liabilityReport() {
        System.out.println("Details of liabilities");
        BigDecimal usds, gbps, eurs;
        usds = gbps = eurs = new BigDecimal(0);
        for (Account a : accounts) {
            if (null != a.getCurrency()) {
                switch (a.getCurrency()) {
                    case GBP:
                        gbps = gbps.add(a.getBalance());
                        break;
                    case EUR:
                        eurs = eurs.add(a.getBalance());
                        break;
                    case USD:
                        usds = usds.add(a.getBalance());
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("GBP "
                + gbps.setScale(2, RoundingMode.HALF_UP).toPlainString());
        System.out.println("EUR "
                + eurs.setScale(2, RoundingMode.HALF_UP).toPlainString());
        System.out.println("USD "
                + usds.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

}
