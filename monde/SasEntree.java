package twisk.monde;

public class SasEntree extends Activite{
    public SasEntree() {
        super("Entrée");
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @return code C correspondant à la création d'un sas d'entrée
     */
    public String toC() {
        String s = "entrer("+this.numEtape+");\n" +
                "delai("+this.temps+","+this.ecartTemps+");\ntransfert("+this.numEtape+","+this.successeurs.getSuccesseur(0).getNumEtape()+");\n"+this.successeurs.getSuccesseur(0).toC();
        return s;
    }
}
