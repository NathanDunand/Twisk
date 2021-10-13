package twisk.mondeIG;

import java.util.ArrayList;

public class GuichetIG extends EtapeIG {

    private int nbjetons;

    public GuichetIG(String nom, String identifiant, int largeur, int hauteur) {
        super(nom, identifiant, largeur, hauteur);
        this.nbjetons = 2;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " "+nbjetons+" jetons";
    }

    public int getNbjetons(){
        return this.nbjetons;
    }

    public void setNbjetons(int i){
        this.nbjetons=i;
    }

    public boolean estUneActivite(){
        return false;
    }
}
