package twisk.monde.test;

import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.GestionnaireSuccesseurs;
import twisk.monde.Guichet;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseursTest {

    GestionnaireSuccesseurs g;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        g = new GestionnaireSuccesseurs();
    }

    @org.junit.jupiter.api.Test
    void ajouter() {
        assertEquals(0,g.nbEtapes());
        g.ajouter(new Guichet("Guichet"));
        assertEquals(1,g.nbEtapes());
        g.ajouter(new Activite("Act"));
        assertEquals(2, g.nbEtapes());
        g.ajouter(new Guichet("g1"), new Activite("a2"));
        assertEquals(4, g.nbEtapes());
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        ajouter();
        Iterator<Etape> i = g.iterator();
        assertEquals("Guichet",i.next().getNom());
        assertEquals("Act",i.next().getNom());
        assertEquals("g1",i.next().getNom());
        assertEquals("a2",i.next().getNom());
    }

    @org.junit.jupiter.api.Test
    void testToString(){
        ajouter();
        assertEquals("4 successeurs -> Guichet - Act - g1 - a2",g.toString());
    }
}