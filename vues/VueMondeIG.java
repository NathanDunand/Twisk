package twisk.vues;

import javafx.application.Platform;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import twisk.mondeIG.*;
import twisk.outils.Observateur;
import java.util.ArrayList;

public class VueMondeIG extends Pane implements Observateur {
    private final MondeIG monde;

    public VueMondeIG(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);
        for (EtapeIG e : this.monde) {//ajout de toutes les Etapes du Monde
            VueEtapeIG vueEtapeIG = new VueActiviteIG(this.monde, e);
            this.getChildren().add(vueEtapeIG);
        }
        this.setOnDragOver(event->{
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        this.setOnDragDropped(event->{
            Dragboard db=event.getDragboard();
            String s= db.getString();
            //déplacement véritable de l'activité
            EtapeIG e=this.monde.getEtapeById(s);
            e.setPosX((int)event.getX()-e.getLargeur()/2);
            e.setPosY((int)event.getY()-e.getHauteur()/2);

            event.setDropCompleted(true);
            event.consume();
            this.reagir();
        });
        this.appliquerStyle();
    }

    private void appliquerStyle(){
        String backgroundColor="#e9e5ea";
        String style="-fx-background-color: "+backgroundColor+";";

        this.setStyle(style);
    }

    private void afficherCercles(VueEtapeIG vueEtape, EtapeIG etape){
        int nbClients;
        if (this.monde.isSimulation()){
            nbClients = this.monde.getGestionnaireClients().getNbClient(this.monde.getEtapeFromEtapeIG(etape));
            for (int j = 0; j < nbClients; j++){
                Circle circle = new Circle();
                circle.setRadius(2.0f);
                vueEtape.ajouterCercle(circle);
            }
        }
    }

    @Override
    public void reagir() {

        Pane panneau = this;
        MondeIG monde = this.monde;

        Runnable command = () -> {
            panneau.getChildren().clear();
            //les arcs
            ArrayList<ArcIG> arcs= monde.getArcs();
            for(ArcIG a:arcs){
                VueArcIG vag=new VueArcIG(monde,a);
                panneau.getChildren().add(vag);
            }

            //afficher toutes les étapes
            for(EtapeIG e: monde){
                for(PointDeControleIG pt:e){//pour chaque ptdeControle
                    VuePointDeControleIG vuePt=new VuePointDeControleIG(monde,pt);
                    panneau.getChildren().add(vuePt);
                }
                if (e.estUnGuichet()){
                    VueGuichetIG vueGuichet = new VueGuichetIG(monde, (GuichetIG) e);
                    //images pour entrées et sorties
                    if(e.isEntree()){
                        e.estUneEntree();
                        vueGuichet.setImageEntree();
                    } else if(e.isSortie()) {
                        e.estUneSortie();
                        vueGuichet.setImageSortie();
                    }

                    panneau.getChildren().add(vueGuichet);
                    afficherCercles(vueGuichet, e);
                } else {
                    VueEtapeIG vueEtapeIG= new VueActiviteIG(monde,e);
                    panneau.getChildren().add(vueEtapeIG);
                    afficherCercles(vueEtapeIG, e);

                    //images pour entrées et sorties
                    if(e.isEntree()) {
                        vueEtapeIG.setImageEntree();
                    } else if(e.isSortie()) {
                        vueEtapeIG.setImageSortie();
                    }
                }
            }
        };

        if (Platform.isFxApplicationThread()){
            command.run();
        } else {
            Platform.runLater(command);
        }

    }
}