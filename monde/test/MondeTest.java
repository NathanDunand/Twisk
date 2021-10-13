package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

class MondeTest {

    SasEntree e;
    Activite a;
    Guichet g;
    ActiviteRestreinte ar;
    SasSortie s;
    Monde m;

    @BeforeEach
    void setUp() {
        e = new SasEntree();
        a = new Activite("a0");
        g = new Guichet("g1");
        ar = new ActiviteRestreinte("ar1");
        s = new SasSortie();
        m = new Monde();
        m.aCommeEntree(e);
        m.aCommeSortie(s);

        e.ajouterSuccesseur(a);
        a.ajouterSuccesseur(g);
        g.ajouterSuccesseur(ar);
        ar.ajouterSuccesseur(s);

        m.ajouter(e,a,g,ar,s);
    }

    @Test
    void toC(){
        assertEquals("#include \"def.h\"\n" +
                "void simulation(int ids){\n" +
                "entrer(5);\n" +
                "delai(10,2);\n" +
                "transfert(5,0);\n" +
                "entrer(0);\n" +
                "delai(10,2);\n" +
                "transfert(0,1);\n" +
                "delai(10,2);\n" +
                "transfert(1,2);\n" +
                "P(ids,1);\n" +
                "transfert(2,3);\n" +
                "delai(10,2);\n" +
                "V(ids,1);\n" +
                "transfert(3,4);\n" +
                "}",m.toC());
    }

    @Test
    void nbGuichetsTest(){
        assertEquals(1,m.nbGuichets());
        m.ajouter(new Guichet("g2"), new Guichet("g3"));
        assertEquals(3,m.nbGuichets());
    }
}