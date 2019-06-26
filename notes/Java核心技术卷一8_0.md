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



