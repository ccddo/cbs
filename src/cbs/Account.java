
package cbs;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Account {
    
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String email;
    
    private final String acctNumber;
    private String PIN = "0000";

    private final Currency currency;
    private BigDecimal balance = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);

    private AcctStatus status = AcctStatus.OPEN;

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

    public boolean checkPIN(String PIN) {
        return this.PIN.equals(PIN);
    }

    public boolean changePIN(String oldPIN, String newPIN) {
        if (checkPIN(oldPIN)) {
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

    public boolean withdraw(BigDecimal amount, String PIN) {
        if (checkPIN(PIN)) {
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
            balance = balance.subtract(amount.setScale(2, RoundingMode.HALF_UP));
        }
        return true;
    }

    public boolean deposit(BigDecimal amount, String PIN) {
        if (checkPIN(PIN)) {
            System.err.println("Incorrect PIN!");
            return false;
        } else if (status == AcctStatus.NO_CREDIT
                || status == AcctStatus.NO_TRANSACTION) {
            System.err.println("Account frozen!");
            return false;
        } else {
            balance = balance.add(amount.setScale(2, RoundingMode.HALF_UP));
        }
        return true;
    }

    public boolean transfer(BigDecimal amount, Account dest, String PIN) {
        if (checkPIN(PIN)) {
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
            balance = balance.subtract(amount.setScale(2, RoundingMode.HALF_UP));
            dest.balance = dest.balance.add(amount.setScale(2, RoundingMode.HALF_UP));
            return true;
        }
    }
    
}
