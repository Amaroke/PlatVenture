package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class EcouteurJoueur implements InputProcessor {

    private static final int hauteurSaut = 40;
    private final Vector2 deplacement = new Vector2(0f, 0f);
    private boolean debugActif = false;

    @Override
    public boolean keyDown(int keycode) {
        // Lors d'un appui sur Echap, le jeu se ferme
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            return true;
        }
        // En cas d'appui sur la flèche du haut on saute
        if (keycode == Input.Keys.UP) {
            deplacement.y = hauteurSaut;
            return true;
        }
        // En cas d'appui sur la flèche de gauche on va à gauche
        if (keycode == Input.Keys.LEFT) {
            deplacement.x = -1f;
            return true;
        }
        // En cas d'appui sur la flèche de droite on va à droite
        if (keycode == Input.Keys.RIGHT) {
            deplacement.x = 1f;
            return true;
        }
        // L'appui sur la touche Shift de Gauche active ou désactive le debugRender
        if (keycode == Input.Keys.SHIFT_LEFT) {
            debugActif = !debugActif;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Lorsque l'on relâche les touches, on retire le déplacement
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT) {
            deplacement.x = 0f;
            return true;
        }
        // De même pour le saut
        if (keycode == Input.Keys.UP) {
            deplacement.y = 0f;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Si on appuie dans la partie droite, on va vers la droite, l'inverse à gauche
        if (pointer == 0) {
            if (screenX > Gdx.graphics.getWidth() / 2) {
                this.deplacement.x = 1;
                return true;
            } else if (screenX < Gdx.graphics.getWidth() / 2) {
                this.deplacement.x = -1;
                return true;
            }
        }
        // Si on appui une deuxième fois sur l'écran, on saute
        if (pointer == 1) {
            this.deplacement.y = hauteurSaut;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Si on relache le premier appui on stop le mouvement latéral
        if (pointer == 0) {
            this.deplacement.x = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Vector2 getDeplacement() {
        // On crée un nouveau vecteur pour reset le déplacement vertical, pour éviter les sauts en boucle
        Vector2 res = new Vector2(deplacement);
        deplacement.y = 0;
        return res;
    }

    public boolean isDebugActif() {
        return debugActif;
    }
}
