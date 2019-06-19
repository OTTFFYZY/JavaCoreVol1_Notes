public class CallAnotherConstructor {
    public static void main(String[] args) {
        Employee a = new Employee("Alice", 50000);
        Employee b = new Employee(60000);
        a.print();
        b.print();
    }
}
class Employee {
    static int nextId = 1;

    private String name;
    private double salary;
    private int id;

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.id = nextId++;
    }

    Employee(double salary) {
        this("Employee #" + nextId, salary);
    }

    public void print() {
        System.out.println("name = " + name + ", salary = " + salary
                         + ", id = " + id);
    }
}