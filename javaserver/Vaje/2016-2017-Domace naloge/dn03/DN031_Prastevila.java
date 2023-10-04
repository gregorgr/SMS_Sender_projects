/* 
DN031_Prastevila
Napisite program, ki za dani argument n izpise vsa prastevila
manjsa od n. Pozor: 1 ni prastevilo!
*/

/* Algoritem
1. preberi podatke iz ukazne vrstice in jih spremeni v int
2. deklariraj in inicializiraj spremenljivke
3. za vsako število preštej število deljiteljev
4. izpisi tista stevila, ki imajo natanko dva deljitelja

Dodatek:
1. Za vsa števila izpiši tudi deljitelje

*/

class DN031_Prastevila {
	public static void main (String[] args) {
		// branje stevila n do katerega moramo preveriti prastevila
		int n = Integer.parseInt(args[0]);
		
		int [] steviloDeljiteljev = new int[n+1]; //zaradi lažjega sledenja posameznemu stevilu je za stevilo 2 zapisano v tabeli v polju 2 
				
		// inicializacija spremenljivk na nič
		for (int i=0; i<=n; i++) {
			steviloDeljiteljev[i]=0;
		}
		
		// izračun št. deljiteljev
		for (int i=1; i<=n; i++) {
			for (int j=1; j<=i; j++) { // racunanje z vsemi stevili od 1 do trenutnega števila			
				if(i%j==0) { // preveri ostanek deljenja z vsemi števili do trenutnega
					steviloDeljiteljev[i]++;
				}
			}
		}

		// izpisi prastevil
		System.out.println("Na intervalu [1," + n + ") imamo naslednja prastevila:");
		for (int i=1; i<n; i++) {	// prastevila manjsa on n
			if (steviloDeljiteljev[i]==2) { // pogoj - kdaj je stevilo prastevilo
				System.out.println(i);
			}
		}
				
	
		//zapis posameznih deljiteljev stevil
		int [][] deljitelji = new int [n+1][]; // za izpis števila deljiteljev
		int stevecDeljiteljevStevilaI;
		for (int i=1; i<=n; i++) {
			stevecDeljiteljevStevilaI=0;
			deljitelji[i] = new int [steviloDeljiteljev[i]];
			for (int j=1; j<=i; j++) {
				if(i%j==0) {
					deljitelji[i][stevecDeljiteljevStevilaI]=j;
					stevecDeljiteljevStevilaI++;
				}
			}		
		}		
				
		// izpis za vsa stevila, katera so njegovi deljitelji
		System.out.println("Na intervalu [1," + n + "] imajo stevila naslednje lastnosti:");
		System.out.println("Stevilo : koliko deljiteljev : kateri deljitelji");
		for (int i=1; i<=n; i++) {
			System.out.print(i+" : "+steviloDeljiteljev[i]+" : ");
			for (int j=0; j<deljitelji[i].length; j++) {
				System.out.print(deljitelji[i][j]+" ");
			}
			System.out.println();
		}
	}
}

/*
Izpis:
java DN031_Prastevila 100
Na intervalu [1,100) imamo naslednja prastevila:
2
3
5
7
11
13
17
19
23
29
31
37
41
43
47
53
59
61
67
71
73
79
83
89
97
Na intervalu [1,100] imajo stevila naslednje lastnosti:
Stevilo : koliko deljiteljev : kateri deljitelji
1 : 1 : 1
2 : 2 : 1 2
3 : 2 : 1 3
4 : 3 : 1 2 4
5 : 2 : 1 5
6 : 4 : 1 2 3 6
7 : 2 : 1 7
8 : 4 : 1 2 4 8
9 : 3 : 1 3 9
10 : 4 : 1 2 5 10
11 : 2 : 1 11
12 : 6 : 1 2 3 4 6 12
13 : 2 : 1 13
14 : 4 : 1 2 7 14
15 : 4 : 1 3 5 15
16 : 5 : 1 2 4 8 16
17 : 2 : 1 17
18 : 6 : 1 2 3 6 9 18
19 : 2 : 1 19
20 : 6 : 1 2 4 5 10 20
21 : 4 : 1 3 7 21
22 : 4 : 1 2 11 22
23 : 2 : 1 23
24 : 8 : 1 2 3 4 6 8 12 24
25 : 3 : 1 5 25
26 : 4 : 1 2 13 26
27 : 4 : 1 3 9 27
28 : 6 : 1 2 4 7 14 28
29 : 2 : 1 29
30 : 8 : 1 2 3 5 6 10 15 30
31 : 2 : 1 31
32 : 6 : 1 2 4 8 16 32
33 : 4 : 1 3 11 33
34 : 4 : 1 2 17 34
35 : 4 : 1 5 7 35
36 : 9 : 1 2 3 4 6 9 12 18 36
37 : 2 : 1 37
38 : 4 : 1 2 19 38
39 : 4 : 1 3 13 39
40 : 8 : 1 2 4 5 8 10 20 40
41 : 2 : 1 41
42 : 8 : 1 2 3 6 7 14 21 42
43 : 2 : 1 43
44 : 6 : 1 2 4 11 22 44
45 : 6 : 1 3 5 9 15 45
46 : 4 : 1 2 23 46
47 : 2 : 1 47
48 : 10 : 1 2 3 4 6 8 12 16 24 48
49 : 3 : 1 7 49
50 : 6 : 1 2 5 10 25 50
51 : 4 : 1 3 17 51
52 : 6 : 1 2 4 13 26 52
53 : 2 : 1 53
54 : 8 : 1 2 3 6 9 18 27 54
55 : 4 : 1 5 11 55
56 : 8 : 1 2 4 7 8 14 28 56
57 : 4 : 1 3 19 57
58 : 4 : 1 2 29 58
59 : 2 : 1 59
60 : 12 : 1 2 3 4 5 6 10 12 15 20 30 60
61 : 2 : 1 61
62 : 4 : 1 2 31 62
63 : 6 : 1 3 7 9 21 63
64 : 7 : 1 2 4 8 16 32 64
65 : 4 : 1 5 13 65
66 : 8 : 1 2 3 6 11 22 33 66
67 : 2 : 1 67
68 : 6 : 1 2 4 17 34 68
69 : 4 : 1 3 23 69
70 : 8 : 1 2 5 7 10 14 35 70
71 : 2 : 1 71
72 : 12 : 1 2 3 4 6 8 9 12 18 24 36 72
73 : 2 : 1 73
74 : 4 : 1 2 37 74
75 : 6 : 1 3 5 15 25 75
76 : 6 : 1 2 4 19 38 76
77 : 4 : 1 7 11 77
78 : 8 : 1 2 3 6 13 26 39 78
79 : 2 : 1 79
80 : 10 : 1 2 4 5 8 10 16 20 40 80
81 : 5 : 1 3 9 27 81
82 : 4 : 1 2 41 82
83 : 2 : 1 83
84 : 12 : 1 2 3 4 6 7 12 14 21 28 42 84
85 : 4 : 1 5 17 85
86 : 4 : 1 2 43 86
87 : 4 : 1 3 29 87
88 : 8 : 1 2 4 8 11 22 44 88
89 : 2 : 1 89
90 : 12 : 1 2 3 5 6 9 10 15 18 30 45 90
91 : 4 : 1 7 13 91
92 : 6 : 1 2 4 23 46 92
93 : 4 : 1 3 31 93
94 : 4 : 1 2 47 94
95 : 4 : 1 5 19 95
96 : 12 : 1 2 3 4 6 8 12 16 24 32 48 96
97 : 2 : 1 97
98 : 6 : 1 2 7 14 49 98
99 : 6 : 1 3 9 11 33 99
100 : 9 : 1 2 4 5 10 20 25 50 100
*/