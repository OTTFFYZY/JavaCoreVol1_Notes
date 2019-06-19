public class ContinueTest {
	public static void main(String[] args) {
		label1:
		for(int i=0;i<10;i++) {
			System.out.println("out i = " + i);
			for(int j=0;j<5;j++) {
				System.out.print("in loop j = " + j + " ");
				if(i == 1 && j == 2)
				{
					System.out.println("\n!!!");
					continue label1;
				}
			}
			System.out.println();
		}
	}
}