



# 《Java核心技术卷一》 笔记 5_3

2019.06.21 09:59



## 第5章 继承（Inheritance）

### 5.7 反射（reflective）

反射库（reflection library）：是一个能够动态操纵 Java 代码的程序库

大量使用于 JavaBeans，它是 Java 组件的体系结构

拥有设计或运行中添加新类时，能够快速的应用开发工具动态的查询添加类的能力。



反射（reflective）能够分析类能力的程序:

在运行时分析类的能力

在运行时查看对象

实现通用的数组操作代码

利用 Method 对象（像 C++ 中的函数指针）



#### 5.7.1 Class 类

程序运行期间，Java 运行时系统始终为所有对象维护一个运行时的类型标识。

这个信息跟踪着每个对象所属的类，虚拟机利用运行时类型信息选择相应的方法执行



可以通过专门的 Java 类（Class 类）访问这些信息。

Class 对象表示一个特定类的属性



```java
Class class = obj.getClass();
```

可以返回 Java 对象的类信息

```java
String className = class.getName();
```

将返回类名，如果类在包里，将会是包名加类名的形式



可以使用 forName 得到对应的类信息

```java
String className = "java.util.Random";
Class cl = Class.forName(className);
```

其中 className 必须是类名或者接口名才行，否则会抛出 checked exception 异常

无论何时使用这个方法应该提供一个异常处理器（exception handler）



在大型程序启动时，main 所在的类首先被加载，之后加载需要的类，之后这些需要的类进一步加载需要的类，因此加载时间可能会很长。

替代方案是，main 方法所在的类不显式引用其他类，首先显示一个启动画面，

然后调用 Class.forName 手动加载需要的类



如果 T 是任意的 Java 类型（或 void 关键字），T.class 将得到类信息

```java
Class cl1 = Random.class;
Class cl2 = int.class;
Class cl3 = Double[].class;
```

注意 Class 其实对应的是类型，并不一定是类



Class 实际上是泛型类， Employee.class 是 Class\<Employee\>

大多数实际问题中可以忽略类型参数，使用原始的 Class



历史原因，getName 作用于数组类型会得到一个 方括号开头的奇怪名字



ClassNameTest.java

```java
import java.util.*;

public class ClassNameTest {
    public static void main(String[] args) {
        Class cl1 = Random.class;
        Class cl2 = int.class;
        Class cl3 = Double[].class;
        Class cl4 = Employee.class;
        Class cl5 = Employee[].class;
        System.out.println("Random.class : " + cl1.getName());
        System.out.println("int.class : " + cl2.getName());
        System.out.println("Double[].class : " + cl3.getName());
        System.out.println("Employee.class : " + cl4.getName());
        System.out.println("Employee[].class : " + cl5.getName());

        System.out.println("\n========================\n");

        System.out.println("short.class : " + short.class.getName());
        System.out.println("byte.class : " + byte.class.getName());
        System.out.println("int.class : " + int.class.getName());
        System.out.println("long.class : " + long.class.getName());
        System.out.println("float.class : " + float.class.getName());
        System.out.println("double.class : " + double.class.getName());
        System.out.println("char.class : " + char.class.getName());
        System.out.println("boolean.class : " + boolean.class.getName());
        System.out.println("void.class : " + void.class.getName());

        System.out.println("\n========================\n");

        System.out.println("short[].class : " + short[].class.getName());
        System.out.println("byte[].class : " + byte[].class.getName());
        System.out.println("int[].class : " + int[].class.getName());
        System.out.println("long[].class : " + long[].class.getName());
        System.out.println("float[].class : " + float[].class.getName());
        System.out.println("double[].class : " + double[].class.getName());
        System.out.println("char[].class : " + char[].class.getName());
        System.out.println("boolean[].class : " + boolean[].class.getName());
        //System.out.println("void[].class : " + void[].class.getName());

        System.out.println("\n========================\n");

        System.out.println("Short.class : " + Short.class.getName());
        System.out.println("Byte.class : " + Byte.class.getName());
        System.out.println("Integer.class : " + Integer.class.getName());
        System.out.println("Long.class : " + Long.class.getName());
        System.out.println("Float.class : " + Float.class.getName());
        System.out.println("Double.class : " + Double.class.getName());
        System.out.println("Character.class : " + Character.class.getName());
        System.out.println("Boolean.class : " + Boolean.class.getName());
        System.out.println("Void.class : " + Void.class.getName());

        System.out.println("\n========================\n");

        System.out.println("Short[].class : " + Short[].class.getName());
        System.out.println("Byte[].class : " + Byte[].class.getName());
        System.out.println("Int[].class : " + Integer[].class.getName());
        System.out.println("Long[].class : " + Long[].class.getName());
        System.out.println("Float[].class : " + Float[].class.getName());
        System.out.println("Double[].class : " + Double[].class.getName());
        System.out.println("Char[].class : " + Character[].class.getName());
        System.out.println("Boolean[].class : " + Boolean[].class.getName());
        System.out.println("Void[].class : " + Void[].class.getName());
    }
}

class Employee {

}
```

结果

```java
Random.class : java.util.Random
int.class : int
Double[].class : [Ljava.lang.Double;
Employee.class : Employee
Employee[].class : [LEmployee;

========================

short.class : short
byte.class : byte
int.class : int
long.class : long
float.class : float
double.class : double
char.class : char
boolean.class : boolean
void.class : void

========================

short[].class : [S
byte[].class : [B
int[].class : [I
long[].class : [J
float[].class : [F
double[].class : [D
char[].class : [C
boolean[].class : [Z

========================

Short.class : java.lang.Short
Byte.class : java.lang.Byte
Integer.class : java.lang.Integer
Long.class : java.lang.Long
Float.class : java.lang.Float
Double.class : java.lang.Double
Character.class : java.lang.Character
Boolean.class : java.lang.Boolean
Void.class : java.lang.Void

========================

Short[].class : [Ljava.lang.Short;
Byte[].class : [Ljava.lang.Byte;
Int[].class : [Ljava.lang.Integer;
Long[].class : [Ljava.lang.Long;
Float[].class : [Ljava.lang.Float;
Double[].class : [Ljava.lang.Double;
Char[].class : [Ljava.lang.Character;
Boolean[].class : [Ljava.lang.Boolean;
Void[].class : [Ljava.lang.Void;
```

可以看到基本类型就是采用了类型名

数组类型基本类型采用了 [单字母简写 的形式，不过有的字母有变化 比如 long -> J，boolean -> Z

对象类型的数组 [L 开头加类名



可以使用 newInstance 方法动态创建一个类的实例

```java
e.getClass().newInstance();
```

需要提供构造器参数时要使用 java.lang.reflect.Constructor 类中的 newInstance 方法



newInstance 类似于 C++ 虚拟构造器的用法，但虚拟构造器不是语言特性 需要库支持

Class 类 类似于 C++ 中的 type_info （但 Class 比 type_info 功能更强）

getClass 类似于 C++ 的 typeid



#### 5.7.2 捕获异常

程序运行错误时会抛出异常，此时需要提供一个捕获异常的处理器（handler）

如果没有处理器，程序就会终止，并在控制台打印错误信息



异常分为未检查异常和已检查异常

对于已检查异常，编译器会检查是否提供了处理器，未提供则会报错

未检查异常编译器不会检查是否提供处理器



最简单的处理器的结构

```java
try {
    statements that might throw exceptions
} catch (Exception e) {
    handler action
}
```

示例

```java
try {
    String name = "";
    Class cl = Class.forName(name);
    
} catch (Exception e) {
    e.printStackTrace(); // Exception 超类 Throwable 的方法
                         // 对象和栈的轨迹输出到标准错误流
}
```



#### 5.7.3 利用反射分析类的能力

反射机制可以检查类的结构

java.lang.reflect 包下有 3 个类 Field、Method 和 Constructor 分别用于描述类的域、方法和构造器

这 3 个类都有 getName 方法返回名字

3 个类都有 getModifiers 方法 返回整型数值，不同的位开关代表 public、static 等修饰符的使用情况，可以使用 java.lang.reflect.Modifier 类的静态方法来分析。

Field 类 有 getType 方法



ReflectionTest.java

```java
import java.util.*;
import java.lang.reflect.*;
public class ReflectionTest {
    public static void main(String[] args) {
        String name;
        if(args.length > 0) name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        try {
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print("class " + name);

            if(supercl != null && supercl != Object.class)
                System.out.print(" extends " + supercl.getName());
            System.out.println(" {");

            printConstructors(cl);
            System.out.println();

            printMethods(cl);
            System.out.println();

            printFields(cl);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getConstructors();
        for(Constructor c : constructors) {
            String name = c.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(c.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++) {
                if(j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printMethods(Class cl) {
        Method[] methods = cl.getMethods();
        for(Method m : methods) {
            Class retType = m.getReturnType();
            String name = m.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(m.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            Class[] paramTypes = m.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++) {
                if(j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    public static void printFields(Class cl) {
        Field[] fields = cl.getFields();
        for(Field f : fields) {
            Class type = f.getType();
            String name = f.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(f.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers + " ");
            System.out.println(type.getName() + " " + name);

        }
    }
}
```

命令行输入

```shell
java ReflectionTest ReflectionTest
```

（我看我自己）

```text
public class ReflectionTest {
    public ReflectionTest();

    public static void main([Ljava.lang.String;);
    public static void printMethods(java.lang.Class);
    public static void printFields(java.lang.Class);
    public static void printConstructors(java.lang.Class);
    public final void wait();
    public final void wait(long, int);
    public final native void wait(long);
    public boolean equals(java.lang.Object);
    public java.lang.String toString();
    public native int hashCode();
    public final native java.lang.Class getClass();
    public final native void notify();
    public final native void notifyAll();

}
```

看个别的

```shell
java ReflectionTest java.util.Random
```

结果

```text
public class java.util.Random {
    public java.util.Random();
    public java.util.Random(long);

    public int nextInt();
    public int nextInt(int);
    public double nextDouble();
    public java.util.stream.DoubleStream doubles(double, double);
    public java.util.stream.DoubleStream doubles(long, double, double);
    public java.util.stream.DoubleStream doubles(long);
    public java.util.stream.DoubleStream doubles();
    public java.util.stream.IntStream ints(int, int);
    public java.util.stream.IntStream ints(long, int, int);
    public java.util.stream.IntStream ints();
    public java.util.stream.IntStream ints(long);
    public java.util.stream.LongStream longs(long, long, long);
    public java.util.stream.LongStream longs(long, long);
    public java.util.stream.LongStream longs();
    public java.util.stream.LongStream longs(long);
    public boolean nextBoolean();
    public void nextBytes([B);
    public float nextFloat();
    public synchronized double nextGaussian();
    public long nextLong();
    public synchronized void setSeed(long);
    public final void wait();
    public final void wait(long, int);
    public final native void wait(long);
    public boolean equals(java.lang.Object);
    public java.lang.String toString();
    public native int hashCode();
    public final native java.lang.Class getClass();
    public final native void notify();
    public final native void notifyAll();

}
```



可以拿去研究各种类



#### 5.7.4 在运行时使用反射分析对象

可以用反射机制来查看编码时不清楚的对象域

```java
Employee harry = new Employee("Harry Hacker", 35000, 10, 1, 1989);
Class cl = harry.getClass();
Field f = cl.getDeclaredField("name");
Object v = f.get(harry);
```

不过因为 name 是 private 的因此会返回 IllegalAccessException

反射机制默认权限受限于 Java 访问控制

然而，如果一个程序没有受到安全管理器的控制，就可以覆盖访问控制，需要调用 setAccessible 方法

```java
Employee harry = new Employee("Harry Hacker", 35000, 10, 1, 1989);
Class cl = harry.getClass();
Field f = cl.getDeclaredField("name");
f.setAccessible(true);
Object v = f.get(harry);
```

setAccessible 是 AccessibleObject （Field，Method，Constructor的公共超类）的方法

这个特性是为了调试，持续存储和相似机制提供的



对于 name 我们作为 Object 返回没有问题。

如果是 double 则用 get 会自动装箱，或者可以使用 getDouble 方法



除了 get 也可以 set

```java
f.set(harry, "Alice");
```



ObjectAnalyzer.java

```java
import java.lang.reflect.*;
import java.util.ArrayList;

public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<>();
    public String toString(Object obj) {
        return toString(obj, 4);
    }
    String getIndent(int indent) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < indent; i++)
            sb.append(' ');
        return sb.toString();
    }
    public String toString(Object obj, int indent) {
        if(obj == null) return "null";
        if(visited.contains(obj)) return "...";
        visited.add(obj);
        Class cl = obj.getClass();
        if(cl == String.class) return (String) obj;
        // array
        if(cl.isArray()) {
            String r = cl.getComponentType() + "[]{";
            for(int i = 0; i < Array.getLength(obj); i++) {
                if(i > 0) r += ",";
                r += "\n" + getIndent(indent);
                Object val = Array.get(obj, i);
                if(cl.getComponentType().isPrimitive()) r += val;
                else r += toString(val, indent + 4);
            }
            return r + "\n" + getIndent(indent - 4) + "}";
        }
        // field of this and super class
        String r = cl.getName();
        do {
            r += "[";
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for(Field f : fields) {
                if(!Modifier.isStatic(f.getModifiers())) {
                    if(!r.endsWith("[")) r += ",";
                    r += "\n" + getIndent(indent) + f.getName() + "=";
                    try {
                        Class t = f.getType();
                        Object val = f.get(obj);
                        if(t.isPrimitive()) r += val;
                        else r += toString(val, indent + 4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!r.endsWith("["))
                r += "\n" + getIndent(indent - 4);
            r += "]";
            cl = cl.getSuperclass();
        }while(cl != null);
        return r;
    }
}
```



ObjectAnalyzerTest.java

```java
import java.util.ArrayList;

public class ObjectAnalyzerTest {
    public static void main(String[] args) {
        ArrayList<Integer> squares = new ArrayList<>();
        for(int i = 1; i <= 5; i++)
            squares.add(i * i);
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}
```



结果

```text
java.util.ArrayList[
    elementData=class java.lang.Object[]{
        java.lang.Integer[
            value=1
        ][][],
        java.lang.Integer[
            value=4
        ][][],
        java.lang.Integer[
            value=9
        ][][],
        java.lang.Integer[
            value=16
        ][][],
        java.lang.Integer[
            value=25
        ][][],
        null,
        null,
        null,
        null,
        null
    },
    size=5
][
    modCount=5
][][]
```



#### 5.7.5 使用反射编写泛型数组代码

java.lang.reflect.Array 类允许动态创建数组



```java
public static Object[] badCopyOf(Object[] a, int newLength) {
    Object[] newArray = new Object[newLength];
    System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
    return newArray;
}
```

但是上述程序一旦 返回 Object[] 无法再变回 Employee[]

创建之初就是 Object[] 的数组是无法转换回来的

```java
public static Object goodCopyOf(Object a, int newLength) {
    Class cl = a.getClass();
    if(!cl.isArray()) return null;
    Class componentType = cl.getComponentType();
    int length = Array.getLength(a);
    Object newArray = Array.newInstance(componentType, newLength);
    System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
    return newArray;
}
```

这样的 参数 写为 Object 而不是 Object[] 的方法可以适用于包括基本类型数组的一切数组



CopyOfTest.java

```java
import java.lang.reflect.*;
import java.util.*;

public class CopyOfTest {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"Tom", "Dick", "Harry"};
        b = (String[]) goodCopyOf(b, 10);
        System.out.println(Arrays.toString(b));

        System.out.println("Will generate an Exception");
        b = (String[]) badCopyOf(b, 10);
    }
    public static Object[] badCopyOf(Object[] a, int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }
    public static Object goodCopyOf(Object a, int newLength) {
        Class cl = a.getClass();
        if(!cl.isArray()) return null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
```

结果

```text
[1, 2, 3, 0, 0, 0, 0, 0, 0, 0]
[Tom, Dick, Harry, null, null, null, null, null, null, null]
Will generate an Exception
Exception in thread "main" java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
	at CopyOfTest.main(CopyOfTest.java:15)
```



#### 5.7.6 调用任意方法

Java 设计者认为接口是比方法指针更好的方式。

反射机制可以调用任意方法

微软为自己的 J++ 和 C# 增加了称为委托（delegate）的方法指针类型，这与 Method 类不同。

然而内部类比委托更加有用



与 Field 类 get 方法 访问域相似，Method 类对象有 invoke 方法来调用方法

```java
Object invoke(Object obj, Object... args)
```

其中第一个 obj 是隐式参数，后面的 args 是显式参数

如果没有显式参数，可以第二参数传一个 null（5.0 以后可以不传）

如果是静态方法则第一个参数传 null



得到方法

```java
Method getMethod(String name, Class... parameterTypes)
```



MethodTableTest.java

```java
import java.lang.reflect.*;

public class MethodTableTest {
    public static void main(String[] args) throws Exception {
        Method square = MethodTableTest.class.getMethod("square", double.class);
        Method sqrt = Math.class.getMethod("sqrt", double.class);

        printTable(1, 10, 10, square);
        printTable(1, 10, 10, sqrt);
    }
    public static double square(double x) {
        return x * x;
    }
    public static void printTable(double from, double to, int n, Method f) {
        double dx = (to - from) / (n - 1);

        for(double x = from; x <= to; x += dx) {
            try {
                double y = (Double) f.invoke(null, x);
                System.out.printf("%10.4f | %10.4f\n", x , y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

结果

```text
    1.0000 |     1.0000
    2.0000 |     4.0000
    3.0000 |     9.0000
    4.0000 |    16.0000
    5.0000 |    25.0000
    6.0000 |    36.0000
    7.0000 |    49.0000
    8.0000 |    64.0000
    9.0000 |    81.0000
   10.0000 |   100.0000
    1.0000 |     1.0000
    2.0000 |     1.4142
    3.0000 |     1.7321
    4.0000 |     2.0000
    5.0000 |     2.2361
    6.0000 |     2.4495
    7.0000 |     2.6458
    8.0000 |     2.8284
    9.0000 |     3.0000
   10.0000 |     3.1623
```



Method 可以完成函数指针的所有操作，但提供错误参数时会抛出异常

由于 invoke 参数返回值总是 Object，所以可能需要多次类型转换，可能测试才能发现问题

反射比直接调用方法明显慢

应当必要时才使用 Method 一般使用接口或 lambda 表达式

建议不适用 Method 对象的回调功能

使用接口进行回调会使代码执行速度更快切更易于维护



ClassReflectAPI.java

```java
import java.lang.reflect.*;
public class ClassReflectAPI {
    public static void main(String[] args) {
        // java.lang.Class
        // Field[] getFields()
        // Field[] getDeclaredFields()
        // getFields 方法包含了超类公有域
        
        // Field getField(String name)
        // Field getDeclaredField(String name)

        // Method[] getMethods()
        // Method[] getDeclaredMethods()

        // Constructor[] getConstructors()
        // Constructor[] getDeclaredConstructors()


        // java.lang.reflect.Field
        // java.lang.reflect.Method
        // java.lang.reflect.Constructor
        // Class getDecleringClass()
        // 返回一个用于描述类中定义的构造器、方法或域的 Class

        // Class[] getExceptionTypes() (Method, Constructor)
        // 异常类型的 Class 类数组

        // int getModifiers()

        // String getName()

        // Class[] getParameterTypes() (Method, Constructor)
        // 参数类型

        // Class getReturnType() (Method)
        // 返回类型

        // Object get(Object obj) (Field)
        // 返回 obj 对象中 Field 对象表示的值域

        // void set(Object obj, Object newValue) (Field)
        // 设置 obj 对象中 Field 对象表示的值域的值为 newValue

        // public Object invoke(Object implicitParameter, Object... explicitParamenters)
        // (Method)


        // java.lang.reflect.Modifier
        // static String toString(int modifiers)

        // static boolean isAbstract(int modifiers)
        // static boolean isFinal(int modifiers)
        // static boolean isInterface(int modifiers)
        // static boolean isNative(int modifiers)
        // static boolean isPrivate(int modifiers)
        // static boolean isProtected(int modifiers)
        // static boolean isPublic(int modifiers)
        // static boolean isStatic(int modifiers)
        // static boolean isStrict(int modifiers)
        // static boolean isSynchronized(int modifiers)
        // static boolean isVolatile(int modifiers)


        // java.lang.reflect.AccessibleObject
        // void setAccessible(Boolean flag)

        // boolean isAccessible()

        // static void setAccessible(AccessibleObject[] array, boolean flag)


        // java.lang.reflect.Array
        // static Object get(Object array, int index)
        // static xxx getxxx(Object array, int index)
        // xxx 是基本类型，返回对应位置的内容

        // static void set(Object array, int index, Object newValue)
        // static void setxxx(Object array, int index, xxx newValue)

        // static int getLength(Object array)
        
        // static Object newInstance(Class componentType, int length)
        // static Object newInstance(Class componentType, int[] lengths)
        // 返回给定类型，给定维数的数组
    }
}
```

