# 《Java核心技术卷一》 笔记 7_0

2019.06.25 15:53



## 第7章 异常、断言和日志

程序总是会出现 bug 的，输入也不一定合法，打开文件也不一定存在，这就需要一套处理问题的机制。

为了避免用户数据丢失，应该：

向用户通告错误

保存所有的工作结果

允许用户以妥善形式退出程序



Java 使用一种称为异常处理（exception handling）的机制。

Java 可以通过断言不必要在调试结束后将测试删掉，重新调试时再将代码粘贴回来



程序出错时并不总是能够与用户或中断进行沟通，此时希望记录下出现的问题，以便日后分析，可以使用 Java 日志框架



### 7.1 处理错误

检测和引发错误的代码通常离恢复数据、保存用户操作并正常退出的代码很远

异常处理的任务就是将控制权从错误产生的地方转移给能处理这种错误的处理器



错误与异常原因

1.用户输入错误。不正确使用，意外错误输入

2.设备错误。硬件问题

3.物理限制。磁盘满

4.代码错误。传统方法是返回特殊的错误码，但是有的时候无法使用错误码



Java 方法无法正常完成任务时，可以通过另一个路径退出方法。这种情况，方法会立即退出，不产生返回值，而是抛出（throw）一个封装了错误信息的对象。调用此方法的方法也无法继续执行，而是异常处理机制开始搜索能够处理这种异常状况的异常处理器（Exception Handler）

异常具有自己的语法和特定的继承结构



#### 7.1.1 异常分类

异常都是派生于 Throwable 类的实例。

Java 内置的异常类不能满足需求时，用户可以自己创建异常类

```text
Throwable <- - Error
            |
             - Exception <- - IOException
                           |
                            - RuntimeException
```



Error 描述了 Java 运行时系统内部的错误和资源耗尽错误，应用程序不应该抛出这个类型的对象。

出现这样的错误应当通知用户，并尽力使程序安全的终止，此外就无能为力了。



设计 Java 程序主要关注 Exception 层次

其中应用程序本身的错误应该关注 RuntimeException 应用程序本身没有问题则关注 IOException

派生于 RuntimeException 的异常：

错误的类型转换

数组访问越界

访问 null 指针

不是派生于 RuntimeException 的异常：

试图在文件尾后面读取数据

试图打开一个不存在的文件

试图根据给定字符串查找 Class 对象，但字符串表示的类不存在



Java 语言规范将派生于 Error 和 RuntimeException 类的所有异常称为非受查（unchecked）异常，其他所有异常称为受查（checked）异常。编译器将检查是否为所有受查异常提供了异常处理器。



C++ 中 logic_error 对应 Java 的 RuntimeException 而 runtime_error 对应 其他的异常



#### 7.1.2 声明受查异常

如果遇到了无法处理的情况，那么 Java 方法可以抛出一个异常。

一个方法不仅要告诉编译器将要返回什么值，还要告诉编译器有可能发生什么错误。

方法应该在首部声明所有可能抛出的异常

```java
public FileInputStream(String name) throws FileNotFoundException
```

构造器可能无法根据给定的参数产生 FileInputStream 对象，如果这样的情况发生，就不会初始化一个对象，而是抛出一个异常。



自己编写方法时不必将所有可能抛出的异常都进行声明。

以下 4 种情况应该抛出异常：

调用一个抛出受查异常的方法

程序运行过程中发现错误，并利用 throw 语句抛出一个受查异常

程序出现错误

Java 虚拟机和运行时库出现内部错误



其中前两种情况必须告诉调用方法的程序员可能会抛出异常。如果没有异常处理器捕获这个异常，当前执行的线程就会结束。

应当根据异常规范（exception specification）在首部声明该方法可能抛出的异常

如果一个方法可能抛出多种异常可以用逗号分隔



不需要声明 Java 的内部错误，我们无法控制和处理这些异常。

不应该声明 RuntimeException 继承的非受查异常，这些运行时错误完全在我们的控制之下，应当通过代码尽量避免。



除了声明异常也可以捕获异常，这样异常不会被抛到方法外，也不需要 throws 规范。



Java 的 throws 说明符与 C++ 中的 throw 说明符基本类似。C++ 中 throw 说明符在运行时执行，而不是编译时，C++ 编译器不处理任何异常规范，如果函数抛出的异常没有出现在 throw 列表中，就会调用 unexcepted 函数，这个函数默认处理方式是终止程序执行。C++ 中如果没给出 throw 说明，函数可能抛出任何异常。Java 中如果没有 throws 说明符，方法将不能抛出任何受查异常。



#### 7.1.3 如何抛出异常

如读文件超过了 EOF 标记

抛出异常的语句为

```java
throw new EOFException();
```

例子

```java
String readData(Scanner in) throws EOFException {
    ...
    while(!in.hasNext()) {
        if(n < len)
            throw new EOFException();
    }
    return s;
}
```

EOFException 还有一个含有字符串参数的构造器，可以传递更详细的异常信息

对于一个已经存在的异常类，将其抛出：

找到合适的异常类，创建该类对象，将对象抛出



Java 中只能抛出 Throwable 的子类，C++ 可以抛出任何值



#### 7.1.4 创建异常类

在程序中可能遇到任何标准异常类都没有充分描述的问题，此时需要创建新的异常类

创建的异常类应该继承一个 Exception 类。

习惯上，定义的类应该包含两个构造器，一个默认构造器，一个带有详细描述信息的构造器

超类 Throwable 的 toString 可以打印这些信息

```java
class FileFormatException extends IOException {
    public FileFormatException(){}
    public FileFormatException(String gripe) {
        super(gripe);
    }
}
```

使用上面的异常

```java
String readData(BufferedRead in) throws FileFormatException {
    while(...) {
        if(ch == -1) { // EOF
            if(n < len)
                throw new FileFormatException();
        }
    }
}
```

ThrowableAPI.java

```java
public class ThrowableAPI {
    public static void main(String[] args) {
        // java.lang.Throwable
        // Throwable()
        
        // Throwable(String message) 
        // 附带详细信息
        
        // String getMessage() 
        // 获得 Throwable 对象的详细信息
    }
}
```

