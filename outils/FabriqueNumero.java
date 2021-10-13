package twisk.outils;

public class FabriqueNumero {
    int cptEtape;
    int cptSemaphore;
    private int numLibTwisk;

    private static FabriqueNumero instance = new FabriqueNumero();

    private FabriqueNumero(){
        cptEtape = 0;
        cptSemaphore = 1;
        numLibTwisk = 0;
    }

    public static FabriqueNumero getInstance(){
        return instance;
    }

    public int getNumeroEtape() {
        return cptEtape++;
    }

    public int getNumeroSemaphore() {
        return cptSemaphore++;
    }

    public int getNumLibTwiskSuivant() { numLibTwisk+=1;
    return numLibTwisk; }

    public int getNumLibTwisk() { return numLibTwisk; }

    public void reset() {
        cptEtape = 0;
        cptSemaphore = 1;
    }
}
