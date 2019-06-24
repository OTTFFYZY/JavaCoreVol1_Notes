import java.util.*;
public class Manager extends Employee {
    private double bonus;
    private Date chargeDay;
    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
        chargeDay = new Date();
    }
    public void setChargeDay(int year, int month, int day) {
        Date newChargeDay = new GregorianCalendar(year, month - 1, day).getTime();
        chargeDay.setTime(newChargeDay.getTime());
    }
    public String toString() {
        return super.toString() + "[bonus = " + bonus + ", chargeDay = " + chargeDay + "]";
    }
}