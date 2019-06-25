

# 《Java核心技术卷一》 笔记 6_0

2019.06.22 11:39



## 第6章 接口、lambda 表达式与内部类

接口（interface）

一个类可以实现（implement）一个或多个接口

lambda 表达式

内部类（inner class）

代理（proxy）



### 6.1 接口

#### 6.1.1 接口概念

接口不是类，而是对类的一组需求描述

类要遵从接口描述的格式统一定义



Arrays 类中的方法 sort 可以对对象数组进行排序，但要求所属类实现了 Comparable 接口

```java
public interface Comparable {
    int compareTo(Object other);
}
```

Java SE 5

```java
public interface Comarable<T> {
    int compareTo(T other);
}
```



接口中的所有方法是自动 public 的

接口中可以包含多个方法和常量

接口不能有实例域

Java SE 8 以前接口不能实现方法（不能引用实例域）

实例域和方法实现由实现接口的类提供



为了让类实现一个接口：

将类声明为实现给定的接口

对接口中的所有方法进行定义

```java
class Employee implements Comparable {
    public int compareTo(Object otherObject) {
    
    }
}
```

注意实现接口方法为 public



必须提供接口是因为 Java 是一种强类型（Strongly typed）语言

调用方法时，编译器会检查方法是否会存在



EmployeeSortTest.java

```java
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
```

结果

```text
name = Harry Hacker, salary = 35000.0
name = Tony Tester, salary = 38000.0
name = Carl Cracker, salary = 75000.0
```



语言标准规定（反对称性）

sgn(x.compareTo(y)) == -sgn(y.compareTo(x))

sgn 是符号函数



和 equals 同样，在继承时，Comparable 接口也有相似的问题

如果一个比较对于多个子类行为一致，应在超类中定义



#### 6.1.2 接口的特性

接口不是类，不能实例化对象

但是能定义接口类型的变量

接口变量必须引用实现了接口的类对象

可以使用 instanceof 来判断一个对象是不是实现了某个接口



接口也能被扩展（继承？）

```java
public interface Moveable {
    void move(double x, double y);
}
public interface Powered extends Moveable {
    double milesPerGallon();
}
```



不能包含实例域或静态方法，但可以包含常量

```java
public interface Powered extends Moveable {
    double milesPerGallon();
    double SPEED_LIMIT = 95;
}
```



接口中的方法默认标记为 public，域默认标记为 public static final



接口可以只定义常量而不定义方法，但不建议这样做



一个类只能有一个超类却可以实现多个接口

```java
class Employee implements Cloneable, Comparable {}
```



#### 6.1.3 接口与抽象类

有了抽象类还是需要接口，是因为一个类只能扩展一个超类

C++ 支持多重继承（multiple inheritance），Java 则不支持多重继承



#### 6.1.4 静态方法

Java SE 8 允许在接口中增加静态方法，虽然这有违接口作为抽象规范的初衷

很多时候使用一个接口和一个伴随类的方法解决这个问题，将静态方法放于伴随类中

如标准库中的 Collection/Collections，Path/Paths 



#### 6.1.5 默认方法

可以为接口方法提供默认实现

```java
public interface Comparable<T> {
    default int compareTo(T other) {
        return 0;
    }
}
```

默认实现的方法要用关键字 default 修饰

具有默认方法的 在实现类可以不给出实现



##### 接口演化（interface evolution）

默认方法的重要用法是接口演化（interface evolution）

假设最初有一些实现了某个接口的类，然后接口中加入了新的方法

如果方法不是默认方法，则所有原来实现接口的类都需要增加新的方法

如果不增加新的方法则实现接口的类不能通过编译

为接口添加非默认的方法不能保证源代码兼容（source compatible）

但如果不重新编译，而是使用类的 JAR 包，则可以正常加载类（接口增加方法可以保证二进制兼容）。但如果调用新加入接口的方法，则会报出 AbstractMethodError



对于增加默认方法的情况

类源文件可以通过编译

JAR 中的类可以正确加载，而且可以调用新加入的默认方法



#### 6.1.6 解决默认方法冲突

如果接口中定义了默认方法，另一个接口或超类中定义了同样的方法，此时

超类优先，如果超类中给出了具体方法，同名且具有相同参数类型的默认方法将被忽略

接口冲突，一个接口中有默认方法，其他接口有同名同参数的默认或非默认方法，编译器会报出错误，需要程序员在实现类中提供一个方法解决二义性。



对于实现多个接口的类，如果同名同参数的方法都是非默认的，则要么实现两个方法，要么作为一个抽象类存在。



由于类优先规则，不要定义任何 Object 方法中的方法为默认方法，这将不会生效



### 6.2 接口示例

#### 6.2.1 接口与回调

回调（callback）是一种常见的程序设计模式，在这种设计模式中，可以指出特定时间发生时应该采取的动作。



TimerTest.java

```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;// conflict with java.util.Timer

public class TimerTest {
    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        Timer t = new Timer(1000, listener); // 1000 ms interval
        t.start();
        // start() start Timer
        // end() end Timer
        JOptionPane.showMessageDialog(null, "Quit program?");
        // block
        System.exit(0);
    }
}
class TimePrinter implements ActionListener {
    public void actionPerformed(ActionEvent event) {
        System.out.println("At the tone, the time is " + new Date());
        //Toolkit.getDefaultToolkit().beep();
        // beep sound
    }
}
```



#### 6.2.2 Comparator 接口

对于某个对象 我们可能需要多种方式的比较函数，而某些类的比较函数我们并不能修改

此时需要使用比较器 Comparator 和 Arrays.sort 方法的第二个版本

```java
public interface Comparator<T> {
    int compare(T first, T second);
}
class LengthComparator implements Comparator<String> {
    public int compare(String first, String second) {
        return first.length() - second.length();
    }
}
```

比较时需要

```java
Comparator<String> comp = new LengthComparator();
if(comp.compare(words[i],words[j]) > 0) ...
```

对于排序

```java
String[] friends = {"Peter", "Acdefg", "Zzz"};
Arrays.sort(friends, new LengthComparator());
```



#### 6.2.3 对象克隆

如果希望拷贝一个对象，但之后原对象变量和新的对象变量具有不同的状态

这种情况应当使用 clone 方法



Object 中的 clone 是 protected 的

因为只拷贝域是不充分的（浅拷贝），基本类型没有问题，但对于引用变量，拷贝仍会和原件有数据共享。如果共享的子对象是不可变类，那么这种共享是安全的。



如果子对象存在可变的，就必须重新定义 clone 进行深拷贝



对于每个类确定：

默认的 clone 是否可以满足要求

是否可以在可变的子对象上调用 clone 修补默认的 clone 方法

是否不该使用 clone



对于以上前两种情况

需要实现 Cloneable 接口，重新定义 clone 为 public

外部代码并不能直接访问 protected 的 clone 方法（Object 继承来的）



Cloneable 接口是 Java 提供的一组标记接口（tagging interface）也称之为记号接口（marker interface）标记接口中不包含任何方法，只是为了方便使用 instanceof



浅拷贝够用时也需要实现 Cloneable 和将 clone 定义为 public

```java
class Employee implements Cloneable {
    public Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }
}
```

深拷贝

```java
class Employee implements Cloneable {
    public Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }
}
```

调用 clone 的对象没有定义 clone 时会抛出 CloneNotSupportedException 异常



捕获异常适合 final 类

```java
class Employee implements Cloneable {
    public Employee clone() throws CloneNotSupportedException {
        try {
            Employee cloned = (Employee) super.clone();
            cloned.hireDay = (Date) hireDay.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
```



注意子类的克隆

当 Employee 定义了 clone 则它的子类 Manager 可以调用这个 clone 但对于 Manager 自己的域可能并不能完成真正的 clone 操作



所有数组类型都有一个 public 的 clone 方法

卷二中将给出使用串行化进行拷贝的方法



Employee.java

```java
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
```

Manager.java

```java
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
```

CloneTest.java

```java
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
```

结果

```text
original = Employee[name = John Q., salary = 50000.0, hireDay = Sat Jan 01 00:00:00 CST 2000]
copy = Employee[name = John Q., salary = 55000.0, hireDay = Tue Dec 31 00:00:00 CST 2002]

================

staff before operation
Employee[name = Alice, salary = 10000.0, hireDay = Mon Jun 24 12:25:06 CST 2019]
Employee[name = Bob, salary = 20000.0, hireDay = Mon Jun 24 12:25:06 CST 2019]
Employee[name = Chris, salary = 30000.0, hireDay = Mon Jun 24 12:25:06 CST 2019]
staff
Employee[name = Alice, salary = 10000.0, hireDay = Sun Jan 01 00:00:00 CST 1995]
Employee[name = Bob, salary = 20000.0, hireDay = Mon Jan 01 00:00:00 CST 1996]
Employee[name = Chris, salary = 30000.0, hireDay = Wed Jan 01 00:00:00 CST 1997]
copyStaff
Employee[name = Alice, salary = 10000.0, hireDay = Sun Jan 01 00:00:00 CST 1995]
Employee[name = Bob, salary = 20000.0, hireDay = Mon Jan 01 00:00:00 CST 1996]
Employee[name = Chris, salary = 30000.0, hireDay = Wed Jan 01 00:00:00 CST 1997]

================

Original Manager Employee[name = Harry, salary = 50000.0, hireDay = Mon Jun 24 12:25:06 CST 2019][bonus = 50000.0, chargeDay = Mon Jun 24 12:25:06 CST 2019]
m instanceof Cloneable : true
Manager Employee[name = Harry, salary = 50000.0, hireDay = Mon Jun 24 12:25:06 CST 2019][bonus = 50000.0, chargeDay = Wed Jan 01 00:00:00 CST 2020]
Copy Manager Employee[name = Harry, salary = 50000.0, hireDay = Mon Jun 24 12:25:06 CST 2019][bonus = 50000.0, chargeDay = Wed Jan 01 00:00:00 CST 2020]
```



可以看到 数组的 clone 是执行的浅拷贝，数组中的变量引用的对象并没有拷贝

可以看到 对于没有在 Manager 中定义 clone 的情况可以执行拷贝，但是只对 manager 独有的域 chargeDay 执行了浅拷贝（Object 中的行为）

