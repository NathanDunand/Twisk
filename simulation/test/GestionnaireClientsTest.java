package twisk.simulation.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.simulation.GestionnaireClients;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireClientsTest {

    GestionnaireClients gestionnaire;

    @BeforeEach
    void setUp() {
        gestionnaire = new GestionnaireClients();
    }

    @Test
    void allerA() {
        gestionnaire.setClients(0,1,2,3);
        Activite act1 = new Activite("aquarium");
        gestionnaire.allerA(0, act1,0);
        assertEquals(act1,gestionnaire.iterator().next().getEtape());
    }
}