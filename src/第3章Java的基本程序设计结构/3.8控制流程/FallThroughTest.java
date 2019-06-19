public class FallThroughTest {
	@SuppressWarnings("fallthrough")
	public static void main(String[] args) {
		int n = 2;
		int m = -1;
		switch (n)
		{
			case 1: 
			    m = 1;
			    break;
			case 2:
			    m = 2;
			//    break;
			case 3:
			    m = 3;
			//    break;
			//default:
			//    m = 4;
		}
		System.out.println(m);
	}
}