package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest {

    Guichet g;
    Activite a;

    @BeforeEach
    void setUp() {
        g = new Guichet("g");
        a = new ActiviteRestreinte("ActR1");
        g.ajouterSuccesseur(a);
        a.ajouterSuccesseur(new SasSortie());
    }

    @Test
    void estUneActivite() {
        assertFalse(g.estUneActivite());
        g = new Guichet("g", 3);
        assertFalse(g.estUneActivite());
    }

    @Test
    void estUnGuichet() {
        assertTrue(g.estUnGuichet());
        g = new Guichet("g",1);
        assertTrue(g.estUnGuichet());
    }

    @Test
    void toC(){
        assertEquals("P(ids,1);\n" +
                "transfert(0,1);\n" +
                "delai(2,2);\n" +
                "V(ids,1);\n",g.toC());
    }
}