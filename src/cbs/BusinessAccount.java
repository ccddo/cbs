package cbs;

public class BusinessAccount extends Account {
    
    // Field to store the business name
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

    @Override // Implements the abstract method in the superclass
    public String getAccountName() {
        return businessName;
    }

}
