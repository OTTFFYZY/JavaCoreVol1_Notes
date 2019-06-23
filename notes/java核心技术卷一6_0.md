

# 《Java核心技术卷一》 笔记 6_0

2019.06.22 11:39



## 第6章 接口、lambda 表达式与内部类

接口（interface）

一个类可以实现（implement）一个或多个接口

lambda 表达式

内部类（inner class）

代理（proxy）



### 6.1 接口

#### 6.1.1 接口概念

接口不是类，而是对类的一组需求描述

类要遵从接口描述的格式统一定义



Arrays 类中的方法 sort 可以对对象数组进行排序，但要求所属类实现了 Comparable 接口

```java
public interface Comparable {
    int compareTo(Object other);
}
```

Java SE 5

```java
public interface Comarable<T> {
    int compareTo(T other);
}
```



接口中的所有方法是自动 public 的

接口中可以包含多个方法和常量

接口不能有实例域

Java SE 8 以前接口不能实现方法（不能引用实例域）

实例域和方法实现由实现接口的类提供



为了让类实现一个接口：

将类声明为实现给定的接口

对接口中的所有方法进行定义

```java
class Employee implements Comparable {
    public int compareTo(Object otherObject) {
    
    }
}
```

注意实现接口方法为 public



必须提供接口是因为 Java 是一种强类型（Strongly typed）语言

调用方法时，编译器会检查方法是否会存在



