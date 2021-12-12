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
import com.mygdx.platventure.gestionnaires.GestionnaireCreationNiveau;
import com.mygdx.platventure.gestionnaires.GestionnaireSons;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Element> {

    private ArrayList<Element> elements;
    private World monde;

    private final GestionnaireSons gestionnaireSons = new GestionnaireSons();
    private GestionnaireCreationNiveau gestionnaireCreationNiveau;

    private EcouteurCollision ecouteurCollision;
    private JoueurP joueur;

    private Timer timer;
    private int temps;
    private int score = 0;
    private int hauteur;
    private int numeroNiveau;
    private int largeurNiveau;
    private int hauteurNiveau;

    private boolean gagne;
    private boolean perdu;
    private boolean entrainDePerdre;
    private boolean auDebut;

    public Monde() {
        // On commence au niveau 1, on peut éditer 1 pour commencer à un autre niveau
        creerMonde(1, 0);
    }

    private void creerMonde(int numeroNiveau, int score) {
        auDebut = true;
        entrainDePerdre = false;
        gagne = false;
        perdu = false;
        // On modifie le numéro du niveau actuel
        this.setNumeroNiveau(numeroNiveau);
        // On charge le score
        this.setScore(score);
        // On charge le niveau 1
        this.setNiveau(new GestionnaireCreationNiveau("levels/level_00" + numeroNiveau + ".txt"));
        // On crée un monde avec une gravité de 10unités/s²
        setMonde(new World(new Vector2(0, -10f), true));
        // On récupère les informations du niveau en cours
        this.setTemps(getTempsNiveau());
        setLargeurNiveau(getNiveauLargeur());
        setHauteurNiveau(getNiveauHauteur());
        // On crée une liste d'éléments présent dans le monde
        this.setElements(new ArrayList<>());
        // On crée tous les éléments resepctivement au tableau extrait du .txt du niveau
        this.setHauteur(getGestionnaireCreationNiveau().getTableau()[0].length - 1);
        for (int i = 0; i < getGestionnaireCreationNiveau().getTableau().length; ++i) {
            for (int j = 0; j < getGestionnaireCreationNiveau().getTableau()[i].length; ++j) {
                creerElement(getGestionnaireCreationNiveau().getTableau()[i][j], i, j);
            }
        }
        // On lance le timer du niveau
        // On utilise un tableau pour modifier dans le timer
        final int[] tabTemps = {this.getTemps()};
        timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                tabTemps[0]--;
                if (tabTemps[0] <= 10) gestionnaireSons.sonAlerte();
                setTemps(tabTemps[0]);
            }
        };
        timer.scheduleTask(task, 1f, 1f);
        // On met en place les collisions
        this.setEcouteurCollision(new EcouteurCollision());
        this.getMonde().setContactListener(this.getEcouteurCollision());
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
                element = new Brique(new Vector2(i, getHauteur() - j), lettre);
                break;
            case 'J':
                element = new PlateformeJ(new Vector2(i, getHauteur() - j));
                break;
            case 'K':
                element = new PlateformeK(new Vector2(i, getHauteur() - j));
                break;
            case 'L':
                element = new PlateformeL(new Vector2(i, getHauteur() - j));
                break;
            case 'P':
                element = new JoueurP(new Vector2(i, getHauteur() - j));
                this.setJoueur((JoueurP) element);
                break;
            case 'W':
                element = new EauW(new Vector2(i, getHauteur() - j));
                break;
            case 'Z':
                element = new SortieZ(new Vector2(i, getHauteur() - j));
                break;
            case '1':
                element = new Gemme1(new Vector2(i, getHauteur() - j));
                break;
            case '2':
                element = new Gemme2(new Vector2(i, getHauteur() - j));
                break;
            default:
                //On nefait rien, V est inclus.
                break;
        }
        if (element != null) {
            // On place les éléments
            element.setBodyDef();
            element.createBody(getMonde());
            element.setFixture();
            this.getElements().add(element);
        }
    }

    public void update() {
        if (!gagne && !perdu) {
            // On met à jour la position des éléments dans le monde
            for (Element e : this) {
                e.setPosition(e.getBody().getPosition());
            }
            // Si le temps arrive à 0, la partie est perdue
            if (getTemps() == 0) {
                finDePartiePerdue();
            }
            // En cas de contact avec une gemme, on la détruit et on augmente le score
            if (this.getEcouteurCollision().getGemmeEnContact() != null) {
                this.getGestionnaireSons().sonGemme();
                Element gemme = recupererElement(this.getEcouteurCollision().getGemmeEnContact());
                supprimerElement(gemme);
                this.setScore(this.getScore() + ((Gemme) gemme).getPoints());
                this.getMonde().destroyBody(this.getEcouteurCollision().getGemmeEnContact());
                this.getEcouteurCollision().setGemmeEnContact(null);
            }
            // En cas de sortie de l'écran
            if (!entrainDePerdre) {
                if (this.getJoueur().getPosition().x > getLargeurNiveau() || this.getJoueur().getPosition().x < -1 || this.getJoueur().getPosition().y > getHauteurNiveau() || this.getJoueur().getPosition().y < -1) {
                    if (this.getEcouteurCollision().isPancarteDejaEnContact()) {
                        getEcouteurCollision().setPancarteDejaEnContact(false);
                        finDePartieGagne();
                    } else {
                        this.getGestionnaireSons().sonPerdu();
                        getEcouteurCollision().setPancarteDejaEnContact(false);
                        finDePartiePerdue();
                    }
                }
                // En cas de contact avec de l'eau on lance la fin de partie
                if (this.getEcouteurCollision().isEauEnContact()) {
                    this.getGestionnaireSons().sonEau();
                    this.finDePartiePerdue();
                }
            }

            // En cas de contact brutal avec une brique
            if (this.getEcouteurCollision().isContactSonorelateforme()) {
                this.getGestionnaireSons().sonCollision();
            }
        }
    }

    public void finDePartiePerdue() {
        // Lorsque la partie est perdue, le score retourne à 0 le timer se reset et la partie redémarre après 2 secondes
        perdu = true;
        setScore(0);
        entrainDePerdre = true;
        this.timer.stop();
        this.timer.clear();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                getMonde().dispose();
                creerMonde(getNumeroNiveau(), getScore());
            }
        }, 2);

    }

    public void finDePartieGagne() {
        // Lorsque la partie est gagnée, le score est conservé le timer se reset et la partie redémarre après 2 secondes
        gagne = true;
        this.getGestionnaireSons().sonGagne();
        this.timer.stop();
        this.timer.clear();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                getMonde().dispose();
                numeroNiveau++;
                creerMonde(getNumeroNiveau() > 3 ? 1 : getNumeroNiveau(), getScore());
            }
        }, 2);
    }

    @Override
    @NotNull
    public Iterator<Element> iterator() {
        // On s'en sert pour parcourir les diférentes éléments avec un foreach
        return this.getElements().iterator();
    }

    public int getTempsNiveau() {
        return getGestionnaireCreationNiveau().getTemps();
    }

    public void setNiveau(GestionnaireCreationNiveau gestionnaireCreationNiveau) {
        this.setGestionnaireCreationNiveau(gestionnaireCreationNiveau);
    }

    public int getNiveauLargeur() {
        return getGestionnaireCreationNiveau().getLargeur();
    }

    public int getNiveauHauteur() {
        return getGestionnaireCreationNiveau().getHauteur();
    }

    public World getWorld() {
        return getMonde();
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
        getElements().remove(e);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public World getMonde() {
        return monde;
    }

    public void setMonde(World monde) {
        this.monde = monde;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public EcouteurCollision getEcouteurCollision() {
        return ecouteurCollision;
    }

    public void setEcouteurCollision(EcouteurCollision ecouteurCollision) {
        this.ecouteurCollision = ecouteurCollision;
    }

    public void setJoueur(JoueurP joueur) {
        this.joueur = joueur;
    }

    public int getTemps() {
        return temps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GestionnaireCreationNiveau getGestionnaireCreationNiveau() {
        return gestionnaireCreationNiveau;
    }

    public void setGestionnaireCreationNiveau(GestionnaireCreationNiveau gestionnaireCreationNiveau) {
        this.gestionnaireCreationNiveau = gestionnaireCreationNiveau;
    }

    public int getNumeroNiveau() {
        return numeroNiveau;
    }

    public void setNumeroNiveau(int numeroNiveau) {
        this.numeroNiveau = numeroNiveau;
    }

    public int getLargeurNiveau() {
        return largeurNiveau;
    }

    public void setLargeurNiveau(int largeurNiveau) {
        this.largeurNiveau = largeurNiveau;
    }

    public int getHauteurNiveau() {
        return hauteurNiveau;
    }

    public void setHauteurNiveau(int hauteurNiveau) {
        this.hauteurNiveau = hauteurNiveau;
    }

    public GestionnaireSons getGestionnaireSons() {
        return gestionnaireSons;
    }

    public boolean isGagne() {
        return gagne;
    }

    public boolean isPerdu() {
        return perdu;
    }

    public boolean isAuDebut() {
        return auDebut;
    }

    public void setAuDebut(boolean auDebut) {
        this.auDebut = auDebut;
    }
}

