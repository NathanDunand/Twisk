package twisk.simulation;

import javafx.scene.paint.Color;
import twisk.monde.Etape;
import twisk.outils.FabriqueCouleur;

public class Client {
    private int numeroClient;
    private int rang;
    private Etape etape;
    private Color couleur;

    public Client(int numero){
        numeroClient=numero;
        couleur = FabriqueCouleur.getInstance().getCouleur();
    }

    public void allerA(Etape etape, int rang){
        this.etape=etape;
        this.rang=rang;
    }

    public int getNumeroClient(){
        return numeroClient;
    }

    public Etape getEtape(){
        return this.etape;
    }

    public Color getCouleur() {
        return couleur;
    }
}
