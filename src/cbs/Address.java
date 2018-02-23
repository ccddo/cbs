package cbs;

public class Address {

    private String line1;
    private String line2;
    private String postCode;

    public Address(String line1, String line2, String postCode) {
        this.line1 = line1;
        this.line2 = line2;
        this.postCode = postCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

}
