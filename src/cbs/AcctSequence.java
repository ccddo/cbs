package cbs;

import uod.gla.io.Storage;

public class AcctSequence {

    // Static field used to store the last assigned account number
    private static int current;

    // Initialises the last assigned account number with the value
    // retrieved from the disk (i.e. the last assigned account number
    // which was saved to the disk).
    static {
        Integer currentSeq = Storage.<Integer>retrieve("currSeq");
        if (currentSeq != null) {
            current = currentSeq;
        } else {
            current = 0;
        }
    }

    public static int getCurrent() {
        return current;
    }
    
    public static String getNext() {
        return Integer.toString(++current);
    }
}
