

# 《Java核心技术卷一》 笔记 3_2

2019.06.13 17:03



## 第3章 Java 的基本程序设计结构

### 3.8 控制流程

条件，循环，switch



#### 3.8.1 块作用域

块（block）：大括号括起来的若干条 Java 语句

```java
public static void main(String[] args)
{
    int n;
    ...
    {
        int k;
    }
}
```

Java 不能在嵌套的两个块声明同名变量。块决定了块内声明的变量的作用域。



#### 3.8.2 条件语句

```java
if (<condition>) <statement>
```

或者

```java
if (<condition>)
{
    <statement1>
    <statement2>
    ...
}
```

或者

```java
if(<cond1>) {

} else if (<cond2>) {

} else if (<cond3>) {

} else {

}
```



#### 3.8.3 循环

```java
while (<cond>) statement
```

检查 cond 成立就运行 statement 然后继续检查 cond 成立就继续运行 statement 重复，

直到 cond 不成立退出。



```java
do {
     <statements>
} while (<cond>)
```

do while，do 的内容至少会执行一次



#### 3.8.4 确定循环

```java
for(int i = 1; i < n; i++)
```



#### 3.8.5 多重选择：switch 语句

```java
Switch (choice)
{
    case 1:
        ...
        break;
    case 2:
        ...
        break;
    case 3:
        ...
        break;
    default:
}
```

注意 break 语句，如果某些 case 没有 break 会从第一个符合的 case 一直向下执行（包含 default）直到遇到 break

如果想检测 break 可以在编译时增加选项

```shell
javac -Xlint:fallthrough Test.java
```

FallThroughTest.java

```java
public class FallThroughTest {
	@SuppressWarnings("fallthrough")
	public static void main(String[] args) {
		int n = 2;
		int m = -1;
		switch (n)
		{
			case 1: 
			    m = 1;
			    break;
			case 2:
			    m = 2;
			//    break;
			case 3:
			    m = 3;
			//    break;
			//default:
			//    m = 4;
		}
		System.out.println(m);
	}
}
```

default 没有 break 不会有警报，其他的 case 必须有 break

没有 default 情况 可以最后一个 case 可以没有 break



如果某些 switch 希望用这个 fallthrough 特性，则使用注解

```java
@SuppressWarnings("fallthrough")
```



case 后面跟的标签可以是：

char byte short int 常量表达式

枚举常量（使用枚举常量标签不需要都指定枚举名）

字符串字面值



#### 3.8.6 中断控制流程语句

goto 是 Java 保留字，但是并没有使用

break 语句用来跳出循环



##### 带标签的 break

可以将一个标签置于一个语句块之前，当遇到对应的 break 时，会跳转到标签语句块的末尾

```java
Scanner in = new Scanner(System.in)
int n;
read_data:
while() {
    for() {
        n = in,nextInt();
        if(n < 0)
            break read_data;
    }
}
```

这样 如果输入小于 0 的 n，将直接终止内外两层循环



continue 也有不带标签和带标签的版本

带标签的 continue 将跳到标签循环的首部

ContinueTest.java

```java
public class ContinueTest {
	public static void main(String[] args) {
		label1:
		for(int i=0;i<10;i++) {
			System.out.println("out i = " + i);
			for(int j=0;j<5;j++) {
				System.out.print("in loop j = " + j + " ");
				if(i == 1 && j == 2)
				{
					System.out.println("\n!!!");
					continue label1;
				}
			}
			System.out.println();
		}
	}
}
```





### 3.9 大数值

java.math 里面有两个类 BigInteger BigDecimal



```java
BigInteger a = BigInteger.valueOf(100); // 一般值转化为 BigInteger
BigInteger c = a.add(b); // 加法
```



注意 Java 没有算符重载，因此 BigInteger 需要调用函数完成四则运算

BigIntegerAPI.java

```java
public class BigIntegerAPI {
	public static void main(String[] args) {
		// BigInteger add(BigInteger other)
		// BigInteger subtract(BigInteger other)
		// BigInteger multiply(BigInteger other)
		// BigInteger divide(BigInteger other)
		// BigInteger mod(BigInteger other)
		
		// int compareTo(BigInteger other)

		// static BigInteger valueOf(long x) 
	}
}
```

BigDecimalAPI.java

```java
public class BigDecimalAPI {
	public static void main(String[] args) {
		// BigDecimal add(BigDecimal other)
		// BigDecimal subtract(BigDecimal other)
		// BigDecimal multiply(BigDecimal other)
		// BigDecimal divide(BigDecimal other, RoundingMode mode)
		// 计算商要给出舍入模式，常见的四舍五入 RoundingMode.HALF_UP
		
		// int compareTo(BigDecimal other)

		// static BigDecimal valueOf(long x) 
		// static BigDecimal valueOf(long x, int scale)
		// 返回 x / (10 ^ scale)
	}
}
```



### 3.10 数组

```java
int[] a = new int[100];
int a[]; // 一般不用这种写法
```

数组创建后基本类型数值会初始化 0 ，boolean 会初始化 false，对象数组元素都初始化为 null

Java 允许长度为 0 的数组（与 null 不同）



可以用

```java
a.length
```

获取数组 a 的长度

遍历可以

```java
for(int i = 0; i < a.length; i++)
    System.out.println(a[i]);
```

如果想打印数组，也可以直接使用 java.util.Arrays.toString 方法

```java
System.out.println(Arrays.toString(a));
```



#### 3.10.1 for each 循环

```java
for (<var> : <collection>) <statement>
```

其中 var 可以包含定义

collection 可以是数组或者实现了 Iterable 接口的类

```java
int[] a = new int[100];
for (int i : a)
    System.out.println(i);
```



#### 3.10.2 数组初始化以及匿名数组

```java
int[] smallPrimes = {2,3,5,7,11,13};
new int[] {17,19,23,29,31,37}; // 匿名数组
smallPrimes = new int[] {17,19,23,29,31,37}; // 可以赋值
```



#### 3.10.3 数组拷贝

ArrayCopyTest.java

```java
import java.util.*;
public class ArrayCopyTest {
	public static void main(String[] args) {
		int[] smallPrimes = {2,3,5,7,11,13};

		System.out.println("Copy Reference");

		int[] copyPrimes = smallPrimes;
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));

		copyPrimes[1] = 23;
		System.out.println("copyPrimes[1] = 23;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));

		smallPrimes[3] = 17;
		System.out.println("smallPrimes[3] = 17;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));


		System.out.println("\nReal Copy");

		int[] realCopy = Arrays.copyOf(smallPrimes, smallPrimes.length);
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));

		smallPrimes[4] = 31;
		System.out.println("smallPrimes[4] = 31;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));

		realCopy[5] = 37;
		System.out.println("realCopy[5] = 37;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));
	}
}
```

如果直接赋值将会复制引用，改一边所有的都会变化

而如果想要真正复制一份一样的数组 需要 java.util.Arrays.copyOf 方法

如果第二个参数小于原数组长度，会复制原数组前面的元素，大于则会用 0，false，null 填充



Java 的数组名是引用 而不是指针，不能通过 +1 移动



#### 3.10.4 命令行参数

Message.java

```java
public class Message {
	public static void main(String[] args) {
		if(args.length == 0 || args[0].equals("-h"))
			System.out.print("Hello,");
		else if(args[0].equals("-g"))
			System.out.print("Goodbye,");
		else
			System.out.print(args[0] + ",");
		for(int i = 1; i < args.length; i++)
			System.out.print(" "+args[i]);
		System.out.println("!");
	}
}
```

Java 的命令行 args[0] 并不是文件名（与C++不同）而是文件名后的第一个参数



#### 3.10.5 数组排序

```java
Arrays.sort(a);
```

使用了优化的快速排序算法



LotteryDrawing.java

```java
import java.util.*;
public class LotteryDrawing {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

        // generate k elements permutation form [1, n]
		System.out.print("Number to draw: ");
		int k = in.nextInt();

		System.out.print("Highest number: ");
		int n = in.nextInt();

		int[] nums = new int[n];
		for(int i = 0; i < n; i++)
			nums[i] = i + 1;

		int[] res =  new int[k];
		for(int i = 0; i < k; i++) {
			int r = (int)(Math.random() * n);
			res[i] = nums[r];

			// remove nums[r]
			nums[r] = nums[n - 1];
			n--;
		}

		Arrays.sort(res); // 排序
		System.out.println(Arrays.toString(res));
	}
}
```



#### 3.10.6 多维数组

```java
double[][] balances = new double[N][N]; // 二维数组
int[][] magicSquare = {
    {16,3,2,13},
    {5,10,11,8},
    {9,6,7,12},
    {4,15,14,1}
};
for(int[] row : magicSquare)
    for(int i : row)
        System.out.println(i);
Arrays.deepToString(magicSquare);
```



#### 3.10.7 不规则数组

IrragularArray.java

```java
import java.util.*;
public class IrragularArray {
	public static void main(String[] args) {
		int NMAX = 10;
		int[][] angle = new int[NMAX + 1][];
		for(int i = 0; i <= NMAX; i++) {
			angle[i] = new int[i + 1];
			angle[i][0] = angle[i][i] = 1;
			for(int j = 1; j < i; j++)
				angle[i][j] = angle[i - 1][j - 1] + angle[i - 1][j];
		}
		System.out.println(Arrays.deepToString(angle));

		System.out.println("\n===============");
		for(int i = 0; i <= NMAX; i++) {
			for(int j = 0; j <= i; j++)
				System.out.print(angle[i][j] + " ");
			System.out.println();
		}
	}
}
```