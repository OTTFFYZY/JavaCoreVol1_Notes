# 《Java核心技术卷一》 笔记 8_0

2019.06.26 17:25



## 第8章 泛型程序设计

泛型是 Java SE 5.0 加入的机制。

泛型比使用 Object 再强制转换有更好的安全性和可读性

泛型对于集合类尤其有用



### 8.1 为什么要使用泛型程序设计

泛型程序设计（Generic Programming）意味着编写的代码可以被很多不同类型的对象所重用。



#### 8.1.1 类型参数的好处

Java 增加泛型类之前，泛型程序设计用继承实现

```java
public class ArrayList {
    private Object[] elementData;
    public Object get(int i){}
    public void add(Object o){}
}
```

这样做主要的两个问题是

获取值时必须要强制类型转换

```java
String filename = (String) list.get(0);
```

另外 add 元素时没有类型检查

```java
list.add(new File("..."));
```

上述语句编译运行时都不会出错，直到 get 强制转为 String 类型时



泛型的解决方案则是提供一个类型参数（type parameters）

```java
ArrayList<String> files = new ArrayList<String>();
```

这样清楚的知道 files 中是 String 对象

Java SE 7 以后构造函数可以省略类型

```java
ArrayList<String> files = new ArrayList<>();
```

此时返回类型时不再需要强制转化

```java
String filename = files.get(0);
```

同时还知道插入时参数应该是 String，下面语句不能通过编译

```java
files.add(new File("..."));
```



#### 8.1.2 谁想成为泛型程序员

使用泛型类很容易

实现泛型类比较困难，对于类型参数，使用代码的程序员可能想要内置（plug in）所有的类。因此一个泛型程序员要预测出类的所有可能的用途。

例如希望 用 addAll 将 ArrayList\<Manager\> 的元素添加到 ArrayList\<Employee\> 中而反过来不行

此时 Java 提出了独创性的通配符类型（wildcard type）



### 8.2 定义简单的泛型类

泛型类（generic class）就是具有一个或多个类型变量的类

```java
public class Pair<T> {
    private T first;
    private T second;
    public Pair() { first = null; second = null； }
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() { return first; }
    public T getSecond() { return second; }
    public void setFirst(T newValue) { first = newValue; }
    public void setSecond(T newValue) { second = newValue; }
}
```

泛型类可以有多个类型变量都写在 \<\> 中用 , 分隔



Java 类库中习惯用

E 表示集合元素类型

K V 表示关键字与值类型

T （U，V）表示任意类型

R 表示返回类型



使用具体的类型替换类型变量就可以实例化泛型类型

泛型类型可以看成普通类的工厂



C++ 和 Java 的泛型机制在本质上是不同的



PairTest.java

```java
public class PairTest {
    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> mm = ArrayArg.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}
class ArrayArg {
    public static Pair<String> minmax(String[] a) {
        if(a == null || a.length == 0) return null;
        String min = a[0];
        String max = a[1];
        for(int i = 1; i < a.length; i++) {
            if(min.compareTo(a[i]) > 0) min = a[i];
            if(max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<>(min, max);
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
```

结果

```text
min = Mary
max = little
```



### 8.3 泛型方法

可以定义带有类型参数的方法

```java
class ArrayAlg {
    public static <T> T getMidlle(T... a) {
        return a[a.length / 2];
    }
}
```

可以看到该方法定义在普通类中

类型变量放在修饰符后返回类型前

调用泛型方法需要提供类型参数

```java
String middle = ArrayAlg.<String>getMiddle("Jhon", "Q.", "Public");
```

不过大多数时候编译器可以推倒类型，从而省略类型参数

```java
String middle = ArrayAlg.getMiddle("Jhon", "Q.", "Public");
```

但有的时候因为推倒也可能有问题比如

```java
double middle = ArrayAlg.getMiddle(3.14, 1729, 0);
```

因为有两种方式处理。所有的都自动装箱，则得到 一个 Double 和两个 Integer 之后寻找公共超类 Number 和公共接口 Comparable 于是编译器不知道该如何处理了。

可以将所有值写为 double



### 8.4 类型变量的限定

注意有时要对泛型的类型变量增加限定以保证能够使用某些方法

```java
public static <T extends Comparable> T min(T[] a)
```



C++ 没有类型限定机制



注意此时无论是类还是接口都将使用 extends 表示是 后面绑定类型（BoundingType）的子类型

一个类型变量或者通配符可以有多个限定，用 & 分隔

限定中可以有多个接口但只能有一个类，而且类必须是列表中的第一个



PairTest.java

```java
import java.time.*;

public class PairTest2 {
    public static void main(String[] args) {
        LocalDate[] birthdays = {
            LocalDate.of(1906, 12, 9),
            LocalDate.of(1815, 12, 10),
            LocalDate.of(1903, 12, 3),
            LocalDate.of(1910, 6, 22)
        };
        Pair<LocalDate> mm = ArrayArg.minmax(birthdays);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}
class ArrayArg {
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if(a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[1];
        for(int i = 1; i < a.length; i++) {
            if(min.compareTo(a[i]) > 0) min = a[i];
            if(max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<>(min, max);
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
```

结果

```text
min = 1815-12-10
max = 1910-06-22
```



### 8.5 泛型代码和虚拟机

虚拟机中没有泛型对象，所有对象都属于普通类



#### 8.5.1 类型擦除

无论何时定义了一个泛型类型，都会自动提供一个相应的原始类型（raw type）

原始类型的名字就是删去类型参数后的泛型类型名。

擦除（erased）类型变量，并替换为限定类型，无限定类型用 Object

例如 Pair\<T\> 的原始类型

```java
class Pair {
    private Object first;
    private Object second;
    public Pair() { first = null; second = null; }
    public Pair(Object first, Object second) {
        this.first = first;
        this.second = second;
    }
    public Object getFirst() { return first; }
    public Object getSecond() { return second; }
    public void setFirst(Object newValue) { first = newValue; }
    public void setSecond(Object newValue) { second = newValue; }
}
```



Java 泛型与 C++ 模板不同，C++ 模板会实例化为不同类型，会产生模板代码膨胀。



多个限定类型时，会使用第一个限定类型，因此应该将标签（tagging）接口放在后部



#### 8.5.2 翻译泛型表达式

当调用泛型方法时，如果擦除返回类型，编译器会插入强制类型转换

```java
Pair<Employee> buddies = ...;
Employee buddy = buddies.getFirst();
```

这个方法调用会被翻译成两条虚拟机指令

对原始方法 Pair.getFirst() 的调用

将返回的 Object 类型强制转换为 Employee 类型



当存取一个泛型域时也要插入强制类型转换。



#### 8.5.3 翻译泛型方法

通常认为泛型方法是一个完整的方法族

```java
public static <T extends Comparable> T min(T[] a)
```

而擦除类型后只剩下一个方法

```java
public static Comparable min(Comparable[] a)
```



对于一个继承泛型类的类

```java
class DateInterval extends Pair<LocalDate> {
    public void setSecond(LocalDate second) {
        if(second.compareTo(getFirst()) >= 0)
            super.setSecond(second);
    }
}
```

但是类型擦除后

存在一个继承的 setSecond 方法

```java
public void setSecond(Object second)
```

但实际类型擦除后希望调用 DateInterval.setSecond

这时编译器会生成一个桥方法

```java
public void setSecond(Object second) {
    setSecond((Date) second);
}
```



对于直接覆盖的情况

```java
public LocalDate getSecond() {
    return (Date) super.getSecond().clone();
}
```

实际的 DateInterval 类中将包含两个方法

```java
Object getSecond();
LocalDate getSecond();
```

注意不能这样写 Java 代码，但虚拟机可以根据返回类型确定方法



桥方法其实不仅用于泛型，覆盖方法时可以使用更严格的返回类型也用了这一特性

```java
public class Employee implements Cloneable {
    public Employee clone() throws CloneNotSupportedException {
        ...
    }
}
```

Object.clone 和 Employee.clone 被说具有协变的返回类型（convariant return types）

实际上 Employee 类有两个 clone 方法，合成的桥方法调用了新定义的方法



BridgeMethodTest.java

```java
import java.time.*;

public class BridgeMethodTest {
    public static void main(String[] args) {
        Pair di = new Pair(LocalDate.of(1995,5,1),LocalDate.of(2000,5,1));
        System.out.println("di : " + di);
        di.setSecond(LocalDate.of(1900,5,1));
        System.out.println("di.setSecond(LocalDate.of(1900,5,1)) : " + di);
        di.setSecond(LocalDate.of(2010,5,1));
        System.out.println("di.setSecond(LocalDate.of(2010,5,1)) : " + di);

        System.out.println("\n===========================\n");

        DateInterval di2 = new DateInterval(LocalDate.of(1995,5,1),LocalDate.of(2000,5,1));
        System.out.println("di2 : " + di2);
        di2.setSecond(LocalDate.of(1900,5,1));
        System.out.println("di2.setSecond(LocalDate.of(1900,5,1)) : " + di2);
        di2.setSecond(LocalDate.of(2010,5,1));
        System.out.println("di2.setSecond(LocalDate.of(2010,5,1)) : " + di2);
        
        System.out.println("\n===========================\n");
        LocalDate ld = di2.getSecond();
        System.out.println("ld : " + ld);
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

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
class DateInterval extends Pair<LocalDate> {
    public DateInterval() { super(); }
    public DateInterval(LocalDate first, LocalDate second) {
        super();
        if(second.compareTo(first) >= 0) {
            setFirst(first);
            super.setSecond(second);
        } else {
            System.out.println("second should be Later than first");
            //throw new IllegalArgumentException();
        }
    }
    public void setSecond(LocalDate second) {
        if(second.compareTo(getFirst()) >= 0)
            super.setSecond(second);
        else {
            System.out.println("second should be Later than first");
            //throw new IllegalArgumentException();
        }
    }
    public LocalDate getSecond() {
        System.out.println("LocalDate.getSecond");
        return (LocalDate) super.getSecond();
    }
}
```

结果

```text
di : (1995-05-01, 2000-05-01)
di.setSecond(LocalDate.of(1900,5,1)) : (1995-05-01, 1900-05-01)
di.setSecond(LocalDate.of(2010,5,1)) : (1995-05-01, 2010-05-01)

===========================

di2 : (1995-05-01, 2000-05-01)
second should be Later than first
di2.setSecond(LocalDate.of(1900,5,1)) : (1995-05-01, 2000-05-01)
di2.setSecond(LocalDate.of(2010,5,1)) : (1995-05-01, 2010-05-01)

===========================

LocalDate.getSecond
ld : 2010-05-01
```

使用 javap 查看

```java
Compiled from "BridgeMethodTest.java"
class DateInterval extends Pair<java.time.LocalDate> {
  public DateInterval();
  public DateInterval(java.time.LocalDate, java.time.LocalDate);
  public void setSecond(java.time.LocalDate);
  public java.time.LocalDate getSecond();
  public void setSecond(java.lang.Object);
  public java.lang.Object getSecond();
}
```

注意到有两个 set 方法和两个 get 方法



BridgeMethodTest2.java

```java
public class BridgeMethodTest2 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee e = new Employee("Alice", 20000);
        Employee e2 = e.clone();
        e2.setSalary(50000);
        e.print();
        e2.print();
    }
}
class Employee implements Cloneable {
    private String name;
    private double salary;
    public Employee() {}
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void print() {
        System.out.println("name = " + name + ", salary = " + salary);
    }
    public Employee clone() throws CloneNotSupportedException {
        Employee e = (Employee) super.clone();
        System.out.println("Employee.clone");
        return e;
    }
}
```

结果

```text
Employee.clone
name = Alice, salary = 20000.0
name = Alice, salary = 50000.0
```

查看生成的类

```java
Compiled from "BridgeMethodTest2.java"
class Employee implements java.lang.Cloneable {
  private java.lang.String name;
  private double salary;
  public Employee();
  public Employee(java.lang.String, double);
  public void setSalary(double);
  public void print();
  public Employee clone() throws java.lang.CloneNotSupportedException;
  public java.lang.Object clone() throws java.lang.CloneNotSupportedException;
}
```

可以看到两个 clone 方法



#### 8.5.4 调用遗留代码

Java 设计泛型时主要目标是泛型可以和遗留代码能够交互

```java
void setLabelTable(Dictionary table);
```

上述函数并没有使用泛型

```java
Dictionary<Integer, Component> labelTable = new HashTable<>();
labelTable.put(0, new JLabel(new ImageIcon("nine.gif")));
...
slider.setLabelTable(labelTable);
```

如上操作会报告一个警告

反过来操作也会得到一个警告

```java
Dictionary<Integer, Component> labelTable = slider.getLabelTable();
```

可以利用注解让警告消失

```java
@SuppressWarnings("unchecked")
Dictionary<Integer, Component> labelTable = slider.getLabelTable();
```

或者直接将注解加在方法上取消整个方法的警告

```java
@SuppressWarnings("unchecked")
public void configureSlider() {...}
```