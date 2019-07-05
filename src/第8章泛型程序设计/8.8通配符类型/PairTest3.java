import java.time.*;

public class PairTest3 {
    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 800000, 2003, 12, 15);
        Manager cfo = new Manager("Sid Sneaky", 600000, 2003, 12, 15);
        Pair<Manager> buddies = new Pair<>(ceo,cfo);
        printBuddies(buddies);
        ceo.setBonus(1000000);
        cfo.setBonus(500000);
        
        Manager[] managers = {ceo, cfo};
        Pair<Employee> result = new Pair<>();
        minmaxBonus(managers, result);
        printBuddies(result);
        maxminBonus(managers, result);
        printBuddies(result);
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
    }
    public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if(a.length == 0) return;
        Manager min = a[0];
        Manager max = a[0];
        for(int i = 1; i < a.length; i++) {
            if(min.getBonus() > a[i].getBonus()) min = a[i];
            if(max.getBonus() < a[i].getBonus()) max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
    }
    public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a,result);
        //PairAlg.swap(result);
        PairAlg.swapHelper(result);
    }
}

class Pair<T> {
    private T first;
    private T second;
    public Pair() { first = null; second = null; }
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() { return first; }
    public T getSecond() { return second; }
    public void setFirst(T newValue) { first = newValue; }
    public void setSecond(T newValue) { second = newValue; }
}

class PairAlg {
    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }
    public static void swap(Pair<?> p) {
        swapHelper(p);
    }
    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
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
}
class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public double getSalary() {
        return super.getSalary() + bonus;
    }
}