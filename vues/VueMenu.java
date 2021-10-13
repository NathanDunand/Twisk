package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import twisk.exceptions.InputException;
import twisk.exceptions.PasSortieEntreeException;
import twisk.exceptions.TwiskException;
import twisk.monde.Etape;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.Observateur;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;
    private Menu fichier;
    private MenuItem quitter;
    private MenuItem changerNbJetons;
    private Menu edition;
    private MenuItem supprimerSelection;
    private MenuItem renomer;
    private MenuItem effacerSelection;
    private Menu mondeMenu;
    private MenuItem entree;
    private MenuItem sortie;
    private Menu param;
    private MenuItem d;
    private MenuItem e;
    private MenuItem nbClients;

    public VueMenu(MondeIG monde){
        super();
        this.monde=monde;
        this.monde.ajouterObservateur(this);

        this.fichier=new Menu("Fichier");
        this.quitter=new MenuItem("Quitter");
        this.quitter.setOnAction(event->Platform.exit());
        this.fichier.getItems().add(this.quitter);

        this.edition=new Menu("Edition");
        this.edition.setOnShowing(event->this.reagir());//pour disable ou pas le MenuItem Renommer
        this.supprimerSelection=new MenuItem("Supprimer la sélection");
        this.effacerSelection=new MenuItem("Effacer la sélection");
        this.renomer=new MenuItem("Renommer la sélection");
        this.supprimerSelection.setOnAction(event->this.monde.supprimerSelection());
        this.effacerSelection.setOnAction(event->this.monde.effacerSelection());
        this.renomer.setOnAction(event->{
            TextInputDialog t=new TextInputDialog();
            t.setTitle("Renommer une activité");
            t.setHeaderText("Entrez le nouveau nom ");
            t.showAndWait();
            //récupère le nom entré
            this.monde.renommerActivite(t.getEditor().getText());
        });

        this.mondeMenu=new Menu("Monde");
        this.entree=new MenuItem("Entrée");
        this.entree.setOnAction(e->{this.monde.ajouterEntree();});
        this.sortie=new MenuItem("Sortie");
        this.sortie.setOnAction(e->{
            try{
                this.monde.ajouterSortie();
            } catch (PasSortieEntreeException exception){
                Alert a=new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.mondeMenu.getItems().addAll(this.entree,this.sortie);

        this.param=new Menu("Paramètres");
        this.d=new MenuItem("Délais");
        this.d.setOnAction(e->{
            try {
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Défin" +
                        "ir le délais");
                t.setHeaderText("Définir le délais");
                t.showAndWait();
                if(!t.getEditor().getText().equals("")){//.  non vide
                    this.monde.setDelais(t.getEditor().getText());
                }
            } catch (TwiskException exc){
                Alert a=new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.e=new MenuItem("Ecart");
        this.e.setOnAction(e->{
            try {
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Définir un écart");
                t.setHeaderText("Définir un écart");
                t.showAndWait();
                if(!t.getEditor().getText().equals("")){//input non vide
                    this.monde.setEcart(t.getEditor().getText());
                }
            } catch (TwiskException exc){
                Alert a=new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.changerNbJetons=new MenuItem("Changer le nombre de Jetons");
        this.changerNbJetons.setOnAction(actionEvent -> {
            try {
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Définir le nombre de jetons souhaité");
                t.setHeaderText("Définir un le nombre de jetons");
                t.showAndWait();
                if(!t.getEditor().getText().equals("")){//input non vide
                    this.monde.setNbJetons(t.getEditor().getText());
                }
            } catch (TwiskException exc){
                Alert a=new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.nbClients=new MenuItem("Changer le nombre de client");
        this.nbClients.setOnAction(actionEvent -> {
            try{
                TextInputDialog t=new TextInputDialog();
                t.setTitle("Définir le nombre de jetons souhaité");
                t.setHeaderText("Définir un le nombre de jetons");
                t.showAndWait();

                int nbClients;
                try{//vérifier que le parse s'effectue bien
                    nbClients=Integer.parseInt(t.getEditor().getText());
                    if(nbClients<=0){
                        throw new InputException("Le nombre entré doit être strictement positif");
                    }
                } catch (NumberFormatException e){
                    throw new InputException("Le nombre entré n'est pas correct");
                }
                this.monde.setNbClients(nbClients);
            } catch (TwiskException e){
                Alert a=new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
                a.showAndWait();
            }
        });
        this.appliquerStyle();

        this.param.getItems().addAll(this.d,this.e, this.changerNbJetons, this.nbClients);

        this.edition.getItems().addAll(this.supprimerSelection,this.renomer,this.effacerSelection);

        this.getMenus().addAll(this.fichier,this.edition,this.mondeMenu,this.param);
    }

    /**
     * Applique le style au menu
     */
    private void appliquerStyle(){
        //style général du menu
        String backgroundColor="#CCCCCC";
        String font="Quicksand Bold";
        String hoover="";
        String fontSize="18";
        String fontColor="red";
        String style="-fx-background-color:"+backgroundColor+";" +
                "-fx-font-family: '"+font+"';" +
                "-fx-font-size: "+fontSize+";"+
                "-fx-text-fill : "+fontColor+";";

        //style des sous-menu
        String styleItem="-fx-text-fill : "+fontColor+";";
        this.mondeMenu.setStyle("-fx-text-inner-color: red");

        this.setStyle(style);
    }

    @Override
    public void reagir() {
        ArrayList<EtapeIG> al=this.monde.getEtapeSelectionnees();
        if(al.size()==0 && this.monde.getArcSelectionnees().size()==0){//s'il n'y a aucune activité sélectionnée && aucun arc
            this.supprimerSelection.setDisable(true);
            this.renomer.setDisable(true);//on affiche pas
            this.effacerSelection.setDisable(true);
            this.entree.setDisable(true);
            this.sortie.setDisable(true);
            this.d.setDisable(true);
            this.e.setDisable(true);
            this.changerNbJetons.setDisable(true);
        } else {
            this.supprimerSelection.setDisable(false);
            this.renomer.setDisable(false);//on affiche
            this.effacerSelection.setDisable(false);
            this.entree.setDisable(false);
            this.sortie.setDisable(false);
            this.d.setDisable(false);
            this.e.setDisable(false);
            this.changerNbJetons.setDisable(false);
            for(EtapeIG e:al){
                //on regarde si on peut traiter toutes les étapes dans la sélection
                if(e.estUnGuichet()){
                    //s'il y a un guichet on ne peut pas appliquer une sortie
                    this.sortie.setDisable(true);
                }
                if(e.estUneActivite()){
                    //s'il y a une activité, on ne peut pas modifier le nombre de jetons
                    this.changerNbJetons.setDisable(true);
                }
            }
        }
    }
}
