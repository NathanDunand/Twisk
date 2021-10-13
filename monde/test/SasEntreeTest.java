package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.SasEntree;
import twisk.monde.SasSortie;

import static org.junit.jupiter.api.Assertions.*;

class SasEntreeTest {
    SasEntree e;
    Activite a;

    @BeforeEach
    void setUp(){
        e=new SasEntree();
        a=new Activite("a1");
    }

    @Test
    void toC(){
        e.ajouterSuccesseur(a);
        a.ajouterSuccesseur(new SasSortie());
        assertEquals("entrer(0);\n" +
                "delai(10,2);\n" +
                "transfert(0,1);\n" +
                "delai(10,2);\n" +
                "transfert(1,2);\n",e.toC());
    }

}