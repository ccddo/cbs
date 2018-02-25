package cbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class Account implements Serializable {

    // Field to store the account details
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String email;

    // Fields for account number and PIN
    private final String acctNumber;
    private String PIN = "0000";

    /*
     * A note about the design of this class.
     * A possibly better way to protect this class would be to require
     *      all methods to check the PIN first. This ensures that
     *      no action can be done on an account without first
     *      verifying the PIN. If the PIN is incorrect, an exception
     *      can be thrown.
     */

    // Fields for currency information and account balance
    private final Currency currency;
    private BigDecimal balance = new BigDecimal(0);

    // Field for account status i.e. whether it's frozen or not
    private AcctStatus status = AcctStatus.OPEN;

    // Constructor for generic account
    public Account(String firstName, String lastName, Address address,
            String phone, String email, Currency currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.currency = currency;
        this.acctNumber = AcctSequence.getNext();
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Abstract method to retrieve account name.
    // Implementation depends on the kind of account i.e. the subclass
    public abstract String getAccountName();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(AcctStatus status) {
        this.status = status;
    }

    public AcctStatus getStatus() {
        return status;
    }

    // Authenticate the PIN without revealing the stored PIN.
    // Returns true if the PIN is correct, false otherwise.
    // In a real scenario, the PIN won't be stored literally as a String.
    public boolean validatePIN(String PIN) {
        return this.PIN.equals(PIN);
    }

    // Method to change PIN after authenticating with old PIN
    public boolean changePIN(String oldPIN, String newPIN) {
        if (validatePIN(oldPIN)) {
            this.PIN = newPIN;
            return true;
        } else {
            System.err.println("Incorrect PIN!");
            return false;
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // This method prints the account balance to screen.
    // It uses subclass implementation of getAccountName().
    public void printBalance() {
        System.out.println("\nAccount Name: " + getAccountName()
                + "\nBalance: " + currency + " "
                + balance.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    public boolean withdraw(BigDecimal amount, String PIN) {
        if (!validatePIN(PIN)) {
            System.err.println("Incorrect PIN!");
            return false;
        } else if (status == AcctStatus.NO_DEBIT
                || status == AcctStatus.NO_TRANSACTION) {
            System.err.println("Account frozen!");
            return false;
        } else if (balance.compareTo(amount) < 0) {
            System.err.println("Insufficient funds!");
            return false;
        } else {
            balance = balance.subtract(amount);
            System.out.println("Cash withdrawal successful!\nYour balance is "
                    + currency + " "
                    + balance.setScale(2, RoundingMode.HALF_UP).toPlainString());
        }
        return true;
    }

    public boolean deposit(BigDecimal amount) {
        if (status == AcctStatus.NO_CREDIT
                || status == AcctStatus.NO_TRANSACTION) {
            System.err.println("Account frozen!");
            return false;
        } else {
            balance = balance.add(amount);
            System.out.println("Cash deposit successful!");
        }
        return true;
    }

    public boolean transfer(BigDecimal amount, Account dest, String PIN) {
        if (!validatePIN(PIN)) {
            System.err.println("Incorrect PIN!");
            return false;
        } else if (status == AcctStatus.NO_DEBIT
                || status == AcctStatus.NO_TRANSACTION) {
            System.err.println("Source account frozen!");
            return false;
        } else if (dest.status == AcctStatus.NO_CREDIT
                || dest.status== AcctStatus.NO_TRANSACTION) {
            System.err.println("Destination account frozen!");
            return false;
        } else if (balance.compareTo(amount) < 0) {
            System.err.println("Insufficient funds!");
            return false;
        } else {
            balance = balance.subtract(amount);
            dest.balance = dest.balance.add(amount);
            System.out.println("Funds transfer successful!\nYour balance is "
                    + currency + " "
                    + balance.setScale(2, RoundingMode.HALF_UP).toPlainString());
            return true;
        }
    }

    @Override // The equality of this class is based on the account number
    // This is because, by design, two accounts cannot have the same acct number
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Account
                && ((Account) obj).acctNumber.equals(this.acctNumber);
    }

    @Override // Must be defined if equals is defined
    // and should use the same fields as is used in the equals method
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.acctNumber);
        return hash;
    }
    
}
