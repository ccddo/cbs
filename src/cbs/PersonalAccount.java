package cbs;

import java.math.RoundingMode;

public class PersonalAccount extends Account {
    
    public PersonalAccount(String firstName, String lastName, Address address, 
            String phone, String email, Currency currency) {
        super(firstName, lastName, address, phone, email, currency);
    }

    @Override
    public void printBalance() {
        System.out.println("\nAccount Name: " + getFirstName() + " " + getLastName()
                + "\nBalance: " + getCurrency() + " "
                + getBalance().setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    @Override
    public String getAccountName() {
        return getFirstName() + " " + getLastName();
    }

}
