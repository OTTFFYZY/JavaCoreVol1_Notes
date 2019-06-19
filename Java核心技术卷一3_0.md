

# 《Java核心技术卷一》 笔记 3_0

2019.06.11 12:38



## 第3章 Java 的基本程序设计结构

### 3.1 一个简单的 Java 应用程序

FirstSample.java

```java
public class FirstSample {
	public static void main(String[] args) {
		System.out.println("We will not use 'Hello, World!'");
	}
}
```

public -> 访问修饰符 access modifier 控制程序其他部分对于该段代码的访问级别

class -> 类关键字

FirstSample -> 类名（一般首字母大写驼峰命名法）

源代码文件名必须与公共类的类名一致（大小写敏感）

每个程序必须拥有一个public static void main

Java 程序正常退出会返回 0 如果想返回其他值需要

```java
System.exit(value);
```



### 3.2 注释

```java
// 单行注释
/*
多
行
注
释
*/

/**
自动生成文档的注释
*/
```

自动生成文档的注释例子

```java
/**
 * This is the first sample program
 * @version 1.01
 * @author Gary Cornell
 */
```



Java中 多行注释不能嵌套



### 3.3 数据类型

Java 是强类型语言，每个变量必须生命一种类型。

Java 中有 8 种基本类型（primitive type），其中 4 种整型，2 种浮点型， 1 种 Unicode 编码的字符单元的字符类型 char ，1 种真值 boolean



#### 3.3.1 整型

整型用于表示没有小数部分的数值，允许是负数。

Java 中同一基本类型的取值范围在不同的机器上一致。

| 类型  | 存储空间（字节） | 取值范围                                               |
| ----- | ---------------- | ------------------------------------------------------ |
| byte  | 1                | -128 ~ 127                                             |
| short | 2                | -32,768 ~ 32,767                                       |
| int   | 4                | -2,147,483,648 ~ 2,147,483,647                         |
| long  | 8                | -9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807 |



对于字面值

二进制数值使用前缀 0b 或者 0B （Java7）

八进制数值使用前缀 0

十六进制数值使用前缀 0x 或者 0X

long 型需要后缀 L

可以在字面值中增加下划线 如  -2_147_483_648



Java 没有无符号的类型



#### 3.3.2 浮点型

Java 中有两种浮点型，用来表示小数

| 类型   | 存储空间（字节） | 取值范围           | 有效数位 |
| ------ | ---------------- | ------------------ | -------- |
| float  | 4                | 约 $\pm$3.403E+38F | 6 ~ 7    |
| double | 8                | 约 $\pm$1.797E+308 | 15       |

float 字面值 需要加后缀 f 或者 F

IEEE 754 溢出出错有 3 个特殊值

正无穷大，负无穷大，NaN（Not a Number）



Double.POSITIVE_INFINITY，Double.NEGATIVE_INFINITY，Double.NaN

注意不应该用任何数值和 NaN 比较 会永远得到 false

要调用 Double.isNaN(x) 方法来判断是不是不是数值



浮点数不能用在不能接受舍入误差的程序中





#### 3.3.3 char 型

char 型原本用来表示单个字符，但是 Unicode 有些字符用一个 char 表示，有些要用两个。

char 型字面值用单引号包裹

可以使用十六进制表示取值范围：‘\u0000’ ~ ‘\uffff’

除了转义字符‘\u’还有一些转义字符

转义字符\u可以出现在字符或者字符串之外（其他转义字符不行）



Unicode 转义序列会在解析之前处理，因此

“ -> \u0022

“\u0022 + \u0022” 不会得到串 “ + ”

而会视作两个空串连接得到空串



注意注释中的 \u

```java
// \u00A0 is a new line
// C:\users
```

第一个会替换成换行因此报错

第二个\u后面不是4个十六进制数因此报错



#### 3.3.4 Unicode 与 char

Unicode 1.0 只有 65536 个值，后来不够用了

码点（Code Point）：与编码表中的某个字符对应的代码值。

Unicode 标准中，码点采用十六进制书写，并加前缀U+。

Unicode 码点分为 17 个代码级别（code plane）。

第一个级别称为基本的多语言级别（basic multilingual plane）码点从 U+0000 到 U+FFFF

其中包含经典的 Unicode 代码

其余 16 个级别的码点从 U+10000 到 U+10FFFF 其中包括一些辅助字符（supplementary character）（我觉得应该翻译成补充字符）



UTF-16 采用不同长度的编码表示所有的 Unicode 码点

基本的多语言级别中，每个字符用 16 位表示，通常称为代码单元（code unit）

辅助字符采用一对连续的代码单元进行编码，这样的编码值落入基本的多语言级别中空闲的 2048 字节内，通常被称为替代区域（surrogate area）（U+D800 ~ U+DBFF用于第一个代码单元，U+DC00 ~ U+DFFF用于第二个代码单元），这样可以迅速知道第一个代码单元是一个字符的编码，还是一个辅助字符的第一部分或第二部分



char 型描述了 UTF-16 编码中的一个代码单元

建议不要在程序中使用 char 而是直接以整体处理 UTF-16 代码单元。

最好直接处理字符串为单位的抽象数据类型。



#### 3.3.5 boolean 型

boolean 型有两个值：true，false

Java 中整型和布尔型之间不能相互转换



### 3.4 变量

Java 变量名以字母开头，由字母和数字组成，大小写敏感，基本没有长度限制。

Java 中数字和字母的范围比其他语言的范围的更大。包含某种语言中表示字母的 Unicode，数字也是某种语言中表示数字的 Unicode。可以使用符号’\_‘，‘\$’

不能出现其他符号和空格

想知道是否是合法字符，可以使用

```java
Character.isJavaIdentifierStart(ch)
Character.isJavaIdentifierPart(ch)
```

尽管‘\$’合法但是用户不应使用

不能使用 Java 保留字



#### 3.4.1 变量初始化

声明一个变量之后，要用赋值语句显式初始化，不要使用未初始化的变量

Java 中变量声明尽量靠近第一次使用的位置

Java 中不区分变量的声明和定义



#### 3.4.2 常量

Java 中用 final 关键字 指示常量

```java
public class Constants {
	public static void main(String[] args) {
		final double CM_PER_INCH = 2.54;
		double paperWidth = 8.5;
		double paperHeight = 11;
		System.out.println("Paper size in centimeters: " 
			+ paperWidth * CM_PER_INCH + " by "
			+ paperHeight * CM_PER_INCH);
	}
}
```

习惯上常量使用全大写字母用 ‘\_’ 连接单词

final 指示的变量只能被赋值一次

如果希望某个常量可以在一个类中多个方法使用（类常量），

可以使用关键字 static final

```java
public class Constants2 {
	public static final double CM_PER_INCH = 2.54;
	public static void main(String[] args) {
		double paperWidth = 8.5;
		double paperHeight = 11;
		System.out.println("Paper size in centimeters: " 
			+ paperWidth * CM_PER_INCH + " by "
			+ paperHeight * CM_PER_INCH);
	}
}
```

声明为 public 则其他类也可以使用这个常量



### 3.5 运算符

Java 中使用算数运算符

```java
+ - * / %
```

表示加，减，乘，除，取模（也叫求余，用于整数）

整数除以 0 会得到异常，小数除以 0 会得到 NaN



Intel 处理器允许计算中间数值使用更高的精度（如使用 80 位寄存器）

Java 可以使用 strictfp 关键字标记方法来限制计算过程中使用严格浮点运算

```java
public static strictfp void main(String[] arg)
```

这个关键字也可以标记类，整个类的方法都将使用严格浮点运算



#### 3.5.1 数学函数与常量

Math 类在包 java.lang 下，这个包会默认 import 

Math 类中有许多静态方法

##### sqrt

```java
double x = 4;
double y = Math.sqrt(x);
```

##### pow

使用 pow 函数求幂

```java
double y = Math.pow(x, a);
```

返回 x 的 a 次幂



##### 取模 % 与 floorMod

自己写了个程序

ModAndFloorMod.java

```java
public class ModAndFloorMod {
	public static void main(String[] args) {
		int a, b;
		a = 4; b = 3; f(a,b);
		a = -4; b = 3; f(a,b);
		a = 4; b = -3; f(a,b);
		a = -4; b = -3; f(a,b);
		a = 5; b = 3; f(a,b);
		a = -5; b = 3; f(a,b);
		a = 5; b = -3; f(a,b);
		a = -5; b = -3; f(a,b);
	}

	static void f(int a, int b)
	{
		System.out.printf("%2d %% %2d = %2d", a, b, (a % b));
		System.out.printf(", floorMod(%2d, %2d) = %2d\n", a, b, Math.floorMod(a, b));
	}
}
```

结果如下

```text
 4 %  3 =  1, floorMod( 4,  3) =  1
-4 %  3 = -1, floorMod(-4,  3) =  2
 4 % -3 =  1, floorMod( 4, -3) = -2
-4 % -3 = -1, floorMod(-4, -3) = -1
 5 %  3 =  2, floorMod( 5,  3) =  2
-5 %  3 = -2, floorMod(-5,  3) =  1
 5 % -3 =  2, floorMod( 5, -3) = -1
-5 % -3 = -2, floorMod(-5, -3) = -2
```

可以看出 % 计算结果的正负性和被除数一致

而 floorMod 方法结果正负性和除数一致



##### 三角函数

```java
Math.sin()
Math.cos()
Math.tan()
Math.atan()
Math.atan2()
```



##### 指数对数函数

```java
Math.exp()
Math.log()
Math.log10()
```



##### $\pi, e$

```
Math.PI
Math.E
```



可以使用如下方式导入，省略 Math

```java
import java.lang.Math.*;
sqrt(PI);
```



#### 3.5.2 数值类型之间的转换

##### 合法转换

| 转换            | 是否保证精度不丢失 |
| --------------- | ------------------ |
| byte -> short   | 是                 |
| short -> int    | 是                 |
| char -> int     | 是                 |
| int -> long     | 是                 |
| int -> float    | 否                 |
| int -> double   | 是                 |
| long -> float   | 否                 |
| long -> double  | 否                 |
| float -> double | 是                 |



##### 数值计算时的类型自动转化

当两个操作数进行数值计算时，遵循如下转换规则

其中有一个 double -> 另一个转化为 double

否则，其中有一个 float -> 另一个转化为 float

否则，其中有一个 long -> 另一个转化为 long

否则，都转化为 int



#### 3.5.3 强制类型转换（cast）

```java
(target_type)var
```

语法就是括号中给出强制类型转换的目标格式，后面紧跟要转化的变量

强制类型转换可能会丢失精度

强制转换浮点数为整数时，直接截断小数部分

需要舍入时，调用 Math.round 方法 round 返回的是 long 类型的结果

```java
double x = 9.997;
int nx = (int) Math.round(x);
```

这种超出范围的强制转换可能会在截断后得到一个完全不同的值



转化 boolean 类型的值和数字类型使用

```java
b ? 1 : 0
```



#### 3.5.4 结合赋值和运算符

```java
x = x + 6;
x += 6;
```

两种写法等价



#### 3.5.5 自增与自减运算符

分为前缀形式和后缀形式

```java
int a = 2 * ++m; // 前缀 发生在表达式之前
int b = 2 * m++; // 后缀 发生在表达式之后
```



#### 3.5.6 关系和 boolean 运算符

比较

```java
=  !=  <  >  <=  >=
```

逻辑与或非（符合短路计算）

```java
&&  ||  !
```

三元

```java
exp1 ? exp2 : exp3
```

exp1 为真 则计算 exp2 返回， 否则计算 exp3 返回



#### 3.5.7 位运算符

```java
& | ^ ~
```

按位 与 或 异或 非

& | 可以用到 boolean 类型上 但是不是短路运算



左右移位运算

```java
<<
>>
```

\>\> 右移位运算符会使用符号位补充高位（C++ 结果不确定）

\>\>\> 三个的右移位会用 0 补充高位



#### 3.5.8 括号与运算符级别

圆括号可以改变计算顺序

同一括号中的计算优先级

结合性给出了同一优先级的结合顺序

| 优先级   | 运算符                                                       | 结合性 |
| -------- | ------------------------------------------------------------ | ------ |
| 最优先   | [].() （方法调用）                                           | ->     |
|          | !, ~, ++, \-\-, +（一元运算）, \-（一元运算）,()（强制类型转换）, new | <-     |
|          | *, / , %                                                     | ->     |
|          | +, \-                                                        | ->     |
|          | <<, >>, >>>                                                  | ->     |
|          | <, >, <=, >=, instanceof                                     | ->     |
|          | ==, !=                                                       | ->     |
|          | &                                                            | ->     |
|          | ^                                                            | ->     |
|          | \|                                                           | ->     |
|          | &&                                                           | ->     |
|          | \|\|                                                         | ->     |
|          | ?:                                                           | <-     |
| 最不优先 | =, +=, \-=, *=, /=, %=, &=, \|=, ^=, <<=, >>=, >>>=          | <-     |

Java 没有逗号运算符，只可以在 for 中使用逗号分隔表达式列表



#### 3.5.9 枚举类型

```java
enum Size {
    SMALL, MEDIUM, LARGE
};
Size s = Size.MEDIUM;
```

详细内容在第5章
