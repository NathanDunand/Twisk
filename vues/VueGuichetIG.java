package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.Observateur;

import java.util.concurrent.ThreadLocalRandom;

public class VueGuichetIG extends VueEtapeIG implements Observateur {

    public VueGuichetIG(MondeIG monde, GuichetIG etapeIG) {
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

            WritableImage capture=this.snapshot(null,null);
            content.putImage(capture);
            db.setContent(content);
            event.consume();
        });

        this.fileAttente.setAlignment(Pos.CENTER_RIGHT);

        this.iconeEtape=new ImageView();

        this.getChildren().addAll(this.label, this.iconeEtape, this.fileAttente);
    }

    /**
     * Applique une image d'entrée sur la vue
     */
    public void setImageEntree(){
        this.iconeEtape.setImage(new Image("/twisk/ressources/images/entree.png"));
        this.iconeEtape.setFitWidth(TailleComposants.getInstance().getTailleIconeEtape());
        this.iconeEtape.setFitHeight(TailleComposants.getInstance().getTailleIconeEtape());
        VBox.setMargin(this.iconeEtape, new Insets(10,10,10,10));
    }

    /**
     * Applique une image de sortie sur la vue
     */
    public void setImageSortie(){
        this.iconeEtape.setImage(new Image("/twisk/ressources/images/sortie.png"));
        this.iconeEtape.setFitWidth(TailleComposants.getInstance().getTailleIconeEtape());
        this.iconeEtape.setFitHeight(TailleComposants.getInstance().getTailleIconeEtape());
        VBox.setMargin(this.iconeEtape, new Insets(10,10,10,10));
    }

    /**
     * N'applique aucune image
     */
    public void setImageDefaut(){
        this.iconeEtape=null;
    }

    /**
     * Applique le style graphique d'un guichet
     */
    @Override
    public void appliquerStyleDefaut() {
        //forme
        String borderColor="#FFFF";
        String backgroundInsets="0 0 0 0, 0, 0, 0";
        String backgroundRadius="3px";
        String borderRadius="3px";
        String backgroundColor="#ffb74d";
        String style="-fx-border-color:"+borderColor+" ;" +
                "-fx-background-insets: "+backgroundInsets+";" +
                " -fx-background-radius: "+backgroundRadius+";" +
                "-fx-background-color: "+backgroundColor+";" +
                "-fx-border-radius: "+borderRadius+";";

        //partie pour le label
        String color="black";
        String font="Quicksand Book";
        String alignment="center";
        String size="20";
        String labelStyle="-fx-text-fill:"+color+";" +
                "-fx-font-family:'"+font+"';" +
                "-fx-alignment:"+alignment+";" +
                "-fx-font-size: "+size+";";

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
        this.fileAttente.setStyle(styleZoneCli);
        this.fileAttente.setMinSize(TailleComposants.getInstance().getLargeurZoneClient(), TailleComposants.getInstance().getHauteurZoneClient());
        this.fileAttente.setMaxSize(TailleComposants.getInstance().getLargeurZoneClient(), TailleComposants.getInstance().getHauteurZoneClient());
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(this.fileAttente, new Insets(0,0,10,0));
        //pour ne pas que la zone d'activité ne réduise avec la partie zone client
        this.setMinSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);
        this.setMaxSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);

        this.label.setStyle(labelStyle);
        this.setStyle(style);
    }

    /**
     * Applique le style au guichet lorsqu'il est sélectionné
     */
    public void appliquerStyleSelectionne(){
        String borderColor="#FFFF";
        String borderWidth="1px";
        String borderRadius="3px";
        String backgroundInsets="0 0 0 0, 0, 0, 0";
        String backgroundRadius="3px";
        String backgroundColor="#ffe97d";
        String style="-fx-border-radius:"+borderRadius+";-fx-border-width:"+borderWidth+";-fx-border-color:"+borderColor+";-fx-background-insets: "+backgroundInsets+"; -fx-background-radius: "+backgroundRadius+";-fx-background-color: "+backgroundColor+"";

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
        this.fileAttente.setStyle(styleZoneCli);
        this.fileAttente.setMinSize(TailleComposants.getInstance().getLargeurZoneClient()*0.9, TailleComposants.getInstance().getHauteurZoneClient()*0.9);
        this.fileAttente.setMaxSize(TailleComposants.getInstance().getLargeurZoneClient()*0.9, TailleComposants.getInstance().getHauteurZoneClient()*0.9);
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(this.fileAttente, new Insets(0,0,10,0));
        //pour ne pas que la zone d'activité ne réduise avec la partie zone client
        this.setMinSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);
        this.setMaxSize(TailleComposants.getInstance().getLargeurZoneEtape(), TailleComposants.getInstance().getHauteurZoneEtape()*1.1);

        this.label.setStyle(labelStyle);
        this.setStyle(style);
    }

    @Override
    void ajouterCercle(Circle circle) {
        this.fileAttente.getChildren().add(circle);
        //TODO régler les marges
        double randomY = ThreadLocalRandom.current().nextDouble(0.0, this.fileAttente.getMaxHeight());
        double randomX = ThreadLocalRandom.current().nextDouble(0.0, this.fileAttente.getMaxWidth());
        circle.setRadius(4.0f);
        circle.relocate(randomX, randomY);
        HBox.setMargin(circle, new Insets(0,0,0,10));
    }
}