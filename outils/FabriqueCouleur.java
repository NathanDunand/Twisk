package twisk.outils;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class FabriqueCouleur {

    private ArrayList<Color> couleurs;
    private static FabriqueCouleur instance = new FabriqueCouleur();

    private FabriqueCouleur(){
        couleurs = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.AQUA, Color.DARKMAGENTA, Color.SIENNA, Color.AQUAMARINE, Color.BROWN, Color.CORNSILK, Color.DARKVIOLET, Color.CYAN, Color.LIGHTGREY));
    }

    public static FabriqueCouleur getInstance(){
        return instance;
    }

    public Color getCouleur(){
        Collections.shuffle(couleurs);
        return couleurs.get(0);
    }

}
