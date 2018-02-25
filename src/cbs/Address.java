package cbs;

import java.io.Serializable;

public class Address implements Serializable {

    private String line1;
    private String line2;
    private String postCode;

    public Address(String line1, String line2, String postCode) {
        this.line1 = line1; // A good place for the so-called sentence case
        this.line2 = line2; // A good place for the so-called sentence case
        this.postCode = postCode.toUpperCase();
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
        this.postCode = postCode.toUpperCase();
    }

}
