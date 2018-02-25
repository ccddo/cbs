package cbs;

import uod.gla.io.Storage;

public class AcctSequence {
    
    private static int current;

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
