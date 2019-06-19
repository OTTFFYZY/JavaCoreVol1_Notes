public class ConstructOrderTest {
    static {
        System.out.println("Add ConstructOrderTest class");
    }
    public static void main(String[] args) {
        System.out.println("Begin Main\n");
        ClassA objA = new ClassA();
        System.out.println("====================\nshow objA");
        objA.print();
        System.out.println("\n ======================== \n");
        ClassA objB = new ClassA(5);
        System.out.println("====================\nshow objB");
        objB.print();
        System.out.println("\nEnd Main");
    }
}

class ClassA {
    static public void main(String[] args) {
        System.out.println("Begin ClassA Main");
        System.out.println("End ClassA Main");
    }

    ClassA() {
        System.out.println("Begin ClassA()");
        System.out.println("End ClassA()");
    }
    ClassA(int a) {
        this();
        System.out.println("Begin ClassA(int)");
        print();
        this.a = a;
        print();
        System.out.println("End ClassA(int)");
    }
    
    static int sa = -1;

    int a = -1;
    int b = -1;
    
    {
        System.out.println("Begin: Object Initialization Block 1");
        print();
        a = 1;
        c = 1;
        print();
        System.out.println("End: Object Initialization Block 1\n");
    }

    public void print() {
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
    }

    private int c = -1;

    {
        System.out.println("Begin: Object Initialization Block 2");
        print();
        b = 2;
        c = 2;
        print();
        System.out.println("End: Object Initialization Block 2\n");
    }

    static {
        System.out.println("Begin: Static Initialization Block 1");
        sprint();
        sa = 1;
        sc = 1;
        sprint();
        System.out.println("End: Static Initialization Block 1\n");
    }

    public static void sprint() {
        System.out.println("sa = " + sa + ", sb = " + sb + ", sc = " + sc);
    }

    static int sb = -1;
    static int sc = -1;

    static {
        System.out.println("Begin: Static Initialization Block 2");
        sprint();
        sb = 2;
        sc = 2;
        sprint();
        System.out.println("End: Static Initialization Block 2\n");
    }

}