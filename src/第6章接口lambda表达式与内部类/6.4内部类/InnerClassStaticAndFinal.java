public class InnerClassStaticAndFinal {
    public static void main(String[] args) {
        //Outer.Inner.f();
    }
}

class Outer {
    public static int v=5;
    class Inner {
        //static int vv=5;
        final static int vv=5;
        /*static void f() {
            System.out.println(v);
        }*/
    }
}