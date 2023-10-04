class Fakulteta {
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int fakulteta = 1;
		for(int i=1; i <= n; i++)
		{
			fakulteta = fakulteta * i;
		}
		System.out.println(n + "! = " + fakulteta);
	}
}