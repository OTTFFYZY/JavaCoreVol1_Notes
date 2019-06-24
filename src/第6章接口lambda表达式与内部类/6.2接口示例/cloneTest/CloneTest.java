import java.util.*;

public class CloneTest {
    public static void main(String[] args) {
        try {
            Employee original = new Employee("John Q.", 50000);
            original.setHireDay(2000, 1, 1);
            Employee copy = original.clone();
            copy.raiseSalary(10);
            copy.setHireDay(2002, 12, 31);
            System.out.println("original = " + original);
            System.out.println("copy = " + copy);

            System.out.println("\n================\n");
            Employee[] staff = new Employee[3];
            staff[0] = new Employee("Alice", 10000);
            staff[1] = new Employee("Bob", 20000);
            staff[2] = new Employee("Chris", 30000);
            Employee[] copyStaff = staff.clone();

            System.out.println("staff before operation");
            for(Employee e : staff) 
                System.out.println(e);

            copyStaff[0].setHireDay(1995,1,1);
            copyStaff[1].setHireDay(1996,1,1);
            copyStaff[2].setHireDay(1997,1,1);
            
            System.out.println("staff");
            for(Employee e : staff) 
                System.out.println(e);
            System.out.println("copyStaff");
            for(Employee e : copyStaff)
                System.out.println(e);
            
            System.out.println("\n================\n");
            Manager m = new Manager("Harry" , 50000, 50000);
            System.out.println("Original Manager " + m);
            System.out.println("m instanceof Cloneable : " + (m instanceof Cloneable));
            Manager copyM = (Manager) m.clone();
            copyM.setChargeDay(2020,1,1);
            System.out.println("Manager " + m);
            System.out.println("Copy Manager " + copyM);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}