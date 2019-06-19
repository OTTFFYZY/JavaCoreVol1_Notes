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