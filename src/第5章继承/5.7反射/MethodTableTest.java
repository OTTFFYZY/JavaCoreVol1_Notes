import java.lang.reflect.*;

public class MethodTableTest {
    public static void main(String[] args) throws Exception {
        Method square = MethodTableTest.class.getMethod("square", double.class);
        Method sqrt = Math.class.getMethod("sqrt", double.class);

        printTable(1, 10, 10, square);
        printTable(1, 10, 10, sqrt);
    }
    public static double square(double x) {
        return x * x;
    }
    public static void printTable(double from, double to, int n, Method f) {
        double dx = (to - from) / (n - 1);

        for(double x = from; x <= to; x += dx) {
            try {
                double y = (Double) f.invoke(null, x);
                System.out.printf("%10.4f | %10.4f\n", x , y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}