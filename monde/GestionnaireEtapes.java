package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import twisk.monde.*;

public class GestionnaireEtapes implements Iterable<Etape> {

    private HashMap<Integer,Etape> etapes;

    public GestionnaireEtapes(){
        this.etapes=new HashMap<>();
    }

    public void ajouter(Etape ... etapes){
        for (Etape e : etapes){
            this.etapes.put(e.getNumEtape(),e);
        }
    }

    public int nbEtapes(){
        return this.etapes.size();
    }

    public Iterator<Etape> iterator(){
        return this.etapes.values().iterator();
    }

    public String toString(){
        StringBuilder s=new StringBuilder();

        for (Etape e : this.etapes.values()) {
            s.append(e.toString());
            s.append("\n");
        }

        return s.toString();
    }

    public Etape getEtape(int index) {
        return etapes.get(index);
    }
}
