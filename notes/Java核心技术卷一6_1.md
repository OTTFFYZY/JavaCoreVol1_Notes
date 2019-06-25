

# 《Java核心技术卷一》 笔记 6_1

2019.06.24 12:50



## 第6章 接口、lambda 表达式与内部类

### 6.3 lambda 表达式

#### 6.3.1 为什么引入 lambda 表达式

lambda 表达式：一个可传递代码块

为了将某个方法（代码块）当做参数传递进函数，而不必包装成实现某个接口的对象



#### 6.3.2 lambda 表达式的语法

```java
(String first, String second) -> first.length() - second.length()
```



逻辑学家 Alonzo Church 想要形式化的表示能有效计算的数学函数，使用了 $\lambda$

最初《数学原理》中使用的是 \^ 然后 Church 使用 $\Lambda$ 最终使用了小写

以后带参数变量的表达式就成为 lambda 表达式



lambda 表达式由以下部分组成：

参数表：写在开头的括号里就像函数的参数表，没有参数仍需要空的小括号

箭头

表达式：可以是一句语句，也可以是花括号中的一段代码，包含显式的 return



当编译器可以推导出参数类型时，可以省略类型

```java
Comparator<String> comp = (first, second) -> first.length() - second.length();
```

当只有一个参数且能推倒出类型时可以省略类型和小括号

```java
ActionListener listener = event -> 
    System.out.println("The time is " + new Date());
```



lambda 表达式不需要提供返回类型，总是根据上下文进行推倒

lambda 表达式不能只在某些分支提供返回值，而其他分支不提供



LambdaTest.java

```java
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class LambdaTest {
    public static void main(String[] args) {
        String[] planets = new String[]{
            "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        System.out.println(Arrays.toString(planets));
        
        System.out.println("Sorted in dictionary order: ");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        
        System.out.println("Sorted by length: ");
        Arrays.sort(planets, (first, second) -> first.length() - second.length());
        System.out.println(Arrays.toString(planets));

        Timer t = new Timer(1000, event -> System.out.println("The time is " + new Date()));
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}
```

结果

```text
[Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune]
Sorted in dictionary order: 
[Earth, Jupiter, Mars, Mercury, Neptune, Saturn, Uranus, Venus]
Sorted by length: 
[Mars, Earth, Venus, Saturn, Uranus, Jupiter, Mercury, Neptune]
The time is Mon Jun 24 14:51:48 CST 2019
The time is Mon Jun 24 14:51:49 CST 2019
The time is Mon Jun 24 14:51:50 CST 2019
The time is Mon Jun 24 14:51:51 CST 2019
The time is Mon Jun 24 14:51:52 CST 2019
The time is Mon Jun 24 14:51:53 CST 2019
```



#### 6.3.3 函数式接口

Java 中已经有了很多封装代码块的接口，lambda 表达式与接口是兼容的

只有一个抽象方法的接口，需要这种接口的对象时就可以提供一个 lambda 表达式

这种接口称为函数式接口（functional interface）

（有些接口定义了一些 Object 类的方法，从而这些可能这些方法不是抽象的（有时这样做是为了生成 javadoc 注释的）接口也可能会包含一些默认方法。）



Java 中对 lambda 表达式能做的就是转化为函数式接口，不如支持函数字面量的语言强大。

java.util.function 包中定义了非常多的通用函数式接口如 BiFunction

```java
BiFunction<String, String, Integer> comp 
    = (first, second) -> first.length() - second.length();
```

又如

```java
public interface Predicate<T> {
    boolean test(T t);
}
list.removeIf(e -> e == null);
```

可以去除列表中的所有 null 元素



需要 lambda 表达式时可以自己定义特定的函数式接口。



#### 6.3.4 方法引用（method reference）

有时 现有方法可以完成想要传递到其他代码的某个动作，可以使用方法引用

```java
Timer t = new Timer(1000, event -> 
    System.out.println(event));
```

可以写作

```java
Timer t = new Timer(1000, System.out::println);
```

忽略大小写的排序可以

```java
Arrays.sort(strings, String::compareToIgnoreCase);
```



:: 操作符分隔了 对象或类名 与 方法名

object::instanceMethod

Class::staticMethod

Class::instanceMethod



前两种等价于省略了方法参数的 lambda 表达式

```java
System.out::print
x -> System.out.print(x)

Math::pow
(x, y) -> Math.pow(x, y)
```

第三种情况，第一个参数会成为方法的目标（调用方法的对象）

```java
String::compareToIgnoreCase
(x, y) -> x.compareToIgnoreCase(y)
```



当有多个同名函数重载时，编译器会尝试从上下文推倒，取决于函数式接口的参数，类似于 lambda 表达式，方法引用不能独立存在



方法引用可以使用 this 和 super

```java
this::equals
x -> this.equals(x)

super::instanceMethod
```

super 将使用 this 作为目标，调用方法的超类版本



MethodReferenceTest.java

```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class MethodReferenceTest {
    public static void main(String[] args) {
        TimedGreeter obj = new TimedGreeter();
        obj.greet(); 
    }
}
class Greeter {
    public void greet(ActionEvent e) {
        System.out.println("Hello, world!");
    }
}
class TimedGreeter extends Greeter {
    public void greet() {
        Timer t = new Timer(1000, super::greet);
        t.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}
```



#### 6.3.5 构造器引用

构造器引用与方法引用类似，不过方法名为 new

Person::new 是 Person 的构造器

```java
ArrayList<String> names = ...;
Stream<Person> stream = names.stream().map(Person::new);
List<Person> people = stream.collect(Collectors.toList());
```

上述 map 方法会对每个列表中的元素调用 Person(String) 的构造器



可以用数组类型建立构造器引用，它接受一个数组长度参数

```java
int[]::new
x -> new int[x]
```



Java 不能构造泛型数组。但是 Object 数组很多时候不能令人满意

```java
Object[] people = stream.toArray();
```

数组构造器则能处理这种情况

```java
Person[] people = stream.toArray(Person[]::new);
```



#### 6.3.6 变量作用域

通常可以在 lambda 表达式中访问外部的变量



 lambda 表达式的组成部分：

代码块，参数，自由变量

其中自由变量就是指不是参数也不是在 lambda 表达式中定义的变量



有时 lambda 表达式的执行在定义之后很久（定义 lambda 表达式的对象可能已经不存在了）

lambda 表达式必须能够存储自由变量的值，这个过程称为捕获（captured）

闭包（closure）代码块和自由变量，Java 中 lambda 表达式就是闭包



lambda 表达式中捕获的值要明确定义，且只能引用值不能改变的变量。

这是为了保证并发时的安全性，

如果值在外部可以改变，这也不合理

因此 lambda 表达式中捕获的变量实际上是最终变量（effectively final）



lambda 表达式体和嵌套块有相同的作用域，因此也适用命名冲突和遮蔽的规则



lambda 表达式中使用 this 关键字时，是指的创建 lambda 表达式的方法的 this



#### 6.3.7 处理 lambda 表达式

使用 lambda 表达式的重点是延迟执行（deferred execution）

希望之后再执行有很多原因：

在单独一个线程运行代码

多次运行代码

在算法的合适位置运行代码

发生某种情况是执行代码

只在必要时才运行代码



接受 lambda 表达式需要一个函数式接口

```java
public static void repeat(int n, Runnable action) {
    for(int i = 0; i < n; i++)
        action.run();
}
repeat(10, () -> System.out.println("Hello, world!"));
```



常用函数式接口

| 函数式接口            | 抽象方法           | 描述                         | 其他方法                   |
| --------------------- | ------------------ | ---------------------------- | -------------------------- |
| Runnable              | void run()         | 作为无参数或返回值的动作运行 |                            |
| Supplier\<T\>         | T get()            | 提供一个 T 类型的值          |                            |
| Consumer\<T\>         | void accept(T)     | 处理一个 T 类型的值          | andThen                    |
| BiConsumer\<T, U\>    | void accept(T, U)  | 处理 T，U 两个值             | andThen                    |
| Function\<T, R\>      | R apply(T)         | 有一个 T 类型参数的函数      | compose, andThen, identity |
| BiFunction\<T, U, R\> | R apply(T, U)      | 有 T，U 两个参数的函数       | andThen                    |
| UnaryOperator\<T\>    | T apply(T)         | T 上的一元运算               | compose, andThem, identity |
| BinaryOperator\<T\>   | T apply(T, T)      | T 上的二元运算               | andThen, maxBy, minBy      |
| Predicate\<T\>        | boolean test(T)    | 布尔值函数                   | and, or, negate, isEqual   |
| BiPredicate\<T, U\>   | boolean test(T, U) | 双参数布尔值函数             | and, or, negate            |



ProcessLambda.java

```java
public class ProcessLambda {
    public static void main(String[] args) {
        repeat(10, i -> System.out.println("Countdown : " + (9 - i)));
    }
    public static void repeat(int n, IntConsumer action) {
        for(int i = 0; i < n; i++)
            action.accept(i);
    }
}
interface IntConsumer {
    void accept(int value);
}
```



基本类型可以使用特殊规范减少自动装箱

| 函数式接口              | 抽象方法                 |
| ----------------------- | ------------------------ |
| BooleanSupplier         | boolean getAsBoolean()   |
| *P*Supplier             | *p* getAS*P*()           |
| *P*Consumer             | void accept(*p*)         |
| Obj*P*Consumer\<T\>     | void accept(T, *p*)      |
| *P*Function\<T\>        | T apply(*p*)             |
| *P*To*Q*Function        | *q* applyAS*Q*(*p*)      |
| To*P*Function\<T\>      | *p* applyAS*P*(T)        |
| To*P*BiFunction\<T, U\> | *p* applyAS*P*(T, U)     |
| *P*UnaryOperator        | *p* applyAS*P*(*p*)      |
| *P*BinaryOperator       | *p* applyAS*P*(*p*, *p*) |
| *P*Predicate            | boolean test(*p*)        |

其中 *p*，*q* 为 int，long，double；*P*，*Q* 为 Int，Long，Double



大多数标准函数式接口都提供了非抽象方法来生成或合并函数

Predicate.isEqual(a) 相当于 a::equals 不过 a 为 null 也能正常工作

提供了 or negate 等来合并谓词

```java
Predicate.isEqual(a).or(Predicate.isEqual(b))
x -> a.equals(x) || b.equals(x)
```



自己定义函数式接口时可以使用注解

```java
@FunctionalInterface
```



#### 6.3.8 再谈 Comparator

Comparator 接口包含很多方便的静态方法来创建比较器

这些方法可以用于 lambda 表达式或方法引用



comparing 方法取一个“键提取器”函数，它将类型 T 映射为一个课比较类型，对比较的对象应用这个函数，然后对返回的键完成比较

```java
Arrays.sort(people, Comparator.comparing(Person::getName));
```

可以同 thenComparing 方法串联使用

```java
Arrays.sort(people, 
            Comparator.comparing(Person::getLastName)
                      .thenComparing(Person::getFirstName));
```

先比 lastName，后比 firstName



按人名长度排序

```java
Arrays.sort(people, 
            Comparator.comparing(Person::getName,
                (s, t) -> Integer.compare(s.length(), t.length())));
```

或者

```java
Arrays.sort(people, Comparator.comparingInt(p -> p.getName().length()));
```



如果键函数可能返回 null 就需要使用 nullsFirst 和 nullsLast 适配器

这些静态方法会修改现有的比较器，从而遇到 null 的时候不会抛出异常，而是将值标记为小于或者大于正常值

```java
Comparator.comparing(Person::getMiddleName(), Comparator.nullsFirst(...))
```

其中 nullsFirst 需要一个比较器参数

Comparator.\<T\>naturalOrder 方法可以为任何实现了 Comparable 的类建立一个比较器

```java
import static java.util.Comparator.*;
Arrays.sort(people, comparing(Person::getMiddleName,                        
                              nullsFirst(naturalOrder())));
```

使用 naturalOrder().reversed() 或者 reverseOrder() 得到逆序



ComparatorTest.java

```java
import java.util.*;

public class ComparatorTest {
    public static void main(String[] args) {
        Person[] people = new Person[6];
        people[0] = new Person("bbc", "bwec", "sdg");
        people[1] = new Person("bawe", "bwqec", "absgd");
        people[2] = new Person("sdb", null, "cxzagagd");
        people[3] = new Person("dg", "ec", "sggad");
        people[4] = new Person("asgag", "abqwec", "sdg");
        people[5] = new Person("gdg", null, "sgd");
        show("original array :", people);

        Arrays.sort(people, Comparator.comparing(Person::getLastName));
        show("Sorted by last name :", people);

        Arrays.sort(people, 
            Comparator.comparing(Person::getLastName)
                      .thenComparing(Person::getFirstName));
        show("Sorted by last name (then first name) :", people);

        Arrays.sort(people, 
            Comparator.comparing(Person::getLastName,
                (s, t) -> Integer.compare(s.length(), t.length())));
        show("Sorted by last name length :", people);

        Arrays.sort(people, Comparator.comparingInt(p -> p.getLastName().length()));
        show("Sorted by last name length :", people);

        Arrays.sort(people, Comparator.comparing(Person::getMiddleName,                        
                                                Comparator.nullsFirst(Comparator.naturalOrder())));
        show("Sorted by middle name (null first) :", people);
    }
    public static void show(String s, Person[] arr) {
        System.out.print(s);
        boolean fi =  true;
        for(Person p : arr) {
            if(!fi) System.out.print(",");
            fi = false;
            System.out.println();
            System.out.print("    " + p.getFirstName() + " " 
                           + p.getMiddleName() + " " + p.getLastName());
        }
        System.out.println('\n');
    }
}
class Person {
    private String firstName, middleName, lastName;
    Person(String fi, String mi, String la) {
        firstName = fi;
        middleName = mi;
        lastName = la;
    }
    String getFirstName() {
        return firstName;
    }
    String getMiddleName() {
        return middleName;
    }
    String getLastName() {
        return lastName;
    }
}
```

结果

```text
original array :
    bbc bwec sdg,
    bawe bwqec absgd,
    sdb null cxzagagd,
    dg ec sggad,
    asgag abqwec sdg,
    gdg null sgd

Sorted by last name :
    bawe bwqec absgd,
    sdb null cxzagagd,
    bbc bwec sdg,
    asgag abqwec sdg,
    gdg null sgd,
    dg ec sggad

Sorted by last name (then first name) :
    bawe bwqec absgd,
    sdb null cxzagagd,
    asgag abqwec sdg,
    bbc bwec sdg,
    gdg null sgd,
    dg ec sggad

Sorted by last name length :
    asgag abqwec sdg,
    bbc bwec sdg,
    gdg null sgd,
    bawe bwqec absgd,
    dg ec sggad,
    sdb null cxzagagd

Sorted by last name length :
    asgag abqwec sdg,
    bbc bwec sdg,
    gdg null sgd,
    bawe bwqec absgd,
    dg ec sggad,
    sdb null cxzagagd

Sorted by middle name (null first) :
    gdg null sgd,
    sdb null cxzagagd,
    asgag abqwec sdg,
    bbc bwec sdg,
    bawe bwqec absgd,
    dg ec sggad
```

