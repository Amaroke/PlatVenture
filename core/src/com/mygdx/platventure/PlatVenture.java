package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.platventure.ecrans.EcranPresentation;

public class PlatVenture extends Game {
    private SpriteBatch listeAff;
    private Monde monde;


    @Override
    public void create() {
        listeAff = new SpriteBatch();
        // On lance l'écran de présentation
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
}
