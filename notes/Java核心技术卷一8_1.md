# 《Java核心技术卷一》 笔记 8_1

2019.06.27 11:38



## 第8章 泛型程序设计

### 8.6 约束与局限性

Java 泛型使用时有一些局限性，多数局限性是由类型擦除引起的



#### 8.6.1 不能用基本类型实例化类型参数

因为类型擦除后为 Object 或者其子类，因此无法存储基本类型



#### 8.6.2 运行时类型查询只适用于原始类型

注意

```java
if(a instanceof Pair<String>)
if(a instanceof Pair<T>)
```

以上写法不能通过编译

而

```java
Pair<String> p = (Pair<String>) a;
```

会得到警告



getClass 将总会返回对应的原始类型

```java
Pair<String> strPair = ...;
Pair<Employee> empPair = ...;
if(strPair.getClass() == empPair.getClass()) // true
```



#### 8.6.3 不能创建参数化类型的数组

不能使用如下写法

```java
Pair<String>[] table = new Pair<String>[10];
```

但是可以声明

```java
Pair<String>[] table;
```

可以使用通配类型

```java
Pair<String>[] table = (Pair<String>) new Pair<?>[10];
```

但这并不安全

应当使用

```java
ArrayList<Pair<String>>
```



#### 8.6.4 Varargs 警告

```java
public static <T> void addAll(Collection<T> coll, T... ts) {
    for(T t : ts) coll.add(t);
}
```

考虑如下调用

```java
Collection<Pair<String>> table = ...;
Pair<String> p1 = ...;
Pair<String> p2 = ...;
addAll(table, p1, p2);
```

这种情况会得到警告而不是错误

可以使用

```java
@SuppressWarning("unchecked")
```

或者 Java SE 7 后

```java
@SafeVarages
```

直接标注方法



可以依靠这个特性创造泛型数组

```java
@SafeVarages
static <E> E[] array(E... array) {
    return array;
}
```

但是可以将错误类型的对象引用赋值给数组某个元素，而不会报错

直到使用时才会报错



#### 8.6.5 不能实例化类型变量

不能使用 new T(…)，new T[…] 或 T.class 

因为类型擦除会将 T 转化为 Object，而这并不是操作的本意

Java SE 8 之后可以使用

```java
Pair<String> p = Pair.makePair(String::new);
```

makePair 方法定义如下

```java
public static <T> Pair<T> makePair(Supplier<T> constr) {
    return new Pair<>(constr.get(), constr.get());
}
```



比较传统的方法如下

```java
public static <T> Pair<T> makePair(Class<T> cl) {
    try {
        return new Pair<>(cl.newInstance(), cl.newInstance());
    } catch (Exception ex) {
        return null;
    }
}
```

使用以下方式调用

```java
Pair<String> p = Pair.makePair(String.class);
```

Class 类本身是泛型的，String.class 是 Class\<String\> 的唯一实例



#### 8.6.6 不能构造泛型数组

这和不能实例化对象一致，因为类型会被擦除

此时可以提供一个数组构造器表达式

```java
String[] ss = ArrayAlg.minmax(String[]::new, "Tom", "Dick", "Harry");
```

部分定义如下

```java
public static <T extends Comparable> T[] minmax(IntFunction<T[]> constr, T... a) {
    T[] mm = constr.apply(2);
    ...
}
```



比较老式的方法是使用反射

```java
public static <T extends Comparable> T[] minmax(T... a) {
    T[] mm = (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
    ...
}
```



ArrayList 的 toArray 方法有两个版本

```java
Object[] toArray();
T[] toArray(T[] result);
```

对于第二个版本如果 result 数组够大，就是用 result 数组，否则根据 result 类型创建一个新的



#### 8.6.7 泛型类的静态上下文中类型变量无效

不能在静态域或方法中引用类型变量。因为类型擦除后只有一个静态域，因此不能使用类型变量



#### 8.6.8 不能抛出或捕获泛型类的实例

不能抛出或捕获泛型类的对象，甚至泛型类扩展 Throwable 都是不合法的



不过可以在异常规范中使用类型变量

```java
public static <T extends Throwable> void doWork(T t) throws T {
    try {
        do work
    } catch (Throwable realCause) {
        t.init(realCause);
        throw t;
    }
}
```



#### 8.6.9 可以消除对受查异常的检查

可以使用泛型消除受查异常必须提供处理器的限制

```java
@SuppressWarnings("unchecked")
public static <T extends Throwable> void throwAs(Throwable e) throws T {
    throw (T) e;
}
```

假设上述代码在类 Block 中

```java
try {
    do work
} catch (Throwable t) {
    Block.<RuntimeException>throwAs(t);
}
```

这样会把所有异常转换为非受查异常



ErasedCheckedTest.java

```java
import java.util.*;
import java.io.*;

public class ErasedCheckedTest {
    public static void main(String[] args) {
        new Block() {
            public void body() throws Exception {
                Scanner in = new Scanner(new File("ququx"), "UTF-8");
                while(in.hasNext())
                    System.out.println(in);
            }
        }.toThread().start();
    }
}

abstract class Block {
    public abstract void body() throws Exception;
    public Thread toThread() {
        return new Thread() {
            public void run() {
                try {
                    body();
                } catch (Throwable t) {
                    Block.<RuntimeException>throwAs(t);
                }
            }
        };
    }
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable e) throws T {
        throw (T) e;
    } 
}
```

结果

```text
Exception in thread "Thread-0" java.io.FileNotFoundException: ququx (系统找不到指定的
文件。)
        at java.io.FileInputStream.open0(Native Method)
        at java.io.FileInputStream.open(Unknown Source)
        at java.io.FileInputStream.<init>(Unknown Source)
        at java.util.Scanner.<init>(Unknown Source)
        at java.util.Scanner.<init>(Unknown Source)
        at ErasedCheckedTest$1.body(ErasedCheckedTest.java:8)
        at Block$1.run(ErasedCheckedTest.java:22)
```

虽然报出了错误，但是 run 方法并不需要提供处理受查异常的操作



#### 8.6.10 注意擦除后的冲突

当泛型擦除时，可能因为完全一样的条件引发冲突。

如擦除后形成一对方法名，参数，返回类型完全一致的函数，此时需要重命名冲突方法。



一个类或类型变量不能同时成为两个接口类型的子类，而两个接口是同一个接口的不同参数化

如，以下代码非法

```java
class Employee implememts Comparable<Employee> {...}
class Manager extends Employee implememts Comparable<Manager> {...}
```

这与桥方法有关

对于不同的 Comparable\<X\> 桥方法不能有两个?

```java
public int comparaTo(Object other) {
    return compareTo((X) other);
}
```



不过非泛型的版本是可以的

```java
class Employee implememts Comparable {...}
class Manager extends Employee implememts Comparable {...}
```



### 8.7 泛型类型的继承规则

无论 S 与 T 之间是什么关系，通常 Pair\<S\> 与 Pair\<T\> 没有关系

所以即使 S 是 T 的子类，Pair\<S\> 与 Pair\<T\> 之间是不能赋值的

注意这一点泛型与数组的行为不同，但数组在赋值时带有特殊保护，这是泛型不具备的



永远可以将参数化类型转化为原始类型



泛型类可以扩展或实现其他泛型类



```text
       ---------------> <<interface>> <--------------------
      |                     List                           |
      |                       ^                            |
<<interface>>                 .                       <<interface>>
List<Manager>                 |                       List<Employee>
      ^                       .                            ^
      .                       |                            .
      |   ---------------> ArrayList <------------------   |
      .   |                 (raw)                       |  .
      |   |                                             |  |
ArrayList<Manager> - - - no relationship - - - - - ArrayList<Employee>
```

\-\.\-\.\-\. implements

\-\-\-\-\-\- extends

\- \- \- no relationship