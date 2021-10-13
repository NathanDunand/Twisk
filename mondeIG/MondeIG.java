package twisk.mondeIG;

import twisk.exceptions.*;
import twisk.monde.*;
import twisk.outils.*;
import twisk.simulation.GestionnaireClients;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG>, Observateur{

    private final HashMap<String, EtapeIG> etapesIG;
    private final ArrayList<ArcIG> arcs;
    private final ArrayList<EtapeIG> selectedEtape;
    private final ArrayList<ArcIG> arcSelectionnees;
    private PointDeControleIG p1;
    private final ArrayList<EtapeIG> sorties;
    private final ArrayList<EtapeIG> entrees;
    private CorrespondanceEtapes correspondance;
    private Object simulation;
    private int nbClients;

    public MondeIG() {
        super();
        this.etapesIG = new HashMap<>();
        String identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        this.etapesIG.put(identifiant, new ActiviteIG("Activité", identifiant, TailleComposants.getInstance().getLargeurEtape(), TailleComposants.getInstance().getHauteurEtape()));
        this.obs = new ArrayList<>();
        this.arcs = new ArrayList<>();
        this.arcSelectionnees = new ArrayList<>();
        this.sorties= new ArrayList<>();
        this.entrees= new ArrayList<>();
        this.selectedEtape = new ArrayList<>();
    }

    public void simuler() throws PasSortieEntreeException, GuichetException {
        //check des conditions pour lancer une simulation
        if(this.entrees.isEmpty() || this.sorties.isEmpty()){
            //s'il manque une entrée et/ou une sortie
            throw new PasSortieEntreeException("Absence d'une entrée et/ou d'une sortie");
        }
        //Ajout de tous les successeurs.
        for (ArcIG a : this.arcs){
            EtapeIG depart = a.getP1().getEtapeIG();
            EtapeIG arrive = a.getP2().getEtapeIG();
            depart.ajouterSuccesseur(arrive);
        }

        //QUESTION 2
        //pb déjà traités : on ne peut pas faire un arc sur un même point, ni sur un point de la même activité
        //-peut-être des arbres interdits : une activité ne peux pas donner sur deux autres
        //-monde sans SasEntree ni SasSortie
        //-pas d'activité isolées
        //-pas plusieurs guichets à la suite ?

        //tester si le MondeIG est correct
        verifierMondeIG();
        //créer le monde par l'appel de la fonction
        Monde m = creerMonde();
        //demander la simulation sur ce monde
        ClassLoaderPerso cl = new ClassLoaderPerso(this.getClass().getClassLoader());

        try{
            Class<?> c = cl.loadClass("twisk.simulation.Simulation");
            cl.loadClass("twisk.simulation.Simulation$1");

            simulation = c.getDeclaredConstructor().newInstance();

            Method setNbClients = c.getMethod("setNbClients", Integer.class);
            setNbClients.invoke(simulation, (Integer)this.nbClients);

            Method ajouterObservateur = c.getMethod("ajouterObservateur", twisk.outils.Observateur.class);
            ajouterObservateur.invoke(simulation, this);

            //méthode simuler
            Method simuler=c.getMethod("simuler", twisk.monde.Monde.class);
            FabriqueNumero.getInstance().reset();
            simuler.invoke(simulation, m);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    public GestionnaireClients getGestionnaireClients(){
        try {
            Method getGestionnaire = simulation.getClass().getDeclaredMethod("getGestionnaireClients");
            return (GestionnaireClients) getGestionnaire.invoke(simulation);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void verifierMondeIG() throws PasSortieEntreeException, GuichetException {
        // Vérification entrée-sortie
        if (this.entrees.isEmpty()){
            throw new PasSortieEntreeException("Il n'y a pas d'entrée");
        } else if (this.sorties.isEmpty()){
            throw new PasSortieEntreeException("il n'y a pas de sortie");
        }

        // Vérification activité qui suit un guichet et transformation en activité restreinte.
        for (EtapeIG e : this.etapesIG.values()){
            if (e.estUnGuichet()){
                if (e.getSuccesseurs().size() == 1){
                    if (e.getSuccesseurs().get(0).estUnGuichet()){
                        throw new GuichetException("On ne peut pas creer un guichet après un guichet");
                    } else{
                        ActiviteIG activite = (ActiviteIG) e.getSuccesseurs().get(0);
                        activite.setActiviteRestreinte(true);
                    }
                }
            }
        }
    }

    private Monde creerMonde(){
        //problème (?) je crois qu'il faut faire la même chose avec les arcs

        Monde monde = new Monde();

        this.correspondance = new CorrespondanceEtapes();
        for (EtapeIG e : this.etapesIG.values()){
            if (e.estUneActivite()){
                Activite act = new Activite(e.getNom(), e.getDelais(), e.getEcart());
                this.correspondance.ajouterEtape(e, act);
                monde.ajouter(act);
                //pour chaque point de contrôle de chaque étape on ajoute l'arc au monde
                for(PointDeControleIG pt:e.getPdcigs()){
                    if(pt.aUnArc()){
                        //si le point a un arcIG il faut l'ajouter au modèle
                        //on créer un nouvel Arc pour le modèle
                        //la première activité de l'arc est celle d'où il part, donc celle que l'on regarde actuellement
                        //la secondes et le premier successeur
                        /*Arc arc=new Arc(act, e.getSuccesseurs().get(0));
                        this.correspondance.ajouterArc(pt.getA(), arc);*/
                    }
                }
            } else if(e.estUneActiviteRestreinte()){
                ActiviteRestreinte act = new ActiviteRestreinte(e.getNom(), e.getDelais(), e.getEcart());
                this.correspondance.ajouterEtape(e, act);
                monde.ajouter(act);
            } else if (e.estUnGuichet()){
                GuichetIG g = (GuichetIG) e;
                Guichet guichet = new Guichet(e.getNom(), g.getNbjetons());
                this.correspondance.ajouterEtape(e, guichet);
                monde.ajouter(guichet);
            }
        }

        for (EtapeIG e : this.etapesIG.values()){
            for (EtapeIG successeur : e.getSuccesseurs()){
                Etape etape = this.correspondance.get(e);
                etape.ajouterSuccesseur(this.correspondance.get(successeur));
            }
        }

        for (EtapeIG e : this.entrees){
            monde.aCommeEntree(this.correspondance.get(e));
        }

        for (EtapeIG e : this.sorties){
            monde.aCommeSortie(this.correspondance.get(e));
        }

        return monde;
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.obs.add(o);
    }

    public void ajouter(String type) {
        String identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        if (type.equals("Activité")) {
            this.etapesIG.put(identifiant, new ActiviteIG("Activité " + identifiant, identifiant, TailleComposants.getInstance().getLargeurEtape(), TailleComposants.getInstance().getHauteurEtape()));
        } else if (type.equals("Guichet")){
            this.etapesIG.put(identifiant, new GuichetIG("Guichet " + identifiant, identifiant, TailleComposants.getInstance().getLargeurGuichet(), TailleComposants.getInstance().getHauteurEtape()));
        }
        this.notifierObservateurs();
    }

    public void ajouterArc(PointDeControleIG p) throws TwiskException {
        if (this.p1 != null) {//on lie les deux arcs
            if (this.p1 == p) {
                this.p1 = null;
                throw new PointsIdentiques("Les deux points sélectionnés sont identiques");
            } else if (this.p1.getEtapeIG() == p.getEtapeIG()) {//vérification de si les deux points appartiennent à la même Etape
                this.p1 = null;
                throw new MemeClasse("Les deux points appartiennent à la même étape");
            } else {
                this.arcs.add(new ArcIG(this.p1, p, this.p1.getEtapeIG(), p.getEtapeIG()));//liaison
            }
            this.p1 = null;
            this.notifierObservateurs();
        } else {
            this.p1 = p;
        }
    }

    public void renommerActivite(String s) {
        if (this.selectedEtape.size() == 1) {
            //on ne peut renommer qu'une seule activité
            EtapeIG e = this.selectedEtape.get(0);
            //on set son nom ici ensuite
            e.setNom(s);
            //on vide l'AL temporaire
            this.selectedEtape.clear();
        }
        this.notifierObservateurs();
    }

    public EtapeIG getEtapeById(String id) {
        return this.etapesIG.get(id);
    }

    public void ajouterSortie() throws PasSortieEntreeException{
        this.sorties.clear();
        //toutes les étapes de cette AL sont donc des sorties
        for(EtapeIG e: this.selectedEtape){
            if(e.estUnGuichet()){
                //erreur, on ne peut pas avoir un guichet comme sortie d'un monde
                throw new PasSortieEntreeException("Un guichet ne peut pas être une sortie.");
            }
            e.estUneSortie();
            this.sorties.add(e);
        }
        this.selectedEtape.clear();
        notifierObservateurs();
    }

    public void setDelais(String s) throws TwiskException{
        try{
            int delai=Integer.parseInt(s);
            for(EtapeIG e:this.selectedEtape){
                if(delai<e.getEcart()){
                    //erreur : l'écart ne peux pas être plus grand que le délai
                    throw new SetDelaiEcart("Ecart supérieur au délai");
                } else {
                    e.setDelais(s);
                }
            }
        } catch (NumberFormatException e){
            throw new SetDelaiEcart("Entrée non valide, entrez un nombre");
        }
    }

    public void setEcart(String s) throws TwiskException{
        try{
            int ecart=Integer.parseInt(s);
            //erreur ici et dans setDelai, les valeurs sont modifiées alors que ça ne devrait pas ...
            for(EtapeIG e:this.selectedEtape){
                if(ecart>e.getDelais()){
                    //erreur : l'écart ne peux pas être plus grand que le délai
                    throw new SetDelaiEcart("Ecart supérieur au délai");
                } else {
                    e.setEcart(s);
                }
            }
        } catch (NumberFormatException e){
            throw new SetDelaiEcart("Entrée non valide, entrez un nombre");
        }
    }

    public void setNbClients(int n){
        this.nbClients=n;
    }

    public boolean isSimulation(){
        if (this.simulation == null){
            return false;
        } else {
            try {
                Method isSimulation = simulation.getClass().getDeclaredMethod("isSimulation");
                return (boolean) isSimulation.invoke(simulation);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public void setNbJetons(String s) throws TwiskException{
        try{
            int nbJetons=Integer.parseInt(s);
            if(nbJetons<0){
                throw new SetDelaiEcart("Le nombre de jetons doit être positif");
            }
            for(EtapeIG e:this.selectedEtape){
                //pour chaque étape (guichet) on applique le changement
                //NB pas besoin de vérifier si ce sont bien des guichets, le menu est grisé si ce n'est pas le cas
                e.setNbjetons(nbJetons);
                //System.out.println("jetons: "+e.getNbjetons());
            }
        } catch (NumberFormatException e){
            throw new SetDelaiEcart("Entrée non valide, entrez un nombre");
        }
    }

    public void ajouterEntree(){
        this.entrees.clear();
        this.entrees.addAll(this.selectedEtape);
        //toutes les étapes de cette AL sont donc des entrées
        for(EtapeIG e:this.entrees){
            e.estUneEntree();
        }
        this.selectedEtape.clear();
        notifierObservateurs();
    }

    public void supprimerSelection() {
        Iterator<Map.Entry<String, EtapeIG>> it = this.etapesIG.entrySet().iterator();
        //suppression des activités
        while (it.hasNext()) {
            Map.Entry<String, EtapeIG> entry = it.next();
            for (EtapeIG e : this.selectedEtape) {
                if (entry.getKey().equals(e.getIdentifiant())) {
                    //on supprime les activités
                    it.remove();
                }
            }
        }

        //suppression des arcs
        Iterator<ArcIG> it2 = this.arcs.iterator();
        while (it2.hasNext()) {
            ArcIG aIG = it2.next();
            EtapeIG depart = aIG.getP1().getEtapeIG();
            EtapeIG arrive = aIG.getP2().getEtapeIG();
            depart.retirerSuccesseur(arrive);
            if(aIG.isSelected()){
                //si vrai alors il appartient à l'AL d'arcs sélectionnés
                it2.remove();
            }
        }

        //on vide l'AL temporaire
        this.selectedEtape.clear();
        this.arcSelectionnees.clear();
        this.notifierObservateurs();
    }

    public ArrayList<ArcIG> getArcSelectionnees(){
        return this.arcSelectionnees;
    }

    public void effacerSelection(){
        this.selectedEtape.clear();
        this.arcSelectionnees.clear();
        this.notifierObservateurs();
    }

    public boolean isEtapeSelected(EtapeIG etape){
        return this.selectedEtape.contains(etape);
    }

    /**
     * Selectionne une étape, la désélectionne si déjà sélectionnée.
     * @param etape étape à sélectionner
     */
    public void selectionEtape(EtapeIG etape){
        boolean estPresent = false;
        for (EtapeIG a : this.selectedEtape){
            if (a == etape) {
                estPresent = true;
                break;
            }
        }
        if (estPresent){
            selectedEtape.remove(etape);
        } else {
            selectedEtape.add(etape);
        }
        notifierObservateurs();
    }

    public void ajouterArcSelection(ArcIG aIG) {
        this.arcSelectionnees.add(aIG);
    }

    public void retirerArcSelection(ArcIG aIG){
        this.arcSelectionnees.remove(aIG);
    }

    public void notifierObservateurs() {
        for (Observateur o : this.obs) {
            o.reagir();
        }
    }

    public ArrayList<EtapeIG> getEtapeSelectionnees() {
        return this.selectedEtape;
    }

    public ArrayList<ArcIG> getArcs(){
        return this.arcs;
    }

    @Override
    public Iterator<EtapeIG> iterator() {
        return this.etapesIG.values().iterator();
    }

    public Iterator<ArcIG> iteratorArc(){
        return this.arcSelectionnees.iterator();
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        for(EtapeIG e : this){
            s.append(e.toString()).append("\n");
        }
        return s.toString();
    }

    public Object getSimulation(){
        return simulation;
    }

    public void stopSimulation(){
        GestionnaireThreads.getInstance().detruireTout();
    }

    @Override
    public void reagir() {
        notifierObservateurs();
    }

    public Etape getEtapeFromEtapeIG(EtapeIG etapeIG){
        return this.correspondance.get(etapeIG);
    }
}
