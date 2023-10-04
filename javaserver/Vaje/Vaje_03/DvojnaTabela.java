class DvojnaTabela {
	public static void main(String[] args) {

		// Naèin 1
		int[][] matrika = new int[3][];
		matrika[0] = new int[1];
		matrika[1] = new int[2];
		matrika[2] = new int[1];

		matrika[0][0] = 9;
		matrika[1][0] = 8;
		matrika[1][1] = 7;
		matrika[2][0] = 6;

		// Naèin 2
		//int[][] matrika = new int[][]{{9}, {8, 7}, {6}};

		System.out.println(matrika[0][0]);
		System.out.println(matrika[1][0]);
		System.out.println(matrika[1][1]);
		System.out.println(matrika[2][0]);
	}
}