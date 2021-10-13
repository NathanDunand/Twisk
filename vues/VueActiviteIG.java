package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.FabriqueCouleur;
import twisk.outils.Observateur;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VueActiviteIG extends VueEtapeIG implements Observateur {

    public VueActiviteIG(MondeIG monde, EtapeIG etapeIG){
        super(monde,etapeIG);

        this.select=false;//par défaut rien de sélectionné

        this.setPrefSize(etapeIG.getLargeur(), etapeIG.getHauteur());
        int posX=etapeIG.getPosX();
        int posY=etapeIG.getPosY();
        this.relocate(posX,posY);

        this.setOnDragDetected(event->{
            Dragboard db=startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content=new ClipboardContent();
            //on place l'id dedans
            content.putString(this.etapeIG.getIdentifiant());
            //on place les coordonnées de l'étape dedans
            /*String x=Integer.toString(this.etapeIG.getPosX());
            String y=Integer.toString(this.etapeIG.getPosY());*/

            WritableImage capture=this.snapshot(null,null);
            content.putImage(capture);
            db.setContent(content);
            event.consume();
        });
        this.iconeEtape=new ImageView();

        this.getChildren().addAll(this.label, this.iconeEtape, this.zoneClient);
    }

    /**
     * Applique le style graphique d'une activité
     */
    @Override
    public void appliquerStyleDefaut() {
        String borderColor="#FFFF";
        String borderWidth="1px";
        String borderRadius="3px";
        String backgroundInsets="0 0 0 0, 0, 0, 0";
        String backgroundRadius="3px";
        String backgroundColor="#64b5f6";
        String style="-fx-border-radius:"+borderRadius+";-fx-border-width:"+borderWidth+";-fx-border-color:"+borderColor+";-fx-background-insets: "+backgroundInsets+"; -fx-background-radius: "+backgroundRadius+";-fx-background-color: "+backgroundColor+"";

        //partie pour le label
        String color="black";
        String font="Quicksand Book";
        String alignment="center";
        String size="20";
        String labelStyle="-fx-text-fill:"+color+";-fx-font-family:'"+font+"';-fx-alignment:"+alignment+";-fx-font-size: "+size+";";

        //partie pour la zone d'affichage du client
        String borderColorZoneCli="#cececc";
        String borderWidthZoneCli="1px";
        String borderRadiusZoneCli="3px";
        String backgroundRadiusZoneCli="3px";
        String backgroundColorZoneCli="#e9e5ea";
        String styleZoneCli="-fx-border-radius:"+borderRadiusZoneCli+";-fx-border-width:"+borderWidthZoneCli+";" +
                "-fx-border-color:"+borderColorZoneCli+";" +
                "-fx-background-radius: "+backgroundRadiusZoneCli+";" +
                "-fx-background-color: "+backgroundColorZoneCli+"";
        this.zoneClient.setStyle(styleZoneCli);
        this.zoneClient.setMinSize(TailleComposants.getInstance().getLargeurZoneClient(), TailleComposants.getInstance().getHauteurZoneClient());
        this.zoneClient.setMaxSize(TailleComposants.getInstance().getLargeurZoneClient(), TailleComposants.getInstance().getHauteurZoneClient());
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(this.zoneClient, new Insets(0,0,0,0));
        //pour ne pas que la zone d'activité ne réduise avec la partie zone client
        this.setMinSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);
        this.setMaxSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);

        this.label.setStyle(labelStyle);
        this.setStyle(style);
    }

    /**
     * Applique le style à l'activité lorsqu'elle est sélectionnée
     */
    public void appliquerStyleSelectionne(){
        String borderColor="#FFFF";
        String borderWidth="1px";
        String borderRadius="3px";
        String backgroundRadius="3px";
        String backgroundColor="#9be7ff";
        String style="-fx-border-radius:"+borderRadius+";-fx-border-width:"+borderWidth+";-fx-border-color:"+borderColor+";-fx-background-radius: "+backgroundRadius+";-fx-background-color: "+backgroundColor+"";

        //partie pour le label
        String color="#919190";
        String font="Quicksand Book";
        String alignment="center";
        String size="17";
        String labelStyle="-fx-text-fill:"+color+";-fx-font-family:'"+font+"';-fx-alignment:"+alignment+";-fx-font-size: "+size+";";

        //partie pour la zone d'affichage du client
        String borderColorZoneCli="#cececc";
        String borderWidthZoneCli="1px";
        String borderRadiusZoneCli="3px";
        String backgroundRadiusZoneCli="3px";
        String backgroundColorZoneCli="#e9e5ea";
        String styleZoneCli="-fx-border-radius:"+borderRadiusZoneCli+";-fx-border-width:"+borderWidthZoneCli+";" +
                "-fx-border-color:"+borderColorZoneCli+";" +
                "-fx-background-radius: "+backgroundRadiusZoneCli+";" +
                "-fx-background-color: "+backgroundColorZoneCli+"";
        this.zoneClient.setStyle(styleZoneCli);
        this.zoneClient.setMinSize(TailleComposants.getInstance().getLargeurZoneClient()*0.9, TailleComposants.getInstance().getHauteurZoneClient()*0.9);
        this.zoneClient.setMaxSize(TailleComposants.getInstance().getLargeurZoneClient()*0.9, TailleComposants.getInstance().getHauteurZoneClient()*0.9);
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(this.zoneClient, new Insets(0,0,0,0));
        //pour ne pas que la zone d'activité ne réduise avec la partie zone client
        this.setMinSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);
        this.setMaxSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);

        this.label.setStyle(labelStyle);
        this.setStyle(style);
    }

    /**
     * Applique une image d'entrée sur la vue
     */
    public void setImageEntree(){
        this.iconeEtape.setImage(new Image("/twisk/ressources/images/entree.png"));
        this.iconeEtape.setFitWidth(TailleComposants.getInstance().getTailleIconeEtape());
        this.iconeEtape.setFitHeight(TailleComposants.getInstance().getTailleIconeEtape());
        VBox.setMargin(this.zoneClient, new Insets(10,10,10,10));
    }

    /**
     * Applique une image de sortie sur la vue
     */
    public void setImageSortie(){
        this.iconeEtape.setImage(new Image("/twisk/ressources/images/sortie.png"));
        this.iconeEtape.setFitWidth(TailleComposants.getInstance().getTailleIconeEtape());
        this.iconeEtape.setFitHeight(TailleComposants.getInstance().getTailleIconeEtape());
        VBox.setMargin(this.zoneClient, new Insets(10,10,10,10));
    }

    /**
     * N'applique aucune image
     */
    public void setImageDefaut(){
        this.iconeEtape=null;
    }

    @Override
    void ajouterCercle(Circle circle) {
        this.zoneClient.getChildren().add(circle);
        //TODO régler les marges
        double randomY = ThreadLocalRandom.current().nextDouble(0.0, this.zoneClient.getMaxHeight());
        double randomX = ThreadLocalRandom.current().nextDouble(0.0, this.zoneClient.getMaxWidth());
        circle.relocate(randomX, randomY);
        circle.setFill(FabriqueCouleur.getInstance().getCouleur());
    }
}
