/*
DN011_Unicode:
Izpisi tabelo malih crk slovenske abecede v treh stolpcih. 
Pri tem za crke uporabi sifre Unicode standarda.
*/

class DN011_Unicode {
	public static void main(String[] args) {
		
		// Definicija spremenljivk in prireditev vrednosti
		char a = '\u0061', b = '\u0062', c = '\u0063';
		char ch = '\u010d', d = '\u0064', e = '\u0065';
		char f = '\u0066', g = '\u0067', h = '\u0068';
		char i = '\u0069', j = '\u006a', k = '\u006b';
		char l = '\u006c', m = '\u006d', n = '\u006e';
		char o = '\u006f', p = '\u0070', r = '\u0072';
		char s = '\u0073', sh = '\u0161', t = '\u0074';
		char u = '\u0075', v = '\u0076', z = '\u007a';
		char zh = '\u017e';
		
		// Izpis crk
		System.out.println("Crke slovenske abecede v treh stolpcih so:");
		System.out.println(a+" "+b+" "+c);
		System.out.println(ch+" "+d+" "+e);
		System.out.println(f+" "+g+" "+h);
		System.out.println(i+" "+j+" "+k);
		System.out.println(l+" "+m+" "+n);
		System.out.println(o+" "+p+" "+r);
		System.out.println(s+" "+sh+" "+t);
		System.out.println(u+" "+v+" "+z);
		System.out.println(zh);
		
			
		// Definicija spremenljivk in prireditev vrednosti - drugic
		char[] crka = {
			'\u0061', '\u0062', '\u0063',
			'\u010d', '\u0064', '\u0065',
			'\u0066', '\u0067', '\u0068',
			'\u0069', '\u006a', '\u006b',
			'\u006c', '\u006d', '\u006e',
			'\u006f', '\u0070', '\u0072',
			'\u0073', '\u0161', '\u0074',
			'\u0075', '\u0076', '\u007a',
			'\u017e'};
		
		// Izpis crk - drugic
		System.out.println("Crke slovenske abecede v treh stolpcih so - drugic:");
		for (int st=1; st<=25; st++){
			System.out.print(crka[st-1]+" ");
			if(st%3==0) {
				System.out.println();
			}
		}		
	}
}

/*
Izpis:
java DN011_Unicode
Crke slovenske abecede v treh stolpcih so:
a b c
č d e
f g h
i j k
l m n
o p r
s š t
u v z
ž
Crke slovenske abecede v treh stolpcih so - drugic:
a b c
č d e
f g h
i j k
l m n
o p r
s š t
u v z
ž
*/