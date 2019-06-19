import java.util.*;
public class IrragularArray {
	public static void main(String[] args) {
		int NMAX = 10;
		int[][] angle = new int[NMAX + 1][];
		for(int i = 0; i <= NMAX; i++) {
			angle[i] = new int[i + 1];
			angle[i][0] = angle[i][i] = 1;
			for(int j = 1; j < i; j++)
				angle[i][j] = angle[i - 1][j - 1] + angle[i - 1][j];
		}
		System.out.println(Arrays.deepToString(angle));

		System.out.println("\n===============");
		for(int i = 0; i <= NMAX; i++) {
			for(int j = 0; j <= i; j++)
				System.out.print(angle[i][j] + " ");
			System.out.println();
		}
	}
}