class IfElse {
	public static void main(String[] args) {

		int n = Integer.valueOf(args[0]);

		if(n % 2 == 0) {
			System.out.println(n + " je sodo �tevilo!");
		}
		else {
			System.out.println(n + " je liho �tevilo!");
		}
	}
}