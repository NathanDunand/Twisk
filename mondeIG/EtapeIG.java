package twisk.mondeIG;

import javafx.scene.paint.Color;
import twisk.outils.FabriqueCouleur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    private String nom;
    private final String identifiant;
    private int posX;
    private int posY;
    private final int largeur;
    private final int hauteur;
    private final ArrayList<PointDeControleIG> pdcigs;
    private final ArrayList<EtapeIG> successeurs;
    protected int delais;
    protected int ecart;
    protected boolean isEntree;
    protected boolean isSortie;
    protected Color couleur;

    public EtapeIG(String nom, String identifiant, int largeur, int hauteur) {
        this.nom = nom;
        this.identifiant = identifiant;
        this.successeurs = new ArrayList<>();

        this.isEntree=false;
        this.isSortie=false;

        Random rand = new Random();
        int maxX = 1000;//valeurs max pour placer aléatoirement la partie graphique
        int maxY = 500;
        this.posX = rand.nextInt(maxX);
        this.posY = rand.nextInt(maxY);
        this.largeur = largeur;
        this.hauteur = hauteur;

        int marge=0;//décallage par rapport aux bords de l'étape
        PointDeControleIG h = new PointDeControleIG(this.posX + (this.largeur / 2), this.posY-marge, "id", this);
        PointDeControleIG d = new PointDeControleIG(this.posX+marge + this.largeur, this.posY + (this.hauteur / 2), "id", this);
        //nb, pour le point du bas, il faut rajouter la taille de la zone d'affichage des clients
        PointDeControleIG b = new PointDeControleIG(this.posX + (this.largeur / 2), this.posY + this.hauteur+marge+TailleComposants.getInstance().getHauteurZoneEtape()/2, "id", this);
        PointDeControleIG g = new PointDeControleIG(this.posX-marge, this.posY + (this.hauteur / 2), "id", this);

        this.pdcigs = new ArrayList<>(4);
        this.pdcigs.add(h);
        this.pdcigs.add(d);
        this.pdcigs.add(b);
        this.pdcigs.add(g);

        this.delais=2;
        this.ecart=1;
    }

    public ArrayList<PointDeControleIG> getPdcigs(){
        return this.pdcigs;
    }

    public void setDelais(String i){
        this.delais=Integer.parseInt(i);
    }

    public void setEcart(String i){
        this.ecart=Integer.parseInt(i);
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        ArrayList<PointDeControleIG> al = new ArrayList<>(this.pdcigs);
        return al.iterator();
    }

    public void estUneEntree(){
        this.isSortie=false;
        this.isEntree=true;
    }

    public void estUneSortie(){
        this.isEntree=false;
        this.isSortie=true;
    }

    public boolean isSortie(){
        return this.isSortie;
    }

    public boolean isEntree(){
        return this.isEntree;
    }

    public String getNom(){
        return this.nom;
    }

    public String getIdentifiant(){
        return this.identifiant;
    }

    public void setPosX(int x){
        this.posX=x;
        int marge=0;//décallage par rapport aux bords de l'étape

        //il faut aussi set les positions des points pour le Drag and Drop
        PointDeControleIG h=this.pdcigs.get(0);
        h.setPosCentreX(this.posX + (this.largeur / 2));
        PointDeControleIG d=this.pdcigs.get(1);
        d.setPosCentreX(this.posX+marge + this.largeur);
        PointDeControleIG b=this.pdcigs.get(2);
        b.setPosCentreX(this.posX + (this.largeur / 2));
        PointDeControleIG g=this.pdcigs.get(3);
        g.setPosCentreX(this.posX-marge);
    }

    public void setPosY(int y){
        this.posY=y-(TailleComposants.getInstance().getHauteurEtape()/2);
        int marge=0;//décallage par rapport aux bords de l'étape

        //il faut aussi set les positions des points pour le Drag and Drop
        PointDeControleIG h=this.pdcigs.get(0);
        h.setPosCentreY(this.posY-marge);
        PointDeControleIG d=this.pdcigs.get(1);
        d.setPosCentreY(this.posY + (this.hauteur / 2));
        PointDeControleIG b=this.pdcigs.get(2);
        b.setPosCentreY(this.posY + this.hauteur+marge+TailleComposants.getInstance().getHauteurZoneEtape()/2);
        PointDeControleIG g=this.pdcigs.get(3);
        g.setPosCentreY(this.posY + (this.hauteur / 2));
    }

    public void setNom(String s){
        this.nom=s;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getLargeur(){
        return this.largeur;
    }

    public int getHauteur(){
        return this.hauteur;
    }

    public String toString(){
        return this.nom;
    }

    public abstract boolean estUnGuichet();

    public abstract boolean estUneActivite();

    public abstract void setNbjetons(int i);

    public abstract int getNbjetons();

    public int getDelais() {
        return delais;
    }

    public int getEcart() {
        return ecart;
    }

    public boolean estUneActiviteRestreinte(){
        return false;
    }

    public void ajouterSuccesseur(EtapeIG etape){
        this.successeurs.add(etape);
    }

    public void retirerSuccesseur(EtapeIG e){
        this.successeurs.remove(e);
    }

    public ArrayList<EtapeIG> getSuccesseurs(){
        return this.successeurs;
    }

    public Color getCouleur() {
        return couleur;
    }
}
