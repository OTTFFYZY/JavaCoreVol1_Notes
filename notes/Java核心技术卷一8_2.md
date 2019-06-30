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



通配符类型的引用

```java
Pair<Manager> managerBuddies = new Pair<>(ceo,cfo);
Pair<? extends Employee> wcBuddies = managerBuddies;
// wcBuddies.setFirst(lowlyEmployee);
```

这样调用 set 方法不会通过编译