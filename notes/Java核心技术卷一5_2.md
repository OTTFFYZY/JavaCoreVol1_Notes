



# 《Java核心技术卷一》 笔记 5_2

2019.06.20 15:11



## 第5章 继承（Inheritance）

### 5.3 泛型数组列表

Java 数组虽然可以在运行时动态指定，但是指定大小之后并不能动态更改

解决方案是使用 ArrayList

ArrayList 是一个采用类型参数（type parameter）的泛型类（generic class）



```java
ArrayList<Employee> staff = new ArrayList<Employee>();
ArrayList<Employee> staff = new ArrayList<>(); // since JDK 7
```

使用 add 方法增加元素

```java
staff.add(new Employee());
```

ArrayList 内部靠数组维护，一定次的 add 内部数组满了之后会自动创建更大的数组，并将小数组拷贝过去

可以分配内部数组大小，以免内部的申请空间和拷贝操作

```java
staff.ensureCapacity(100);
```

也可以初始化时指定大小

```java
ArrayList<Employee> staff = new ArrayList<>(100);
```



注意这样分配的空间只是只存储能力，并不是真的有那么多个对象

（这点与申请 100 大小的数组不同）

size 方法返回实际的数组列表元素数量

```java
staff.size()
```



不会添加元素时可以使用 trimToSize 方法释放多余的空间



Java 的 ArrayList 类似于 C++ 的 vector



#### 5.3.1 访问数组列表元素

设置第 i 个元素（从 0 开始）

```java
staff.set(i,harry);
```

set 只能替换已有的元素，添加新元素要使用 add 方法



获取第 i 个元素

```java
Employee e = staff.get(i);
```



可以添加足够的元素之后转为数组

```java
ArrayList<X> list = new ArrayList<>();
while(...) {
    X x ...
    list.add(x);
}
X[] a = new X[list.size()];
list.toArray(a);
```



可以使用带有下标的 add 操作

```java
staff.add(idx, e);
```

可以从中间删除元素

```java
staff.remove(idx);
```



可以使用 for each loop

```java
for(Employee e : staff) ...
```



ArrayListTest.java

```java
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
```

结果

```text
name=Carl Cracker, salary=78750.0, hireDay=1987-12-15
name=Harry Hacker, salary=52500.0, hireDay=1989-10-01
name=Tony Tester, salary=42000.0, hireDay=1990-03-15

===============

name = Carl Cracker, salary = 78750.0, hireDay = 1987-12-15
name = Harry Hacker, salary = 52500.0, hireDay = 1989-10-01
```



#### 5.3.2 类型化与原始数组列表的兼容性

```java
public EmployeeDB {
    public void update(ArrayList list) {...}
    public ArrayList find(String query) {...}
}
```

可以将 类型化的数组列表传递给 update 而不需要类型转换

```java
ArrayList<Employee> staff = ...;
employeeDB.update(staff);
```

编译器没有给出错误信息或者警告。

这样做不是很安全，因为数组列表中的元素可能不是 Employee 的。

对元素进行检索时会发生异常。



```java
ArrayList<Employee> result = employeeDB.find(query);
```

反过来的赋值则会有警告信息（需要增加编译选项 -Xlint:unchecked）

```java
ArrayList<Employee> result = (ArrayList<Employee>)employeeDB.find(query);
```

类型转换也会得到警告信息，指出类型转换有误



出于兼容性考虑，如果确定没有违反规则的现象，可以使用

```
@SuppressWarnings("unchecked")
ArrayList<Employee> result = (ArrayList<Employee>)employeeDB.find(query);
```

取消警告