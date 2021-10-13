package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape{

    /**
     * nombre de jetons pour aller dans l'étape suivante
     */
    int nbjetons;
    int semaphore;

    public Guichet(String nom) {
        super(nom);
        this.nbjetons = 2;
        semaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Guichet(String nom, int nb) {
        super(nom);
        this.nbjetons = nb;
        semaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    @Override
    public boolean estUneActivite() {
        return false;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }

    public int getNbjetons(){
        return nbjetons;
    }

    /**
     * @return code C correspondant à l'ajout d'un Guichet
     */
    public String toC() {
        ActiviteRestreinte n = (ActiviteRestreinte)this.successeurs.getSuccesseur(0);
        return "P(ids,"+ this.semaphore+");\n"+"transfert("+ this.numEtape+","+ this.successeurs.getSuccesseur(0).getNumEtape()+");\n" +
                "delai("+n.getTemps()+","+n.getEcart()+");\n"+"V(ids,"+ this.semaphore+");\n"+n.toC();
    }
}
