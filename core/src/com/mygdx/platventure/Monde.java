package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.elements.Brique;
import com.mygdx.platventure.elements.EauW;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.JoueurP;
import com.mygdx.platventure.elements.SortieZ;
import com.mygdx.platventure.elements.gemmes.Gemme1;
import com.mygdx.platventure.elements.gemmes.Gemme2;
import com.mygdx.platventure.elements.plateformes.PlateformeJ;
import com.mygdx.platventure.elements.plateformes.PlateformeK;
import com.mygdx.platventure.elements.plateformes.PlateformeL;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Element> {

    private final ArrayList<Element> elements;
    private JoueurP joueur;
    private final World monde;
    private final int hauteur;

    public Monde(char[][] tab) {
        monde = new World(new Vector2(0, -10f), true);
        this.elements = new ArrayList<>();
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
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
                element = new Brique(new Vector2(i, hauteur - j));
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
                element = new JoueurP(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.joueur = (JoueurP) element;
                this.elements.add(joueur);
                break;
            case 'W':
                element = new EauW(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'Z':
                element = new SortieZ(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case '1':
                element = new Gemme1(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case '2':
                element = new Gemme2(new Vector2(i, hauteur - j));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            default:
                //On nefait rien, V est inclus.
                break;
        }
    }

    public void update() {
        for (Element e : this) {
            e.setPosition(e.getBody().getPosition());
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

    public JoueurP getJoueur() {
        return joueur;
    }
}

