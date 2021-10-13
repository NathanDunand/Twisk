package twisk.mondeIG;

public class ActiviteIG extends EtapeIG {

    boolean activiteRestreinte;

    public ActiviteIG(String nom, String identifiant, int largeur, int hauteur) {
        super(nom, identifiant, largeur, hauteur);
        this.activiteRestreinte = false;
    }

    @Override
    public boolean estUneActiviteRestreinte() {
        return this.activiteRestreinte;
    }

    @Override
    public boolean estUneActivite() {
        return !this.activiteRestreinte;
    }

    public void setActiviteRestreinte(boolean activiteRestreinte) {
        this.activiteRestreinte = activiteRestreinte;
    }

    public boolean estUnGuichet(){
        return false;
    }

    public void setNbjetons(int i){
        //ne fait rien
    }

    public int getNbjetons(){
        return -1;
    }
}
