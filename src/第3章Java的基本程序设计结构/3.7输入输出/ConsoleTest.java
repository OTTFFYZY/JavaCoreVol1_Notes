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