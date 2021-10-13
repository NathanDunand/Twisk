package twisk.simulation;

import javafx.concurrent.Task;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.*;

import java.util.Arrays;

public class Simulation extends SujetObserve {

    KitC kit;
    int nbClients;
    GestionnaireClients lesClients;
    boolean simulation;

    public Simulation() {
        super();
        kit = new KitC();
        kit.creerEnvironnement();
        nbClients = 6;
    }

    public Simulation(int nbClients){
        super();
        this.kit=new KitC();
        this.kit.creerEnvironnement();
        this.nbClients=nbClients;
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.obs.add(o);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur o : this.obs) {
            o.reagir();
        }
    }

    public void simuler(Monde monde) {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                simulation = true;
                System.out.println("TEST");
                //System.out.println(monde.toString());
                try {

                    kit.creerFichier(monde.toC());
                    kit.compiler();
                    kit.construireLaLibrairie();

                    System.load("/tmp/twisk/libTwisk" + FabriqueNumero.getInstance().getNumLibTwisk() + ".so");
                    System.out.println("Test libTwisk");

                    int[] tabJetonsGuichet;

                    if (monde.nbGuichets() != 0) {
                        System.out.println("Il y a des guichets");
                        tabJetonsGuichet = new int[monde.nbGuichets()];
                        int a = 0;
                        for (Guichet g : monde.getGuichets()) {
                            tabJetonsGuichet[a++] = g.getNbjetons();
                        }
                    } else {
                        System.out.println("Il n'y a pas de guichet");
                        tabJetonsGuichet = new int[0];
                    }
                    System.out.println(monde.nbEtapes() + " : nb etape");
                    System.out.println(monde.nbGuichets() + " : nbGuichet");
                    System.out.println(nbClients + " : nbclient");
                    System.out.println(Arrays.toString(tabJetonsGuichet));
                    int[] res = start_simulation(monde.nbEtapes(), monde.nbGuichets(), nbClients, tabJetonsGuichet);
                    //instanciation des Clients
                    lesClients = new GestionnaireClients(res.length);
                    monde.setLesClients(lesClients);

                    lesClients.setClients(res);

                    int[] clients = ou_sont_les_clients(monde.nbEtapes(), nbClients);

                    int LASTSTEP = clients.length - 1 - nbClients;

                    System.out.print("Les clients : ");
                    for (int i = 0; i < nbClients; i++) {
                        if (i == 0) {
                            System.out.print(res[i]);
                        } else {
                            System.out.print(", " + res[i]);
                        }
                    }

                    System.out.println("\n");


                    while (clients[LASTSTEP] != nbClients) {
                        clients = ou_sont_les_clients(monde.nbEtapes(), nbClients);

                        int g = 0;
                        for (int i = 0; i < monde.nbEtapes(); i++) {
                            System.out.print("Etape " + i + " (" + monde.getEtape(i).getNom() + ") ==> " + clients[g] + " clients : ");
                            for (int j = g + 1; j <= clients[g] + g; j++) {
                                if (clients[j] != 0) {
                                    lesClients.allerA(clients[j], monde.getEtape(i), 0);
                                    System.out.print(clients[j] + ", ");
                                }
                            }
                            g = g + nbClients + 1;
                            System.out.println();
                        }
                        notifierObservateurs();
                        Thread.sleep(1000);
                        System.out.println("---------------------------------------------");
                    }
                    nettoyage();
                    notifierObservateurs();
                    lesClients.supprimerClients();
                    simulation = false;
                } catch (Exception e) {
                    System.out.println("try catch thread");
                    nettoyage();
                    notifierObservateurs();
                    lesClients.supprimerClients();
                    simulation = false;
                }

                return null;
            }
        };
        GestionnaireThreads.getInstance().lancer(task);
    }

    public String simulerTest(Monde monde){
        return monde.toString();
    }

    public native int[] start_simulation(int nbEtape,int nbGuichets, int nbClients, int[] tabJetonsGuichet);

    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);

    public native void nettoyage();

    public void setNbClients(Integer nbClients) {
        this.nbClients = nbClients;
    }

    public int getNbClients(){
        return this.nbClients;
    }

    public boolean isSimulation(){
        return simulation;
    }

    public GestionnaireClients getGestionnaireClients(){
        return lesClients;
    }
}
