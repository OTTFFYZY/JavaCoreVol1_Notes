# 《Java核心技术卷一》 笔记 7_1

2019.06.26 10:39



## 第7章 异常、断言和日志

### 7.2 捕获异常

#### 7.2.1 捕获异常

如果某个异常发生时没有进行捕获，那么应用程序就会终止，并在控制台打印错误信息

包括异常类型和堆栈信息。

想要捕获异常需要使用 try/catch 语句块

```java
try {
    ...
} catch (ExceptionType e) {
    handler for this type
}
```

如果 try 语句块内抛出了任何一个在 catch 子句中说明的异常类，那么：

程序跳过 try 中的剩余代码

程序执行 catch 中的其余代码



如果 try 中没有抛出异常，将跳过 catch 部分

如果抛出了 catch 子句中以外的异常类型，那么方法会立即退出



例子

```java
public void read(String filename) {
    try {
        InputStream in = new FileInputStream(filename);
        int b;
        while((b = in.read()) != -1) {
            ...
        }
    } catch (IOException exception) {
        exception.printStackTrace();
    }
}
```

其实对于这个例子更好的选择是抛出异常，将异常传递给调用者，让调用者处理问题

这时要使用 throws



将异常传递给能够胜任的处理器比压制异常要好。

如果编写一个覆盖超类的方法，而这个方法又没有抛出异常，那么这个方法必须捕获方法代码中的每一个受查异常。不允许在子类的 throws 说明符中出现超过超类方法所列出的异常类的范围



Java 中没有对应 C++ catch() 的机制，因为 Java 异常派生于公共超类，不需要这个机制



#### 7.2.2 捕获多个异常

可以为一个 try 语句设置多个 catch

```java
try {
    ...
} catch (...) {
    ...
} catch (...) {
    ...
} catch (...) {
    ...
}
```

如果几类异常处理方式一致可以使用 | 合并类型，同一个 catch 捕获多个异常

```java
try {
    ...
} catch (Exception1 | Exception2 e) {
    ...
}
```

只有异常之间不存在子类关系才需要这个特性

捕获多个异常时，e 变量为隐含 final



#### 7.2.3 再次抛出异常与异常链

catch 子句可以抛出异常，这样做的目的是为了改变异常类型。

有时候调用者不想知道异常的细节，而想知道子系统是否发生异常，此时可以先捕获再抛出

```java
try {
    access data base
} catch (SQLException e) {
    throw new ServletException("database error: " + e.getMessage());
}
```

可以将原始异常设置为新异常的原因

```java
try {
    access data base
} catch (SQLException e) {
    Throwable se = new ServletException("database error");
    se.initCause(e);
    throw se;
}
```

从而最终捕获异常时可以通过

```java
Throwable e = se.getCause();
```

找到原始异常

这样既可以抛出高级异常，又不会丢失原始异常。



可以捕获一个受查异常，转化为运行时异常。

有时只是想记录异常而不改变类型

```java
try {
    access data base
} catch (Exception e) {
    logger.log(level, message, e);
    throw e;
}
```

Java SE 7 以前要注意 最终抛出的异常的类型 和 这个 catch 中的异常类型要兼容

Java SE 7 以后编译器会查看 try 块内的受查异常种类决定 throws 的异常是否合法



#### 7.2.4 finally 子句

当代码发生异常时，经常有些本地资源需要释放。

在代码正常运行和抛出异常时都需要运行某些部分时，可以使用 finally 子句

不管是否捕获异常 finally 中的部分都会运行



```java
try {
    ...
} catch (...) {
    ...
} finally {
    ...
}
```

try 代码块正常运行，try 中代码块运行到结束，运行 finally 中代码块，继续执行 finally 之后的代码

try 代码块抛出异常被 catch 捕获，try 代码块运行终止，运行 catch 中代码块，之后运行 finally 中代码块。如果 catch 子句中没有抛出异常，将运行 finally 之后的代码块

try 代码块抛出错误没有被 catch 捕获，try 代码块运行终止，运行 finally 中代码块



建议将 try/catch，try/finally 解耦合

例如

```java
InputStream in = ...;
try {
    try {
        code that might throw exceptions
    } finally {
        in.close();
    }
} catch (IOException e) {
    show error message
}
```

每个 try 语句块只有一个职责

而且这样也会报告 finally 子句中出现的错误



finally 中有 return 子句时，会覆盖原始的返回结果



有时 finally 在发生异常时关闭资源也发生异常会使原始异常丢失

此时如果还想抛出原始异常需要

```java
InputStream in = ...;
Exception ex = null;
try {
    try {
        code that might throw exceptions
    } catch (Exception e) {
        ex = e;
        throw e;
    }
} finally {
    try {
        in.close();
    } catch (Exception e) {
        if(ex == null) return e;
    }
}
```





#### 7.2.5 带资源的 try 语句

对于以下代码模式

```java
open a resource
try {
    work with the resource
} finally {
    close the resource
}
```

假定资源属于一个实现了 AutoCloseable 接口的类

AutoCloseable 接口有一个方法

```java
void close() throws Exception
```

还有一个 Closeable 接口是 AutoCloseable 的子接口，也包含 close 方法，不过抛出的是 IOException 

带资源的 try 语句（try-with-resources）的最简形式：

```java
try (Resource res = ) {
    work with res
}
```

例如

```java
try (Scanner in = new Scanner(new FileInputStream("/usr/share/dict/words")，"UTF-8")) {
    while(in.hasNext())
        System.out.println(in.next());
}
```

当 try 块退出时会自动调用资源的 close 方法

可以用 ; 间隔多个资源



当 try 和 close 同时抛出异常时，try 异常会被抛出，而 close 异常会“被抑制”，这些异常将被自动捕获并通过 addSuppressed 方法添加到被抛出的异常

如果对这些异常感兴趣，可以通过 getSuppressed 方法查看

带资源的 try 语句也可以有 catch 和 finally，catch 和 finally 将会在关闭资源之后进行



#### 7.2.6 分析堆栈轨迹元素

堆栈轨迹（stack trace）是一个方法调用过程的列表，它包含了程序执行过程中方法调用的特定位置。

可以使用 Throwable 类的 printStackTrace 方法打印访问堆栈轨迹的文本信息

```java
Throwable t = new Throwable();
StringWriter out = new StringWriter();
t.printStackTrace(new PrintWriter(out));
String description = out.toString();
```

可以调用 getStackTrace 方法，得到一个 StackTraceElement 对象的数组

```java
Throwable t = new Throwable();
StackTraceElement[] frames = t.getStackTrace();
for(StackTraceElement frame : frames)
    analyze frame
```

静态的 Thread.getAllStackTraces() 可以产生所有线程的堆栈轨迹

```java
Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
for(Thread t : map.keySet()) {
    StackTraceElement[] frames = map.get(t);
    analyze frames
}
```



StackTraceTest.java

```java
import java.util.*;

public class StackTraceTest {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //System.out.println("Enter n : ");
        //int n = in.nextInt();
        int n = 5;
        factorial(n);
    }
    public static int factorial(int n) {
        System.out.println("factorial(" + n + ") : ");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for(StackTraceElement f : frames)
            System.out.println(f);
        int r;
        if(n <= 1) r = 1;
        else r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }
}
```

结果

```text
factorial(5) : 
StackTraceTest.factorial(StackTraceTest.java:13)
StackTraceTest.main(StackTraceTest.java:9)
factorial(4) : 
StackTraceTest.factorial(StackTraceTest.java:13)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.main(StackTraceTest.java:9)
factorial(3) : 
StackTraceTest.factorial(StackTraceTest.java:13)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.main(StackTraceTest.java:9)
factorial(2) : 
StackTraceTest.factorial(StackTraceTest.java:13)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.main(StackTraceTest.java:9)
factorial(1) : 
StackTraceTest.factorial(StackTraceTest.java:13)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.factorial(StackTraceTest.java:19)
StackTraceTest.main(StackTraceTest.java:9)
return 1
return 2
return 6
return 24
return 120
```



ExceptionAPI.java

```java
public class ExceptionAPI {
    public static void main(String[] args) {
        // java.lang.Throwable
        // Throwable(Throwable cause)
        // Throwable(String message, Throwable cause)
        
        // Throwable initCause(Throwable cause);
        // 设置原因，已经设置原因时会报异常
        // 返回 this

        // Throwable getCause()

        // StackTraceElement[] getStackTrace()

        // void addSuppressed(Throwable t)
        // Throwable[] getSuppressed()


        // java.lang.Exception
        // Exception(Throwable cause)
        // Exception(String message, Throwable cause)


        // java.lang.RuntimeException
        // RuntimeException(Throwable cause)
        // RuntimeException(String message, Throwable cause)


        // java.lang.StackTraceElement
        // String getFileName()
        // 源文件名

        // int getLineNumber()
        // 元素运行时对应的行号

        // String getClassName()
        // 对应类的完全限定名

        // String getMethodName()
        // 方法名，构造器对应 <init>，静态初始化器是 <clinit>
        // 无法区分同名的重载方法

        // boolean isNativeMethod()
        // 元素运行时在一个本地方法中返回 true

        // String toString()
        // 返回包含类名，方法名，文件名和行数的格式化字符串
    }
}
```

