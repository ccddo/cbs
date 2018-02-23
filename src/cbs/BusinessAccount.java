package cbs;

public class BusinessAccount extends Account {
    
    private String businessName;
    
    public BusinessAccount(String firstName, String lastName, Address address, 
            String phone, String email, String businessName, Currency currency) {
        super(firstName, lastName, address, phone, email, currency);
        this.businessName = businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessName() {
        return businessName;
    }
    
}
