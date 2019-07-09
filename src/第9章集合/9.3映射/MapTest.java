import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));
        System.out.println("org Map : \n" + staff);

        staff.remove("567-24-2546");
        System.out.println("remove \"567-24-2546\" Map : \n" + staff);

        staff.put("456-62-5527", new Employee("Francesca Miller"));
        System.out.println("put (\"456-62-5527\", \"Francesca Miller\") Map : \n" + staff);

        System.out.println("get(\"157-62-7935\") : \n" + staff.get("157-62-7935"));

        System.out.println("getOrDefault(\"123-45-6789\", new Employee(\"Bob Smith\")) : \n" 
            + staff.getOrDefault("123-45-6789", new Employee("Bob Smith")));

        System.out.println("\nforEach : ");
        staff.forEach((k, v) -> System.out.println("key = " + k + ", value = " +v));
    }
}
class Employee {
    private String name;
    Employee(String name) {
        this.name = name;
    }
    public String toString() {
        return "E[" + name + "]";
    }
}