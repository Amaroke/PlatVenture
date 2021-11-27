package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.briques.BriqueA;

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
        this.hauteur = tab.length;
        this.largeur = tab[0].length;
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
                element = new BriqueA(new Vector2(j, hauteur - (i + 1)));
                element.setBodyDef();
                element.createBody(monde);
                element.setFixture();
                this.elements.add(element);
                break;
            case 'B':
                break;
            case 'C':
                break;
            case 'D':
                break;
            case 'E':
                break;
            case 'F':
                break;
            case 'G':
                break;
            case 'H':
                break;
            case 'I':
                break;
            case 'J':
                break;
            case 'K':
                break;
            case 'L':
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

