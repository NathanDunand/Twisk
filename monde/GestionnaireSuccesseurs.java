package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseurs implements Iterable<Etape> {

    private ArrayList<Etape> etapes;

    public GestionnaireSuccesseurs(){
        this.etapes=new ArrayList<>();
    }

    public void ajouter(Etape ... etapes){
        this.etapes.addAll(Arrays.asList(etapes));
    }

    public void retirer(Etape e){
        this.etapes.remove(e);
    }

    public int nbEtapes(){
        return this.etapes.size();
    }

    public Iterator<Etape> iterator(){
        return this.etapes.iterator();
    }

    public Etape getSuccesseur(int rank) {
        return etapes.get(rank);
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        s.append(nbEtapes());
        s.append( " successeurs");
        if(nbEtapes() > 0){
            s.append(" -> ");
        }
        Iterator<Etape> i=this.etapes.iterator();
        while(i.hasNext()){
            s.append(i.next().getNom());
            if (i.hasNext()){
                s.append(" - ");
            }
        }

        return s.toString();
    }
}
