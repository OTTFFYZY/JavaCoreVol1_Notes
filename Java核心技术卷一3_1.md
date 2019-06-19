

# 《Java核心技术卷一》 笔记 3_1

2019.06.11 17:00



## 第3章 Java 的基本程序设计结构

### 3.6 字符串

Java 字符串就是一个 Unicode 序列。

Java 没有内置的字符串类型，而是在标准库提供了一个预定义类 String， 每个双引号括起来的字符串都是一个 String 类的实例



#### 3.6.1 子串

```java
String s = "abcdef";
s.substring(l); // 截取从 l 开始的子串
s.substring(l, r); // 截取 [l,r) 的子串长度为 r-l
```



#### 3.6.2 拼接

可以使用 + 拼接两个字符串

字符串与非字符串拼接时，非字符串的值会转换成字符串（一切 Java 对象都可以转换成串）



拼接多个字符串并用固定串间隔可以用join

StringJoin.java

```java
public class StringJoin {
	public static void main(String[] args) {
		String all = String.join(" / ", "S", "M", "L", "XL", "XXL");
		print(all);
		all = String.join("", "S", "M", "L", "XL", "XXL");
		print(all);
		all = String.join(" / ");
		print(all);
		all = String.join(" / ", "S");
		print(all);
	}
	static void print(String s) {
		System.out.println("\"" + s + "\"");
	}
}
```

输出

```text
"S / M / L / XL / XXL"
"SMLXLXXL"
""
"S"
```



#### 3.6.3 不可变字符串

String 类没有提供用于修改字符串的方法。

如果将 “Hello” 改为 “Help!“ 则需要先截取再拼接

```java
String a = "Hello";
String b = a.substring(0,3) + "p!";
```



复制字符串变量时，原始变量与复制之后的变量共享相同的字符

Java 的 String 不是字符型数组，行为更像是字符指针



#### 3.6.4 检测字符串是否相等

```java
public class StringEqual {
	public static void main(String[] args) {
		String a = "Hello", b = "hello";
		String c = "hello";
		System.out.println("a == b : " + (a == b));
		System.out.println("b == c : " + (b == c));
		System.out.println("a.equals(b) : " + (a.equals(b)));
		System.out.println("b.equals(c) : " + (b.equals(c)));
		System.out.println("\"hello\".equals(b) : " + ("hello".equals(b)));
		System.out.println("\"hello\" == b : " + ("hello" == b));
		
		System.out.println("b.substring(0, 3) : " + b.substring(0, 3));
		System.out.println("\"hel\" == b.substring(0, 3) : " + ("hel" == b.substring(0, 3)));

		System.out.println("a.equalsIgnoreCase(b) : " + (a.equalsIgnoreCase(b))); // 忽略大小写比较
	}
}
```

结果

```text
a == b : false
b == c : true
a.equals(b) : false
b.equals(c) : true
"hello".equals(b) : true
"hello" == b : true
b.substring(0, 3) : hel
"hel" == b.substring(0, 3) : false
a.equalsIgnoreCase(b) : true
```

检查是否相等要用 equals 方法，

常量串也可以调用 equals 方法。

虚拟机并不总是将相同的字符串共享，因此直接使用 == 判断是否是同一位置是不对的。

事实上只有字符串常量是共享的，+ 或 substring 产生的字符串不是共享的

也可以使用 compareTo 比较

```java
a.compareTo(b) == 0; // 相等返回 true
```

不过使用 equals 更简略清晰



#### 3.6.5 空串与 Null 串

空串 “” 是个长度为 0 的字符串

```java
str.length() == 0;
str.equals("");
```

空串是 Java 对象有长度和内容



null 表示不与任何对象关联，因此检测 null 串 要

```
str != null
```



同时不为 null 且不为空

```java
str != null && str.length() != 0;
```



#### 3.6.6 码点与代码单元

CodePoint.java

```java
public class CodePoint {
	public static void main(String[] args) {
		String s = "abc\ud836\udd46gg";
		System.out.println(s);
		System.out.println(s.length()); // 代码单元长度
		System.out.println(s.codePointCount(0,s.length()));
		// 码点数量

		int i = 5;
		System.out.println(s.charAt(0)); // 第0个代码单元

		i = 4; // 获取第i个码点
		int idx = s.offsetByCodePoints(0, i);
		System.out.println(s.codePointAt(idx));

		// 遍历字符串的每个码点
		System.out.println("==============");
		for(int j = 0; j < s.length(); j++) {
			System.out.print(s.codePointAt(j) + " ");
			int cp = s.codePointAt(j);
			if(Character.isSupplementaryCodePoint(cp))
				j++;
		}

		/*
		// 回退
		i--;
		if(Character.isSurrogate(s.charAt(i))) i--;
		int cp = s.codePointAt();
		*/

		// 生成码点数组
		int[] codePoints = s.codePoints().toArray();

		// 从码点数组生成串
		String ss = new String(codePoints, 0, codePoints.length);
	}
}
```

结果

```text
abc?gg
7
6
a
103
==============
97 98 99 121158 103 103
```

length() 返回的是代码单元数量（可以理解为多少个char）

codePointCount 返回的是区间内码点的数量（真实字符数）

s.charAt(idx) 返回idx代码单元



#### 3.6.7 String API

```java
public class StringAPI {
	public static void main(String[] args) {
		// char charAt(int index) 返回某个位置的代码单元

		// int codePointAt(int index) 返回某个位置开始的码点

		// int offsetByCodePoints(int startIndex, int cpCount)
		// startIndex 开始，位移 cpCount 单位后的码点索引

		// int compareTo(String other) 字典序比较结果 
		// other 小则返回正数，相等返回0，否则返回负数

		// IntStream codePoints() 返回码点的流，配合toArray方法生成数组

		// new String(int[] codePoints, int offset, int count)
		// 从数组 offset 开始 count 个码点生成串

		// boolean equals(Object other) 字符串与other相等返回true

		// boolean equalsIgnoreCase(String other) 忽略大小写比较

		// boolean startsWith(String prefix) 以 prefix 开始

		// boolean endsWith(String suffix) 以 suffix 结束

		// int indexOf(String str)
		// int indexOf(String str, int fromIndex)
		// int indexOf(int cp)
		// int indexOf(int cp,int fromIndex)
		// 返回与串 str 或码点 cp 匹配的最后一个子串开始位置

		// int length() 返回长度

		// int codePointCount(int startIndex, int endIndex)
		// 返回 [startIndex, endIndex) 之间的码点数量，
		// 没有配成对的替代字符计入码点数

		// String replace(CharSequence oldString, CharSequence newString)
		// 返回一个新的字符串，新字符串将旧的所有 oldString 替换为 newString
		// CharSequence 可以是 String 或者 StringBuilder

		// String substring(int beginIndex)
		// String substring(int beginIndex, int endIndex)

		// String toLowerCase()
		// String toUpperCase()

		// String trim() 返回去掉首尾空格的新串

		// String join(CharSequence delimiter, CharSequence... elements)
		// 返回用给定的定界符连接所有的元素
	}
}
```



#### 3.6.8 阅读联机 API 文档

https://docs.oracle.com/javase/8/docs/api/



#### 3.6.9 构建字符串

多次连接较短的字符串可以使用 StringBuilder

TestStringBuilder.java

```java
public class TestStringBuilder {
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		char ch = 'a';
		String str = "bcd";
		builder.append(ch);
		builder.append(str);
		String completedString = builder.toString();
		System.out.println(builder);
		System.out.println(completedString);
	}
}
```

StringBuilder 有线程安全的版本 StringBuffer。

单线程时应使用 StringBuilder 因为其效率更好

两者具有相同的 API 接口

```java
public class StringBuilderAPI {
	public static void main(String[] args) {
		// StringBuilder() 空串构造器

		// int length() 构造器或缓冲器中代码单元数量

		// StringBuilder append(String str)
		// StringBuilder append(char c)
		// StringBuilder appendCodePoint(int cp)
		// 追加并返回 this

		// void setCharAt(int i, char c)
		// 将第 i 个代码单元替换为 c

		// StringBuilder insert(int offset, String str)
		// StringBuilder insert(int offset, char c)
		// offset 位置插入字符串 str 或 c 并返回 this

		// StringBuilder delete(int startIndex, int endIndex)
		// 删除 [startIndex, endIndex) 的代码单元，返回this

		// String toString()
		// 返回与构建器或缓冲器相同的字符串

	}
}
```



### 3.7 输入输出

#### 3.7.1 读取输入

```java
Scanner in = new Scanner(System.in);
```

新建 Scanner 对象 in，并将它绑定到标准输入

InputTest.java

```java
import java.util.*;
public class InputTest {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("What is your name? ");
		String name = in.nextLine();

		System.out.print("How old are you? ");
		int age = in.nextInt();

		System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));
	}
}
```



因为输入是可见的，所以 Scanner 类不适合从控制台读取密码。

可以使用 java.io.Console 类实现这个目的。

```java
import java.io.*;
public class ConsoleTest {
	public static void main(String[] args) {
		Console cons = System.console();
		String username = cons.readLine("User name: ");
		char[] passwd = cons.readPassword("Password: ");

		System.out.println("==========");
		System.out.println(username);
		System.out.println(passwd);
	}
}
```

对密码处理后应该尽快用填充值填充 passwd 数组



ScannerAPI.java

```java
public class ScannerAPI {
	public static void main(String[] args) {
		// Scanner(InputStream in) 输入流创建对象

		// String nextLine() 读下一行内容

		// String next() 读下一个单词，空格间隔
		// int nextInt()
		// double nextDouble()
		
		// boolean hasNext() 是否还有下一个单词
		// boolean hasNextInt() 是否还有下一个整数
		// boolean hasNextDouble()
 	}
}
```



#### 3.7.2 格式化输出

可以使用

```java
System.out.printf("%d", 8);
```

进行 C 风格的格式化输出

可以使用多个参数替换前面的占位符

| 占位符     | 类型               | 例         |
| ---------- | ------------------ | ---------- |
| %d         | 十进制整数         | 233        |
| %x         | 十六进制整数       | 9f         |
| %o         | 八进制整数         | 237        |
| %f         | 定点浮点数         | 15.9       |
| %e         | 指数浮点数         | 1.59e+91   |
| %g         | 通用浮点数         |            |
| %a         | 十六进制浮点数     | 0x1.fccdp3 |
| %s         | 字符串             | Hello      |
| %c         | 字符               | H          |
| %b         | 布尔               | True       |
| %h         | 散列码             | 42628b2    |
| %tx 或 %Tx | 日期时间（过时）   |            |
| %%         | 百分号             |            |
| %n         | 与平台有关行分隔符 |            |

还可以使用各种标志

| 标志            | 目的                              | 例       |
| --------------- | --------------------------------- | -------- |
| +               | 打印正数负数符号                  | +2333.33 |
| 空格            | 补空格                            |          |
| 0               | 补0                               |          |
| \_              | 左对齐                            |          |
| (               | 负数括在括号内                    |          |
| ,               | 添加分组分隔符                    |          |
| #(对于f格式)    | 包含小数点                        |          |
| #(对于x或0格式) | 添加前缀0x或0                     |          |
| $               | 给出被格式化参数的索引（从1开始） |          |
| <               | 格式化前面说明的数值              |          |



可以用 %s 格式化任意对象，对于实现了 Formattable 接口的对象 将调用 formatTo 方法，否则调用toString 方法将对象转化为字符串



可以用 String.format 方法创建格式化字符串而不输出

```java
String s = String.format("Hello, %s!", name);
```



日期时间采用 %t 加其他格式化选项组合输出但是已经过时



格式说明符的语法

```text
% 
|
|-> 参数索引值 -> $ -
|                  |
|<-----------------
|
|<-------
|        |
|-> 标志 -
|
|-> 宽度 -
|        |
|<-------
|
|-> t -> 转换字符 -
|                 |
|-> . -> 精度 -    |
|             |   |
|<------------    |
|                 |
转换字符            |
|                 |
|<----------------
|
```



#### 3.7.3 文件输入与输出

读取文件需要先用 File 对象构建一个 java.util.Scanner 对象

用到了 java.nio.file.Paths

```java
Scanner in = new Scanner(Paths.get("myFile.txt"), "UTF-8");
```

如果文件名用有\\需要双写

```java
Scanner in = new Scanner(Paths.get("C:\\myFile.txt"), "UTF-8");
```

如果不写 “UTF-8” 显式指定编码的话将会使用运行 Java 的机器的默认编码



写入文件 java.io.PrintWriter

```java
PrintWriter out = new PrintWriter("myFile.txt", "UTF-8");
```

如果文件不存在将创建文件



注意直接写

```java
Scanner in = new Scanner("myFile.txt");
```

不会报错，但不会打开文件，而是以字符串 “myFile.txt” 作为数据



##### 文件位置

当命令行

```java
java myIO
```

运行程序时，相对位置就是这个虚拟机启动位置的目录



如果是 IDE，则启动路径由 IDE 决定

可以通过下面方式找到路径位置

```java
String dir = System.getProperty("user.dir");
```



可以考虑使用绝对路径



##### IO 异常

```java
public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(Paths.get("myFile.txt"), "UTF-8");
}
```

由于读入文件可能不存在，输出文件可能不能被创建，此时会发生异常

应当告知编译器可能出现异常 使用 throws IOException



IOTest.java

```java
import java.util.*;
import java.io.*;
import java.nio.file.*;
public class IOTest {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(Paths.get("inFile.txt"), "UTF-8");
		PrintWriter ot = new PrintWriter("outFile.txt", "UTF-8");

		while(in.hasNext())
		{
			String line = in.nextLine();
			System.out.println(line);
			ot.println(line);
			System.out.println(line);
		}

		ot.close(); // 需要关闭 不然文件里没东西
	}
}
```

