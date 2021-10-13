package twisk.vues;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.FabriqueCouleur;
import twisk.outils.Observateur;

public abstract class VueEtapeIG extends VBox implements Observateur {
    protected MondeIG monde;
    protected EtapeIG etapeIG;
    protected Label label;
    protected boolean select;
    protected Pane zoneClient;
    protected HBox fileAttente;
    protected ImageView iconeEtape;
    protected boolean isEntree;
    protected boolean isSortie;

    public VueEtapeIG(MondeIG monde, EtapeIG etapeIG){
        super();
        this.isEntree=false;
        this.isSortie=false;
        this.monde=monde;
        this.etapeIG=etapeIG;
        this.label=new Label(this.etapeIG.toString());
        this.zoneClient=new Pane();
        this.fileAttente=new HBox();
        //le label fait la taille du composant : plus facile à centrer
        this.label.setMinHeight(TailleComposants.getInstance().getHauteurLabel());
        this.label.setMinWidth(TailleComposants.getInstance().getLargeurLabel());

        //ajout de l'écouteur qui permet de sélectionner une Etape
        this.setOnMouseClicked(event-> this.monde.selectionEtape(this.etapeIG));

        if(this.monde.isEtapeSelected(this.etapeIG)){//set style si une act est sélectionnée
            this.appliquerStyleSelectionne();
        } else {
            this.appliquerStyleDefaut();
        }
    }

    /**
     * Applique le style par défaut à l'étape
     */
    abstract void appliquerStyleDefaut();

    /**
     * Applique le style à l'étape lorsqu'elle est sélectionnée
     */
    abstract void appliquerStyleSelectionne();

    /**
     * Ajoute un client à l'étape
     * @param circle cercle représentant le client.
     */
    abstract void ajouterCercle(Circle circle);

    @Override
    public void reagir() {
        
    }

    abstract void setImageEntree();

    abstract void setImageSortie();

    abstract void setImageDefaut();
}
