package twisk.outils;

import javafx.concurrent.Task;

import java.util.ArrayList;

public class GestionnaireThreads {

    private ArrayList<Thread> threads;

    private static  GestionnaireThreads instance = new GestionnaireThreads();

    private GestionnaireThreads(){
        threads = new ArrayList<>();
    }

    public static GestionnaireThreads getInstance(){
        return instance;
    }

    public void lancer(Task lancer){
        Thread t = new Thread(lancer);
        threads.add(t);
        t.start();
    }

    public void detruireTout(){
        for (Thread t : this.threads){
            t.interrupt();
        }
    }
}
