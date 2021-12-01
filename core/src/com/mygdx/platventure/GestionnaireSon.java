package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GestionnaireSon {

    private final Sound alert;
    private final Sound collision;
    private final Sound gem;
    private final Sound loose;
    private final Sound plouf;
    private final Sound win;

    public GestionnaireSon() {
        this.alert = Gdx.audio.newSound(Gdx.files.internal("sounds/alert.ogg"));
        this.collision = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.ogg"));
        this.gem = Gdx.audio.newSound(Gdx.files.internal("sounds/gem.ogg"));
        this.loose = Gdx.audio.newSound(Gdx.files.internal("sounds/loose.ogg"));
        this.plouf = Gdx.audio.newSound(Gdx.files.internal("sounds/plouf.ogg"));
        this.win = Gdx.audio.newSound(Gdx.files.internal("sounds/win.ogg"));
    }

    public void sonAlerte() {
        this.alert.play();
    }

    public void sonCollision() {
        this.collision.play();
    }

    public void sonGemme() {
        this.gem.play();
    }

    public void sonPerdu() {
        this.loose.play();
    }

    public void sonEau() {
        this.plouf.play();
    }

    public void sonGagne() {
        this.win.play();
    }
}
