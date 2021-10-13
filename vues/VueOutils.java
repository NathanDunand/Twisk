package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.exceptions.GuichetException;
import twisk.exceptions.PasSortieEntreeException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.Observateur;

public class VueOutils extends TilePane implements Observateur {

    private final MondeIG monde;
    private final Button ajouterActivite;
    private final Button ajouterGuichet;
    private final Button simuler;
    private String styleSimuler;

    public VueOutils(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);
        this.ajouterActivite =new Button("");
        this.ajouterActivite.setTooltip(new Tooltip("Ajouter une activté"));
        this.ajouterGuichet = new Button("");
        this.ajouterGuichet.setTooltip(new Tooltip("Ajouter un guichet"));
        this.simuler = new Button();
        this.simuler.setTooltip(new Tooltip("Démarrer la simulation"));

        Image plusActivite=new Image(getClass().getResourceAsStream("/twisk/ressources/images/plus_activite.png"));
        Image plusGuichet=new Image(getClass().getResourceAsStream("/twisk/ressources/images/plus_guichet.png"));
        Image startImg = new Image(getClass().getResourceAsStream("/twisk/ressources/images/start.png"));
        ImageView icone1 =new ImageView(plusActivite);
        ImageView icone2 = new ImageView(plusGuichet);
        ImageView iconeStart = new ImageView(startImg);
        icone1.setFitHeight(TailleComposants.getInstance().getTailleIconeBouton());
        icone1.setFitWidth(TailleComposants.getInstance().getTailleIconeBouton());
        icone2.setFitHeight(TailleComposants.getInstance().getTailleIconeBouton());
        icone2.setFitWidth(TailleComposants.getInstance().getTailleIconeBouton());
        iconeStart.setFitHeight(TailleComposants.getInstance().getTailleIconeBouton());
        iconeStart.setFitWidth(TailleComposants.getInstance().getTailleIconeBouton());
        this.ajouterActivite.setGraphic(icone1);
        this.ajouterActivite.setOnAction(event->this.monde.ajouter("Activité"));
        this.ajouterGuichet.setGraphic(icone2);
        this.ajouterGuichet.setOnAction(actionEvent -> this.monde.ajouter("Guichet"));
        this.simuler.setGraphic(iconeStart);
        this.simuler.setOnAction(actionEvent -> {
            try {
                this.monde.simuler();
            } catch (PasSortieEntreeException | GuichetException e) {
                Alert a=new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.getChildren().addAll(this.ajouterActivite, this.ajouterGuichet, this.simuler);
        this.appliquerStyle();

    }

    private void appliquerStyle(){
        //style général
        String minHeight=String.valueOf(TailleComposants.getInstance().getHauteurVueOutils());
        String backgroundColor="white";
        String style="-fx-min-height: "+minHeight+";-fx-background-color: "+backgroundColor+";";

        //style des boutons
        String borderColor="none";
        String borderWidth="1px";
        String backgroundColorBtn="none";
        String borderRadius="3px";
        String backgroundRadius="3px";

        String styleAActivite="-fx-border-color: "+borderColor+";" +
                "-fx-border-width: "+borderWidth+";" +
                "-fx-background-color: "+backgroundColorBtn+";" +
                "-fx-border-radius: "+borderRadius+";" +
                "-fx-background-radius: "+backgroundRadius+";";
        String styleAGuichet="-fx-border-color: "+borderColor+";" +
                "-fx-border-width: "+borderWidth+";" +
                "-fx-background-color: "+backgroundColorBtn+";" +
                "-fx-border-radius: "+borderRadius+";" +
                "-fx-background-radius: "+backgroundRadius+";";
        styleSimuler="-fx-border-color: "+borderColor+";" +
                "-fx-border-width: "+borderWidth+";" +
                "-fx-background-color: "+backgroundColorBtn+";" +
                "-fx-border-radius: "+borderRadius+";" +
                "-fx-background-radius: "+backgroundRadius+";";

        this.ajouterActivite.setStyle(styleAActivite);
        this.ajouterGuichet.setStyle(styleAGuichet);
        this.simuler.setStyle(styleSimuler);
        this.setStyle(style);
    }

    @Override
    public void reagir() {
        Platform.runLater(() -> {
            ImageView img;
            if (this.monde.isSimulation()){
                Image image = new Image(getClass().getResourceAsStream("/twisk/ressources/images/stop.png"));
                img = new ImageView(image);
                this.simuler.setOnAction(actionEvent -> this.monde.stopSimulation());
            } else {
                img = new ImageView(new Image(getClass().getResourceAsStream("/twisk/ressources/images/start.png")));
                this.simuler.setOnAction(actionEvent -> {
                    try {
                        this.monde.simuler();
                    } catch (PasSortieEntreeException | GuichetException e) {
                        Alert a=new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                        a.showAndWait();
                    }
                });
            }
            img.setFitHeight(TailleComposants.getInstance().getTailleIconeBouton());
            img.setFitWidth(TailleComposants.getInstance().getTailleIconeBouton());
            this.simuler.setGraphic(img);
            this.simuler.setStyle(styleSimuler);
        });
    }
}
