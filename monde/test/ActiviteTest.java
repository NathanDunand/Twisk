package twisk.monde.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest {
    Activite a;
    SasSortie s;

    @BeforeEach
    void setUp() {
        a = new Activite("a0");
        s = new SasSortie();
        a.ajouterSuccesseur(s);
    }

    @Test
    void toC(){
        assertEquals("delai(10,2);\ntransfert(0,1);\n",a.toC());
    }
}