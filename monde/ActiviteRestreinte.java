package twisk.monde;

/**
 * Classe qui gère une activite restreinte.
 */
public class ActiviteRestreinte  extends Activite{

    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom,t,e);
    }

    /**
     * @return code c correspondant à l'ajout d'une activité restreinte
     */
    public String toC() {
        return "transfert("+this.numEtape+","+this.successeurs.getSuccesseur(0).getNumEtape()+");\n"+this.successeurs.getSuccesseur(0).toC();
    }

    public int getTemps(){
        return this.temps;
    }

    public int getEcart(){
        return this.ecartTemps;
    }
}
