

# 《Java核心技术卷一》 笔记 4_2

2019.06.19 12:08



## 第4章 对象与类

### 4.7 包

Java 使用包将类组织起来，方便分开管理不同的代码库

所有的标准 java 包在 java 和 javax 包层次结构中



使用包的原因是为确保类名的唯一性。同名的类放于不同的包下就可以避免冲突



建议使用域名反写作为唯一的包名，不同项目使用子包



从编译器的角度嵌套的包之间没有关系。例如 java.util 包与 java.util.jar 包没有关系



#### 4.7.1 类的导入

可以使用带包名的完整类名

```java
java.time.LocalDate today = java.time.LocalDate.now();
```

可以使用如下语句直接导入 java.time 中的所有类（没有副作用）

```java
import java.time.*;
```

这样使用时可以直接

```java
LocalDate today = LocalDate.now();
```

或者可以使用

```java
import java.time.LocalDate;
```

直接导入特定类



如果两个包有命名冲突时可以特别增加冲突的名字从哪个包中导入

```java
import java.util.*;
import java.sql.*;
import java.util.Date;
```

如果两个类都要用到就必须用完整类名的形式了



##### import 与 C++ include

import 的行为与 C++ include 的行为是不同的。

C++ 编译器不能查看 当前编译文件及头文件中包含的文件 以外的文件，

Java 编译器可以查看其他文件内部，只需给出去哪查看即可

Java 可以总是写完整类名而不写 import 而 C++ 无法避免 import



C++ 与 包机制类似的是 namespace 和 using 声明



#### 4.7.2 静态导入

import 可以导入静态方法或者静态域

```java
import static java.lang.System.*;
```

之后可以使用‘

```java
out.println("Hi");
exit(0);
```

也可以使用

```java
import static java.lang.System.out;
```



#### 4.7.3 将类放入包中

```java
package com.horstmann.corejava;

import ...

public class ClassName {
    
}
```

将类放入包中的基本格式如上



没有 package 的包会被放入默认包（default package）中

默认包是一个没有名字的包



包中的文件应当放于与完整包名匹配的子目录下 如 com.horstmann.corejava 包应当放于

com/horstmann/corejava 目录下

```text
-root
  |
   --- PackageTest.java
   --- PackageTest.class
  |
   --- com/
        |
         --- horstmann/
              |
               --- corejava/
                    |
                     --- Employee.java
                     --- Employee.class
```

编译并运行

```shell
javac PackageTest.java
java PackageTest
```



更复杂的情况

```text
-root
  |
   --- com/
        |
         --- horstmann/
        |     |
        |      --- corejava/
        |           |
        |            --- Employee.java
        |            --- Employee.class
        |
         --- mycompany/
              |
               --- PayrollApp.java
               --- PayrollApp.class
```

此时要在根目录完成编译和运行

```shell
javac com/mycompany/PayrollApp.java
java com/mycompany/PayrollApp
```



编译器在编译源文件的时候不检查目录结构

如果包与目录不匹配最终虚拟机不能找到类



需要设置环境变量 CLASSPATH 的值为

```java
.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
```

没有第一个 .; 可能不会到当前目录下寻找包

文件目录

```text
root
 |
  --- PackageTest.java
 |
  --- com/
       |
        --- mypackage/
             |
              --- Employee.java
```

PackageTest.java

```java
import com.mypackage.*;
import static java.lang.System.*;

public class PackageTest {
    public static void main(String[] args) {
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);

        harry.raiseSalary(5);

        out.println("name = " + harry.getName() + ", salary = " + harry.getSalary());
    }
}
```

Employee.java

```java
package com.mypackage;

import java.time.*;

public class Employee {
	private String name;
	private double salary;
	private LocalDate hireDay;
	public Employee(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
		hireDay = LocalDate.of(year, month, day);
	}
	public String getName() {
		return name;
	}
	public double getSalary() {
		return salary;
	}
	public LocalDate getHireDay() {
		return hireDay;
	}
	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}
}
```



#### 4.7.4 包作用域

没有指定 private 和 public 的方法或域，可以被同一包内方法访问

 包不是一个封闭的实体，可以人为向包中添加方法，因此应该尽量让包中的变量是 private 的



JDK 1.2 之后类加载器禁止加载用户自定义的 java. 开头的包



更好的手段是通过包密封（package sealing）机制解决各种包混杂的问题

密封的包不能再添加类，见第 9 章



### 4.8 类路径

类可以存储在文件系统子目录中。类路径需要与包名匹配

类也可以存储在 JAR （Java 归档）中



环境变量 CLASSPATH 用来设置寻找类和 jar 包的位置

可以在寻找 jar 的目录使用通配符



如果匹配了一个以上的类，就会返回错误



#### 4.8.1 设置类路径

```shell
java -classpath c:\classdir;.;c:\archives\archive.jar MyProg
```

或者在命令行（Linux）

```shell
export CLASSPATH=
```

（Windows）

```shell
set CLASSPATH=
```

将在退出 shell 之前都有效



### 4.9 文档注释

JDK 包含一个由源文件生成 HTML 文档的工具 javadoc



#### 4.9.1 注释的插入

javadoc 实体应用（utility）将从下面几个特性抽取信息

包

公有类与接口

公有的和受保护的构造器及方法

公有的和受保护的域



注释应当放于所描述的特性前并以

```java
/**
```

开始，并以

```java
*/
```

结束

文档注释在标记之后紧跟自由格式文本（free-form text）。标记由 @ 开始

如 @author @param

自由格式文本第一句应该是概要性的句子，将用来生成概要页。



可以使用 HTML



#### 4.9.2 类注释

类注释放在 import 之后 类定义之前

大多数 IDE 会在每行补充 * 开头 不过这不是必须的



#### 4.9.3 方法注释

放在方法前，可以使用

@param 变量 描述，方法所有变量描述要放在一起

@return 描述

@throws 异常类描述



#### 4.9.4 域注释

通常只对公有域（静态常量）建立文档



#### 4.9.5 通用注释

@author 作者，可以有多个

@version 文本

@since 文本

@deprecated 文本，过时写法不再使用，可以通过@see 或 @link 跳转到其他新的方法描述

@see 引用 引用部分的格式是 类名#方法，或者在当前类时直接给出方法

也可以使用超链接

@link 可以在任意位置增加链接，用法同 @see



#### 4.9.6 包与描述注释

包注释需要在单独的文件提供

package.html

或者一个

package-info.java 的文件

该文件只应该包含 package 语句和 /\*\*          \*/ 注释部分



可以对所有源文件提供注释在 overview.html 下



#### 4.9.7 注释的提取

假设 html 放于 doc 目录下

切换到想生成文档的源文件目录



如果是一个包，执行

```shell
javadoc -d doc nameOfPackage
```

多个包

```shell
javadoc -d doc package1 package2 ...
```

文件在默认包中则

```shell
javadoc -d doc *.java
```

不建议省略 -d doc 不然 html 会生成在当前文件夹下



还有一些其他的 javadoc 命令可用



### 4.10 类设计技巧

1 保证数据私有

2 要对数据初始化

3 不要在类中使用过多的基本类型（用其他类代替）

4 不是所有域都需要独立的访问器和更改器

5 将职责过多的类分解

6 类名和方法名能够体现职责

​    采用名词或形容词修饰的名词，动名词修饰的名词，访问器 get 开头，修改器 set 开头

7 优先使用不可变类

​    类是可变的，多线程会发生并发更改，从而线程不安全 