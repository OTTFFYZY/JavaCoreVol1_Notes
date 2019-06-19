

# 《Java核心技术卷一》 笔记 4_01

2019.06.18 21:01



## 第4章 对象与类

### 4.4 静态域与静态方法

static

#### 4.4.1 静态域

用关键字 static 修饰的静态域属于类，而不属于对象。

实例域每个对象都有自己的一份拷贝



static 沿用了 C++ 的叫法



#### 4.4.2 静态常量

同时被 static 和 final 修饰

静态常量可以 public



Math.PI，System.out 都是静态常量

setOut 可以更改 System.out 的流，但这是一个本地方法，不是 Java 语言实现的



#### 4.4.3 静态方法

静态方法没有隐式参数

可以访问类本身的静态域

可以使用对象调用，但不推荐



方法不需要访问对象状态而所有的参数都是通过显示参数提供，

或方法只需要访问类的静态域时应当使用静态方法



#### 4.4.4 工厂方法 （factory method）

因为构造器必须和类名一致，因此希望使用相同的参数表，进行不同的实例化流程时需要使用工厂方法

通过工厂方法可以返回不同的类型的对象（子类对象）



#### 4.4.5 main 方法

main 方法不对任何对象进行操作。程序启动时没有任何对象，main 将执行和创建需要的对象

可以在一些类里创建自己的 main 方法用于测试

整个程序运行时只会运行首先加载的类的 main 方法，将不会调用其他 main 方法



StaticTest.java

```java
import java.time.*;

public class StaticTest 
{
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("Tom", 40000); 
		staff[1] = new Employee("Dick", 60000);
		staff[2] = new Employee("Harry", 65000);

		for(Employee e : staff)
		{
			e.setId();
			System.out.println("name=" + e.getName() + ", id=" + e.getId()
				             + ", salary=" + e.getSalary());
		}
		System.out.println("nextId=" + Employee.getNextId());

	}
}

class Employee {
	private static int nextId = 1;

	private String name;
	private double salary;
	private int id;

	public Employee(String n, double s) {
		name = n;
		salary = s;
		id = 0;
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public int getId() {
		return id;
	}
	public void setId() {
		id = nextId++;
	}
	public static int getNextId() {
		return nextId;
	}
	public static void main(String[] args) {
		Employee e = new Employee("Harry", 50000);
		System.out.println(e.getName() + " " + e.getSalary());
	}
}
```

使用

```shell
javac StaticTest.java
```

编译以后

```shell
java StaticTest
```

将会显示

```text
name=Tom, id=1, salary=40000.0
name=Dick, id=2, salary=60000.0
name=Harry, id=3, salary=65000.0
nextId=4
```

如果使用

```shell
java Employee
```

将会显示

```text
Harry 50000.0
```



### 4.5 方法参数

