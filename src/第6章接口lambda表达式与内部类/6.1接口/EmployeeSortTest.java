import java.util.*;

public class EmployeeSortTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry Hacker", 35000);
        staff[1] = new Employee("Carl Cracker", 75000);
        staff[2] = new Employee("Tony Tester", 38000);

        Arrays.sort(staff);

        for(Employee e : staff) {
            e.print();
        }
    }
}

class Employee implements Comparable<Employee> {
    private String name;
    private double salary;
    
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void print() {
        System.out.println("name = " + name + ", salary = " + salary);
    }
    
    public int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }
}