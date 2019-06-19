public class ParamTest {
    public static void main(String[] args) {
        // Methods can not modify numeric parameters
        System.out.println("Testing numeric parameter");
        double percent = 10;
        System.out.println("Before: percent = " + percent);
        tripleValue(percent);
        System.out.println("After: percent = " + percent);

        // Methods can change the state of object parameters
        System.out.println("\nTesting state of object parameters");
        Employee harry = new Employee("Harry", 50000);
        System.out.println("Before: salary = " + harry.getSalary());
        tripleSalary(harry);
        System.out.println("After: salary = " + harry.getSalary());

        // Methods can not attach new objects to object parameters
        System.out.println("\nTesting object parameters");
        Employee a = new Employee("Alice", 70000);
        Employee b = new Employee("Bob", 60000);
        System.out.println("Before: a = " + a.getName());
        System.out.println("Before: b = " + b.getName());
        swap(a, b);
        System.out.println("After: a = " + a.getName());
        System.out.println("After: b = " + b.getName());
    }

    public static void tripleValue(double x) {
        x *= 3;
        System.out.println("End of method: x = " + x);
    }
    public static void tripleSalary(Employee e) {
        e.raiseSalary(200);
        System.out.println("End of method: salary = " + e.getSalary());
    }

    public static void swap(Employee x, Employee y) {
        Employee tmp = x;
        x = y;
        y = tmp;
        System.out.println("End of method: E x = " + x.getName());
        System.out.println("End of method: E y = " + y.getName());
    }
}
class Employee {
	private String name;
	private double salary;

	public Employee(String n, double s) {
		name = n;
		salary = s;
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}
}