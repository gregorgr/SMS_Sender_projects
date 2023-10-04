class MatrikaSosednosti {
	public static void main(String[] args) {

		int[][] matSosed = new int[][]{
				{0,1,1,1,1},
				{1,0,1,0,0},
				{1,1,0,0,1},
				{1,0,0,0,1},
				{1,0,1,1,0}
			};

		System.out.println("Matrika sosednosti grafa je: ");
		for(int i=0; i<matSosed.length; i++)
		{
			for(int j=0; j<matSosed[i].length; j++)
			{
				System.out.print(" " + matSosed[i][j]);
			}
			System.out.println("");
		}
	}
}