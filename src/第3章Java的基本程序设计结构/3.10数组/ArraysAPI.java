import java.util.*;
public class ArraysAPI {
	public static void main(String[] args) {
		int[] arr = new int[10];
		for(int i = 0; i < 10; i++)
			arr[i] = i;
		A[] arrObj = new A[10];
		for(int i = 0; i < 10; i++)
			arrObj[i] = new A(i, 9 - i);

		// static String toString(type[] a)
		System.out.println("arr : " + Arrays.toString(arr));
		System.out.println("arrObj : " + Arrays.toString(arrObj));

		// static type copyOf(type[] a,int length)
		int[] arrB = Arrays.copyOf(arr, arr.length - 5);
		print("copyOf(arr, arr.length - 5)", arrB);
		int[] arrC = Arrays.copyOf(arr, arr.length + 5);
		System.out.println("copyOf(arr, arr.length + 5) : " + Arrays.toString(arrC));

		A[] objArrB = Arrays.copyOf(arrObj, arrObj.length - 5);
		System.out.println("copyOf(arrObj, arrObj.length - 5) : " + Arrays.toString(arrObj));


		// static type copyOfRange(type[] a, int start, int end)
		// 复制 [start, end) 可能 end 会大于 a.length

		// static void sort(type[] a)

		// static int binarySearch(type[] a, type v)
		// static int binarySearch(type[] a, int start, int end, type v)

		// static void fill(type[] a, type v)
		// 将 a 中所有元素填成 v

		// static boolean equals(type[] a, type[] b)
		// 数组大小相同且元素相同则返回 true
	}
	static void print(String s, int[] arr) {
		System.out.print(s + " : ");
		System.out.println(Arrays.toString(arr));
	}
	static void print(String s, Object[] arr) {
		System.out.print(s + " : ");
		System.out.println(Arrays.toString(arr));
	}
}
class A {
	public int a;
	public int b;
	A(int a, int b) {
		this.a = a;
		this.b = b;
	}
	@Override
	public String toString() {
		return "(a, b) = (" + a + ", " + b + ")";
	}
}