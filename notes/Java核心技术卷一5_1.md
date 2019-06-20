



# 《Java核心技术卷一》 笔记 5_1

2019.06.20 12:32



## 第5章 继承（Inheritance）

### 5.2 Object：所有类的超类

Object 类是 Java 中所有类的始祖

没有特别指出超类时，Object 就被认为是该类的超类



Object 类型的变量可以引用任何类型的对象

只有基本类型不是对象

数组无论是对象数组还是基本类型数组都扩展了 Object 类



C++ 中没有根类，但有 void\* 指针



#### 5.2.1 equals 方法

用来判断两个对象状态是否一致

Object.equals  方法只比较了是否使用了相同的引用

一个覆盖 equals 的例子

```java
public class Employee {
    public boolean equals(Object otherObject) {
        // quick test
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        
        // classes don't match
        if(getClass() != otherObject.getClass()) return false;
        
        Employee other = (Employee) otherObject;
        
        //return name.equals(other.name)
        //    && salary == other.salary
        //    && hireDay.equals(other.hireDay);
        // aviod name or hireDay == null
        return Objects.equals(name, other.name)
            && salary == other.salary
            && Objects.equals(hireDay, other.hireDay);  
    }
}
```

子类可以调用超类的 equals 先判断

```java
public class Manager extends Employee {
    public boolean equals(Object otherObject) {
        if(!super.equals(otherObject)) return false;
        Manager other = (Manager)otherObject;
        return bonus == other.bonus;
    }
}
```



#### 5.2.2 相等测试与继承

最好不使用

```java
if(!(otherObject instanceof Employee)) return false;
```

这样的判断

因为这没有解决 otherObject 是子类的情况。



Java 要求 equals 有以下特性

自反性：对于非 null 的任意 x，x.equals(x) 为 true

对称性：任意 x，y 仅当 x.equals(y) 为 true 时 y.equals(x) 为 true

传递性：任意 x，y，z，如果 x.equals(y) 和 y.equals(z) 是 true 则 x.equals(z) 是 true

一致性：如果 x，y 引用的对象没有变化，则多次调用 equals 的结果不变

任意非空引用 x，x.equals(null) 的结果应该为 false



如果使用 instanceof 判断 会破坏对称性

拥有相同域的子类 equals 超类 会得到 false，超类 equals 子类 则会得到 true



实际应用中，如果子类有自己的相等概念，需要强制采用 instanceof

如果超类定义相等概念，子类之间也可以比较，此时使用 instanceof



关于 equals 的建议

1.显式参数命名为 otherObject，稍后转换类型的参数叫做 other

2.检测 if(this == otherObject) return true; 高效的小优化

3.检测 if(otherObject != null) return false;

4.对于子类要定义equals的，比较所属类 

​    if(getClass() != otherObject.getClass()) return false;

​    对于子类比较有统一的语义的使用

​    if(!(otherObject instanceof ClassName)) return false;

5.ClassName other = (ClassName)otherObject;

6.对需要比较的域进行比较，基本类型用 = ，对象变量调用其 equals，数组调用 Arrays.equals

​    怕某个域为空的话，可以调用 Objects.equals(Object a, Object b)

​    a，b 任一为空返回 false 否则返回调用 a.equals(b) 的结果

7.子类重新定义 equals 要包含超类 super.equals(other)



注意 equals 的参数类型是 Object 不然不会覆盖超类中的方法，而是额外定义了一个方法

可以用 @Override 注解对覆盖超类的方法进行标记

如果没发生覆盖则编译器会返回一个错误



几个例子

EqualsTest.java

```java
import java.util.Objects;

public class EqualsTest {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", 50000);
        Employee m1 = new Manager("Alice", 50000, 30000);
        Employee e2 = new Employee("Alice", 50000);
        Employee m2 = new Manager("Alice", 50000, 20000);
        System.out.println("e1.equals(m1) = " + e1.equals(m1));
        System.out.println("m1.equals(e1) = " + m1.equals(e1));
        System.out.println("e1.equals(e2) = " + e1.equals(e2));
        System.out.println("m1.equals(m2) = " + m1.equals(m2));
    }
}

class Employee {
    private String name;
    private double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.getName())
            && salary == other.getSalary();
    }
}

class Manager extends Employee {
    private double bonus;
    Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }
}
```

结果

```text
e1.equals(m1) = false
m1.equals(e1) = false
e1.equals(e2) = true
m1.equals(m2) = true
```

只在超类中写了 equals 方法运用了 getClass() 则不同的类的对象比较都是 false

注意子类没有特别定义 equals ，因此对于只有子类域不同的 m1 和 m2 也返回了一致的结果



EqualsTest2.java

```java
import java.util.Objects;

public class EqualsTest2 {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", 50000);
        Employee m1 = new Manager("Alice", 50000, 30000);
        Employee e2 = new Employee("Alice", 50000);
        Employee m2 = new Manager("Alice", 50000, 20000);
        System.out.println("e1.equals(m1) = " + e1.equals(m1));
        System.out.println("m1.equals(e1) = " + m1.equals(e1));
        System.out.println("e1.equals(e2) = " + e1.equals(e2));
        System.out.println("m1.equals(m2) = " + m1.equals(m2));
    }
}

class Employee {
    private String name;
    private double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.getName())
            && salary == other.getSalary();
    }
}

class Manager extends Employee {
    private double bonus;
    Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }
    public double getBonus() {
        return bonus;
    }
    @Override
    public boolean equals(Object otherObject) {
        if(!super.equals(otherObject)) return false;
        Manager other = (Manager)otherObject;
        return bonus == other.getBonus();
    }
}
```

子类自己增加自己的 equals 则可以得到正确结果

```java
e1.equals(m1) = false
m1.equals(e1) = false
e1.equals(e2) = true
m1.equals(m2) = false
```



EqualsTest3.java 使用 instanceof 视所有的 Employee 对于 equals 的行为一致

```java
import java.util.Objects;

public class EqualsTest3 {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", 50000);
        Employee m1 = new Manager("Alice", 50000, 30000);
        Employee e2 = new Employee("Alice", 50000);
        Employee m2 = new Manager("Alice", 50000, 20000);
        System.out.println("e1.equals(m1) = " + e1.equals(m1));
        System.out.println("m1.equals(e1) = " + m1.equals(e1));
        System.out.println("e1.equals(e2) = " + e1.equals(e2));
        System.out.println("m1.equals(m2) = " + m1.equals(m2));
    }
}

class Employee {
    private String name;
    private double salary;
    
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(!(otherObject instanceof Employee)) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.getName())
            && salary == other.getSalary();
    }
}

class Manager extends Employee {
    private double bonus;
    Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }
    public double getBonus() {
        return bonus;
    }
}
```

结果

```text
e1.equals(m1) = true
m1.equals(e1) = true
e1.equals(e2) = true
m1.equals(m2) = true
```



#### 5.2.3 hashCode 方法

散列码（hash code）是由对象导出的一个整型值

每个对象都有默认的散列码值是存储地址



重新定义了 equals 则需要重新定义 hashCode

equals 为 true 的对象应该拥有相同的 hashCode



hashCode 返回一个整型值（可以为负值）



String 定义了自己的 hashCode 从而内容相同的不同 String 对象有相同的哈希值



覆盖 hashCode

```java
public class Employee {
    //@Override
    //public int hashCode() {
    //    return 7 * name.hashCode()
    //         + 11 * new Double(salary).hashCode()
    //         + 13 * hireDay.hashCode();
    //}
    // 更好的方法在下面 可以避免 创建 Double 对象，以及避免 name 或 hireDay 为 null
    @Override
    public int hashCode() {
        return 7 * Objects.hashCode(name)
             + 11 * Double.hashCode(salary)
             + 13 * Objects.hashCode(hireDay);
    }
}
```

或者也可以

```java
public class Employee {
    @Override
    public int hashCode() {
        return Objects.hash(name, salary, hireDay);
    }
}
```



HashCodeAPI.java

```java
public class HashCodeAPI {
    public static void main(String[] args) {
        // java.util.Object
        // int hashCode()

        // java.util.Objects
        // static int hash(Object... objects)
        // 返回一个散列码 由所有对象的散列码组合而成（为所有对象调用下面的hashCode）
        
        // java.util.Objects
        // static int hashCode(Object a)
        // 如果 a 是 null 返回 0 否则返回 a.hashCode()

        // java.lang.(Integer|Long|Short|Byte|Double|Float|Character|Boolean)
        // static int hashCode((int|long|short|byte|double|float|char|boolean) value)
        // 返回给定基本类型值的散列码

        // java.util.Arrays
        // static int hashCode(type[] a)
        // 返回 a 数组的散列码
    }
}
```



#### 5.2.4 toString 方法

大多数内部的 toString 方法是

```text
类名[域值]
```

的形式

Object 类的 toString 

```text
类名@散列码
```

数组的 toString 使用了 Object 类的格式

如整型数组

```text
[I@散列值
```

[ 代表数组

I 代表整型

打印数组需要使用 Arrays.toString()



调用 toString 可以依靠 “” + x 而不是 x.toString()

因为前一种格式对于基本类型也适用



推荐每个类自己实现 toString 方便使用和调试



EqualsTest.java

```java
public class EqualsTest {
    public static void main(String[] args) {
        Employee alice1 = new Employee("Alice Adams", 75000, 1987, 12, 15);
        Employee alice2 = alice1;
        Employee alice3 = new Employee("Alice Adams", 75000, 1987, 12, 15);
        Employee bob = new Employee("Bob Brandson", 50000, 1989, 10, 1);

        System.out.println("alice1 == alice2 : " + (alice1 == alice2));
        System.out.println("alice1 == alice3 : " + (alice1 == alice3));
        System.out.println("alice1.equals(alice3) : " + (alice1.equals(alice3)));
        System.out.println("alice1.equals(bob) : " + (alice1.equals(bob)));

        System.out.println("\n========================\n");

        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        boss.setBonus(5000);

        System.out.println("boss.toString() : " + boss);
        System.out.println("carl.equals(boss) : " + carl.equals(boss));

        System.out.println("\n========================\n");

        System.out.println("alice1.hashCode() : " + alice1.hashCode());
        System.out.println("alice3.hashCode() : " + alice3.hashCode());
        System.out.println("bob.hashCode() : " + bob.hashCode());
        System.out.println("carl.hashCode() : " + carl.hashCode());
    }
}
```

Employee.java

```java
import java.time.*;
import java.util.Objects;

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
    
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        Employee other = (Employee) otherObject;
        return Objects.equals(name, other.name)
            && salary == other.salary
            && Objects.equals(hireDay, other.hireDay);
    }

    public int hashCode() {
        return Objects.hash(name, salary, hireDay);
    }

    public String toString() {
        return getClass().getName() + "[name=" + name +",salary=" + salary
             + ",hireDay=" + hireDay + "]";
    }
}
```

Manager.java

```java
import java.util.*;
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

    @Override
    public boolean equals(Object otherObject) {
        if(!super.equals(otherObject)) return false;
        Manager other = (Manager)otherObject;
        return bonus == other.bonus;
    }

    public int hashCode() {
        return super.hashCode() + 17 * Double.hashCode(bonus);
    }

    public String toString() {
        return super.toString() + "[bonus=" + bonus + "]";
    }
}
```

结果

```text
alice1 == alice2 : true
alice1 == alice2 : true
alice1.equals(alice3) : true
alice1.equals(bob) : false

[Done] exited with code=0 in 0.644 seconds

[Running] cd "d:\Reading\ComputerScience\Java核心技术卷一\src\第5章继承\5.2Object所有类的超类\EqualsTest\" && javac EqualsTest.java && java EqualsTest
alice1 == alice2 : true
alice1 == alice3 : false
alice1.equals(alice3) : true
alice1.equals(bob) : false

========================

boss.toString() : Manager[name=Carl Cracker,salary=80000.0,hireDay=1987-12-15][bonus=5000.0]
carl.equals(boss) : false

========================

alice1.hashCode() : -808853550
alice3.hashCode() : -808853550
bob.hashCode() : -624019882
carl.hashCode() : -341762419
```



ClassAPI.java

```java
public class ClassAPI {
    public static void main(String[] args) {
        // java.lang.Object
        // Class getCalss()
        // 返回类运行时的描述

        // java.lang.Object
        // boolean equals(Object otherObject)

        // java.lang.Object
        // String toString()

        // java.lang.Class
        // String getName()
        // 返回类的名字

        // java.lang.Class
        // Class getSuperclass()
        // 返回超类信息
    }
}
```



