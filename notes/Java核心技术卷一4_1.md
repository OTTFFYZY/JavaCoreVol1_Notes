

# 《Java核心技术卷一》 笔记 4_1

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

按值调用（call by value）方法接受调用者提供的值

按引用调用（call by reference）方法接受调用者提供的变量地址

按名调用（call by name）已经成为历史



Java 总是按值调用的

对于基本类型，无法使用传参的方式改变变量的内容

但是对于对象引用，由于拷贝引用的对象是同一个，因此可以改变对象内容（注意这种也不是按应用调用）

一个例子

```java
public static void swap(Employee x, Employee) {
    Employee tmp = x;
    x = y;
    y = tmp;
}
```

这样并不能交换对象的引用关系



Java 中方法参数的使用情况：

不能修改基本数据类型的参数

可以改变对象参数的状态

不能让对象参数引用新的对象



ParamTest.java

```java
public class ParamTest {
    public static void main(String[] args) {
        // Methods can not modify numeric parameters
        System.out.println("Testing numeric parameter");
        double percent = 10;
        System.out.println("Before: percent = " + percent);
        tripleValue(percent);
        System.out.println("After: percent = " + percent);

        // Methods can change the state of object parameters
        System.out.println("\nTesting state of object parameters");
        Employee harry = new Employee("Harry", 50000);
        System.out.println("Before: salary = " + harry.getSalary());
        tripleSalary(harry);
        System.out.println("After: salary = " + harry.getSalary());

        // Methods can not attach new objects to object parameters
        System.out.println("\nTesting object parameters");
        Employee a = new Employee("Alice", 70000);
        Employee b = new Employee("Bob", 60000);
        System.out.println("Before: a = " + a.getName());
        System.out.println("Before: b = " + b.getName());
        swap(a, b);
        System.out.println("After: a = " + a.getName());
        System.out.println("After: b = " + b.getName());
    }

    public static void tripleValue(double x) {
        x *= 3;
        System.out.println("End of method: x = " + x);
    }
    public static void tripleSalary(Employee e) {
        e.raiseSalary(200);
        System.out.println("End of method: salary = " + e.getSalary());
    }

    public static void swap(Employee x, Employee y) {
        Employee tmp = x;
        x = y;
        y = tmp;
        System.out.println("End of method: E x = " + x.getName());
        System.out.println("End of method: E y = " + y.getName());
    }
}
class Employee {
	private String name;
	private double salary;

	public Employee(String n, double s) {
		name = n;
		salary = s;
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}
}
```

结果

```test
Testing numeric parameter
Before: percent = 10.0
End of method: x = 30.0
After: percent = 10.0

Testing state of object parameters
Before: salary = 50000.0
End of method: salary = 150000.0
After: salary = 150000.0

Testing object parameters
Before: a = Alice
Before: b = Bob
End of method: E x = Bob
End of method: E y = Alice
After: a = Alice
After: b = Bob
```



### 4.6 对象构造

#### 4.6.1 重载（Overloading）

有时需要同名构造器实现不同的功能，需要重载。

编译器通过参数匹配寻找对应方法的过程称为重载解析（Overloading Resolution）

Java 允许重载任何方法

方法的签名（signature）：要完整的描述一个方法需要方法名和参数类型

返回类型不是方法签名的一部分



#### 4.6.2 默认域初始化

没有在构造器中显式赋予初值的域会按照类型赋予 0，false，null

但这样会降低程序的可读性



#### 4.6.3 无参数的构造器

对象由无参构造器创建时会赋予适当的状态

如果一个类没有编写构造器，则系统会自动提供无参构造器，将域都赋予 0 值

如果提供了至少一个构造器，但没有无参数的构造器，则不会提供默认的无参数构造器，此时如果调用无参数的构造器将非法

```java
public ClassName() {}
```

可以显式提供和默认构造器功能一致的无参数构造器



#### 4.6.4 显式域初始化

```java
class Employee {
    private String name = "";
}
```

执行构造器前会先执行赋值操作

如果某个域对于构造器都希望设置相同的初始值，可以使用显式域初始化

初始值不止可以使用常量值，也可以使用方法

C++ 不能直接初始化实例域

ExplicitInit.java

```java
public class ExplicitInit {
    public static void main(String[] args) {
        Employee a = new Employee();
        Employee b = new Employee();
        Employee c = new Employee();
        System.out.println("a id = " + a.getId());
        System.out.println("b id = " + b.getId());
        System.out.println("c id = " + c.getId());
    }
}
class Employee {
    private static int nextId = 1;
    private int id = assignId();
    private static int assignId() {
        return nextId++;
    }

    int getId() {
        return id;
    }
}
```

结果

```text
a id = 1
b id = 2
c id = 3
```



#### 4.6.5 参数名

构造器传递参数时可以在使用和实例域同名的变量，使用 this 加以区分

```java
public Employee(String name) {
    this.name = name;
}
```

Java 程序员一般不用下划线开头的变量名



#### 4.6.6 调用另一个构造器

如果构造器中的第一个语句如下，则将调用同一类的另一个构造器

```java
this(...)
```

C++ 中构造器不能调用构造器

CallAnotherConstructor.java

```java
public class CallAnotherConstructor {
    public static void main(String[] args) {
        Employee a = new Employee("Alice", 50000);
        Employee b = new Employee(60000);
        a.print();
        b.print();
    }
}
class Employee {
    static int nextId = 1;

    private String name;
    private double salary;
    private int id;

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.id = nextId++;
    }

    Employee(double salary) {
        this("Employee #" + nextId, salary);
    }

    public void print() {
        System.out.println("name = " + name + ", salary = " + salary
                         + ", id = " + id);
    }
}
```

结果

```text
name = Alice, salary = 50000.0, id = 1
name = Employee #2, salary = 60000.0, id = 2
```



#### 4.6.7 初始化块（Initialization Block）

可以使用初始化块，可以有多个初始化块，构造器执行前初始化块会被执行

一个构造器被调用后的执行顺序为

所有数据域被初始化为默认 0 值

按类声明的中的顺序，执行所有的域初始化语句和初始化块

如果构造器第一行调用了第二个构造器，则执行第二个构造器主体

执行这个构造器主体



对于静态域也可以使用块初始化（块前增加 static 标识），将在类加载时进行静态域的初始化



ConstructorTest.java

```java
import java.util.*;
public class ConstructorTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry", 50000);
        staff[1] = new Employee(60000);
        staff[2] = new Employee();

        for(Employee e : staff)
            e.print();
    }
}

class Employee {
    private static int nextId;

    private int id;
    private String name = "";
    private double salary;

    // static initialization block
    static {
        Random gen = new Random();
        nextId = gen.nextInt(100000);
    }

    // object initialization block
    {
        id = nextId;
        nextId++;
    }

    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    Employee(double salary) {
        this("Employee #" + nextId, salary);
    }

    Employee() {}

    public void print() {
        System.out.println("name = " + name + ", salary = " + salary
                         + ", id = " + id);
    }
}
```

结果

```text
name = Harry, salary = 50000.0, id = 86526
name = Employee #86527, salary = 60000.0, id = 86527
name = , salary = 0.0, id = 86528
```



顺序测试

ConstructOrderTest.java

```java
public class ConstructOrderTest {
    static {
        System.out.println("Add ConstructOrderTest class");
    }
    public static void main(String[] args) {
        System.out.println("Begin Main\n");
        ClassA objA = new ClassA();
        System.out.println("====================\nshow objA");
        objA.print();
        System.out.println("\n ======================== \n");
        ClassA objB = new ClassA(5);
        System.out.println("====================\nshow objB");
        objB.print();
        System.out.println("\nEnd Main");
    }
}

class ClassA {
    static public void main(String[] args) {
        System.out.println("Begin ClassA Main");
        System.out.println("End ClassA Main");
    }

    ClassA() {
        System.out.println("Begin ClassA()");
        System.out.println("End ClassA()");
    }
    ClassA(int a) {
        this();
        System.out.println("Begin ClassA(int)");
        print();
        this.a = a;
        print();
        System.out.println("End ClassA(int)");
    }
    
    static int sa = -1;

    int a = -1;
    int b = -1;
    
    {
        System.out.println("Begin: Object Initialization Block 1");
        print();
        a = 1;
        c = 1;
        print();
        System.out.println("End: Object Initialization Block 1\n");
    }

    public void print() {
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
    }

    private int c = -1;

    {
        System.out.println("Begin: Object Initialization Block 2");
        print();
        b = 2;
        c = 2;
        print();
        System.out.println("End: Object Initialization Block 2\n");
    }

    static {
        System.out.println("Begin: Static Initialization Block 1");
        sprint();
        sa = 1;
        sc = 1;
        sprint();
        System.out.println("End: Static Initialization Block 1\n");
    }

    public static void sprint() {
        System.out.println("sa = " + sa + ", sb = " + sb + ", sc = " + sc);
    }

    static int sb = -1;
    static int sc = -1;

    static {
        System.out.println("Begin: Static Initialization Block 2");
        sprint();
        sb = 2;
        sc = 2;
        sprint();
        System.out.println("End: Static Initialization Block 2\n");
    }

}
```

故意将变量和函数写的比较混乱，以便得出结论，结果如下

```text
Add ConstructOrderTest class
Begin Main

Begin: Static Initialization Block 1
sa = -1, sb = 0, sc = 0
sa = 1, sb = 0, sc = 1
End: Static Initialization Block 1

Begin: Static Initialization Block 2
sa = 1, sb = -1, sc = -1
sa = 1, sb = 2, sc = 2
End: Static Initialization Block 2

Begin: Object Initialization Block 1
a = -1, b = -1, c = 0
a = 1, b = -1, c = 1
End: Object Initialization Block 1

Begin: Object Initialization Block 2
a = 1, b = -1, c = -1
a = 1, b = 2, c = 2
End: Object Initialization Block 2

Begin ClassA()
End ClassA()
====================
show objA
a = 1, b = 2, c = 2

 ======================== 

Begin: Object Initialization Block 1
a = -1, b = -1, c = 0
a = 1, b = -1, c = 1
End: Object Initialization Block 1

Begin: Object Initialization Block 2
a = 1, b = -1, c = -1
a = 1, b = 2, c = 2
End: Object Initialization Block 2

Begin ClassA()
End ClassA()
Begin ClassA(int)
a = 1, b = 2, c = 2
a = 5, b = 2, c = 2
End ClassA(int)
====================
show objB
a = 5, b = 2, c = 2

End Main
```



类中的方法和变量无论定义的位置前后，在类中都是可见的



主类会在 main 函数运行前加载

其他类会根据 main 函数的需要加载

静态初始化在类加载时运行，多次对象生成也只运行一次



最初都会初始化 0 值

显示初始化和块初始化优先级一致，按照顺序执行因此写在后面的会覆盖前面的运行结果

构造器调用构造器的语句只能写在第一行



可以通过

```shell
java ClassA
```

结果如下

```text
Begin: Static Initialization Block 1
sa = -1, sb = 0, sc = 0
sa = 1, sb = 0, sc = 1
End: Static Initialization Block 1

Begin: Static Initialization Block 2
sa = 1, sb = -1, sc = -1
sa = 1, sb = 2, sc = 2
End: Static Initialization Block 2

Begin ClassA Main
End ClassA Main
```

发现此时 ConstructOrderTest 类没有加载



#### 4.6.8 对象析构与 finalize 方法

析构函数主要用来释放内存，Java 有自动的垃圾回收机制，Java 没有析构函数。

有些时候对象使用了内存以外的资源，此时需要特别的回收和再利用。

可以为类添加 finalize 方法，将在垃圾回收器清楚对象之前调用。

实际应用中不应使用 finalize 方法回收短缺资源，因为无法预估其调用时间



```java
System.runFinalizersOnExit(true);
```

方法确保关闭之前调用 finalize 方法，但是该方法并不安全。

替代方法是使用 Runtime.addShutdownHook 添加“关闭钩”



人工管理可以在对象使用结束时应用 close 方法



