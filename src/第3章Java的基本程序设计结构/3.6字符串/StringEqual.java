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

		System.out.println("a.equalsIgnoreCase(b) : " + (a.equalsIgnoreCase(b)));
	}
}