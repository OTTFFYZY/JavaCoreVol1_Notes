import java.util.*;
public class ConstructorTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry", 50000);
        staff[1] = new Employee(60000);
        staff[2] = new Employee();

        for(Employee e : staff)
            e.print();
    }
}

class Employee {
    private static int nextId;

    private int id;
    private String name = "";
    private double salary;

    // static initialization block
    static {
        Random gen = new Random();
        nextId = gen.nextInt(100000);
    }

    // object initialization block
    {
        id = nextId;
        nextId++;
    }

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    Employee(double salary) {
        this("Employee #" + nextId, salary);
    }

    Employee() {}

    public void print() {
        System.out.println("name = " + name + ", salary = " + salary
                         + ", id = " + id);
    }

}