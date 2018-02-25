package cbs;

import java.math.RoundingMode;

public class BusinessAccount extends Account {
    
    private String businessName;
    
    public BusinessAccount(String businessName, String firstName, String lastName,
            Address address, String phone, String email, Currency currency) {
        super(firstName, lastName, address, phone, email, currency);
        this.businessName = businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }

    @Override
    public void printBalance() {
        System.out.println("\nAccount Name: " + businessName
                + "\nBalance: " + getCurrency() + " "
                + getBalance().setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    @Override
    public String getAccountName() {
        return businessName;
    }

}
