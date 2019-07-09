# 《Java核心技术卷一》 笔记 9_2

2019.07.08 17:50



## 第9章 集合

### 9.3 映射（Map）

集是一个集合可以快速查找现有的元素。我们需要用键查找值时就不应该使用集，而是应该使用映射了。



#### 9.3.1 基本映射操作

Map 接口 Java 类库提供了两个通用实现 HashMap 和 TreeMap

对应的散列和比较函数将作用于键

```java
Map<String, Employee> staff = new HashMap<>();
Employee harry = new Employee("Harry Hacker");
staff.put("987-98-9996", harry); // 插入新元素
String id = "987-98-9996";
Employee e = staff.get(id); // 取键为 id 的值
// getOrDefault 方法，找不到时会返回后面的默认值
Employee e = staff.getOrDefault(id, new Employee());
```

Map 中键是唯一的，值可以重复

当对已经存在的键使用 put 插入新值时，会替换旧的值

put 会返回之前存的值



可以使用 forEach 提供一个 lambda 表达式依次作用于每一个键值对

```java
scores.forEach((k, v) -> 
    System.out.println("key = " + k + ", value = " + v));
```



MapTest.java

```java
import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));
        System.out.println("org Map : \n" + staff);

        staff.remove("567-24-2546");
        System.out.println("remove \"567-24-2546\" Map : \n" + staff);

        staff.put("456-62-5527", new Employee("Francesca Miller"));
        System.out.println("put (\"456-62-5527\", \"Francesca Miller\") Map : \n" + staff);

        System.out.println("get(\"157-62-7935\") : \n" + staff.get("157-62-7935"));

        System.out.println("getOrDefault(\"123-45-6789\", new Employee(\"Bob Smith\")) : \n" 
            + staff.getOrDefault("123-45-6789", new Employee("Bob Smith")));

        System.out.println("\nforEach : ");
        staff.forEach((k, v) -> System.out.println("key = " + k + ", value = " +v));
    }
}
class Employee {
    private String name;
    Employee(String name) {
        this.name = name;
    }
    public String toString() {
        return "E[" + name + "]";
    }
}
```

结果

```text
org Map : 
{157-62-7935=E[Gary Cooper], 144-25-5464=E[Amy Lee], 456-62-5527=E[Francesca Cruz], 567-24-2546=E[Harry Hacker]}
remove "567-24-2546" Map : 
{157-62-7935=E[Gary Cooper], 144-25-5464=E[Amy Lee], 456-62-5527=E[Francesca Cruz]}
put ("456-62-5527", "Francesca Miller") Map : 
{157-62-7935=E[Gary Cooper], 144-25-5464=E[Amy Lee], 456-62-5527=E[Francesca Miller]}
get("157-62-7935") : 
E[Gary Cooper]
getOrDefault("123-45-6789", new Employee("Bob Smith")) : 
E[Bob Smith]

forEach : 
key = 157-62-7935, value = E[Gary Cooper]
key = 144-25-5464, value = E[Amy Lee]
key = 456-62-5527, value = E[Francesca Miller]
```



MapAPI.java

```java
public class MapAPI {
    public static void main(String[] args) {
        // java.util.Map<K, V>
        // V get(Object key)
        // default V getOrDefault(Object key, V defaultValue)
        
        // V put(K key, V value)
        // void putAll(Map<? extends  K, ? extends V> entries)
        
        // boolean containsKey(Object key)
        // boolean containsValue(Object value)

        // default void forEach(BiConsumer<? super K, ? super V> action)


        // java.util.HashMap<K, V>
        // HashMap()
        // HashMap(int initialCapacity)
        // HashMap(int initialCapacity, float loadFactor)


        // java.util.TreeMap<K, V>
        // TreeMap()
        // TreeMap(Comparator<? super K> c)

        // TreeMap(Map<? extends K, ? extends V> entries)
        // 使用映射构建 TreeMap

        // TreeMap(SortedMap<? extends K, ? extends V> entries)
        // 使用有序映射构建 TreeMap 并使用其比较器
    }
}
```



#### 9.3.2 更新映射项

有时需要做如下更新

```java
counts.put(word, counts.get(word) + 1);
```

此时如果一个 word 第一次出现就会抛出 NullPointerException 异常



简单补救可以使用

```
counts.put(word, counts.getOrDefault(word, 0) + 1);
```

或者使用 putIfAbsent 没有键的时候才会插入值

```java
counts.putIfAbsent(word, 0);
counts.put(word, counts.get(word) + 1);
```



或者可以使用 merge

```java
counts.merge(word, 1, Integer::sum);
```

当不存在 word 时会将 word 和 1 关联，否则将 sum 作用于原先的值与 1，将结果与 word 关联



MapUpdateAPI.java

```java
public class MapUpdateAPI {
    public static void main(String[] args) {
        // java.util.Map<K, V>
        // default V merge(K key, V value, 
        //     BiFunction<? super V, ? super V, ? extends V> remappingFunction)
        // 如果 key 与非 null 值关联，将函数应用到 v 和 value，将 key 与结果关联
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V compute(K key, 
        //     BiFunction<? super K, ? super V, ? extends V> remappingFunction)
        // 将函数作用于 key 和 v 结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V computeIfPresent(K key, 
        //     BiFunction<? super K, ? super V, ? extends V> remappingFunction)
        // 如果 key 和一个非 null 值绑定，将函数作用于 key 和 v 结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default V computeIfAbsent(K key, 
        //     Function<? super K, ? extends V> mappingFunction)
        // 将函数应用到 key 除非 key 已经与非 null 值绑定，结果绑定到 key
        // 如果结果为 null 则删除该键
        // 返回 get(key)

        // default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function)
        // 所有项上应用函数，将键与非 null 结果关联，null 结果删除对应项
    }
}
```



#### 9.3.3 映射视图

映射本身不被认为是集合不过可以从映射得到三种视图（view）：

键集，值集合，键/值对集合

```java
Set<K> keySet()
Collection<V> values()
Set<Map.Entry<K, V>> entrySet()
```

其中 keySet 不是 HashSet 也不是 TreeSet 只是一种实现了 Set 接口的对象



查看所有键

```java
Set<String> keys = map.keySet();
for(String key : keys) {
    ...
}
```



查看所有键值对

```java
for(Map.Entry<String, Employee> entry : staff.entrySet()) {
    String k = entry.getKey();
    Employee v = entry.getValue();
    ...
}
```

不过可以尽可能的使用 forEach 来完成这样的操作



键集的视图上删除元素时，会同时删除键和值

调用 add 则会抛出 UnsupportedOperationException 异常



视图上都可以删除元素，但不能增加元素



MapViewAPI.java

```java
public class MapViewAPI {
    public static void main(String[] args) {
        // java.util.Map<K, V>
        // Set<Map.Entry<K, V>> entrySet()
        // Set<K> keySet()
        // Collection<V> values()


        // java.util.Map.Entry<K, V>
        // K getKey()
        // V getValue()
        // V setValue(V newValue)
    }
}
```



#### 9.3.4 弱散列映射

假设有一个映射中的值对应的键已经不再使用了，此时应该释放空间。但对于一般的 Map，只要 Map 是活动对象，则这些没有使用的桶也是活动的（不会自动回收）。

WeakHashMap 就会回收这些不需要的空间

WeakHashMap 使用弱引用（weak reference）保存键



#### 9.3.5 链接散列集与映射

链接散列集与映射比普通的集或映射多了一个双向链表（域）

这个双向链表记录了访问顺序（不是插入顺序）

get 和 put 方法都会改变这个双向链表的顺序

```java
staff.keySet().iterator()
staff.values().iterator()
```

都会以这个顺序枚举集或者映射中的值



构建这样的映射

```java
LinkedHashMap<K, V>(initialCapacity, loadFactor, true)
```



如果希望自动删除多于100个的元素可以

```java
Map<K, V> cache = new LinkedHashMap<>(128, 0.75F, true) {
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > 100;
    }
}();
```



#### 9.3.6 枚举集与映射

由于枚举集合只有有限个实例，因此内部使用位序列实现

EnumSet 没有公有构造器，可以使用静态工厂方法构建

```java
enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
};
EnumSet<Weekday> always = EnumSet.allOf(Weekday.class);
EnumSet<Weekday> never = EnumSet.noneOf(Weekday.class);
EnumSet<Weekday> workday = EnumSet.range(Weekday.MONDAY, Weekday.FRIDAY);
EnumSet<Weekday> mwf = EnumSet.of(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY);
```



创建一个枚举类型作为键的映射也需要指定枚举类

```java
EnumMap<Weekday, Employee> personInCharge = new EnumMap<>(Weekday.class);
```



在 API 文档中，类型参数是 E extends Enum\<E\>

实际上所有枚举类都扩展于泛型 Enum 类



#### 9.3.7 标识散列映射

IdentityHashMap 散列值不是用 hashCode 计算的，是用 System.identityHashCode 计算的。

这是 Object.hashCode 的方式。两个对象进行比较时使用 == 而不是 equals

不同键的对象即使内容相同，也被视为不同的对象

在实现对象遍历算法（比如串行化）时，可以追踪每个对象的遍历情况



SpecialSetMapAPI.java

```java
public class SpecialSetMapAPI {
    public static void main(String[] args) {
        // java.util.WeakHashMap<K, V>
        // WeakHashMap()
        // WeakHashMap(int initialCapacity)
        // WeakHashMap(int initialCapacity, float loadFactor)


        // java.util.LinkedHashSet<E>
        // LinkedHashSet()
        // LinkedHashSet(int initialCapacity)
        // linkedHashSet(int initialCapacity, float loadFactor)


        // java.util.LinkedHashMap<K, V>
        // LinkedHashMap()
        // LinkedHashMap(int initialCapacity)
        
        // LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
        // accessOrder true 表示访问顺序，false 表示插入顺序
        
        // prodtected boolean removeEldestEntry(Map.Entry<K, V> eldest)
        // eldest 是预期要删除的参数
        // 默认是总返回 false


        // java.util.EnumSet<E extends Enum<E>>
        // static <E extends Enum<E>> EnumSet<E> allOf(Class<E> enumType)
        // static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> enumType)
        // static <E extends Enum<E>> EnumSet<E> range(E from, E to)
        // static <E extends Enum<E>> EnumSet<E> of(E value)
        // static <E extends Enum<E>> EnumSet<E> of(E value, E... values)


        // java.util.EnumMap<K extends Enum<K>>
        // EnumMap(Class<K> keyType)


        // java.util.IdentityHashMap<K, V>
        // IdentityHashMap()
        // IdentityHashMap(int expectedMaxSize)


        // java.lang.System
        // static int identityHashCode(Object obj)
    }
}
```



### 9.4 视图与包装器

视图并没有保存真实的实体对象，但有的时候同样的实体可能属于多个集合，这时视图就非常有用



#### 9.4.1 轻量级集合包装器

可以使用 Arrays 类的 asList 将数组包装成一个 List 视图对象

```java
Card[] cardDeck = new Card[52];
...
List<Card> cardList = Arrays.asList(cardDeck);
```

此时 cardList 能使用 get 和 set 方法，但是不能使用和迭代器相关的 add 和 remove

会抛出 UnsupportedOperationException 异常

也可以使用可变参数

```java
List<String> names = Arrays.asList("Amy", "Bob", "Carl");
```

这个方法会调用

```java
Collections.nCopies(n, anObject)
```

这个方法会返回一个实现了 List 接口的有 n 个 anObject 的视图

```java
List<String> settings = Collections.nCopies(100, "DEFAULT");
```



使用

```java
Collections.singleton(anObject)
```

则会返回一个实现了 Set 接口的对象



对于集合框架中的每一个接口，还有一些生成空集，列表，映射等等

集合的类型可以通过推导得出

```java
Set<String> deepThoughts = Collections.emptySet();
```



#### 9.4.2 子范围

可以为很多集合建立子范围（subrange）视图

可以使用 subList 得到列表子范围（左闭右开）的视图

```java
List group2 = staff.subList(10, 20);
```

对子范围的操作也将作用于原数组



有序集合及映射，可以使用排序顺序而不是元素位置建立子范围

```java
SortedSet<E> subSet(E from, E to)
SortedSet<E> headSet(E to)
SortedSet<E> tailSet(E from)
SortedMap<K, V> subMap(K from, K to)
SortedMap<K, V> headMap(K to)
SortedMap<K, V> tailMap(K from)
```

大于等于 from 小于 to 的所有值



NavigableSet 则赋予了更多操作

```java
NavigableSet<E> subSet(E from, boolean fromInclusive, E to, boolean toInclusive)
NavigableSet<E> headSet(E to, boolean toInclusive)
NavigableSet<E> tailSet(E from, boolean fromInclusive)
```



#### 9.4.3 不可修改的视图

Collections 中也有一些方法生成不可更改视图（unmodifiable views）

这些视图增加了一个运行时检查，如果更改就会抛出异常

```java
Collections.unmodifiableCollection
Collections.unmodifiableList
Collections.unmodifiableSet
Collections.unmodifiableSortedSet
Collections.unmodifiableNavigableSet
Collections.unmodifiableMap
Collections.unmodifiableSortedMap
Collections.unmodifiableNavigableMap
```



不可修改视图并不是集合本身不可更改

可以使用原始引用更改



视图只是包装了接口而不是真正的集合对象



注意 unmodifiableCollection 的 equals 直接比较引用是否相同，hashCode 也采用相同行为



#### 9.4.4 同步视图

多线程访问时，要确保集不会被以外破坏

Collections 的静态方法 synchronizedMap 可以将映射转化成同步映射

```java
Map<String, Employee> map = Collections.synchronizedMap(new HashMap<String, Employee>());
```



#### 9.4.5 受查视图

受查视图用来对泛型类型发生问题时提供调试支持

```java
ArrayList<String> strings = new ArrayList<>();
ArrayList rawList = strings;
rawList.add(new Date());
```

如上直到调用 get 时才会发生错误

可以使用视图

```java
List<String> safeStrings = Collections.checkedList(strings, String.class);
ArrayList rawList = safeStrings;
rawList.add(new Date());
```

此时的 add 语句将会抛出 ClassCastException 异常



#### 9.4.6 关于可选操作的说明

通常视图有只读，无法改变大小，只支持删除不支持插入等局限性

集合和迭代器接口文档中很多方法描述为可选操作

这是与接口思想相悖的，不推荐用户这样设计类库



CollectionsAPI.java

```java
public class CollectionsAPI {
    public static void main(String[] args) {
        // java.util.Collections
        // static <E> Collection unmodifiableCollection(Collection<E> c)
        // static <E> List unmodifiableList(List<E> c)
        // static <E> Set unmodifiableSet(Set<E> c)
        // static <E> SortedSet unmodifiableSortedSet(SortedSet<E> c)
        // static <E> SortedSet unmodifiableNavigableSet(NavigableSet<E> c)
        // static <K, V> Map unmodifiableMap(Map<K, V> c)
        // static <K, V> SortedMap unmodifiableSortedMap(SortedMap<K, V> c)
        // static <K, V> SortedMap unmodifiableNavigableMap(NavigableMap<K, V> c)
        // 构建视图，更改器方法抛出 UnsupportedOperationException

        // static <E> Collection synchronizedCollection(Collection<E> c)
        // static <E> List synchronizedList(List<E> c)
        // static <E> Set synchronizedSet(Set<E> c)
        // static <E> SortedSet synchronizedSortedSet(SortedSet<E> c)
        // static <E> SortedSet synchronizedNavigableSet(NavigableSet<E> c)
        // static <K, V> Map synchronizedMap(Map<K, V> c)
        // static <K, V> SortedMap synchronizedSortedMap(SortedMap<K, V> c)
        // static <K, V> SortedMap synchronizedNavigableMap(NavigableMap<K, V> c)
        // 构建视图，视图的方法同步

        // static <E> Collection checkedCollection(Collection<E> c, Class<E> elementType)
        // static <E> List checkedList(List<E> c, Class<E> elementType)
        // static <E> Set checkedSet(Set<E> c, Class<E> elementType)
        // static <E> SortedSet checkedSortedSet(SortedSet<E> c, Class<E> elementType)
        // static <E> SortedSet checkedNavigableSet(NavigableSet<E> c, Class<E> elementType)
        // static <K, V> Map checkedMap(Map<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <K, V> SortedMap checkedSortedMap(SortedMap<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <K, V> SortedMap checkedNavigableMap(NavigableMap<K, V> c, Class<K> keyType, Class<V> valueType)
        // static <E> Queue<E> checkedQueue(Queue<E> queue, Class<E> elementType)
        // 构建视图，插入错误类型的元素将抛出 ClassCastException

        // static <E> List<E> nCopies(int n, E value)
        // static <E> Set<E> singleton(E value)
        // static <E> List<E> singletonList(E value)
        // static <K, V> Map<K, V> singletonMap(K key, V value)
        
        // static <E> List<E> emptyList()
        // static <E> Set<E> emptySet()
        // static <E> SortedSet<E> emptySortedSet()
        // static <E> NavigableSet<E> emptyNavigableSet()
        // static <K, V> Map<K, V> emptyMap()
        // static <K, V> SortedMap<K, V> emptySortedMap()
        // static <K, V> NavigableMap<K,V> emptyNavigableMap()
        // static <T> Enumeration<T> emptyEnumeration()
        // static <T> Iterator<T> emptyIterator()
        // static <T> ListIterator<T> emptyListIterator()


        // java.util.Arrays
        // static <E> List<E> asList(E... array)


        // java.util.List<E>
        // List<E> subList(int firstIncluded, int firstExcluded)


        // java.util.SortedSet<E>
        // SortedList<E> subSet(E firstIncluded, E firstExcluded)
        // SortedList<E> headSet(E firstExcluded)
        // SortedList<E> tailSet(E firstIncluded)


        // java.util.NavigableSet<E>
        // NavigableSet<E> subSet(E from, boolean fromInclusive, E to, boolean toInclusive)
        // NavigableSet<E> headSet(E to, boolean toInclusive)
        // NavigableSet<E> tailSet(E from, boolean fromInclusive)


        // java.util.SortedMap<K, V>
        // SortedMap<K, V> subMap(K firstIncluded, K firstExcluded)
        // SortedMap<K, V> headMap(K firstExcluded)
        // SortedMap<K, V> tailMap(K firstIncluded)


        // java.util.NavigableMap<K, V>
        // NavigableSortedMap<K, V> subMap(K firstIncluded, K firstExcluded)
        // NavigableSortedMap<K, V> headMap(K firstExcluded)
        // NavigableSortedMap<K, V> tailMap(K firstIncluded)
    }
}
```

