package twisk.mondeIG.tests;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.MondeIG;

class MondeIGTest {

    @Test
    void testConstruct(){
        MondeIG monde=new MondeIG();
        assert(monde.toString().equals("Activité\n")):"Erreur construct MondeIG";
    }

    @Test
    void testAjouter(){
        MondeIG monde=new MondeIG();
        monde.ajouter("Activité");
        assert(monde.toString().equals("Activité\nActivité default1\n")):"Erreur ajouter MondeIG";
        monde.ajouter("Activité");
        assert(monde.toString().equals("Activité\nActivité default1\nActivité default2\n")):"Erreur ajouter MondeIG";
    }
}