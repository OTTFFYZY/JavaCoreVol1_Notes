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