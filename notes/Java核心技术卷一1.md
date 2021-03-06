

# 《Java核心技术卷一》 笔记 1

2019.06.06 17:18



## 第1章 Java程序设计概述

### 1.1 Java程序设计平台

Java 不仅仅是一种语言，同时也是一个程序设计平台和一个庞大的库，提供了很多可重用的代码和一个提供安全性、可移植性、自动垃圾回收的执行环境。

### 1.2 Java “白皮书”的关键术语

简单性：Java简化了很多C++的语法。有一个适合嵌入式的Java Micro Edition

面向对象：将重心放在数据对象和对象的接口上的程序设计技术。

​		Java没有多重继承机制而使用接口

分布式：Java有处理HTTP、FTP之类协议的类库可以方便的通过URL访问网络上的对象

健壮性：Java编译时检查比很多语言更细致，

​		Java的指针模型可以消除重写内存和损坏数据的可能

安全性：Java程序运行在沙箱中因此不会影响主系统。Java拥有复杂的安全模型。

体系结构中立：Java编译产生与机器无关的字节码，再通过虚拟机运行。

​		可以通过即时编译的手段把执行最频繁的代码编译称为机器码

可移植性：Java 规范没有依赖具体实现的地方，基本类型大小及相关运算都是明确的

​		除了UI以外的库都有很好的支持平台独立性

解释型：Java解释器可以在任何移植了解释器的机器上执行字节码。

​		链接是一个增量式且轻量级的过程，所以开发是快捷且具有探索性的

高性能：即时编译技术

多线程：支持在多核处理器上的并发程序设计

动态性：Java库中可以自有添加新方法和变量而不影响客户端。

​		Java中找出运行时类型信息十分容易



### 1.3 Java applet 与 Internet

用户从 Internet 下载字节码在自己机器上运行。在网页中运行的Java程序称为applet。

使用applet需要启用Java的Web浏览器执行字节码，

任何时候访问包含applet的网页都会得到程序的最新版本

虚拟机的安全性保证不会受到恶意代码的攻击

applet就像一个可以和用户交互的图片

但现在applet缤购试试很流行



### 1.4 Java发展简史

1991 Patrick Naughton 和 James Gosling 带领 Sun 公司的小组设计了不与体系结构绑定的计算机语言 项目名为 Green。 Gosling 命名语言为 Oak 但是 Oak 已经是计算机语言了 于是起名Java

1992 Green 发布了第一个产品 “*7” 智能远程控制

1994-1995 Java 写的 HotJava 浏览器诞生

1996 Java 1.0 发布

1998 Java 1.2 Java 成为服务器端的首选

2004 Java 5.0 发布 foreach语法 

2009 Oracle 收购 Sun

2011 Java 7 发布 字符串switch 钻石操作符 异常处理改进

2014 Java 8 发布 函数式编程 lambda表达式 流和日期时间库



### 1.5 关于 Java 的常见误解

以下是正确的说法：

Java 不是 HTML 的扩展

使用 XML 和使用 Java 没有关系

Java 功能强大学习成本也很高

Java 并不能适用于所有平台，比如 JavaScript 开发浏览器端，Obj-C 和 Swift 开发 iOS，C++、C# 开发 Windows 应用。Java 主要用于服务端和跨平台客户端应用。

Java 除了作为一个编程语言，同时提供了一套成熟的软件生态

Java 逐渐开源了（2007年起GPL许可）

除了applet以外的大多数Java程序都是在Web浏览器以外独立运行的

同其他执行平台比较，Java是很安全的

JavaScript 与 Java 没多大关系

Internet 设备还不能取代桌面计算机。但现在的终端在向手机和平板电脑转化，其操作系统是Android。Java 对 Android 开发有帮助。

