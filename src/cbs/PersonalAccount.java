
package cbs;


public class PersonalAccount extends Account {
    
    public PersonalAccount(String firstName, String lastName, Address address, 
            String phone, String email, Currency currency) {
        super(firstName, lastName, address, phone, email, currency);
    }
    
}
