package twisk.vues;

import javafx.scene.control.Alert;
import twisk.mondeIG.MondeIG;
import twisk.outils.Observateur;

public class VueAlert extends Alert implements Observateur {
    MondeIG monde;
    String message;

    public VueAlert(MondeIG monde, String message){
        super(AlertType.ERROR);
        this.monde=monde;
        this.message=message;
        this.setTitle("Attention");
        this.setContentText(this.message);
    }

    @Override
    public void reagir() {

    }
}
