package cbs;

public enum AcctStatus {

    OPEN, // Account is not frozen
    NO_DEBIT, // Debit transactions are prohibited (partial freeze).
    NO_CREDIT, // Credit transactions are prohibited (partial freeze).
    NO_TRANSACTION, // No transaction is allowed (total freeze).

}
