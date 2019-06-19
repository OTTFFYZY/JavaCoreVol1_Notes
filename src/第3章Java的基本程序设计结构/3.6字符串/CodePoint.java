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