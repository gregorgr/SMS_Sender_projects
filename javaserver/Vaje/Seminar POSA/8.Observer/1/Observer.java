


class Observer{

 public static void main(String[] args) {


   NewsAgency observable = new NewsAgency();
   NovicarskiKanal observer = new NovicarskiKanal();

   observable.addObserver(observer);
   observable.setNews("news");
   assertEquals(observer.getNews(), "news");


   /*
    ONewsAgency observable = new ONewsAgency();
    ONewsChannel observer = new ONewsChannel();

    observable.addObserver(observer);
    observable.setNews("news");
    assertEquals(observer.getNews(), "news");
    */

 }


}
