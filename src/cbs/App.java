package cbs;

import java.util.ArrayList;
import java.util.List;
import uod.gla.menu.MenuBuilder;
import uod.gla.menu.MenuItem;

public class App {

    static List<Transaction> ledger = new ArrayList<>();
    static List<Account> accounts = new ArrayList<>();

    static App app = new App();

    public static void main(String[] args) {
        MenuItem c = new MenuItem("C", "Create a new account", app, "createNewAccount");
        MenuItem m = new MenuItem("M", "Modify Account Details", app, "modifyAccountDetails");
        MenuItem d = new MenuItem("D", "Deposit Cash", app, "depositCash");
        MenuItem w = new MenuItem("W", "Withdraw Cash", app, "withdrawCash");
        MenuItem t = new MenuItem("T", "Transfer Funds", app, "transferFunds");
        MenuItem b = new MenuItem("B", "Balance Inquiry", app, "balanceInquiry");
        MenuItem s = new MenuItem("S", "View Account Statement", app, "viewAccountStatement");
        MenuItem a = new MenuItem("A", "Modify Account Status", app, "modifyAccountStatus");
        MenuItem l = new MenuItem("L", "Liability Report", app, "liabilityReport");
        MenuBuilder.displayMenu(c, m, d, w, t, b, s, a, l);
    }

    public static void createNewAccount() {

    }

    public static void modifyAccountDetails() {

    }

    public static void depositCash() {

    }

    public static void withdrawCash() {

    }

    public static void transferFunds() {

    }

    public static void balanceInquiry() {

    }

    public static void viewAccountStatement() {

    }

    public static void modifyAccountStatus() {

    }

    public static void liabilityReport() {

    }

}
