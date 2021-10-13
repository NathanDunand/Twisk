package twisk.simulation.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.simulation.*;
import twisk.monde.*;
import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    Simulation sim;
    Monde m;

    @BeforeEach
    void setUp(){
        sim=new Simulation();
        m=new Monde();
    }

    @Test
    void testSimulerTest(){
        m.aCommeEntree(new Activite("entree"));
        m.aCommeSortie(new Activite("sortie"));

        assert(sim.simulerTest(m).equals("(Etape 0) Entrée : 1 successeurs -> entree\n" +
                "(Etape 3) Sortie : 0 successeurs\n" +
                "\n" +
                "FIN MONDE\n")):"Erreur simuler";
    }

}