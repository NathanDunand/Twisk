package twisk.mondeIG;

public class TailleComposants {
    private static TailleComposants instance=new TailleComposants();
    int largeurEtape;
    int hauteurEtape;
    int largeurGuichet;
    int largeurLabel;

    int hauteurLabel;

    int fleche;
    int ligne;

    int rond;

    int hauteurVueOutils;

    int tailleIconeBouton;

    int hauteurZoneClient;
    int largeurZoneClient;

    int largeurZoneEtape;
    int hauteurZoneEtape;

    int tailleIconeEtape;

    int tailleFenetreHeight;
    int tailleFenetreWidth;

    private TailleComposants(){
        //centralisation des dimensions
        this.hauteurZoneClient=50;
        this.largeurZoneClient=150;

        this.hauteurEtape=70;
        this.largeurEtape=170;
        this.largeurGuichet = this.largeurEtape;

        this.hauteurLabel=30;
        this.largeurLabel=this.largeurEtape;

        this.fleche=25;//largeur de la flèche pour les arcs
        this.ligne=10;//largeur de la ligne pour les arcs

        this.rond=10;//taille du RAYON du rond pour les points de contrôle

        this.hauteurVueOutils=70;
        this.tailleIconeBouton=40;

        this.largeurZoneEtape=this.largeurEtape;
        this.hauteurZoneEtape=this.hauteurZoneClient+this.hauteurEtape;

        this.tailleIconeEtape=20;

        int tailleFenetreHeight=1280;
        int tailleFenetreWidth=720;
    }

    public int getTailleFenetreHeight(){
        return this.tailleFenetreHeight;
    }

    public int getTailleFenetreWidth(){
        return this.tailleFenetreWidth;
    }

    public int getTailleIconeEtape() {
        return tailleIconeEtape;
    }

    public static TailleComposants getInstance(){
        return instance;
    }

    public int getHauteurEtape(){
        return this.hauteurEtape;
    }

    public int getLargeurEtape(){
        return this.largeurEtape;
    }

    public int getLargeurGuichet(){ return this.largeurGuichet; }

    public int getHauteurLabel(){
        return this.hauteurLabel;
    }

    public int getFleche(){
        return this.fleche;
    }

    public int getLargeurLabel(){
        return this.largeurLabel;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getRond(){
        return this.rond;
    }

    public int getHauteurVueOutils(){
        return this.hauteurVueOutils;
    }

    public int getTailleIconeBouton(){
        return this.tailleIconeBouton;
    }

    public int getHauteurZoneClient(){
        return this.hauteurZoneClient;
    }

    public int getLargeurZoneClient(){
        return this.largeurZoneClient;
    }

    public int getLargeurZoneEtape() {
        return largeurZoneEtape;
    }

    public int getHauteurZoneEtape() {
        return hauteurZoneEtape;
    }
}
