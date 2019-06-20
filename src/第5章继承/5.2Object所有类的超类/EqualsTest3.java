import java.util.Objects;

public class EqualsTest3 {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", 50000);
        Employee m1 = new Manager("Alice", 50000, 30000);
        Employee e2 = new Employee("Alice", 50000);
        Employee m2 = new Manager("Alice", 50000, 20000);
        System.out.println("e1.equals(m1) = " + e1.equals(m1));
        System.out.println("m1.equals(e1) = " + m1.equals(e1));
        System.out.println("e1.equals(e2) = " + e1.equals(e2));
        System.out.println("m1.equals(m2) = " + m1.equals(m2));
    }
}

class Employee {
    private String name;
    private double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(!(otherObject instanceof Employee)) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.getName())
            && salary == other.getSalary();
    }
}

class Manager extends Employee {
    private double bonus;
    Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }
    public double getBonus() {
        return bonus;
    }
}