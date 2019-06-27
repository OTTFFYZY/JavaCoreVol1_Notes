public class BridgeMethodTest2 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee e = new Employee("Alice", 20000);
        Employee e2 = e.clone();
        e2.setSalary(50000);
        e.print();
        e2.print();
    }
}
class Employee implements Cloneable {
    private String name;
    private double salary;
    public Employee() {}
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void print() {
        System.out.println("name = " + name + ", salary = " + salary);
    }
    public Employee clone() throws CloneNotSupportedException {
        Employee e = (Employee) super.clone();
        System.out.println("Employee.clone");
        return e;
    }
}