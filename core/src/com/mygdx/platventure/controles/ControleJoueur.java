package com.mygdx.platventure.controles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class ControleJoueur implements InputProcessor {

    static final int hauteurSaut = 45;
    private final Vector2 deplacement = new Vector2(0f, 0f);
    private boolean debugActif = true;
    private long appui = 0;

    @Override
    public boolean keyDown(int keycode) {
        // En cas d'appui sur la flèche du haut ou de Z on saute
        if (keycode == Input.Keys.UP || keycode == Input.Keys.Z) {
            deplacement.y = hauteurSaut;
            return true;
        }
        // En cas d'appui sur la flèche de gauche ou de Q on va à gauche
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.Q) {
            deplacement.x = -1f;
            return true;
        }
        // En cas d'appui sur la flèche de droite ou de D on va à droite
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
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
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT || keycode == Input.Keys.Q || keycode == Input.Keys.D) {
            deplacement.x = 0f;
            return true;
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.Z) {
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
        // Lors d'un appui sur l'écran tactile, on enregistre le moment de l'appui
        appui = System.currentTimeMillis();
        // Si on appuie dans la partie droite, on va vers la droite, l'inverse à gauche
        if (screenX > Gdx.graphics.getWidth() / 2) {
            this.deplacement.x = 1;
        } else if (screenX < Gdx.graphics.getWidth() / 2) {
            this.deplacement.x = -1;
        }
        // En cas de déplacement horizontal on stop les déplacements verticaux
        deplacement.y = 0;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // En cas de deux appuis successif, on fait un saut
        if ((System.currentTimeMillis() - appui) < 100) {
            this.deplacement.y = hauteurSaut;
        }
        appui = 0;
        this.deplacement.x = 0;
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
