import java.time.*;
import java.util.*;

public class ArrayListTest {
	public static void main(String[] args) {
		ArrayList<Employee> staff = new ArrayList<>();

		staff.add(new Employee("Carl Cracker", 75000, 1987, 12, 15)); 
		staff.add(new Employee("Harry Hacker", 50000, 1989, 10, 1));
		//staff.set(2, new Employee("Tony Tester", 40000, 1990, 3, 15));
		// java.lang.IndexOutOfBoundsException
		staff.add(new Employee("Tony Tester", 40000, 1990, 3, 15));

		for(Employee e : staff)
			e.raiseSalary(5);

		for(Employee e : staff)
			System.out.println("name=" + e.getName() + ", salary=" + e.getSalary()
							  + ", hireDay=" + e.getHireDay());
		
		System.out.println("\n===============\n");

		System.out.println(staff.get(0));

		staff.remove(0);
		System.out.println(staff.get(0));

	}
}

class Employee {
	private String name;
	private double salary;
	private LocalDate hireDay;
	public Employee(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
		hireDay = LocalDate.of(year, month, day);
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public LocalDate getHireDay() {
		return hireDay;
	}
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	@Override
	public String toString() {
		return "name = " + name + ", salary = " + salary 
		     + ", hireDay = " + hireDay;
	}
}