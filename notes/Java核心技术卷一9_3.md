# 《Java核心技术卷一》 笔记 9_3

2019.07.09 17:53



## 第9章 集合

### 9.5 算法

由于有了接口化的类库，因此很多算法可以只实现一次

算法总是建立在能够实现算法的最小集合接口上

例如可以将 max 函数定义在 Collection 上

```java
public static <T extends Comparable> T max(Collection<T> c) {
    if(c.isEmpty()) throw new NoSuchElementException();
    Iterator<T> iter = c.iterator();
    T largest = iter.next();
    while(iter.hasNext()) {
        T next = iter.next();
        if(largest.compareTo(next) < 0) {
            largest = next;
        }
    }
    return largest;
}
```



#### 9.5.1 排序与混排

```java
List<String> staff = new LinkedList<>();
...
Collections.sort(staff);
```

这个方法假定了元素实现了 Comparable 接口



用其他方法排序，可以使用 List 接口的 sort 方法，并传入一个 Comparator 对象

```java
staff.sort(Comparator.comparingDouble(Employee::getSalary));
staff.sort(Comparator.reverseOrder());
staff.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
```



集合类库 sort 实现的是归并排序（Java 7 Timsort？）

特别对于 int 和 long 调用 Arrays.sort 时会使用 quick sort 这会造成最坏情况 $O(n^2)$ 的复杂度



Collections 类有一个 shuffle 方法，用来打乱数组

```java
Collections.shuffle(staff);
```

如果提供的集合没有实现 RandomAccess 接口，就会将元素拷贝至数组，然后打乱数组，最后再复制回列表



ShuffleTest.java

```java
import java.util.*;

public class ShuffleTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i <= 49; i++)
            numbers.add(i);
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
    }
}
```

结果

```text
[6, 7, 19, 20, 22, 42]
```



SortShuffleAPI.java

```java
public class SortShuffleAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <T extends Comparable<? super T>> void sort(List<T> elements)
        // 稳定排序 nlogn 复杂度

        // static void shuffle(List<?> elements)
        // static void shuffle(List<?> elements, Random r)
        // 随机打乱元素，时间复杂度 n*a(n), a(n)是元素平均访问时间

        
        // java.util.List<E>
        // default void sort(Comparator<? super T> comparator)
        // 用给定比较器给列表排序

        
        // java.util.Comparator<T>
        // static <T extends Comparable<? super T>> Comparator<T> reverseOrder()
        // 生成一个比较器，逆置 Comparable 的顺序

        // default Compartor<T> reversed()
        // 逆置这个比较器的顺序
    }
}
```





#### 9.5.2 二分查找

```java
i = Collections.binarySearch(c, element);
i = Collections.binarySearch(c, element, comparator);
```

如果找不到对象时会返回负值，此时可以将元素插入到 $-i-1$ 的位置上保证原列表的有序性

如果提供链表则会自动退化成线性查找



BinarySearchAPI.java

```java
public class BinarySearchAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <T extends Comparable<? super T>> int binarySearch(List<T> elements, T key)
        // static <T> int binarySearch(List<T> elements, T key, Comparator<? super T> c)
    }
}
```



#### 9.5.3 简单算法

为了更好地代码可读性，很多简单的算法都在类库中封装成了可以直接使用的方法



SimpleAlgorithmAPI.java

```java
public class SimpleAlgorithmAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <T extends Comparable<? super T>> T min(Collection<T> elements)
        // static <T extends Comparable<? super T>> T max(Collection<T> elements)
        // static <T> T min(Collection<T> elements, Comparator<? super T> c)
        // static <T> T max(Collection<T> elements, Comparator<? super T> c)
        // 返回集合最小，最大的元素

        // static <T> void copy(List<? super T> to, List<T> from)
        // 复制，目标位置长度至少和原列表一样

        // static <T> void fill(List<? super T> list, T value)
        // list 所有元素都用 value 填充
        
        // static <T> boolean addAll(Collection<? super T> c, T... values)
        // 所有 values 添加到 c 如果 c 改变了返回 true

        // static <T> boolean replaceAll(List<T> list, T oldValue, T newValue)

        // static int indexOfSubList(List<?> list, List<?> s)
        // static int lastIndexOfSubList(List<?> list, List<?> s)
        // 返回第一个或者最后一个等于子列表的索引
        // 没有则返回 -1

        // static void swap(List<?> list, int i, int j)
        // 交换元素

        // static void reverse(List<?> list)

        // static void rotate(List<?> list, int d)
        // 将 i 条目移动到 (i + d) % list.size() 的位置

        // static int frequency(Collection<?> c, Object o)
        // c 中与 o 数量相同的元素个数

        // boolean disjoint(Collection<?> c1, Collection<?> c2)
        // 两个集合没有公共元素 返回 true


        // java.util.Collection<T>
        // default boolean removeIf(Predicate<? super E> filter)
        // 删除所有 filter 返回 true 的元素
        
        // default void replaceAll(UnaryOperator<E> op)
        // 列表中所有元素应用 op 操作
    }
}
```



#### 9.5.4 批操作

很多操作会成批的复制或删除元素

```java
coll1.removeAll(coll2);
coll1.retainAll(coll2);
```

removeAll 会从 coll1 去掉 coll2 中的所有元素

retainAll 则会从 coll1 去掉所有不属于 coll2 的元素



想得到 a b 交集可以

```java
Set<String> res = new HashSet<>(a);
res.retainAll(b);
```



可以通过如下方式从映射中删掉键在某个集合中的所有条目

```java
Map<String, Employee> staffMap = ...
Set<String> terminatedIDs = ...
staffMap.keySet().removeAll(terminatedIDs);
```



#### 9.5.5 集合与数组的转换

数组转化为集合可以靠 asList

```java
String[] values = ...
HashSet<String> staff = new HashSet<>(Arrays.asList(values));
```

集合转化为数组则情况要复杂得多

```java
Object[] values = staff.toArray();
//String[] values = (String[]) staff.toArray();
```

但不能强制类型转换



可以使用变体

```java
String[] values = staff.toArray(new String[0]);
```

或者使用不创建新数组的方法

```java
staff.toArray(new String[staff.size()]);
```

不直接使用 Class 对象创建数组正式因为有的时候不会创建新的数组，而是直接复制到一个已有的数组上



#### 9.5.6 编写自己的算法

自己编写任何以集合作为参数的方法，应该使用接口而不是具体实现

例如应该使用

```java
void fillMenu(JMenu menu, Collection<JMenuItem> items) {
    for(JMenuItem item : items)
        menu.add(item);
}
```



如果编写结合应该编写返回接口而不是返回类的方法。

这样方便日后替换实现

例如，可以复制实现

```java
List<JMenuItem> getAllItems(final JMenu menu) {
    List<JMenuItem> items = new ArrayList();
    for(int i = 0; i < menu.getItemCount(); i++)
        items.add(menu.getItem(i));
    return items;
}
```

也可以使用匿名子类扩展 Abstract 类实现

```java
List<JMenuItem> getAllItems(final JMenu menu) {
    return new AbstractList<>() {
        public JMenuItem get(i) {
            return menu.getItem(i);
        }
        public int size() {
            return menu.getItemCount();
        }
    };
}
```



### 9.6 遗留的集合

```text
List <...... AbstractList <------ Vector <------ Stack
                                   |
               RandomAccess <------

Map <...... Hashtable <------ Properties
```



#### 9.6.1 Hashtable 类

Hashtable 与 hashMap 作用一致且拥有相同的接口，HashTable 的方法也是同步的。如果不需要考虑对遗留代码的兼容应该使用 HashMap 如果考虑并发应该使用 ConcurrentHashMap



#### 9.6.2 枚举

遗留集合使用 Enumeration 接口对元素序列进行遍历

Enumeration 接口有两个方法，hasMoreElements 和 nextElement

与 Iterator 接口的 hasNext 方法和 next 方法十分类似

```java
Enumeration<Employee> e = staff.elements();
while(e.hasMoreElements()) {
    Employee e = e.nextElement();
    ...
}
```

可能有些遗留方法参数是 Enumeration 的

使用 Collections.enumeration 可以产生这样的对象

```java
List<InputStream> streams = ...;
SequenceInputStream in = new SequenceInputStream(Collections.enumeration(stream));
```



EnumerationAPI.java

```java
public class EnumerationAPI {
    public static void main(String[] args) {
        // java.util.Enumeration<E>
        // boolean hasMoreElements()
        // E nextElement()


        // java.util.Hashtable<K, V>
        // Enumeration<K> keys()
        // Enumeration<V> elements()
        

        // java.util.Vector<E>
        // Enumeration<E> elements()
    }
}
```



#### 9.6.3 属性映射

属性映射 property map 是一个类型特殊的映射结构，它有 3 个特性

1.键和值都是字符串

2.表可以保存到一个文件中，也可以从文件中加载

3.使用一个默认的辅助表



PropertiesAPI.java

```java
public class PropertiesAPI {
    public static void main(String[] args) {
        // java.util.Properties 
        // Properties()
        // Properties(Properties defaults)
        // String getProperty(String key)

        // String getProperty(String key, String defaultValue)
        // 没找到 key 对应的属性时 返回 defaultValue

        // void load(InputStream in)
        // 从 in 中加载对象

        // void store(OutputStream out, String commentString)
        // 把属性映射存到 out
    }
}
```



#### 9.6.4 栈

Stack 包含了熟悉的 push 和 pop 方法，但是 Stack 扩展于 Vector 使其拥有了 insert 和 remove 方法这不是很好

StackAPI.java

```java
public class StackAPI {
    public static void main(String[] args) {
        // java.util.Stack<E>
        // E push(E item)

        // E pop()
        // 返回栈顶元素，并弹出

        // E peak()
        // 返回栈顶元素，不弹出
    }
}
```



#### 9.6.5 位集

BitSet 存放一个位集序列，比使用 Boolean 的 ArrayList 要高效



BitSetAPI.java

```java
public class BitSetAPI {
    public static void main(String[] args) {
        // java.util.BitSet
        // BitSet(int initialCapacity)
        // int length()
        // boolean get(int bit)
        // void set(int bit)
        // void clear(int bit)
        // void and(BitSet set)
        // void or(BitSet set)
        // void xor(BitSet set)

        // void andNot(BitSet set)
        // 清除这个位集中对应另一个位集设置的所有位
    }
}
```



Sieve.java

```java
import java.util.*;

public class Sieve {
    public static void main(String[] args) {
        int n=2_000_000;
        long start = System.currentTimeMillis();
        BitSet b = new BitSet(n + 1);
        int count = 0;
        int i;
        for(i = 2; i <= n; i++)
            b.set(i);
        i = 2;
        while(i * i <= n) {
            if(b.get(i)) {
                count++;
                int k = 2 * i;
                while(k <= n) {
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }
        while(i <= n) {
            if(b.get(i)) count++;
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println("primes : " + count);
        System.out.println((end - start) + " ms");
    }
}
```

结果

```text
primes : 148933
33 ms
```

