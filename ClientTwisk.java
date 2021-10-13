package twisk;

import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.FabriqueNumero;
import twisk.simulation.Simulation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {

    private ClassLoaderPerso cl;

    public ClientTwisk(){
        Monde monde = new Monde();

        Guichet guichet = new Guichet("Guichet au toboggan", 2);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);

        Guichet g2=new Guichet("guichet interm",2);

        Activite interm = new Activite("interm",10,2);

        Guichet guichetZoo = new Guichet("Guichet au zoo", 1);
        ActiviteRestreinte zoo = new ActiviteRestreinte("balade au zoo", 3, 1);

        guichet.ajouterSuccesseur(tob);
        tob.ajouterSuccesseur(g2);
        g2.ajouterSuccesseur(interm);
        interm.ajouterSuccesseur(guichetZoo);
        guichetZoo.ajouterSuccesseur(zoo);

        monde.ajouter(zoo, tob, guichetZoo, guichet, interm, g2);

        monde.aCommeEntree(guichet);
        monde.aCommeSortie(zoo);

        Simulation s = new Simulation();
        s.setNbClients(5);
        //s.simuler(monde);

        this.cl=new ClassLoaderPerso(this.getClass().getClassLoader());

        try{
            Class<?> c=this.cl.loadClass("twisk.simulation.Simulation");
            //récupération du constructeur par défaut
            this.cl = null;
            System.gc();
            Object o = c.newInstance();

            //méthode setNbClients
            Method setNbClients =c.getMethod("setNbClients", int.class);
            setNbClients.invoke(o,5);//paramètre de test

            //méthode simuler
            Method simuler=c.getMethod("simuler", Monde.class);
            FabriqueNumero.getInstance().reset();
            simuler.invoke(o,cinema2());

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
            System.out.println(e.getMessage());
        }

        this.cl=new ClassLoaderPerso(this.getClass().getClassLoader());

        try{
            Class<?> c=this.cl.loadClass("twisk.simulation.Simulation");
            //récupération du constructeur par défaut
            this.cl = null;
            System.gc();
            Object o = c.newInstance();

            //méthode setNbClients
            Method setNbClients =c.getMethod("setNbClients", int.class);
            setNbClients.invoke(o,5);//paramètre de test

            //méthode simuler
            Method simuler=c.getMethod("simuler", Monde.class);
            FabriqueNumero.getInstance().reset();
            simuler.invoke(o,tram());

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
            System.out.println(e.getMessage());
        }




    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientTwisk clT=new ClientTwisk();
    }

    public static void testClient() throws IOException, InterruptedException {
        Monde monde = new Monde();

        Guichet guichet = new Guichet("Guichet au toboggan", 2);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);

        Activite interm = new Activite("interm",10,2);

        Guichet guichetZoo = new Guichet("Guichet au zoo", 1);
        ActiviteRestreinte zoo = new ActiviteRestreinte("balade au zoo", 3, 1);

        guichet.ajouterSuccesseur(tob);
        tob.ajouterSuccesseur(interm);
        interm.ajouterSuccesseur(guichetZoo);
        guichetZoo.ajouterSuccesseur(zoo);

        monde.ajouter(zoo, tob, guichetZoo, guichet, interm);

        monde.aCommeEntree(guichet);
        monde.aCommeSortie(zoo);

        Simulation s = new Simulation();
        s.setNbClients(5);
        s.simuler(monde);
        FabriqueNumero.getInstance().reset();
    }

    public static Monde tram() {
        Monde monde = new Monde();
        Activite a = new Activite("Guichet tram", 10, 2);
        Activite a1 = new Activite("test", 10, 2);

        a.ajouterSuccesseur(a1);


        monde.ajouter(a,a1);
        monde.aCommeEntree(a);
        monde.aCommeSortie(a1);

        return monde;
    }

    public static Monde cinema2() {
        Monde monde = new Monde();
        Activite a = new Activite("Guichet ciné", 10, 2);
        Activite a1 = new Activite("test", 10, 2);

        a.ajouterSuccesseur(a1);


        monde.ajouter(a,a1);
        monde.aCommeEntree(a);
        monde.aCommeSortie(a1);

        return monde;
    }

    public static void cinema() throws IOException, InterruptedException {
        Monde monde = new Monde();
        Guichet fbillet = new Guichet("File Billetterie");
        Guichet fsalle1 = new Guichet("File salle 1");
        Guichet fsalle2 = new Guichet("File salle 2");
        Guichet fsortie1 = new Guichet("File sortie 1");
        Guichet fsortie2 = new Guichet("File sortie 2");
        Activite salle1 = new Activite("Salle 1");
        Activite salle2 = new Activite("Salle 2");

        fbillet.ajouterSuccesseur(fsalle1);
        fbillet.ajouterSuccesseur(fsalle2);
        fsalle1.ajouterSuccesseur(salle1);
        fsalle2.ajouterSuccesseur(salle2);
        salle2.ajouterSuccesseur(fsortie2);
        salle1.ajouterSuccesseur(fsortie1);

        monde.aCommeEntree(fbillet);
        monde.aCommeSortie(fsortie1,fsortie2);
        monde.ajouter(fbillet,fsalle1,fsalle2,fsortie1,fsortie2,salle1,salle2);

        Simulation s = new Simulation();
        s.simuler(monde);
        FabriqueNumero.getInstance().reset();
    }

    public static void ski() throws IOException, InterruptedException {
        Monde monde = new Monde();
        Guichet gforfait = new Guichet("Guichet forfaits");
        Guichet fsiege1 = new Guichet("File télésiège");
        Guichet ftele1 = new Guichet("File télécabine");
        Activite piste1 = new Activite("Piste 1");
        Activite piste2 = new Activite("Piste 2");
        Activite piste3 = new Activite("Piste 3");

        gforfait.ajouterSuccesseur(ftele1,fsiege1);
        fsiege1.ajouterSuccesseur(piste1);
        ftele1.ajouterSuccesseur(piste2,piste3);
        piste2.ajouterSuccesseur(piste1);
        piste3.ajouterSuccesseur(ftele1);

        monde.aCommeEntree(gforfait);
        monde.aCommeSortie(piste1);
        monde.ajouter(gforfait,fsiege1,ftele1,piste1,piste2,piste3);

        (new Simulation()).simuler(monde);
        FabriqueNumero.getInstance().reset();
    }

}
