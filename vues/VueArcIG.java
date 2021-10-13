package twisk.vues;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.outils.Observateur;

public class VueArcIG extends Pane implements Observateur {
    private MondeIG monde;
    private ArcIG arc;
    private boolean select;
    private Line l;
    private Polyline p;

    public VueArcIG(MondeIG monde, ArcIG arc){
        super();
        this.monde=monde;
        this.arc=arc;
        int debutX=this.arc.getP1().getPosCentreX();
        int debutY=this.arc.getP1().getPosCentreY();
        double finX=this.arc.getP2().getPosCentreX();
        double finY=this.arc.getP2().getPosCentreY();
        this.l=new Line(debutX, debutY, finX, finY);

        this.select=false;//défaut : rien de sélectionné

        //triangle
        double angle=Math.atan2((finY-debutY),(finX-debutX))-Math.PI/2.0;
        double sin=Math.sin(angle);
        double cos=Math.cos(angle);

        double ptX1=(-1.0/2.0*cos+Math.sqrt(3)/2*sin)*TailleComposants.getInstance().getFleche()+finX;
        double ptY1=(-1.0/2.0*sin-Math.sqrt(3)/2*cos)*TailleComposants.getInstance().getFleche()+finY;

        double ptX2 =(1.0/2.0*cos+Math.sqrt(3)/2*sin)*TailleComposants.getInstance().getFleche()+finX;
        double ptY2=(1.0/2.0*sin-Math.sqrt(3)/2*cos)*TailleComposants.getInstance().getFleche()+finY;
        this.setPickOnBounds(false);//gestion de la hitbox de l'élément

        this.p=new Polyline();
        this.p.getPoints().addAll(ptX1,ptY1, ptX2, ptY2, finX, finY);//pt1 pt2 pt3

        this.appliquerStyleDefaut();//applique le style à la flèche toute entière

        //ajout de l'écouteur qui permet de sélectionner un Arc
        this.l.setOnMouseClicked(event->selectionnerArc(event));
        this.p.setOnMouseClicked(event->selectionnerArc(event));

        this.getChildren().addAll(this.l, this.p);
    }

    public void selectionnerArc(MouseEvent event){
        this.select=!this.select;//inverse
        if(this.select){//set style si une act est sélectionnée
            this.appliquerStyleSelectionne();
            this.arc.selected();
            this.monde.ajouterArcSelection(this.arc);
        } else {
            this.appliquerStyleDefaut();
            this.arc.unselected();
            this.monde.retirerArcSelection(this.arc);
        }
        event.consume();
    }

    /**
     * Applique le style graphique d'un arc
     */
    public void appliquerStyleDefaut(){
        //ligne
        this.l.setStrokeWidth(TailleComposants.getInstance().getLigne());
        String color="#43a047";
        String styleL="-fx-stroke:"+color+";-fx-stroke: "+color+";";

        //flèche
        String colorP="#7cb342";
        String styleP="-fx-fill:"+colorP+";-fx-stroke: "+colorP+";";

        this.l.setStyle(styleL);
        this.p.setStyle(styleP);
    }

    /**
     * Applique le style graphique d'un arc
     */
    private void appliquerStyleSelectionne(){
        //ligne
        this.l.setStrokeWidth(TailleComposants.getInstance().getLigne());
        String color="#76d275";
        String styleL="-fx-stroke:"+color+";-fx-stroke: "+color+";";

        //flèche
        this.l.setStrokeWidth(TailleComposants.getInstance().getLigne()/1.5);
        String colorP="#aee571";
        String styleP="-fx-fill:"+colorP+";-fx-stroke: "+colorP+";";

        this.l.setStyle(styleL);
        this.p.setStyle(styleP);
    }

    @Override
    public void reagir() {

    }
}
