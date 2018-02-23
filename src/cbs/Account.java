
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
    private final Currency currency;
    
    private BigDecimal balance = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
    private AcctStatus status = AcctStatus.OPEN;
    
    private String PIN = "0000";

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

    public boolean checkPIN(String PIN) {
        return this.PIN.equals(PIN);
    }

    public boolean changePIN(String oldPIN, String newPIN) {
        if (checkPIN(oldPIN)) {
            this.PIN = newPIN;
            return true;
        } else {
            System.out.println("Incorrect PIN!");
            return false;
        }

    }

    public void setStatus(AcctStatus status) {
        this.status = status;
    }

    public AcctStatus getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
}
