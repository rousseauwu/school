import java.util.Random;

public class Test {
	public static void main(String[] args) {
		Random r = new Random();
		for (int i = 10; i > 0; i--) {
			System.out.println(r.nextInt(10));
		}
	}
}
