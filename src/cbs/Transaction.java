package cbs;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {

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

}
