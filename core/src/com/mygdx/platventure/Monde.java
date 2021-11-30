package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.platventure.ecouteurs.EcouteurCollision;
import com.mygdx.platventure.elements.Brique;
import com.mygdx.platventure.elements.EauW;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.JoueurP;
import com.mygdx.platventure.elements.SortieZ;
import com.mygdx.platventure.elements.gemmes.Gemme;
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
    private int temps;
    private int score = 0;
    private final EcouteurCollision ecouteurCollision;

    public Monde(char[][] tab, int temps) {
        // On crée un monde avec une gravité de 10unités/s²
        monde = new World(new Vector2(0, -10f), true);
        this.temps = temps;
        // On crée une liste d'éléments présent dans le monde
        this.elements = new ArrayList<>();
        // On crée tous les éléments resepctivement au tableau extrait du .txt du niveau
        this.hauteur = tab[0].length - 1;
        for (int i = 0; i < tab.length; ++i) {
            for (int j = 0; j < tab[i].length; ++j) {
                creerElement(tab[i][j], i, j);
            }
        }
        // On lance le timer du niveau
        // On utilise untableau pour modifier dans le timer
        final int[] tabTemps = {this.temps};
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                tabTemps[0]--;
                setTemps(tabTemps[0]);
            }
        };
        timer.scheduleTask(task, 1f, 1f);
        // On met en place les collisions
        this.ecouteurCollision = new EcouteurCollision();
        this.monde.setContactListener(this.ecouteurCollision);
    }

    private void creerElement(char lettre, int i, int j) {
        Element element = null;
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
                break;
            case 'J':
                element = new PlateformeJ(new Vector2(i, hauteur - j));
                break;
            case 'K':
                element = new PlateformeK(new Vector2(i, hauteur - j));
                break;
            case 'L':
                element = new PlateformeL(new Vector2(i, hauteur - j));
                break;
            case 'P':
                element = new JoueurP(new Vector2(i, hauteur - j));
                this.joueur = (JoueurP) element;
                break;
            case 'W':
                element = new EauW(new Vector2(i, hauteur - j));
                break;
            case 'Z':
                element = new SortieZ(new Vector2(i, hauteur - j));
                break;
            case '1':
                element = new Gemme1(new Vector2(i, hauteur - j));
                break;
            case '2':
                element = new Gemme2(new Vector2(i, hauteur - j));
                break;
            default:
                //On nefait rien, V est inclus.
                break;
        }
        if (element != null) {
            // On place les éléments
            element.setBodyDef();
            element.createBody(monde);
            element.setFixture();
            this.elements.add(element);
        }
    }

    public void update() {
        // On met à jour la position des éléments dans le monde
        for (Element e : this) {
            e.setPosition(e.getBody().getPosition());
        }
        // En cas de contact avec une gemme, on la détruit et on augmente le score
        if (this.ecouteurCollision.getGemmeEnContact() != null) {
            Element gemme = recupererElement(this.ecouteurCollision.getGemmeEnContact());
            removeElement(gemme);
            this.score += ((Gemme) gemme).getPoints();
            this.monde.destroyBody(this.ecouteurCollision.getGemmeEnContact());
            this.ecouteurCollision.setGemmeEnContact(null);
        }
        System.out.println(score);
        System.out.println(getTemps());
    }

    @Override
    @NotNull
    public Iterator<Element> iterator() {
        // On s'en sert pour parcourir les diférentes éléments avec un foreach
        return this.elements.iterator();
    }

    public World getWorld() {
        return monde;
    }

    public JoueurP getJoueur() {
        return joueur;
    }

    public int getTemps() {
        return temps;
    }

    public Element recupererElement(Body b) {
        for (Element e : this) {
            if (b == e.getBody()) {
                return e;
            }
        }
        return null;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public void removeElement(Element e) {
        elements.remove(e);
    }
}

