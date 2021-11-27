package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.briques.BriqueA;
import com.mygdx.platventure.elements.briques.BriqueB;
import com.mygdx.platventure.elements.briques.BriqueC;
import com.mygdx.platventure.elements.briques.BriqueD;
import com.mygdx.platventure.elements.briques.BriqueE;
import com.mygdx.platventure.elements.briques.BriqueF;
import com.mygdx.platventure.elements.briques.BriqueG;
import com.mygdx.platventure.elements.briques.BriqueH;
import com.mygdx.platventure.elements.briques.BriqueI;
import com.mygdx.platventure.elements.plateformes.PlateformeJ;
import com.mygdx.platventure.elements.plateformes.PlateformeK;
import com.mygdx.platventure.elements.plateformes.PlateformeL;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Element> {

    private final ArrayList<Element> elements;
    private final World monde;
    private final int hauteur;
    private final int largeur;

    public Monde(char[][] tab) {
        monde = new World(new Vector2(0, -10f), true);
        this.elements = new ArrayList<>();
        this.largeur = tab.length;
        this.hauteur = tab[0].length - 1;
        for (int i = 0; i < tab.length; ++i) {
            for (int j = 0; j < tab[i].length; ++j) {
                creerElement(tab[i][j], i, j);
            }
        }
    }

    private void creerElement(char lettre, int i, int j) {
        Element element;
        switch (lettre) {
            case 'A':
                element = new BriqueA(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'B':
                element = new BriqueB(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'C':
                element = new BriqueC(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'D':
                element = new BriqueD(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'E':
                element = new BriqueE(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'F':
                element = new BriqueF(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'G':
                element = new BriqueG(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'H':
                element = new BriqueH(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'I':
                element = new BriqueI(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'J':
                element = new PlateformeJ(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'K':
                element = new PlateformeK(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'L':
                element = new PlateformeL(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'P':
                break;
            case 'V':
                break;
            case 'W':
                break;
            case 'Z':
                break;
            case '1':
                break;
            case '2':
                break;
        }
    }

    @Override
    @NotNull
    public Iterator<Element> iterator() {
        return this.elements.iterator();
    }

    public World getMonde() {
        return monde;
    }
}

