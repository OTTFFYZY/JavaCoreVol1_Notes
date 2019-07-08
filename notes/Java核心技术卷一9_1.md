# 《Java核心技术卷一》 笔记 9_1

2019.07.08 14:44



## 第9章 集合

### 9.2 具体的集合

| 集合类型        | 描述                                 |
| --------------- | ------------------------------------ |
| ArrayList       | 可以动态增长缩减的索引序列           |
| LinkedList      | 在任何位置高效增删的有序序列         |
| ArrayDeque      | 循环数组实现的双端队列               |
| HashSet         | 没有重复元素的无序集合               |
| TreeSet         | 有序集                               |
| EnumSet         | 枚举类型值的集合                     |
| LinkedHashSet   | 可以记住元素插入顺序的集             |
| PriorityQueue   | 允许高效删除最小元素的集合           |
| HashMap         | 键值关联的映射表                     |
| TreeMap         | 键有序排列的映射表                   |
| EnumMap         | 键属于枚举类型的映射表               |
| LinkedHashMap   | 可以记住键值项添加次序的映射表       |
| WeakHashMap     | 值无用后会被垃圾回收的映射表         |
| IdentityHashMap | 用 == 而不是 equals 比较键值的映射表 |

继承关系图

```text
AbstractCollection
    ^
    |--- AbstractList
    |        ^
    |        |--- AbstractSequentialList
    |        |        ^
    |        |         --- LinkedList
    |        |
    |         --- ArrayList
    |
    |--- AbstractSet
    |        ^
    |        |--- HashSet
    |        |        ^
    |        |         --- LinkedHashSet
    |        |
    |        |--- EnumSet
    |        |
    |         --- TreeSet
    |
    |--- AbstractQueue
    |        ^
    |         --- PriorityQueue
    |
     --- ArrayQueue


AbstractMap
    ^
    |--- HashMap
    |        ^
    |         --- LinkedHashMap
    |
    |--- TreeMap
    |
    |--- EnumMap
    |
    |--- WeakHashMap
    |
     --- IdentityHashMap
```



#### 9.2.1 链表

Java 中的链表都是双向链接的（doubly linked）

每个节点都保存了前驱和后继的引用

链表的 add 总是向尾部插

集合类 ListIterator 提供了 add 方法向中间部分增加元素

```java
interface ListIterator<E> extends Iterator<E> {
    void add(E element);   // 在迭代器位置之前添加一个元素
    E previous();          // 这两个方法
    boolean hasPrevious(); // 用来反向遍历链表
    void set(E element); // 替换 next 或 previous 方法上次返回的对象
}
```

特别注意，remove方法在调用 next 后会删除迭代器左侧的元素

previous 之后则会删除迭代器右侧的元素



如果一个迭代器正在遍历，而另一个迭代器修改了集合，此时会发生混乱，链表的迭代器可以发现这样的混乱，在一个迭代器的集合发现被另一个迭代器更改后，会抛出 ConcurrentModificationException

```java
List<String> list = ...;
ListIterator<String> it1 = list.listIterator();
ListIterator<String> it2 = list.listIterator();
it1.next();
it1.remove();
it2.next(); // throw ConcurrentModificationException
```



对于并发修改，每个迭代器和集合都会维护一个计数器，每次迭代器比较自己的计数器和集合的计数器是否一致。

链表修改只记录对结构有影响的修改，set 不包括在内



链表有 get 方法，但是效率极低，因为每次都要从开始访问（极小的优化，如果 get 的 index > size()/2 则从尾部开始查询）

nextIndex 和 previousIndex 可以给出 调用 next 或 previous 时返回元素的 index



LinkedListTest.java

```java
import java.util.*;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        System.out.println("org a = " + a);
        System.out.println("org b = " + b);

        while(bIter.hasNext()) {
            if(aIter.hasNext()) aIter.next();
            aIter.add(bIter.next());
        }

        System.out.println(a);

        bIter = b.iterator();
        while(bIter.hasNext()) {
            bIter.next();
            if(bIter.hasNext()) {
                bIter.next();
                bIter.remove();
            }
        }
        System.out.println(b);

        a.removeAll(b);

        System.out.println(a);
    }
}
```

结果

```text
org a = [Amy, Carl, Erica]
org b = [Bob, Doug, Frances, Gloria]
[Amy, Bob, Carl, Doug, Erica, Frances, Gloria]
[Bob, Frances]
[Amy, Carl, Doug, Erica, Gloria]
```



ListAPI.java

```java
public class ListAPI {
    public static void main(String[] args) {
        // java.util.List<E>
        // ListIterator<E> listIterator()
        // 返回一个列表迭代器

        // ListIterator<E> listIterator(int index)
        // 第一次调用 next 将返回 index 的元素

        // void add(int i, E element)
        // 在 i 位置新增元素 element

        // void addAll(int i, Collection<? extends E> elements)
        // 将某个集合中所有元素添加到给定位置

        // E remove(int i)

        // E get(int i)

        // E set(int i, E element)
        // 将给定 i 位置的元素替换并返回原来的元素

        // int indexOf(Object element)
        // 返回 element 第一次出现的位置，没有则返回 -1

        // int lastIndexOf(Object element)
        // 最后一次出现的位置

        
        // java.util.ListIterator<E>
        // void add(E newElement)
        // void set(E newElement)
        // boolean hasPrevious()
        // E previous()
        // int nextIndex()
        // int previousIndex()
    }
}
```

LinkedListAPI.java

```java
public class LinkedListAPI {
    public static void main(String[] args) {
        // java.util.LinkedList<E>
        // LinkedList()

        // LinkedList(Collection<? extends E> elements)

        // void addFirst(E element)
        // void addLast(E element)
        // E getFirst()
        // E getLast()
        // E removeFirst()
        // E removeLast()
    }
}
```



#### 9.2.2 数组列表

ArrayList 更适合随机下标访问，但不适合在中间增删

Vector 所有方法是同步的，因此单线程访问不要使用 Vector



#### 9.2.3 散列集

Java 中使用链表数组实现散列表

每个链表是一个桶（bucket）

对于 Java SE 8 桶满时会从 链表变成平衡树

一般设置桶数是最终元素数的 75% ~ 150%

如果散列表太满就需要再散列（rehashed）

装载因子（load factor）会决定何时进行再散列

默认的装载因子是 0.75



HashSet 提供了 add 方法，和重写了 contains 方法，使得其变得高效



注意更改集中元素时，如果散列值发生了变化，则位置也会发生变化



SetTest.java

```java
import java.util.*;

public class SetTest {
    public static void main(String[] args) {
        Set<String> words = new HashSet<>();
        long totalTime = 0;

        try (Scanner in = new Scanner(System.in)) {
            while(in.hasNext()) {
                String word = in.next();
                long callTime = System.currentTimeMillis();
                words.add(word);
                callTime = System.currentTimeMillis() - callTime;
                totalTime += callTime;
            }
        }

        Iterator<String> iter = words.iterator();
        for(int i = 1; i <= 20 && iter.hasNext(); i++) {
            System.out.println(iter.next());
        }
        System.out.println("...");
        System.out.println(words.size() + " distinct words.\n" + totalTime + " ms");
    }
}
```



SetAPI.java

```java
public class SetAPI {
    public static void main(String[] args) {
        // java.util.HashSet<E>
        // HashSet()

        // HashSet(Collection<? extends E> elements)

        // HashSet(int initialCapacity)

        // HashSet(int initialCapacity, float loadFactor)


        // java.lang.Object
        // int hashCode()
    }
}
```



#### 9.2.4 树集

树集是一个有序集合（sorted collection）

内部实现是红黑树（red-black tree）

需要元素实现 Comparable 接口

或者提供一个 Comparator



TreeSetTest.java

```java
import java.util.*;

public class TreeSetTest {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet();
        parts.add(new Item("Toaster", 1234));
        parts.add(new Item("Widget", 4562));
        parts.add(new Item("Modem", 9912));
        System.out.println(parts);

        NavigableSet<Item> sortByDescription = new TreeSet<>(
            Comparator.comparing(Item::getDescription)
        );
        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}

class Item implements Comparable<Item> {
    private String description;
    private int partNumber;

    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }
    public String getDescription() {
        return description;
    }
    public int getPartNumber() {
        return partNumber;
    }
    public String toString() {
        return "[description = " + description + ", partNumber = " + partNumber + "]";
    }
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        Item other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
    public int compareTo(Item other) {
        int diff = Integer.compare(partNumber, other.partNumber);
        return diff != 0 ? diff : description.compareTo(other.description);
    }
}
```

结果

```text
[[description = Toaster, partNumber = 1234], [description = Widget, partNumber = 4562], [description = Modem, partNumber = 9912]]
[[description = Modem, partNumber = 9912], [description = Toaster, partNumber = 1234], [description = Widget, partNumber = 4562]]
```



TreeSetAPI.java

```java
public class TreeSetAPI {
    public static void main(String[] args) {
        // java.util.TreeSet<E> 
        // TreeSet()
        // TreeSet(Comparator<? super E> comparator)
        // TreeSet(Collection<? extends E> elements)
        // TreeSet(SortedSet<E> s)


        // java.util.SortedSet<E>
        // Comparator<? super E> comparator()
        // 返回对元素排序的比较器
        // 使用 Comparable 接口的 compareTo 则返回 null

        // E first()
        // E last()


        // java.util.NavigableSet<E>
        // E higher(E value)
        // 大于 value 的最小元素

        // E lower(E value)
        // 小于 value 的最大元素

        // E ceiling(E value)
        // 小于等于 value 的最大元素
        
        // E floor(E value)
        // 大于等于 value 的最小元素

        // E poolFirst()
        // E poolLast()
        // 删除并返回集中的最大（最小）元素，集为空时返回 null

        // Iterator<E> descendingIterator()
        // 递减顺序遍历的迭代器
    }
}
```



#### 9.2.5 队列与双端队列

Java SE 6 中引入了 Deque 接口， ArrayDeque 和 LinkedList 都实现了 Deque



QueueAPI.java

```java
public class QueueAPI {
    public static void main(String[] args) {
        // java.util.Queue
        // boolean add(E element)
        // boolean offer(E element)
        // 尾部添加元素，并返回 true
        // 不能添加时 add 抛出 IllegalStateException 异常，offer 返回 false

        // E remove()
        // E poll()
        // 删除并返回队首第一个元素
        // 队列为空时，remove 抛出 NoSuchElementException 异常，poll 返回 null

        // E element()
        // E peek()
        // 返回队首第一个元素
        // 队列为空时，element 抛出 NoSuchElementException 异常，peek 返回 null


        // java.util.Deque<E>
        // void addFirst(E element)
        // void addLast(E element)
        // boolean offerFirst(E element)
        // boolean offerLast(E element)

        // E removeFirst()
        // E removeLast()
        // E pollFirst()
        // E pollLast()

        // E getFirst()
        // E getLast()
        // E peekFirst()
        // E peekLast()

        // java.util.ArrayDeque<E>
        // ArrayDeque()
        // ArrayDeque(int initialCapacity)
    }
}
```



#### 9.2.6 优先级队列

优先队列（priority queue）中的元素可以按照任意顺序插入，却总是按照排序后的顺序进行检索

priority queue 可以由堆（heap）实现

优先队列迭代不是按照顺序进行的，但是删除总是按排序顺序进行的

PriorityQueueTest.java

```java
import java.util.*;
import java.time.*;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<LocalDate> pq = new PriorityQueue<>();
        pq.add(LocalDate.of(1906, 12, 9));
        pq.add(LocalDate.of(1815, 12, 10));
        pq.add(LocalDate.of(1903, 12, 3));
        pq.add(LocalDate.of(1910, 6, 22));

        System.out.println("Iterating over elements ...");
        for(LocalDate date : pq)
            System.out.print(date + " ");
        System.out.println("\nRemoving elements ...");
        while(!pq.isEmpty())
            System.out.print(pq.remove() + " ");
    }
}
```

结果

```text
Iterating over elements ...
1815-12-10 1906-12-09 1903-12-03 1910-06-22 
Removing elements ...
1815-12-10 1903-12-03 1906-12-09 1910-06-22 
```



PriorityQueueAPI.java

```java
public class PriorityQueueAPI {
    public static void main(String[] args) {
        // java.util.PriorityQueue
        // PriorityQueue()
        // PriorityQueue(int initialCapacity)
        // PriorityQueue(itn initialCapacity, Comparator<? super E> c)
    }
}
```

