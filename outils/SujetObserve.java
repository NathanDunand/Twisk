package twisk.outils;

import twisk.outils.Observateur;

import java.util.ArrayList;

public abstract class SujetObserve {
    protected ArrayList<Observateur> obs;

    public SujetObserve(){
        this.obs = new ArrayList<>();
    }

    public abstract void ajouterObservateur(Observateur o);

    public abstract void notifierObservateurs();
}
