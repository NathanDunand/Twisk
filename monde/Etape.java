package twisk.monde;

import twisk.outils.FabriqueNumero;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {


    /**
     * nom de la l'étape
     */
    protected String nom;
    /**
     * ensemble des successeurs de l'étape
     */
    protected GestionnaireSuccesseurs successeurs;
    /**
     * Numéro de l'étape
     */
    protected int numEtape;

    public Etape(String nom) {
        this.nom = nom;
        this.successeurs = new GestionnaireSuccesseurs();
        this.numEtape = FabriqueNumero.getInstance().getNumeroEtape();
    }

    public GestionnaireSuccesseurs getGestionnaireSuccesseurs(){
        return this.successeurs;
    }

    /**
     * Fonction qui ajoute un ou des successeurs à l'étape courante
     * @param e successeurs de l'étape actuelle
     */
    public void ajouterSuccesseur(Etape... e) {
        for(Etape etape : e){
            this.successeurs.ajouter(etape);
        }
    }

    public void retirerSuccesseur(Etape ... e){
        for(Etape etape:e){
            this.successeurs.retirer(etape);
        }
    }

    public int getNumEtape(){
        return this.numEtape;
    }

    public abstract boolean estUneActivite();

    public abstract boolean estUnGuichet();

    public String getNom() {
        return nom;
    }

    public Iterator<Etape> iterator() {
        return this.successeurs.iterator();
    }


    public String toString(){
        StringBuilder s=new StringBuilder("(Etape ");
        s.append(numEtape);
        s.append(") ");
        s.append(nom);
        s.append(" : ");
        s.append(this.successeurs.toString());

        return s.toString();
    }

    public abstract String toC();
}
