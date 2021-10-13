package twisk.monde;

/**
 * Gère une activité
 */
public class Activite extends Etape{

    int temps;
    int ecartTemps;

    public Activite(String nom) {
        super(nom);
        this.temps = 3;
        this.ecartTemps = 2;
    }

    public Activite(String nom, int t, int e){
        super(nom);
        this.temps = t;
        this.ecartTemps = e;
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

    @Override
    public boolean estUnGuichet() {
        return false;
    }

    /**
     * @return code C correspondant à l'ajout d'une activité
     */
    public String toC() {
        String s = "delai("+this.temps+","+this.ecartTemps+");\ntransfert("+this.numEtape+","+this.successeurs.getSuccesseur(0).getNumEtape()+");\n"+this.successeurs.getSuccesseur(0).toC();
        return s;
    }

    public int getTemps(){
        return this.temps;
    }

    public int getEcart(){
        return this.ecartTemps;
    }
}
