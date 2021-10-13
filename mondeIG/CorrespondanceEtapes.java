package twisk.mondeIG;

import twisk.monde.Etape;

import java.util.HashMap;

public class CorrespondanceEtapes {

    HashMap<EtapeIG, Etape> etapes;

    public CorrespondanceEtapes(){
        this.etapes = new HashMap<>();
    }

    public void ajouterEtape(EtapeIG etig, Etape et){
        this.etapes.put(etig, et);
    }

    public Etape get(EtapeIG e){
        return this.etapes.get(e);
    }
}
