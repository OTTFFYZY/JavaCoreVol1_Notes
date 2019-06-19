public class ExplicitInit {
    public static void main(String[] args) {
        Employee a = new Employee();
        Employee b = new Employee();
        Employee c = new Employee();
        System.out.println("a id = " + a.getId());
        System.out.println("b id = " + b.getId());
        System.out.println("c id = " + c.getId());
    }
}
class Employee {
    private static int nextId = 1;
    private int id = assignId();
    private static int assignId() {
        return nextId++;
    }

    int getId() {
        return id;
    }
}