package cbs;

public class PersonalAccount extends Account {
    
    public PersonalAccount(String firstName, String lastName, Address address, 
            String phone, String email, Currency currency) {
        super(firstName, lastName, address, phone, email, currency);
    }

    @Override
    public void printBalance() {
        System.out.println("Account Name: " + getFirstName() + " " + getLastName()
                + "\nBalance: " + getCurrency() + " " + getBalance().toPlainString());
    }

    @Override
    public String getAccountName() {
        return getFirstName() + " " + getLastName();
    }

}
