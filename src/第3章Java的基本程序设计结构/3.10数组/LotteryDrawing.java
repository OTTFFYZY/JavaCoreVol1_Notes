import java.util.*;
public class LotteryDrawing {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

        // generate k elements permutation form [1, n]
		System.out.print("Number to draw: ");
		int k = in.nextInt();

		System.out.print("Highest number: ");
		int n = in.nextInt();

		int[] nums = new int[n];
		for(int i = 0; i < n; i++)
			nums[i] = i + 1;

		int[] res =  new int[k];
		for(int i = 0; i < k; i++) {
			int r = (int)(Math.random() * n);
			res[i] = nums[r];

			// remove nums[r]
			nums[r] = nums[n - 1];
			n--;
		}

		Arrays.sort(res); // 排序
		System.out.println(Arrays.toString(res));
	}
}