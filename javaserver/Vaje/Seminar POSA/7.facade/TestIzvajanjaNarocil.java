

// import static org.junit.Assert.*;


public class TestIzvajanjaNarocil {


/*
    @Test
    public void testNarociProdukt() throws Exception {

      KontrolerIzvedenihNarocil controller=new KontrolerIzvedenihNarocil();

      controller.facade=new NarocniskaSluzbaImpl();

      controller.narociIzdelek(9);

      boolean result=controller.narociloIzvedeno;

        assertTrue(result);


    }*/



    public static void main(String[] args){

      KontrolerIzvedenihNarocil controller=new KontrolerIzvedenihNarocil();

      controller.facade=new NarocniskaSluzbaImpl();

      controller.narociIzdelek(9);

      boolean result=controller.narociloIzvedeno;

      // assertTrue(result);

    }
}
