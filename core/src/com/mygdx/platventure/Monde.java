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

    private ArrayList<Element> elements;
    private World monde;
    private int hauteur;
    private EcouteurCollision ecouteurCollision;
    private JoueurP joueur;
    private Timer timer;
    private int temps;
    private int score = 0;
    private Niveau niveau;
    private int numeroNiveau;
    private int largeurNiveau;
    private int hauteurNiveau;
    private final GestionnaireSon gestionnaireSon = new GestionnaireSon();

    public Monde() {
        creerMonde(1);
    }

    private void creerMonde(int numeroNiveau) {
        // On modifie le numéro du niveau actuel
        this.numeroNiveau = numeroNiveau;
        // On charge le niveau 1
        this.setNiveau(new Niveau("levels/level_00" + numeroNiveau + ".txt"));
        // On crée un monde avec une gravité de 10unités/s²
        monde = new World(new Vector2(0, -10f), true);
        // On récupère les informations du niveau en cours
        this.temps = getTempsNiveau();
        largeurNiveau = getNiveauLargeur();
        hauteurNiveau = getNiveauHauteur();
        // On crée une liste d'éléments présent dans le monde
        this.elements = new ArrayList<>();
        // On crée tous les éléments resepctivement au tableau extrait du .txt du niveau
        this.hauteur = niveau.getTableau()[0].length - 1;
        for (int i = 0; i < niveau.getTableau().length; ++i) {
            for (int j = 0; j < niveau.getTableau()[i].length; ++j) {
                creerElement(niveau.getTableau()[i][j], i, j);
            }
        }
        // On lance le timer du niveau
        // On utilise untableau pour modifier dans le timer
        final int[] tabTemps = {this.temps};
        timer = new Timer();
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
        // Si le temps arrive à 0, la partie est perdue
        if (temps == 0) {
            finDePartiePerdue();
        }
        // En cas de contact avec une gemme, on la détruit et on augmente le score
        if (this.ecouteurCollision.getGemmeEnContact() != null) {
            this.gestionnaireSon.sonGemme();
            Element gemme = recupererElement(this.ecouteurCollision.getGemmeEnContact());
            supprimerElement(gemme);
            this.score += ((Gemme) gemme).getPoints();
            this.monde.destroyBody(this.ecouteurCollision.getGemmeEnContact());
            this.ecouteurCollision.setGemmeEnContact(null);
        }
        // En cas de contact avec de l'eau on lance la fin de partie
        if (this.ecouteurCollision.isEauEnContact()) {
            this.gestionnaireSon.sonEau();
            this.finDePartiePerdue();
        }
        // En cas de sortie de l'écran
        if (this.joueur.getPosition().x > largeurNiveau || this.joueur.getPosition().x < -1 || this.joueur.getPosition().y > hauteurNiveau || this.joueur.getPosition().y < -1) {
            if (this.ecouteurCollision.isPancarteDejaEnContact()) {
                finDePartieGagne();
            } else {
                finDePartiePerdue();
            }
        }
    }

    public void finDePartiePerdue() {
        score = 0;
        // TODO Affichage de l'écran de fin de partie
        this.gestionnaireSon.sonPerdu();
        try {
            this.timer.clear();
            this.monde.dispose();
            Thread.sleep(2000);
            creerMonde(numeroNiveau);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finDePartieGagne() {
        score = 0;
        // TODO Affichage de l'écran de fin de partie
        this.gestionnaireSon.sonGagne();
        try {
            this.timer.clear();
            this.monde.dispose();
            Thread.sleep(2000);
            // On passe au niveau suivant
            numeroNiveau++;
            creerMonde(numeroNiveau > 3 ? 1 : numeroNiveau);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @NotNull
    public Iterator<Element> iterator() {
        // On s'en sert pour parcourir les diférentes éléments avec un foreach
        return this.elements.iterator();
    }

    public int getTempsNiveau() {
        return niveau.getTemps();
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public int getNiveauLargeur() {
        return niveau.getLargeur();
    }

    public int getNiveauHauteur() {
        return niveau.getHauteur();
    }

    public World getWorld() {
        return monde;
    }

    public JoueurP getJoueur() {
        return joueur;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public Element recupererElement(Body b) {
        for (Element e : this) {
            if (b == e.getBody()) {
                return e;
            }
        }
        return null;
    }

    public void supprimerElement(Element e) {
        elements.remove(e);
    }
}

