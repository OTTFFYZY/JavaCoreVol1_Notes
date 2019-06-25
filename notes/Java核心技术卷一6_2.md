# 《Java核心技术卷一》 笔记 6_2

2019.06.24 17:28



## 第6章 接口、lambda 表达式与内部类

### 6.4 内部类（inner class）

内部类是定义在另一个类中的类。

使用内部类的动机：

内部类可以访问该类定义所在的作用域中的数据

内部类可以对同一个包中的其他类隐藏起来

当想定义一个回调函数不想编写大量代码时，使用匿名（anonymous）内部类比较便捷



C++ 也有类似的嵌套类。嵌套是类与类的关系，不是对象间的关系。外层类对象并不一定包含内层类的对象。C++ 可以通过内部类避免重名，Java 不需要这样控制重名，但是 Java 需要内部类独特的访问权限控制。相比 C++，Java 的内部类包含了一个对象的隐式引用，引用了实例化该内部类的外围类对象。Java static 的内部类没有这个附加引用与 C++ 嵌套类类似。



#### 6.4.1 使用内部类访问对象状态

InnerClassTest.java

```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, false);
        clock.start();
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}
class TalkingClock {
    private int interval;
    private boolean beep;
    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }
    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }
    
    public class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Time " + new Date());
            if(beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
```

内部类并没有定义 beep 但是可以使用外部的 beep

外层类的隐式引用在内部类并不可见，但是使用时会使用这个引用

内部类的构造器编译器增加了一个添加外层类引用参数的过程

外层对象创建内部类实例时会自动把 this 作为参数传递给内部类构造器



可以将内部类设置为私有，这样只有定义它的类方法可以访问。

常规类只能是包可见或者公有



#### 6.4.2 内部类的特殊语法规则

使用外部类的引用

```java
OuterClass.this
```

例如

```java
TalkingClock.this.beep
```



可以用以下形式更清晰的使用内部类的构造器

```java
outerObject.new InnerClass(parameters)
```

例如

```java
ActionListener listener = this.new TimePrinter();
```

这时 this 是多余 但是也有下面的情况

```java
TalkingClock jabberer = new TalkingClock(1000, true);
TalkingClock.TimePrinter listener = jabberer.new TimePrinter();
```

在外围类作用域外可以通过

```java
OuterClass.InnerClass
```

使用内部类



内部类的所有静态域必须是 final 的，因为希望一个静态域只有一个实例

内部类不能定义 static 方法

InnerClassStaticAndFinal.java

```java
public class InnerClassStaticAndFinal {
    public static void main(String[] args) {
        //Outer.Inner.f();
    }
}

class Outer {
    public static int v=5;
    class Inner {
        //static int vv=5;
        final static int vv=5;
        /*static void f() {
            System.out.println(v);
        }*/
    }
}
```



#### 6.4.3 内部类是否有用、必要和安全

内部类是编译器行为，与虚拟机无关

例如 TalkingClock 中的 TimePrinter 类会被编译成

TalkingClock$TimePrinter.class 中间增加了美元符号间隔



可以使用 javap -private 查看 

```shell
javap -private TalkingClock$TimePrinter
```

结果

```java
Compiled from "InnerClassTest.java"
class TalkingClock {
  private int interval;
  private boolean beep;
  public TalkingClock(int, boolean);
  public void start();
  static boolean access$000(TalkingClock);
}
```

不清楚什么原因，这个结果是外层函数的，但是我在 VSCode 要使用下述命令

```shell
javap -private TalkingClock.TimePrinter
```

结果

```java
Compiled from "InnerClassTest.java"
public class TalkingClock$TimePrinter implements java.awt.event.ActionListener {
  final TalkingClock this$0;
  public TalkingClock$TimePrinter(TalkingClock);
  public void actionPerformed(java.awt.event.ActionEvent);
}
```

可以看到编译器添加的构造函数 和添加 的 this\$0 变量（自己代码不能引用）



自己编写的常规类并不能访问其他类的私有变量，即使传递引用也是不行的

注意到 编译器在 TalkingClock 也添加了一个 access\$000 函数



任何人可以通过调用 access$000 访问私有数据，虽然这不是一个 Java 的合法方法名，但是可以通过 16 进制编辑器穿件用虚拟机指令调用方法的类文件。隐秘的访问方法需要拥有包权限

总之如果内部类访问了私有数据域，就与可能通过附加在外围类所在包中的其他类访问它们

程序员不能无意中获得类访问权限，只有可以构造或修改类文件才可能达到这个目的



由于虚拟机中没有 private 类

因此通过私有构造器完成这个过程

```java
Compiled from "InnerClassTest.java"
class TalkingClock$TimePrinter implements java.awt.event.ActionListener {
  final TalkingClock this$0;
  private TalkingClock$TimePrinter(TalkingClock);
  public void actionPerformed(java.awt.event.ActionEvent);
  TalkingClock$TimePrinter(TalkingClock, TalkingClock$1);
}
```

如上 Talking 类 start 方法中调用构造器会翻译为

```java
new TalkingClock$TimePrinter(this, null)
```



#### 6.4.4 局部内部类

对于上面例子中的内部类只在一个位置使用的，可以使用局部内部类，直接将类定义在方法（代码块内）

```java
public void start() {
    class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Time is " + new Date());
            if(beep) Toolkit.getDefaultToolkit().beep();
        }
    }
    ActionListener listener = new TimePrinter();
    Timer t = new Timer(interval, listener);
    t.start();
}
```



局部类不能使用 public 或 private 限定符，它的作用域被限制在声明它的局部块



局部类可以对外界完全隐藏，如上例中 strat 方法以外都不能访问 TimPrinter 类



#### 6.4.5 由外部方法访问变量

局部类不但能访问外部类，还能访问局部变量

那些局部变量必须事实上是 final 的

```java
public void start(int interval, boolean beep) {
    class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Time is " + new Date());
            if(beep) Toolkit.getDefaultToolkit().beep();
        }
    }
    ActionListener listener = new TimePrinter();
    Timer t = new Timer(interval, listener);
    t.start();
}
```

注意有可能方法先返回了，但是还是需要使用 beep 

因此内部类会保存 beep 的副本 在 Java 8 前 必须为变量增加 final 关键字



InnerClassLocalParam.java

```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class InnerClassLocalParam {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock();
        clock.start(1000, false);
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}
class TalkingClock {
    public void start(int interval, boolean beep) {
        class TimePrinter implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Time " + new Date());
                if(beep) Toolkit.getDefaultToolkit().beep();
            }
        }
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }
}
```

使用命令

```shell
javap -private TalkingClock.1TimePrinter
```

看到

```java
Compiled from "InnerClassLocalParam.java"
class TalkingClock$1TimePrinter implements java.awt.event.ActionListener {
  final boolean val$beep;
  final TalkingClock this$0;
  TalkingClock$1TimePrinter();
  public void actionPerformed(java.awt.event.ActionEvent);
}
```

编译器添加了变量 val$beep



但有的时候希望传入的参数是一个可变值，此时可以通过长度为1的数组实现

（下面用了匿名内部类）

```java
int[] counter = new int[1];
for(int i = 0; i < dates.length; i++) {
    dates[i] = new Date() {
        public int compareTo(Date other) {
            counter[0]++;
            return super.compareTo(other);
        }
    }
}
```

不过这样使用，并发时会导致竞态条件



#### 6.4.6 匿名内部类

对于只创建一个对象的内部类，也不必命名了

语法如下

```java
new SuperType(construction parameters) {
    inner class method and data
}
```

SuperType 是要实现的接口或者要继承的超类



匿名类不能有构造器，构造器将参数传给超类构造器，匿名类实现接口时不能有任何参数



曾经内部类就是用来实现各种事件监听器和回调，但是现在更好的方式是使用 lambda 表达式

```java
public void start(int interval, boolean beep) {
    Timer t = new Timer(interval, event -> {
        System.out.println("Time " + new Date());
        if(beep) Toolkit.getDefaultToolkit().beep();
    });
    t.start();
}
```

AnonymousInnerClassTest.java

```java
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class AnonymousInnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock();
        clock.start(1000, false);
        JOptionPane.showMessageDialog(null, "Quit Program?");
        System.exit(0);
    }
}
class TalkingClock {
    public void start(int interval, boolean beep) {
        ActionListener listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Time " + new Date());
                if(beep) Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer t = new Timer(interval, listener);
        t.start();
    }
}
```

此时可以使用

```shell
javap -private TalkingClock.1
```

查看信息

```java
Compiled from "AnonymousInnerClassTest.java"
class TalkingClock$1 implements java.awt.event.ActionListener {
  final boolean val$beep;
  final TalkingClock this$0;
  TalkingClock$1(TalkingClock, boolean);
  public void actionPerformed(java.awt.event.ActionEvent);
}
```



##### 双括号初始化

双括号初始化（double brace initialization）是一个依靠内部类实现的语法

初始化一个数组列表，但是只需要用一次，此时可以使用匿名数组列表

```java
invite(new ArrayList<String>(){{add("Harry"); add("Tony");}});
```

这里 外层括号表示创建一个匿名类，内层括号表示对象构造块



注意对于匿名类，equals 希望的 if(getClass() != other.getClass()) return false; 会失败



##### 静态方法的类信息

生成日志消息时

```java
System.err.println("xxxx " + getClass());
```

这对于静态方法不会生效，因为要 this.getClass() 而静态方法没有 this

此时需要使用匿名类

```java
new Object(){}.getClass().getEnclosingClass();
```

其中 getEnclosingClass 会得到外围类，也就是包含静态方法的类



#### 6.4.7 静态内部类

有时内部类并不需要引用外围类的对象，为此可以将内部类声明为 static 来取消引用



StaticInnerClassTest.java

```java
public class StaticInnerClassTest {
    public static void main(String[] args) {
        double[] d = new double[20];
        for(int i = 0; i < d.length; i++) 
            d[i] = 100 * Math.random();
        ArrayAlg.Pair p = ArrayAlg.minmax(d);
        System.out.println("min = " + p.getFirst());
        System.out.println("max = " + p.getSecond());
    }
}
class ArrayAlg {
    public static class Pair {
        private double first;
        private double second;
        public Pair(double f, double s) {
            first = f;
            second = s;
        }
        public double getFirst() {
            return first;
        }
        public double getSecond() {
            return second;
        }
    }
    public static Pair minmax(double[] values) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for(double v : values)
        {
            if(min > v) min = v;
            if(max < v) max = v;
        }
        return new Pair(min, max);
    }
}
```



此时可以在 static minmax 中构造 Pair 对象，因为 Pair 不需要外层对象的引用

如果 Pair 不是静态的 则会报错



静态内部类中可以有静态域和方法



声明在接口中的内部类会自动成为 static 和 public 的类

