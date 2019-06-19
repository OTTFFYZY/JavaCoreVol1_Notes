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