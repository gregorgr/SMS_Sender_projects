

class ObserverTest{


  public static void main(String[] args)
   {
       NarocnikEna n1 = new NarocnikEna();
       NarocnikDva n2 = new NarocnikDva();
       NarocnikTri n3 = new NarocnikTri();

       //MessagePublisher
       Obvescevalec p = new Obvescevalec();

       p.dodaj(n1);
       p.dodaj(n2);
       System.out.println("Pošljem sporočilo 1");
       p.obvestiNarocnike(new Sporocilo("Prvo sporočilo"));   //n1 in n2 bosta prejela posodobitev

       System.out.println("Odstranim naročnika Ena in dodam Tri");
       p.odstrani(n1);
       p.dodaj(n3);
      System.out.println("\nPošljem sporočilo 2");
       p.obvestiNarocnike(new Sporocilo("Drugo sporočilo"));  //n2 in n3 bosta prejela posodobitev
   }


}
