package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.platventure.ecrans.EcranPresentation;

public class PlatVenture extends Game {
    private SpriteBatch listeAff;
    private Niveau niveau;
    private Monde monde;


    @Override
    public void create() {
        listeAff = new SpriteBatch();
        setScreen(new EcranPresentation(this));
    }

    @Override
    public void dispose() {
        listeAff.dispose();
    }

    public SpriteBatch getListeAff() {
        return listeAff;
    }

    public Monde getMonde() {
        return monde;
    }

    public void setMonde(Monde monde) {
        this.monde = monde;
    }

    public char[][] getNiveau() {
        return niveau.getTableau();
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
}
