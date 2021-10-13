package twisk.outils.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class FabriqueNumeroTest {

    /*NB ici tout dépend de si on exécute la class de test entièrement ou fonction après fonction.
    Si entièrement : alors les test sur le numéro Semaphore seront faux car une instance de FabriqueNumero aura déjà été faite et le premier appel à getNumeroSemaphore ne sera bon
    Si une fonction de test après l'autre : alors les deux fonctions de test seront bons.
     */

    @Test
    void testGetNumeroEtape(){
        //System.out.println(FabriqueNumero.getInstance().getNumeroEtape());
        assert(FabriqueNumero.getInstance().getNumeroEtape()==0):"Erreur getNumeroEtape";
        assert(FabriqueNumero.getInstance().getNumeroEtape()==1):"Erreur getNumeroEtape";
        assert(FabriqueNumero.getInstance().getNumeroEtape()==2):"Erreur getNumeroEtape";

        FabriqueNumero.getInstance().reset();

        assert(FabriqueNumero.getInstance().getNumeroEtape()==0):"Erreur reset";
    }

    @Test
    void testGetNumeroSemaphore(){
        assert(FabriqueNumero.getInstance().getNumeroSemaphore()==1):"Erreur getNumeroSemaphore";
        assert(FabriqueNumero.getInstance().getNumeroSemaphore()==2):"Erreur getNumeroSemaphore";
        assert(FabriqueNumero.getInstance().getNumeroSemaphore()==3):"Erreur getNumeroSemaphore";

        FabriqueNumero.getInstance().reset();

        assert(FabriqueNumero.getInstance().getNumeroSemaphore()==0):"Erreur reset";
    }
}