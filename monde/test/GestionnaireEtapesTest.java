package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {

    GestionnaireEtapes g;

    @BeforeEach
    void setUp() {
        g = new GestionnaireEtapes();
    }

    @Test
    void ajouter() {
        assertEquals(0,g.nbEtapes());
        g.ajouter(new Activite("a"));
        assertEquals(1,g.nbEtapes());
        g.ajouter(new Activite("a1"), new Guichet("g1"), new SasEntree(), new SasSortie(), new ActiviteRestreinte("ar"));
        assertEquals(6, g.nbEtapes());
    }

    @Test
    void iterator() {
        ajouter();
        Iterator<Etape> i = g.iterator();
        assertEquals("a", i.next().getNom());
        assertEquals("a1", i.next().getNom());
        assertEquals("g1", i.next().getNom());
        assertEquals("Entrée", i.next().getNom());
        assertEquals("Sortie", i.next().getNom());
    }

    @Test
    void testToString() {
        ajouter();
        StringBuilder s=new StringBuilder();
        assertEquals("(Etape 0) a : 0 successeurs\n" +
                "(Etape 1) a1 : 0 successeurs\n" +
                "(Etape 2) g1 : 0 successeurs\n" +
                "(Etape 3) Entrée : 0 successeurs\n" +
                "(Etape 4) Sortie : 0 successeurs\n" +
                "(Etape 5) ar : 0 successeurs\n",g.toString());
    }
}