# 《Java核心技术卷一》 笔记 7_2

2019.06.26 15:19



## 第7章 异常、断言和日志

### 7.4 使用断言

在一个具有自我保护能力的程序中，断言很常用



#### 7.4.1 断言的概念

假设确信某个属性符合要求，并且代码的执行依赖于这个属性，此时可以使用断言。

当然可以使用抛出异常的方式

```java
if(x < 0) throw new IllegalArgumentException("x < 0");
```

但是抛出异常会一直留在程序里，即使测试完毕也不会自动删除，如果包含了大量的这样类型的检查，程序效率就会受到影响。

断言机制允许测试期间向代码中插入一些检查语句，当代码发布时，插入的检测语句将会被自动的移走。

Java 语言引入了关键字 assert 有两种使用形式

```java
assert 条件;
assert 条件 : 表达式;
```

两种形式都会对条件进行判断，如果是 false，就抛出一个 AssertionError 异常

对于第二种形式表达式将会被传入 AssertionError 构造器转化成消息字符串



C 中 assert 会用宏把条件打印出来，Java 如果需要条件，需要自己增加字符串



#### 7.4.2 启用和禁用断言

默认情况下断言被禁用

可以在运行程序时

```shell
java -enableassertions MyApp
java -ea MyApp
```

两种任意一个启用断言

启用和禁用断言不需要重新编译，因为这是类加载器（class loader）的功能

当断言被禁用时，类加载器会跳过断言代码，因此不会降低程序运行效率



也可以对某个类或某个包使用断言

```shell
java -ea:com.mycompany.mylib -ea:MyClass MyApp
```

或者禁用某些断言

```shell
java -da:MyClass MyApp
java -disableassertions MyApp
```



有些类不是由类加载器加载的，而是由虚拟机直接加载的，对于这些类，使用

-enablesystemassertions 或者 -esa 来开启



#### 7.4.3 使用断言完成参数检查

断言失败是致命的、不可恢复的错误

断言检查只用于开发和测试阶段

不应该使用断言向应用其他部分通告发生了可恢复错误

不应该使用断言作为通告用户的手段



可以看如 sort 的文档

```java
/**
...
@param a the array to be sorted
@throws IllegalArgumentException if fromIndex > toIndex
*/
static void sort(int[] a, int fromIndex, int toIndex)
```

这里如果下标错误就该抛出错误而不是使用断言判断

对于 a 是否为 null

由于文档没有规定行为，那么 a 数组完全可以为 null 并正确运行返回

如果文档改为

```java
/**
...
@param a the array to be sorted (must not be null)
@throws IllegalArgumentException if fromIndex > toIndex
*/
```

此时应该使用断言判断 a 不为 null

计算机科学家称这种约定为前置条件（precondition）

修订 a 不为 null 后方法有了一个前置条件



#### 7.4.4 为文档假设使用断言

很多程序员使用注释来说明假设条件，其实使用断言更好

```java
if(i % 3 == 0) {

} else if(i % 3 == 1) {

} else { // i % 3 == 2

}
```

使用断言的版本

```java
if(i % 3 == 0) {

} else if(i % 3 == 1) {

} else {
    assert i % 3 == 2;
    
}
```

实际上 i 可能是负值



ClassLoaderAPI.java

```java
public class ClassLoaderAPI {
    public static void main(String[] args) {
        // java.lang.ClassLoader
        // void setDefaultAssertionStatus(boolean b)
        
        // void setClassAssertionStatus(String className, boolean b)

        // void setPackageAssertionStatus(String packageName, boolean b)

        // void clearAssertionStatus
        // 移除所有类和包的显示断言状态
    }
}
```



## 7.5 记录日志

日志记录是一种在程序整个生命周期都可以使用的策略性工具

日志 API 就是为了解决调试时需要不断反复插入和删除输出语句的问题。



日志 API 的优点：

可以很容易的取消全部日志记录，或仅仅取消某个个级别的日志

可以简单的禁止日志记录的输出，将日志代码留在程序中开销很小

日志记录可以被定向到不同的处理器，在控制台显示或存储到文件

日志记录器和处理器都可以对记录进行过滤。过滤器可以丢弃无用的记录项

日志记录可以采用不同的方式格式化

应用程序可以使用多个日志记录器，它们使用类似包名的具有层次结构的名字

默认情况下，日志系统的配置由配置文件控制，应用程序可以替换这个配置



#### 7.5.1 基本日志

生成简单日志可以使用全局日志记录器（global logger）

```java
Logger.getGlobal().info("File->Open menu item selected");
```

适当位置调用

```java
Logger.getGlobal().setLevel(Level.OFF);
```

会取消所有日志



#### 7.5.2 高级日志

企业级（industrial-strength）日志

专业的应用不要将所有的日志记录到一个全局的日志记录器中，而是应当自定义日志记录器

```java
private static final Logger myLogger = Logger.getLogger("com.mycompany.myapp");
```

未被任何变量引用的日志记录器可能会被垃圾回收，应当用静态变量储存日志记录器的引用



日志记录器具有层次结构，且父子之间共享部分属性，子记录器会继承父记录器的日志级别

通常有 7 个日志级别：

SEVERE

WARNING

INFO

CONFIG

FINE

FINER

FINEST

默认只记录前三个级别

可以使用 Level.ALL 开启所有级别记录，也可以使用 Level.OFF 关闭所有级别记录

可以使用

```java
logger.warning(message);
logger.fine(message);
logger.log(Level.FINE, message);
```



默认的日志记录将显示日志调用的类和方法名，但是虚拟机可能对执行过程进行了优化，不能得到准确位置，此时可以使用 logp 方法

```java
void logp(Level l,String className, String methodName, String message);
```



一些跟踪执行流的方法

```java
void entering(String className, String methodName);
void entering(String className, String methodName, Object param);
void entering(String className, String methodName, Object[] params);
void exiting(String className, String methodName);
void exiting(String className, String methodName, Object result);
```

会生成 FINER 级别 ENTRY 或 RETURN 开始的日志记录



日志通常是为了记录那些不可预料的异常

可以用以下方法提供日志记录中包含的异常描述信息

```java
void throwing(String className, String methodName, Throwable t);
void log(Level l, String message, Throwable t);
```

throwing 将记录 THROW 开始的 FINE 级别的记录



#### 7.5.3 修改日志管理器配置

（暂缺）