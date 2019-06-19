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