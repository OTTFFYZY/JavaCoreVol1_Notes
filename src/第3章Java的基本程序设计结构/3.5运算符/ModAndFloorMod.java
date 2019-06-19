public class ModAndFloorMod {
	public static void main(String[] args) {
		int a, b;
		a = 4; b = 3; f(a,b);
		a = -4; b = 3; f(a,b);
		a = 4; b = -3; f(a,b);
		a = -4; b = -3; f(a,b);
		a = 5; b = 3; f(a,b);
		a = -5; b = 3; f(a,b);
		a = 5; b = -3; f(a,b);
		a = -5; b = -3; f(a,b);
	}

	static void f(int a, int b)
	{
		System.out.printf("%2d %% %2d = %2d", a, b, (a % b));
		System.out.printf(", floorMod(%2d, %2d) = %2d\n", a, b, Math.floorMod(a, b));
	}
}