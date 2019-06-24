import java.util.*;

public class Employee implements Cloneable {
	private String name;
	private double salary;
	private Date hireDay;
	public Employee(String n, double s) {
		name = n;
		salary = s;
        hireDay = new Date();
    }
    
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
    }
    
    public void setHireDay(int year, int month, int day) {
        Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();
        hireDay.setTime(newHireDay.getTime());
    }

    public Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }

    public String toString() {
        return "Employee[name = " + name + ", salary = " + salary 
             + ", hireDay = " + hireDay + "]";
    }
}