class MnozenjeSodih {

    public static void main(String[] args) {

		int zmnozek = 1;

		for(int i = 2; i <= 16; i = i + 2)
		{
			zmnozek = zmnozek * i;
		}
		zmnozek = zmnozek / 2;
		System.out.println("Polovièna vrednost zmnožka je " + zmnozek + ".");
    }
}