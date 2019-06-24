public class ProcessLambda {
    public static void main(String[] args) {
        repeat(10, i -> System.out.println("Countdown : " + (9 - i)));
    }
    public static void repeat(int n, IntConsumer action) {
        for(int i = 0; i < n; i++)
            action.accept(i);
    }
}
interface IntConsumer {
    void accept(int value);
}