# 《Java核心技术卷一》 笔记 8_3

2019.07.05 15:40



## 第8章 泛型程序设计

### 8.9 反射和泛型

因为泛型运行时会被类型擦除，因此不会有太多的类型信息

反射也可以得到一些泛型类的信息



#### 8.9.1 泛型 Class 类

现在的 Class 类也是泛型的，如 String.class 实际上是 Class\<String\> 的唯一对象

Class\<T\> 中有一些方法使用了泛型

ClassAPI.java

```java
public class ClassAPI {
    public static void main(String[] args) {
        // java.lang.Class<T>
        // T newInstance()
        // 用无参构造器构建一个新的 T 对象

        // T cast(Object obj)
        // obj 是 null 或可以转化成 T 的类型则返回 obj
        // 否则抛出 BadCastException 异常

        // T[] getEnumConstants()
        // 如果 T 是枚举类型（或是枚举值数组），返回所有值组成的数组

        // Class<? super T> getSuperclass()
        // 返回这个类的超类。如果 T 不是一个类或是 Object 类则返回 null

        // Constructor<T> getConstructor(Class... parameterTypes)
        // Constructor<T> getDeclaredConstructor(Class... parameterTypes)
        // 获得给定参数类型的构造器
    }
}
```



#### 8.9.2 使用 Class\<T\> 参数进行类型匹配

```java
public static <T> Pair<T> makePair(Class<T> c) throws InstantiationException,
    IllegalAccessException {
    return new Pair<>(c.newInstance(), c.newInstance());
}
```

可以通过以下语句

```java
makePair(Employee.class)
```

其中 Employee.class 是 Class\<T\> 的对象，T 将匹配 Employee 于是可以得到 Pair\<Employee\>



#### 8.9.3 虚拟机中的泛型类型信息

对于泛型方法

```java
public static <T extends Comparable<? super T>> T min(T[] a)
```

擦除类型后

```java
public static Comparable min(Comparable[] a)
```

可以使用反射 API 得到：

泛型方法有一个叫做 T 的类型参数

类型参数有一个子类型限定，其自身又是一个泛型类型，这个限定类型有一个通配符参数，通配符参数有一个超类限定

泛型方法有一个泛型数组参数



为了表达泛型类型声明，可以使用 java.lang.reflect 包中的 Type

```text
<<interface>>   <........ Class<T>
    Type        <---- --- <<interface>> TypeVariable
                     |
                      --- <<interface>> WildCardType
                     |
                      --- <<interface>> ParameterizedType
                     |
                      --- <<interface>> GenericArrayType
```



GenericReflectionTest.java

```java
import java.lang.reflect.*;
import java.util.*;

public class GenericReflectionTest {
    public static void main(String[] args) {
        String name = "java.util.Collection";
        try {
            Class<?> cl = Class.forName(name);
            printClass(cl);
            for(Method m : cl.getDeclaredMethods()) {
                printMethod(m);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printClass(Class<?> cl) {
        System.out.print(cl);
        printTypes(cl.getTypeParameters(), "<", ", ", ">", true);
        Type sc = cl.getGenericSuperclass();
        if(sc != null) {
            System.out.print(" extends ");
            printType(sc, false);
        }
        printTypes(cl.getGenericInterfaces(), " implements ", ", ", "", false);
        System.out.println();
    }

    public static void printMethod(Method m) {
        String name = m.getName();
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.print(" ");
        printTypes(m.getTypeParameters(), "<", ", ", "> ", false);
        
        printType(m.getGenericReturnType(), false);
        System.out.print(" " + name + "(");
        printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
        System.out.println(")");
    }

    public static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
        if(pre.equals(" extends ") && Arrays.equals(types, new Type[]{ Object.class }))
            return;
        if(types.length > 0) System.out.print(pre);
        for(int i = 0; i < types.length; i++) {
            if(i > 0) System.out.print(sep);
            printType(types[i], isDefinition);
        }
        if(types.length > 0) System.out.print(suf);
    }

    public static void printType(Type type, boolean isDefinition) {
        if(type instanceof Class) {
            Class<?> t = (Class<?>) type;
            System.out.print(t.getName());
        } else if (type instanceof TypeVariable) {
            TypeVariable<?> t = (TypeVariable<?>) type;
            System.out.print(t.getName());
            if(isDefinition)
                printTypes(t.getBounds(), " extends ", " & ", "", false);
        } else if (type instanceof WildcardType) {
            WildcardType t = (WildcardType) type;
            System.out.print("?");
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " super ", " & ", "", false);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            Type owner = t.getOwnerType();
            if(owner != null) {
                printType(owner, false);
                System.out.print(".");
            }
            printType(t.getRawType(), false);
            printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
        } else if (type instanceof GenericArrayType) {
            GenericArrayType t = (GenericArrayType) type;
            //System.out.print(" ");
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }
}
```

结果

```java
interface java.util.Collection<E> implements java.lang.Iterable<E>
public abstract boolean add(E)
public abstract boolean remove(java.lang.Object)
public abstract boolean equals(java.lang.Object)
public abstract int hashCode()
public abstract void clear()
public abstract boolean contains(java.lang.Object)
public abstract boolean isEmpty()
public abstract java.util.Iterator<E> iterator()
public abstract int size()
public abstract <T> T[] toArray(T[])
public abstract [Ljava.lang.Object; toArray()
public java.util.Spliterator<E> spliterator()
public abstract boolean addAll(java.util.Collection<? extends E>)
public java.util.stream.Stream<E> stream()
public abstract boolean containsAll(java.util.Collection<?>)
public abstract boolean removeAll(java.util.Collection<?>)
public boolean removeIf(java.util.function.Predicate<? super E>)
public abstract boolean retainAll(java.util.Collection<?>)
public java.util.stream.Stream<E> parallelStream()
```



GenericReflectionAPI.java

```java
public class GenericReflectionAPI {
    public static void main(String[] args) {
        // java.lang.Class<T>
        // TypeVariable[] getTypeParameters()
        // 如果类型是泛型类型 获得泛型类型变量，否则返回0长数组

        // Type getGenericSuperclass()
        // 返回泛型的超类类型，如果是 Object 或不是一个类类型（class type） 则返回 null

        // Type[] getGenericInterfaces()
        // 返回这个类型的接口的泛型类型，如果没有实现接口返回 0 长数组

        // java.lang.reflect.Method
        // TypeVarible[] getTypeParameters()
        // 方法的泛型类型变量，非泛型方法返回 0 长数组

        // Type getGenericReturnType()
        // 获得返回类型

        // Type[] getGenericParameterTypes()
        // 方法被声明的泛型参数类型，没有参数则返回 0 长数组

        // java.lang.reflect.TypeVariable
        // String getName()
        // 获得类型变量的名字

        // Type[] getBounds()
        // 获得类型变量的子类限定，无限定则返回 0 长数组

        // java.lang.reflect.WildcardType
        // Type[] getUpperBounds()
        // 得到子类限定
        
        // Type[] getLowerBounds()
        // 得到超类限定

        // java.lang.reflect.ParameterizedType
        // Type getRawType()
        // 获得参数化类型的原始类型

        // Type[] getActualTypeArguments()
        // 获得参数化类型声明时所使用的类型参数

        // Type getOwnerType()
        // 如果是内部类型，则返回其外部类型，顶级类型返回 null

        // java.lang.reflect.GenericArrayType
        // Type getGenericComponentType()
        // 获得声明该数组乐行的泛型组件类型
    }
}
```

