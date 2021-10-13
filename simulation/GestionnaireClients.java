package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GestionnaireClients {
    private HashMap<Integer,Client> lesClients;
    private int nbClients;

    public GestionnaireClients(){ // valeurs par défaut
        lesClients = new HashMap<>(4);
        nbClients = 4;
    }

    public GestionnaireClients(int nbClients){
        //instanciation
        lesClients=new HashMap<>(nbClients);
        this.nbClients = nbClients;
    }

    /**
     * instancie les clients identifiés par leur numéro de processus (numéro de client)
     * @param tabClients ensemble des numéros de clients à instancier
     */
    public void setClients(int ... tabClients){
        //création des clients
        for(int numClient : tabClients){
            lesClients.put(numClient,new Client(numClient));
        }
    }

    /**
     * indique le nombre de clients à gérer
     * @param nbClients nombre de clients à gérer
     */
    public void setNbClients(int nbClients){
        this.nbClients = nbClients;
    }

    /**
     * met à jour les attributs etape et rang d’un client
     * @param numeroClient numéro du client sur lequel on travaille
     * @param etape étape du monde dans laquelle se trouve le client
     * @param rang rang dans une file d'attente
     */
    public void allerA(int numeroClient, Etape etape, int rang){
        this.lesClients.get(numeroClient).allerA(etape,rang);
    }

    /**
     * Fait le ménage dans les clients
     */
    public void reset(){
        lesClients.clear();
        this.lesClients = new HashMap<>(nbClients);
    }

    public Iterator<Client> iterator(){
        return lesClients.values().iterator();
    }

    public int getNbClient(Etape etape){
        int res = 0;

        for (Client c : this.lesClients.values()){
            if (c.getEtape() == etape){
                res++;
            }
        }

        return res;
    }

    public void supprimerClients(){
        this.lesClients.clear();
    }
}
