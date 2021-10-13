package twisk.mondeIG.tests;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.FabriqueIdentifiant;

import java.util.ArrayList;
import java.util.Iterator;

class PointDeControleIGTest {

    @Test
    void testConstructeur(){
        //vérification des formules pour placer les PDC
        String identifiant= FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        EtapeIG e=new ActiviteIG("Activité default"+identifiant, identifiant, TailleComposants.getInstance().getLargeurEtape(), TailleComposants.getInstance().getHauteurEtape());

        int largeur=TailleComposants.getInstance().getLargeurEtape();
        int hauteur=TailleComposants.getInstance().getHauteurEtape();

        ArrayList<PointDeControleIG> al=new ArrayList<PointDeControleIG>();
        Iterator it=e.iterator();
        /*while(it.hasNext()){
            al.add((PointDeControleIG) it.next());
        }*/
        //point du haut
        PointDeControleIG h=(PointDeControleIG) it.next();
        //System.out.println("X de l'étape : "+e.getPosX()+" X du pt haut : "+h.getPosCentreX());//problème ici : valeur abérante
        assert(h.getPosCentreX()==e.getPosX()+(largeur/2) && h.getPosCentreY()==e.getPosY()):"Erreur positions du point du haut";

        //point à droite
        PointDeControleIG d=(PointDeControleIG) it.next();
        assert(d.getPosCentreX()==e.getPosX()+largeur && d.getPosCentreY()==e.getPosY()+(hauteur/2)):"Erreur position X du point de droite";

        //point en bas
        PointDeControleIG b=(PointDeControleIG) it.next();
        assert(b.getPosCentreX()==e.getPosX()+(largeur/2) && b.getPosCentreY()==e.getPosY()+hauteur):"Erreur position X du point du bas";

        //point à gauche
        PointDeControleIG g=(PointDeControleIG) it.next();
        assert(g.getPosCentreX()==e.getPosX() && g.getPosCentreY()==e.getPosY()+(hauteur/2)):"Erreur position X du point de gauche";
    }
}