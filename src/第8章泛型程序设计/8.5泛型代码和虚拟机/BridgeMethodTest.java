import java.time.*;

public class BridgeMethodTest {
    public static void main(String[] args) {
        Pair di = new Pair(LocalDate.of(1995,5,1),LocalDate.of(2000,5,1));
        System.out.println("di : " + di);
        di.setSecond(LocalDate.of(1900,5,1));
        System.out.println("di.setSecond(LocalDate.of(1900,5,1)) : " + di);
        di.setSecond(LocalDate.of(2010,5,1));
        System.out.println("di.setSecond(LocalDate.of(2010,5,1)) : " + di);

        System.out.println("\n===========================\n");

        DateInterval di2 = new DateInterval(LocalDate.of(1995,5,1),LocalDate.of(2000,5,1));
        System.out.println("di2 : " + di2);
        di2.setSecond(LocalDate.of(1900,5,1));
        System.out.println("di2.setSecond(LocalDate.of(1900,5,1)) : " + di2);
        di2.setSecond(LocalDate.of(2010,5,1));
        System.out.println("di2.setSecond(LocalDate.of(2010,5,1)) : " + di2);
        
        System.out.println("\n===========================\n");
        LocalDate ld = di2.getSecond();
        System.out.println("ld : " + ld);
    }
}
class Pair<T> {
    private T first;
    private T second;
    public Pair() { first = null; second = null; }
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() { return first; }
    public T getSecond() { return second; }
    public void setFirst(T newValue) { first = newValue; }
    public void setSecond(T newValue) { second = newValue; }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
class DateInterval extends Pair<LocalDate> {
    public DateInterval() { super(); }
    public DateInterval(LocalDate first, LocalDate second) {
        super();
        if(second.compareTo(first) >= 0) {
            setFirst(first);
            super.setSecond(second);
        } else {
            System.out.println("second should be Later than first");
            //throw new IllegalArgumentException();
        }
    }
    public void setSecond(LocalDate second) {
        if(second.compareTo(getFirst()) >= 0)
            super.setSecond(second);
        else {
            System.out.println("second should be Later than first");
            //throw new IllegalArgumentException();
        }
    }
    public LocalDate getSecond() {
        System.out.println("LocalDate.getSecond");
        return (LocalDate) super.getSecond();
    }
}