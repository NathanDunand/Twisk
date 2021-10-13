package twisk.vues;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Circle;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.Observateur;

public class VuePointDeControleIG extends Circle implements Observateur {
    private MondeIG monde;
    private PointDeControleIG pt;

    public VuePointDeControleIG(MondeIG monde, PointDeControleIG pt){
        super();
        this.monde=monde;
        this.pt=pt;

        this.setCenterX(this.pt.getPosCentreX());
        this.setCenterY(this.pt.getPosCentreY());
        this.appliquerStyle();

        this.setOnMouseClicked(event -> {
            try {
                this.monde.ajouterArc(this.pt);
            } catch (TwiskException e) {
                Alert a=new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
    }

    @Override
    public void reagir() {

    }

    /**
     * Applique le style au point de contrôle
     */
    private void appliquerStyle(){
        this.setRadius(TailleComposants.getInstance().getRond());
        String style="";
        String color="ffff";
        if(this.pt.estRattacheActivite()){
            //style pour un pt rattaché à une activité
            color="#2286c3";
        } else if(this.pt.estRattacheGuichet()){
            //style pour un pt rattaché à un guichet
            color="#c88719";
        }
        style="-fx-fill: "+color+";";
        this.setStyle(style);
    }
}
