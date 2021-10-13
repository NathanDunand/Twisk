package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class EtapeTest {

    Activite a;
    Guichet g;

    @BeforeEach
    void setUp() {
        a = new Activite("a0");
        g  = new Guichet("g0");
    }

    @Test
    void ajouterSuccesseur() {
        a.ajouterSuccesseur(new Activite("a1"), new Guichet("g1"), new Activite("a2"));
        g.ajouterSuccesseur(new Activite("a3"), new Guichet("g2"), new SasEntree(), new SasSortie(), new ActiviteRestreinte("ar1"));
    }

    @Test
    void testEstUneActivite(){
        assert(!g.estUneActivite()):"Erreur estUneActivite";
        assert(a.estUneActivite()):"Erreur estUneActivite";
    }

    @Test
    void testEstUnGuichet(){
        assert(g.estUnGuichet()):"Erreur estUnGuichet";
        assert(!a.estUnGuichet()):"Erreur estUnGuichet";
    }

    @Test
    void testAjouterSuccesseur(){
        ajouterSuccesseur();
        assert(g.toString().equals("(Etape 1) g0 : 5 successeurs -> a3 - g2 - Entrée - Sortie - ar1"));
        assert(a.toString().equals("(Etape 0) a0 : 3 successeurs -> a1 - g1 - a2"));
    }

    @Test
    void testIterator(){
        ajouterSuccesseur();

        Iterator<Etape> it=g.iterator();
        assertEquals("a3", it.next().getNom());
        assertEquals("g2", it.next().getNom());
        assertEquals("Entrée", it.next().getNom());
        assertEquals("Sortie", it.next().getNom());
        assertEquals("ar1", it.next().getNom());

        Iterator<Etape> i=a.iterator();
        assertEquals("a1", i.next().getNom());
        assertEquals("g1", i.next().getNom());
        assertEquals("a2", i.next().getNom());
    }

    @Test
    void testToC(){
        SasEntree sasEntree=new SasEntree();
        Guichet g=new Guichet("g0");
        Activite a = new Activite("a0");
        SasSortie sasSortie=new SasSortie();
        sasEntree.ajouterSuccesseur(g);
        g.ajouterSuccesseur(a);
        a.ajouterSuccesseur(sasSortie);

        System.out.println("#define "+sasEntree.getNom()+" "+sasEntree.getNumEtape());
        System.out.println("#define "+g.getNom()+" "+g.getNumEtape());
        System.out.println("#define "+a.getNom()+" "+a.getNumEtape());
        System.out.println("#define "+sasSortie.getNom()+" "+sasSortie.getNumEtape());

        System.out.println(sasEntree.toC());
    }
}