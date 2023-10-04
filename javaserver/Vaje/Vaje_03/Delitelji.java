class Delitelji {
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);

		System.out.println("Delitelji števila " + n + ":");
		int stevec = 1;
		while (stevec <= n)
		{
			if(n % stevec == 0)
				System.out.println(stevec);
			stevec++;
		}
	}
}