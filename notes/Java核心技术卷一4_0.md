

# 《Java核心技术卷一》 笔记 4_0

2019.06.14 12:11



## 第4章 对象与类

### 4.1 面向对象程序设计（OOP）概述

#### 4.1.1 类

类（class）是构造对象的模板或蓝图

由类构建（construct）对象的过程称为创建类的实例（instance）

封装（encapsulation，有时称为数据隐藏）形式上，封装将数据和行为组合在一个包中，并对对象的使用者隐藏了数据的实现方式。

实例域（Instance field）对象中的数据

方法（Method）操纵数据的过程

状态（state）对于某个特定类对象的一组特定的实例域值的组合，就是当前对象的状态

封装赋予类黑盒特性

继承（Inheritance）通过一个类来创建另一个类的过程



#### 4.1.2 对象

对象 3 个主要特性：

对象的行为（behavior）可对对象施加的方法

对象的状态（state）对象对于方法的响应如何

对象的标识（identity）判别具有相同行为和状态的不同对象的唯一标识



对象状态变化必须通过调用方法，不然就破坏了封装性



#### 4.1.3 识别类

OOP 从设计类开始，再向类中添加方法

识别类可以分析问题过程中的名词，方法则对应动词



#### 4.1.4 类之间的关系

类之间的常见关系有

依赖（Dependence，uses-a）一个类的方法操纵另一个类的对象，则一个类依赖于另一个类

应当减少依赖（耦合度最小）

聚合（Aggregation，has-a）一种对象包含一些另一种对象

继承（Inheritance，is-a）特殊与一般的类之间的关系



有一系列表示类关系的 UML（Unified Modeling Language） 符号



### 4.2 使用预定义类

并不是所有类都有面向对象特性



#### 4.2.1 对象与对象变量

使用构造器（constructor）来构建新实例。构造器是一种特殊的方法构建和初始化对象。

构造器名字与类名相同



对象与对象变量不同

一个对象变量并没有真正包含对象，而是一个对象的引用

设置对象变量为 null 表示没有引用任何对象



Java 的引用更像安全版的 C++ 指针



#### 4.2.2 Java 类库中的 LocalDate 类

Date 类实例的状态就是特定的时间点，保存从特定时间，纪元（epoch）开始的毫秒数

纪元是 UTC/GMT 1970.01.01 00:00:00



Java 将保存时间点和给时间点命名分成了两个类。

保存时间点使用 Date 类

保存日历表示法，给时间点命名的是 LocalDate 类



使用 LocalDate 类不使用构造器，要用静态工厂方法（factory method）

DateAndLocalDate.java

```java
import java.util.*; // Date
import java.time.*; // LocalDate

public class DateAndLocalDate {
	public static void main(String[] args) {
		Date birthday = new Date();
		System.out.println("Date : " + birthday);

		LocalDate nowLocalDate = LocalDate.now();
		LocalDate oldLocalDate = LocalDate.of(1966,1,1);
		System.out.println("nowLocalDate : " + nowLocalDate);
		System.out.println("oldLocalDate : " + oldLocalDate);

		System.out.println("now year : " + nowLocalDate.getYear());
		System.out.println("now month : " + nowLocalDate.getMonth());
		System.out.println("now month value : " + nowLocalDate.getMonthValue());
		System.out.println("now day of month : " + nowLocalDate.getDayOfMonth());
		System.out.println("now day of year : " + nowLocalDate.getDayOfYear());
	}
}
```

不要使用 Date 中的 getYear 等方法，已经过时



#### 4.2.3 更改器方法与访问器方法

更改器方法（mutator method）修改对象的方法

访问器方法（accessor method）不修改对象的方法



C++ 中默认是更改器方法，后加 const 关键字为访问器方法

Java 不做语法区分

CalendarTest.java

```java
import java.time.*;
public class CalendarTest {
	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int today = date.getDayOfMonth();

		date = date.minusDays(today - 1);
		DayOfWeek weekday = date.getDayOfWeek;
		int value = weekday.getValue();

		System.out.println("Mon Tue Wed Thu Fri Sat Sun");
		for(int i = 1; i < value; i++)
			System.out.print("    ");

		while(date.getMonthValue() == month)
		{
			System.out.printf("%3d", date.getDayOfMonth());
			if(date.getDayOfMonth() == today)
				System.out.print("*");
			else
				System.out.print(" ");
			date = date.plusDays(1);
			if(date.getDayOfWeek().getValue() == 1)
				System.out.println();
		}
		if(date.getDayOfWeek().getValue() != 1)
			System.out.println();
	}
}
```



### 4.3 用户自定义类

复杂应用程序需要各种主力类（workhorse class）这些类通常没有 main 方法，只有自己的实例域和实例方法。

一个完整的应用程序有若干类，只有其中一个类有 main 方法



#### 4.3.1 Employee 类

Java 中最简单的类定义

```java
class ClassName {
    <field1>
    <field2>
    ...
    <constructor1>
    <constructor2>
    ...
    <method1>
    <method2>
    ...
}
```

EmployeeTest.java

```java
import java.time.*;

public class EmployeeTest 
{
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15); 
		staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

		for(Employee e : staff)
			e.raiseSalary(5);

		for(Employee e : staff)
			System.out.println("name=" + e.getName() + ", salary=" + e.getSalary()
				              + ", hireDay=" + e.getHireDay());
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
```



#### 4.3.2 多个源文件的使用

可以认为 Java 内置了 make 工具。

如将 EmployeeTest 类和 Employee 分别放置于两个文件下，则可以使用两种方式进行编译。

第一种使用通配符，第二种可以直接使用命令

```shell
javac EmployeeTest.java
```

当编译发现需要 Employee 类时会自动寻找这个类并比较 .java 和 .class 的版本

如果不存在 .class 或者 .java 比已有的 .class 更新 则重新编译成 .class



#### 4.3.3 剖析 Employee 类

public 意味着所有类都可以访问

private 意味着只有所属的类内部有访问权限



#### 4.3.4 从构造器开始

构造器与类同名

可以有多个构造器

构造器的参数个数可以是任意的

构造器没有返回值

构造器总是伴随 new 操作一起调用



Java 中对象都是在堆中构建需要注意。

不要在构造器中定义与实例域重名的局部变量



#### 4.3.5 隐式参数与显式参数

隐式（implicit）参数：调用函数的对象

显示（explicit）参数：参数表中声明的

每个方法中关键字 this 代表了隐式参数，必要时可以用 this 强调是实例域中的变量



C++ 通常在类外定义函数，类内定义的会被默认内联

Java 只能在类内定义方法，内联由虚拟机决定



#### 4.3.6 封装的优点

可以保证内部一些变量是只读的

需要获得或者设置实例域值的情况可以设置以下 3 个部分：

一个私有的数据域，一个共有的域访问器方法，一个共有的域更改器方法

可以在改变内部实现的情况下不影响其他外部的代码。

可以在更改时对更改内容进行检查



不要编写返回饮用可变对象的访问器方法，这样在更改引用的对象的时候就会破坏封装性

对于可变对象可以用 clone 返回对象的副本的引用

```java
return (Date) day.clone();
```



#### 4.3.7 基于类的访问权限

一个方法可以访问所属类的所有对象的私有特性，而不只是隐式参数的私有特性



#### 4.3.8 私有方法

应将所有的数据域设置为私有

对于方法，辅助方法最好私有



一般私有方法在内部实现改变不在需要时可以删除，共有的方法则不能随意删除



#### 4.3.9 final 实例域

定义成 final 的实例域在对象构建时必须初始化，并在之后的操作中不能更改。

final 大多用在基本类型（primitive）域或不可变（immutable）类的域

不可变类：类中的每个方法都不会改变其对象



对于可变类，final 只是意味着引用的引用对象不能改变为引用其他对象，但是引用对象的内容可以改变

