package twisk.mondeIG;

import twisk.monde.Etape;
import twisk.outils.FabriqueIdentifiant;

public class ArcIG {
    private PointDeControleIG p1;
    private PointDeControleIG p2;
    private String id;
    private EtapeIG e1;
    private EtapeIG e2;
    private boolean isSelected;

    public ArcIG(PointDeControleIG p1, PointDeControleIG p2, EtapeIG e1, EtapeIG e2){
        this.p1=p1;
        this.p2=p2;
        this.id=FabriqueIdentifiant.getInstance().getIdentifiantArc();
        this.e1=e1;
        this.e2=e2;
        this.isSelected=false;
    }

    public void selected(){
        //on sélectionne l'arc
        this.isSelected=true;
    }

    public void unselected(){
        //on déselectionne l'arc
        this.isSelected=false;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public PointDeControleIG getP1(){
        return this.p1;
    }

    public PointDeControleIG getP2(){
        return this.p2;
    }

    public String getIdentifiant(){
        return this.id;
    }

    public EtapeIG getE1(){
        return this.e1;
    }

    public EtapeIG getE2(){
        return this.e2;
    }

    public String toString(){
        return this.id;
    }
}
