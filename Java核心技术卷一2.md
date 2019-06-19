

# 《Java核心技术卷一》 笔记 2

2019.06.11 10:18



## 第2章 Java程序设计环境

### 2.1 安装 Java 开发工具包（JDK）

JDK: Java Development Kit

JRE: Java Runtime Environment

Java SE: Standard Edition

Java EE: Enterprise Edition

Java ME: Micro Edition



Java SE 6 对应的内部版本号是 1.6.0

Java SE 7 -> 1.7.0

Java SE 8 -> 1.8.0



并不是所有版本更新都会公开发布



需要把JDK安装的bin目录加到环境变量

可以在终端上输入

```shell
javac -version
```

来判断安装是否成功



### 2.2 使用命令行工具

编写 Welcome.java

```java
public class Welcome {
    public static void main(String[] args) {
        String greeting = "Welcome to Core Java!";
        System.out.println(greeting);
        for(int i = 0; i < greeting.length(); i++)
            System.out.print("=");
        System.out.println();
    }
}
```



```shell
javac Welcome.java
java Welcome
```

编译并运行Java程序

javac 命令会编译出一个 .class 字节码文件

java 命令会用java虚拟机执行字节码文件（其实是指定的类名，所以没有文件后缀）



注意系统CLASSPATH的设置有时会导致找不到类



### 2.3 使用集成开发环境

书中使用Eclipse



### 2.4 运行图形化应用程序

ImageViewer.java

```java
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ImageViewer {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new ImageViewerFrame();
			frame.setTitle("ImageViewer");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}

class ImageViewerFrame extends JFrame {
	private JLabel label;
	private JFileChooser chooser;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;

	public ImageViewerFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		label = new JLabel();
		add(label);

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);

		openItem.addActionListener(event -> {
			int result = chooser.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				String name = chooser.getSelectedFile().getPath();
				label.setIcon(new ImageIcon(name));
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(event -> System.exit(0));
	}
}
```

可以选一个图片并显示出来



### 2.5 构建并运行 applet *

（未完成）

RoadApplet.java

RoadApplet.html



```shell
javac RoadApplet.java
jar cvfm RoadApplet.jar RoadApplet.mf *.class
appletviewer RoadApplet.html
```



注意HTML中的\<applet\>标签

```html
<applet code="RoadApplet.class" archive="RoadApplet.jar"
        width="400" height="400" alt="Traffic jam visualization">
</applet>
```

appletviewer会忽略这个标签以外的内容

浏览器需要支持java并设置好java信任本地applet才可以运行查看

