package twisk.monde;

import twisk.simulation.GestionnaireClients;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Etape> {

    /**
     * Ensemble des étapes du monde
     */
    private GestionnaireEtapes lesEtapes;
    /**
     * sas de sortie du monde
     */
    private SasSortie sortie;
    /**
     * sas d'entrée du monde
     */
    private SasEntree entree;

    private GestionnaireClients lesClients;

    public Monde(){
        this.lesEtapes = new GestionnaireEtapes();
        entree = new SasEntree();
        lesEtapes.ajouter(entree);

        //pour le moment, un nombre nul de client
        this.lesClients=new GestionnaireClients(0);
    }

    /**
     * ajoute des étapes pour l'entrée du monde
     * @param etapes la ou les étapes d'entrée du monde
     */
    public void aCommeEntree(Etape ... etapes){
        entree.ajouterSuccesseur(etapes);
    }

    /**
     * ajoute des étapes pour la sortie du monde
     * @param etapes la ou les étapes de sortie du monde
     */
    public void aCommeSortie(Etape ... etapes){
        sortie = new SasSortie();
        for (Etape e : etapes){
            e.ajouterSuccesseur(sortie);
        }
        this.lesEtapes.ajouter(sortie);
    }

    public void ajouter(Etape ... etapes){
        for(Etape e : etapes){
            this.lesEtapes.ajouter(e);
        }
    }

    public int nbEtapes(){
        return lesEtapes.nbEtapes();
    }

    public int nbGuichets(){
        Iterator<Etape> i = lesEtapes.iterator();
        int nb = 0;
        while (i.hasNext()){
            Etape e = i.next();
            if (e.estUnGuichet()){
                nb++;
            }
        }
        return nb;
    }

    public ArrayList<Guichet> getGuichets() {
        Iterator<Etape> i = lesEtapes.iterator();
        ArrayList<Guichet> guichets = new ArrayList<Guichet>(nbGuichets());
        while (i.hasNext()){
            Etape e = i.next();
            if (e.estUnGuichet()){
                guichets.add((Guichet) e);
            }
        }
        return guichets;
    }

    public Iterator<Etape> iterator(){
        return this.lesEtapes.iterator();
    }

    public Etape getEtape(int etapeId){
        return this.lesEtapes.getEtape(etapeId);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
       /* s.append(entree.toString());
        s.append("\n");
        s.append(sortie.toString());
        s.append("\n");*/
        s.append(this.lesEtapes.toString());
        s.append("\nFIN MONDE\n");
        return s.toString();
    }

    public void setLesClients(GestionnaireClients lesClients){
        this.lesClients = lesClients;
    }

    public int getNbClientEtape(Etape etape){
        return this.lesClients.getNbClient(etape);
    }

    /**
     * @return code C correspondant à la création d'un monde
     */
    public String toC(){
        String s = "#include \"def.h\"\nvoid simulation(int ids){\n"+entree.toC()+"}";
        return s;
    }

}
