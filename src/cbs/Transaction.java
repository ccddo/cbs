package cbs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class Transaction implements Serializable {

    // Fields to store transaction information
    private final long timestamp;
    private final String accountNumber;
    private final TranType transactionType;
    private final String description;
    private final BigDecimal value;
    private final BigDecimal closingBalance;

    public Transaction(String accountNumber, TranType transactionType,
            String description, BigDecimal value, BigDecimal closingBalance) {
        this.timestamp = System.currentTimeMillis();
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.value = value;
        this.closingBalance = closingBalance;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Returns a string representation of the date and time of transaction
    private String getDateTime() {
        LocalDateTime time = LocalDateTime.ofEpochSecond(
                timestamp / 1000, 0, ZoneOffset.UTC);
        return String.format("%02d/%02d/%d %02d:%02d:%02d", time.getDayOfMonth(),
                time.getMonth().getValue(), time.getYear(),
                time.getHour(), time.getMinute(), time.getSecond());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TranType getTransactionType() {
        return transactionType;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    // Used to display header information for an account statement
    public static void printHeader() {
        System.out.println("\nDescription/Date\tType\tValue\t\tBalance");
    }

    @Override // String representation of a transaction
    public String toString() {
        return "\n(" + description + ")\n"
                + getDateTime() + "\t" + transactionType + "\t"
                + value.setScale(2, RoundingMode.HALF_UP).toPlainString() + "\t\t"
                + closingBalance.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

}
