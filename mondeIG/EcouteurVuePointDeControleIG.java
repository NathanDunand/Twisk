package twisk.mondeIG;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import twisk.exceptions.TwiskException;
import twisk.vues.VueAlert;
import twisk.vues.VuePointDeControleIG;

public class EcouteurVuePointDeControleIG implements EventHandler<MouseEvent> {
    private MondeIG monde;
    private PointDeControleIG pt;

    public EcouteurVuePointDeControleIG(MondeIG monde, PointDeControleIG pt){
        this.monde=monde;
        this.pt=pt;
    }

    @Override
    public void handle(MouseEvent mouseEvent){
        try{
            this.monde.ajouterArc(this.pt);
        } catch (TwiskException e){
            VuePointDeControleIG v=new VuePointDeControleIG(this.monde,this.pt);
            VueAlert va=new VueAlert(this.monde, e.getMessage());
            this.monde.ajouterObservateur(va);
        }
    }
}
