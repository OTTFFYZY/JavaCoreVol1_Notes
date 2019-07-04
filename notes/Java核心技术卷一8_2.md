# 《Java核心技术卷一》 笔记 8_2

2019.06.27 15:31



## 第8章 泛型程序设计

### 8.8 通配符类型

固定的泛型类型使用起来有些限制，于是有了统配符类型



#### 8.8.1 通配符概念

通配符类型中，允许类型参数变化。通配符类型

```java
Pair<? extends Employee> 
```

比如针对下面的函数

```java
public static void printBuddies(Pair<Employee> p) {
    Employee first = p.getFirst();
    Employee second = p.getSecond();
    System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
}
```

就不能传递参数 Pair\<Manager\>

此时应该使用通配符类型

```java
public static void printBuddies(Pair<? extends Employee> p) {
    Employee first = p.getFirst();
    Employee second = p.getSecond();
    System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
}
```

类型之间的关系

```text
              Pair
              (raw)
                ^
                |
       Pair<? extends Employee>
       ^                      ^
       |                      |
Pair<Manager>           Pair<Employee>
```



通配符类型的引用

```java
Pair<Manager> managerBuddies = new Pair<>(ceo,cfo);
Pair<? extends Employee> wcBuddies = managerBuddies;
// wcBuddies.setFirst(lowlyEmployee);
```

这样调用 set 方法不会通过编译

因为 set 方法看起来如下

```java
//void setFirst(? extends Employee)
```

编译器并不知道实际传递的是什么类型，这将拒绝所有的特定类型

get 方法则可以正常使用。

```java
Employee e = wcBuddies.getFirst();
```

因为传递出来的总会是 Employee 的子类对象



#### 8.8.2 通配符的超类型限定

通配符限定于类型变量限定很像，但还可以使用超类型限定（supertype bound）

统配符限制为某个类的所有超类

```java
Pair<? super Manager>
// void setFirst(? super Manager)
// ? super Manager getFirst()
```

此时将不能使用 get 方法，因为只知道得到的结果可以赋值给 Object

使用 set 方法则是安全的，此时传递的对象不能是 Employee 或者 Object，只能传递 Manager 或其子类型对象



例子

```java
public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
    if(a.length == 0) return;
    Manager min = a[0];
    Manager max = a[0];
    for(int i = 1; i < n; i++) {
        if(min.getBonus() > a[i].getBonus()) min = a[i];
        if(max.getBonus() < a[i].getBonus()) max = a[i];
    }
    result.setFirst(min);
    result.setSecond(max);
}
```

如上 result 可以由 Manager 及其超类构建的 Pair



对于实现了泛型接口的情况

```java
public interface Comparable<T> {
    public int compareTo(T other);
}
class ArrayAlg
{
    public static <T extends Comparable<T>> T min(T[] a) {
        
    }
}
```

可以在 extends 后面的接口也使用泛型



但是继承可能会和这样的泛型设定冲突

比如，LocalDate 实现了 ChronoLocalDate 而 ChronoLocalDate 扩展了 Comparable\<ChronoLocalDate\> 而不是 Comparable\<LocalDate\>

此时就要

```java
public static <T extends Comparable<? super T>> T min(T[] a)
```

可能实现了 T 或 T 超类的的 Comparable 此时传入 T 或其子类参数是安全的



超类限定可能用于函数式接口

```java
default boolean removeIf(Predicate<? super E> filter)
```

希望传入 Object 而不仅是 Employee 的时候就可以使用这种方式



#### 8.8.3 无限定通配符

```java
Pair<?>
// ? getFirst()
// void setFirst(?)
```

注意 getFirst 只能赋值给 Object

setFirst 方法则不能使用包括 Object 在内的对象调用

只可以

```java
setFirst(null);
```

可以写出一个判空方法

```java
public static boolean hasNull(Pair<?> p) {
    return p.getFirst() == null || p.getSecond() == null;
}
```

当然也可以只使用泛型

```java
public static <T> boolean hasNull(Pair<T> p)
```



#### 8.8.4 通配符捕获

注意统配符不是类型变量，因此不能书写如下代码

```java
public static void swap(Pair<?> p) {
    // ? t = p.getFirst();
}
```

不过可以通过辅助方法

```java
public static <T> void swapHelper(Pair<T> p) {
    T t = p.getFirst();
    p.setFirst(p.getSecond());
    p.setSecond(t);
}
public static void swap(Pair<?> p) {
    swapHelper(p);
}
```

此时参数 T 将会捕获通配符



有些时候需要使用通配符捕获

```java
public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
    minmaxBonus(a, result);
    PairAlg.swap(result);
}
```

其中 swap 函数就需要类型是 Pair\<?\>



编译器必须确信通配符表达的是单个、确定的类型时，捕获才是合法的。





