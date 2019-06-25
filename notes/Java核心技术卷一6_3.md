# 《Java核心技术卷一》 笔记 6_3

2019.06.25 14:30



## 第6章 接口、lambda 表达式与内部类

### 6.5 代理（proxy）

利用代理可以在运行时创建一个实现了一组给定接口的新类

这种功能只有在编译时无法确定需要实现哪个接口时才有必要使用



#### 6.5.1 何时使用代理

表示接口的对象，确切类型在编译时无法知道，构造实现接口的类就需要使用 newInstance 方法或使用反射机制找出类的构造器。但是不能实例化接口，需要在运行时定义新类。

可以使用程序生成代码到文件再编译文件后加载，但是这样做速度比较慢。

代理类包含接口所需要的全部方法，Object 类的全部方法



不能在运行时定义方法的新代码，而是提供一个调用处理器（invocation handler）

调用处理器是实现了 InvocationHandler 接口的类。

接口中只有一个方法

```java
Object invoke(Object proxy, Method method, Object[] args);
```

无论何时调用代理对象的方法，调用处理器的 invoke 方法都会被调用，并向其传递 Method 对象和原始的调用参数。调用处理器必须给出处理调用的方式



#### 6.5.2 创建代理对象

想创建一个代理对象，需要使用 Proxy 类的 newProxyInstance 方法

这个方法有 3 个参数

一个类加载器（class loader），目前用 null 默认加载器

一个 Class 对象数组，每个元素都是需要实现的接口

一个调用处理器



使用代理可能的目的：

路由对远程服务器的方法调用

在程序运行期间，将用户接口事件与动作关联起来

为调试，跟踪方法调用



ProxyTest.java

```java
import java.lang.reflect.*;
import java.util.*;

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for(int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
            elements[i] = proxy;
        }

        Integer key = new Random().nextInt(elements.length) + 1;
        int result = Arrays.binarySearch(elements, key);
        if(result >= 0)
            System.out.println(elements[result]);
    }
}

class TraceHandler implements InvocationHandler {
    private Object target;
    public TraceHandler(Object t) {
        target = t;
    }
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        System.out.print(target);
        System.out.print("." + m.getName() + "(");
        if(args != null) {
            for(int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if(i < args.length - 1) System.out.print(", ");
            }
        }
        System.out.println(")");
        return m.invoke(target, args);
    }
}
```

结果

```text
500.compareTo(672)
750.compareTo(672)
625.compareTo(672)
687.compareTo(672)
656.compareTo(672)
671.compareTo(672)
679.compareTo(672)
675.compareTo(672)
673.compareTo(672)
672.compareTo(672)
672.toString()
672
```



#### 6.5.3 代理类的特性

代理类是运行时创建的，一旦被创建就和普通类没有区别了。

所有代理类都扩展于 Proxy 类。一个代理类只有一个实例域，调用处理器，它定义在 Proxy 的超类中。为了履行代理对象的职责，所需要的任何附加数据都必须存储在调用处理器中。



所有的代理类都覆盖了 Object 类的 toString，equals 和 hashCode 方法

这些方法和其他代理方法一样仅仅调用了调用处理器的 invoke。Object 类中的其他方法没有被定义

没有定义代理类的名字，虚拟机将自动生成一个以 $Proxy 开头的类名



对于特定的类加载器和预设的一组接口，只能有一个代理类，多次调用 newProxyInstance 只是得到了同一个类的不同对象。

可以利用 getProxyClass 得到这个类

```java
Class proxyClass = Proxy.getProxyClass(null, interface);
```



代理类一定是 public 和 final 的。如果代理类的所有接口都是 public，代理类就不属于某个特定包

否则所有非公有接口必须属于同一个包，同时代理类也属于这个包

通过调用 Proxy 类中的 isProxyClass 方法检测一个特定的 Class 对象是否代表一个代理类



ProxyAPI.java

```java
public class ProxyAPI {
    public static void main(String[] args) {
        // java.lang.reflect.InvocationHandler
        // Object invoke(Object proxy, Mehtod method, Object[] args)
        // 定义了代理对象调用方法时希望执行的操作
        
        // java.lang.reflect.Proxy
        // static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces)
        // 返回实现指定接口的代理类

        // static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler handler)
        // 构造实现指定接口的代理类的一个实例
        // 所有方法都会调用给定处理器对象的 invoke 方法

        // static boolean isProxyClass(Class<?> cl)
        // cl 是代理类则返回 true
    }
}
```