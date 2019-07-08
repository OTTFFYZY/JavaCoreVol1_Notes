# 《Java核心技术卷一》 笔记 9_0

2019.07.08 09:28



## 第9章 集合

### 9.1 Java 集合框架

Java 最初只提供了很少的类： Vector、Stack、HashTable、BitSet 和 Enumeration

Java SE 1.2 开始推出了新的集合框架



#### 9.1.1 将集合的接口与实现分离

Java 集合类库也将接口（interface）和实现（implementation）分离

例如 Queue 的最简形式可能如下

```java
public interface Queue<E> {
    void add(E element);
    E remove();
    int size();
}
```

这并没有指定实现队列的方式

队列通常有如下两种实现

使用循环数组

```java
public class CircularArrayQueue<E> implements Queue<E> {
    private int head;
    private int tail;
    private E[] elements;
    
    CircularArrayQueue(int capacity){...}
    public void add(E element){...}
    public E remove(){...}
    public int size(){...}
}
```

使用链表

```java
public class LinkedListQueue<E> implements Queue<E> {
    private Link head;
    private Link tail;
    
    LinkedListQueue(){...}
    public void add(E element){...}
    public E remove(){...}
    public int size(){...}
}
```



实际 Java 类库中提供类似功能的类是 ArrayDeque 和 LinkedList



一旦创建集合就不需要知道具体使用了哪种实现，因此可以使用接口类型存放集合引用

```java
Queue<Customer> expressLane = new CircularArrayQueue<>(100);
expressLane.add(new Customer("Harry"));
```



这样实际更改实现时，只需要在定义处更改即可

```java
Queue<Customer> expressLane = new LinkedListQueue<>();
```



因为接口只决定了行为，而具体实现决定了效率，有时有了更好效率的实现，就可以使用这种简单的更改方式。



Java 中会有一组 Abstract 开头的类，实现自己的类时，扩展 Abstract 类比自己实现对应的接口要容易。



#### 9.1.2 Collection 接口

集合类的基本接口是 Collection 接口

这个接口有两个基本方法

```java
public interface Collection<E> {
    boolean add(E element);
    // 向集合添加元素，如果集合变化返回 true 否则返回 false
    Iterator<E> iterator();
    // iterator 方法返回一个实现了 Iterator 接口的对象，能够依次遍历集合中的对象
    ...
}
```



#### 9.1.3 迭代器

Iterator

```java
public interface Iterator<E> {
    E next();
    boolean hasNext();
    void remove();
    default void forEachRemaining(Consumer<? super E> action);
}
```

通过调用 hasNext 判断是否还有下个元素，使用 next 来得到下个元素。如果已经达到集合末尾调用 next 则会抛出 NoSuchElementException，因此调用 next 前应当使用 hasNext 检查

```java
Collection<String> c = ...;
Iterator<String> iter = c.iterator();
while(iter.hasNext()) {
    String element = iter.next();
    ...
}
```

也可以使用 for each 简化这个过程

```java
for(String element : c) {
    ...
}
```

for each 可以与任何实现了 Iterable 接口的对象一起工作

```java
public interface Iterable<E> {
    Iterator<E> iterator();
}
```



Collection 接口也扩展了 Iterable 接口，因此标准类库中的所有集合都可以使用 for each



Java SE 8 中甚至不需要写循环，可以调用 forEachRemaining 方法 并提供一个 lambda 表达式

```java
iterator.forEachRemaining(element -> ...);
```



元素访问的顺序取决于集合的类型，但可以保证不重不漏的遍历所有元素



Iterable 接口的 next 和 hasNext 与 Enumeration 接口的 nextElement 与 hasMoreElements 的作用一样，但要更简洁。



Java 集合类库的迭代器与其他类库中的迭代器概念上有重要区别。传统集合类库迭代器根据数组索引建模，给定一个迭代器就可以查看指定位置上的元素，不需要查找元素就可以移动迭代器位置。

Java 的迭代器查找操作和位置变更是紧密相连的。查找元素的唯一方法时 next 而与此同时迭代器位置会移动。因此，Java 迭代器被认为是在两个元素之间，当调用 next 时，越过一个元素，并且返回越过的元素的引用。

可以将 Iterator.next 与 InputStream.read 视作等效



Iterator 接口中的 remove 方法会删除上次调用 next 方法越过的元素

```java
Iterator<String> it = c.iterator();
it.next();
it.remove();
```

如果调用 remove 之前没有调用 next 将抛出 IllegalStateException 异常

如果想删除两个相邻的元素必须先删除一个越过一个再删除另一个

```java
it.remove();
it.next();
it.remove();
```



#### 9.1.4 泛型实用方法

```java
public static <E> boolean contains(Collection<E> c, Object obj) {
    for(E element : c) 
        if(element.equals(obj)) return true;
    return false;
}
```

实际 Collection 接口声明了很多向上面的实用方法

```java
int size()
boolean isEmpty()
boolean contains(Object obj)
boolean containsAll(Collection<?> c)
boolean equals(Object other)
boolean addAll(Collection<? extends E> from)
boolean remove(Object obj)
boolean removeAll(Collection<?> c)
void clear()
boolean retainAll(Collection<?> c)
Object[] toArray()
<T> T[] toArray(T[] arrayToFill)
default boolean removeIf(Predicate<? super E> filter)
// 删除 filter 返回 true 的所有元素
```



#### 9.1.5 集合框架中的接口

```text
        Iterable
            ^
            |
        Collection               Map           Iterator      RandomAccess
            ^                     ^                ^  
            |                     |                |
  ---------- ---------            |                |
 |          |         |           |                |
List       Set      Queue     SortedMap       ListIterator
            ^         ^           ^
            |         |           |
        SortedSet   Deque         |
            ^                     |
            |                NavigableMap
       NavigableSet
```



集合有来能个基本接口 Collection 和 Map



##### Map

Map 是键值对因此使用 put 方法插入元素

```java
V put(K key, V value)
```

读取则使用 get 方法

```java
V get(K key)
```



##### List

List 是一个有序集合，元素会增加到容器特定位置，可以使用迭代器或数组索引访问

也可以使用数组下标进行随机访问（random access）

用于随机访问的方法

```java
void add(int index, E element)
void remove(int index)
E get(int index)
E set(int index, E element)
```

ListIterator 接口是 Iterator 的子接口，定义类一个在迭代器前面增加元素的方法

```java
void add(E element)
```



Java SE 1.4 引入了一个标记接口 RandomAccess

这个接口中没有任何方法，用来判断是否支持高效随机访问

```java
if(c instanceof RandomAccess) {
    use random access algorithm
} else {
    use sequential access algorithm
}
```



##### Set

Set 接口等同于 Collection 接口，不过对方法的行为有更严谨的定义。

add 方法不允许添加重复元素

equals 方法只考虑包含的所有元素是否一致，而不考虑顺序

hashCode 方法保证元素相同的两个集合得到的散列码一致



SortedSet 和 SortedMap 提供了用于排序的比较器，定义了可以得到集合子集视图的方法



NavigableSet 和 NavigableMap 包含了用于搜索和遍历有序集和映射的方法

TreeSet 和 TreeMap 实现了这些方法



