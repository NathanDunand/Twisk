package twisk.outils.test;

import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueIdentifiant;

class FabriqueIdentifiantTest {

    @Test
    void testConstruct(){
        String identifiant= FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        assert(identifiant.equals("0")):"Erreur Fabrique construct";
    }

    @Test
    void testIncrementation(){
        String identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        assert(FabriqueIdentifiant.getInstance().getIdentifiantEtape().equals("1")):"Erreur Fabrique incrementation";
        assert(FabriqueIdentifiant.getInstance().getIdentifiantEtape().equals("2")):"Erreur Fabrique incrementation";
        assert(FabriqueIdentifiant.getInstance().getIdentifiantEtape().equals("3")):"Erreur Fabrique incrementation";
    }
}