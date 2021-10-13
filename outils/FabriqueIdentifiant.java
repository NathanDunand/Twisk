package twisk.outils;

public class FabriqueIdentifiant {
    int noEtape;
    int noArc;
    private static FabriqueIdentifiant instance=new FabriqueIdentifiant();

    private FabriqueIdentifiant(){
        this.noEtape=0;
        this.noArc=0;
    }

    public static FabriqueIdentifiant getInstance(){
        return instance;
    }

    public String getIdentifiantEtape(){
        return String.valueOf(this.noEtape++);
    }

    public String getIdentifiantArc(){
        return String.valueOf(this.noArc++);
    }
}
