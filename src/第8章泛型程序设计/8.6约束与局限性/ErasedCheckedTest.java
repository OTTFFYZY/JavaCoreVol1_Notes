import java.util.*;
import java.io.*;

public class ErasedCheckedTest {
    public static void main(String[] args) {
        new Block() {
            public void body() throws Exception {
                Scanner in = new Scanner(new File("ququx"), "UTF-8");
                while(in.hasNext())
                    System.out.println(in);
            }
        }.toThread().start();
    }
}

abstract class Block {
    public abstract void body() throws Exception;
    public Thread toThread() {
        return new Thread() {
            public void run() {
                try {
                    body();
                } catch (Throwable t) {
                    Block.<RuntimeException>throwAs(t);
                }
            }
        };
    }
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable e) throws T {
        throw (T) e;
    } 
}