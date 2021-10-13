package twisk.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.TailleComposants;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;

public class MainIG extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MondeIG monde=new MondeIG();
        BorderPane root= new BorderPane();
        root.setTop(new VueMenu(monde));
        root.setCenter(new VueMondeIG(monde));
        root.setBottom(new VueOutils(monde));

        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
