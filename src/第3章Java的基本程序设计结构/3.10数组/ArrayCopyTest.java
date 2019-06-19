import java.util.*;
public class ArrayCopyTest {
	public static void main(String[] args) {
		int[] smallPrimes = {2,3,5,7,11,13};

		System.out.println("Copy Reference");

		int[] copyPrimes = smallPrimes;
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));

		copyPrimes[1] = 23;
		System.out.println("copyPrimes[1] = 23;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));

		smallPrimes[3] = 17;
		System.out.println("smallPrimes[3] = 17;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("copyPrimes : " + Arrays.toString(copyPrimes));


		System.out.println("\nReal Copy");

		int[] realCopy = Arrays.copyOf(smallPrimes, smallPrimes.length);
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));

		smallPrimes[4] = 31;
		System.out.println("smallPrimes[4] = 31;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));

		realCopy[5] = 37;
		System.out.println("realCopy[5] = 37;");
		System.out.println("smallPrimes : " + Arrays.toString(smallPrimes));
		System.out.println("realCopy : " + Arrays.toString(realCopy));
	}
}