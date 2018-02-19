
package cbs;

public class AcctSequence {
    
    private static int current = 0;
    
    public static String getCurrent() {
        return Integer.toString(current);
    }
    
    public static String getNext() {
        return Integer.toString(++current);
    }
}
