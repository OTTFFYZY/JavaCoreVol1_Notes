

# 《Java核心技术卷一》 笔记 5_0

2019.06.19 16:50



## 第5章 继承（Inheritance）

本章介绍继承和反射（reflection）的概念



### 5.1 类、超类和子类

比如经理 Manager 也是员工 Employee 我们希望 Manager 可以直接使用 Employee 的一些属性和方法因此可以使用继承



#### 5.1.1 定义子类

```java
public class Manager extends Employee {

}
```

 

Java 与 C++ 的继承方式类似 extends 关键字取代了 : 符号

Java 所有继承都是公有继承没有私有和保护继承



已经存在的类称为 超类（superclass）、基类（base class）或者父类（parent class）

新派生出的类称为子类（subclass）、派生类（derived class）或孩子类（child class）



一般子类比超类功能更加丰富

子类会自动继承超类的域和方法而不必要显式定义



#### 5.1.2 覆盖方法

超类有些方法子类并不适用，此时要提供一个新方法覆盖（Override）超类中的方法

```java
public class Manager extends Employee {
    private double bonus;
    public double getSalary() {
        return salary + bonus;
    }
}
```

但此时并不能正常运行，因为子类的方法不能访问超类的私有域

此时需要使用 super 关键字调用超类方法

```java
public class Manager extends Employee {
    private double bonus;
    public double getSalary() {
        return super.getSalary() + bonus;
    }
}
```



注意 super 和 this 的用法看起来相似，但其实是不同的概念

super 并不是一个对象的引用，不能将 super 赋值给其他的变量



不能删除继承的超类的方法



C++ 使用超类名和 :: 调用超类方法



#### 5.1.3 子类构造器

可以使用 super 调用超类构造器

子类如果不显式调用超类的构造器，将会自动调用超类的无参构造器，没有定义时会出错

调用构造器的语句只能在构造器第一句出现



ManagerTest.java

```java
public class ManagerTest {
    public static void main(String[] args) {
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        boss.setBonus(5000);
        Employee[] staff = new Employee[3];
        staff[0] = boss;
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tommy Tester", 40000, 1990, 3, 15);

        for(Employee e : staff)
            System.out.println("name = " + e.getName() + ", salary = " + e.getSalary());

    }
}
```

Manager.java

```java
public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalary() {
        return super.getSalary() + bonus;
    }
}
```

Employee.java

```java
import java.time.*;

public class Employee {
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
```

运行

```shell
java ManagerTest
```

得到结果

```text
name = Carl Cracker, salary = 85000.0
name = Harry Hacker, salary = 50000.0
name = Tommy Tester, salary = 40000.0
```



注意到 当把 boss 也视作 Employee 对象操作时依旧可以调用到自己的 getSalary()

多态（polymorphism）引用可以引用多种实际类型对象的现象

动态绑定（dynamic binding）运行时自动选择调用哪个方法



Java 不需要将方法定义为 vitual，如果不需要虚拟特性可以标记为 final



#### 5.1.4

Java 继承不只限于一个层次。

继承层次（inheritance hierarchy）由一个公共超类派生出来的所有类的集合

继承链（inheritance chain）继承层次中，从某个特定的类到其祖先的路径



Java 不同于 C++，不支持多重继承



#### 5.1.5 多态

判断是否设计为继承，可以用 “is-a” 规则，即判断子类每个对象是不是也是超类的对象

另一种表述是置换法则，程序中任意位置的超类对象都可以用子类对象置换



超类对象变量任意时刻都可以引用子类对象（不需要强制类型转换）

不能将超类对象赋值给子类



子类数组引用可以赋值给超类数组的引用

但如果此时对超类数组的某个域进行赋值，赋值为超类对象

此时再调用子类数组该位置子类对象方法将会发生错误



#### 5.1.6 理解方法调用

调用过程：

1.编译器查看对象的声明类型和方法名。编译器列举方法名所有同名方法和超类中 public 的同名方法

2.编译器查看调用方法时的参数类型，如果有一个完全匹配，则使用这个方法。这个过程称为重载解析（Overloading Resolution）。由于允许类型转换，所以这个过程很复杂。如果编译器没有找到参数类型匹配的方法，或类型转换后与多个方法匹配，则会报错



同样签名的子类方法会覆盖超类方法。返回类型不是签名的一部分，覆盖方法要保证返回类型的兼容性。

```java
class Employee {
    public Employee f() {...}
}
class Manager extends Employee {
	public Manager f() {...}
}
```

如上，我们称两个f 具有可协变的返回类型。



3.对于 private，static，final 方法或构造器，由于能够准确知道调用哪个方法，我们称这种调用方式为静态绑定（static binding），与之对应的是需要依赖与隐式参数实际类型，运行时实现的动态绑定



4.当程序运行，并且采用动态绑定调用方法时，虚拟机调用实际类型最合适的类的方法。先从实际类型找方法，没用则找其超类。

为了不每次找方法都进行搜索，虚拟机创建了方法表（method table）

如果调用 super.f() 则编译器（应该是虚拟机？）将对隐式参数超类方法表进行搜索



动态绑定的重要特性：不需要对现存代码进行修改就可以对程序进行扩展。



覆盖方法时，子类方法不能低于超类方法的可见性。如果超类是 public 子类也必须是 public



#### 5.1.7 组织继承：final 类和方法

final 类：用 final 关键字修饰，不允许继承的类

类中方法也可以用 final 修饰，这样子类就不能覆盖这个方法。

final 类中的方法会自动变成 final 方法（域不会变为 final）



Java 默认具有多态性，而 C++ 默认没有



现在虚拟机可以通过推测，判断类的方法是否被覆盖。简短，频繁使用并且未被覆盖的方法将会被内联（inline），即使推断错误，也可以取消内联（过程慢，不过很少发生）



#### 5.1.8 强制类型转换

如上将 Manager 对象存储在 Employee 数组后，如果需要使用 Manager 的方法，则需要强制类型转换

```java
Manager b = (Manager)staff[0];
```



如果沿着继承链向下转化，但对象实际类型并不支持，会报 ClassCastException 异常

可以用 instanceof 判断 转换是否会成功

```java
if(staff[1] instanceof Manager)
    b = (Manager)staff[1];
```

只能在继承层次内进行转换，超类转化为子类前要进行 instanceof 检查

一个没有引用对象的对象变量（null）使用 instanceof 总会得到 false



由于多态性，只有使用子类中的特有方法时才有类型转换的必要



强制类型转换类似于 C++ 的 dynamic_cast

dynamic_cast 出错时会返回 NULL 而不是异常



#### 5.1.9 抽象类

对于祖先类，可能某些高度抽象的方法无法提供具体实现，此时可以使用 abstract 将其定义为抽象函数，不需要给出实现。具体实现在子类中给出

有一个或多个抽象函数的类为抽象类

```java
public abstract class Person {
    public abstract String getDescription();
}
```



抽象类可以包含具体数据和具体方法。



抽象类可以不定义所有抽象方法派生出抽象类

也可以定义全部抽象方法从而变成具体类



类即使不含抽象方法，也可以定义为抽象类

抽象类不能实例化对象

可以创建具体的子类对象

可以创建抽象类的对象变量但只能引用具体子类的对象



C++ 纯虚函数对应抽象函数概念，没有抽象类的关键字

PersonTest.java

```java
public class PersonTest {
    public static void main(String[] args) {
        Person[] people = new Person[2];

        people[0] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        people[1] = new Student("Maria Morris", "computer science");

        for(Person p : people)
            System.out.println(p.getName() + " : " + p.getDescription());
    }
}
```

Person.java

```java
public abstract class Person {
    public abstract String getDescription();
    
    private String name;

    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
```

Employee.java

```java
import java.time.*;

public class Employee extends Person{
	// private String name;
	private double salary;
	private LocalDate hireDay;
	public Employee(String name, double salary, int year, int month, int day) {
		super(name);
		this.salary = salary;
		hireDay = LocalDate.of(year, month, day);
	}
	// public String getName() {
	// 	return name;
	// }
	public double getSalary() {
		return salary;
	}
	public LocalDate getHireDay() {
		return hireDay;
	}
	public String getDescription() {
		return String.format("An employee with a salary of $%.2f", salary);
	}
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}
}
```

Student.java

```java
public class Student extends Person {
    // private String name;
    private String major;
    public Student(String name, String major) {
        super(name);
        this.major = major;
    }
    public String getDescription() {
        return "A student majoring in " + major;
    }
}
```

结果

```text
Harry Hacker : An employee with a salary of $50000.00
Maria Morris : A student majoring in computer science
```



#### 5.1.10 受保护访问

希望子类可以访问超类的某些域或方法，可以将这些域或方法定义为 protected

尽量不要定义受保护域，不然可以通过派生来访问，破坏了封装性



实际上 Java 中 protected 除了子类 同一包中的其他类也都可见，这比 C++ 的 protected 安全性差



private 仅对本类可见

默认 对本包可见

protected 对子类和本包可见

public 对所有类可见