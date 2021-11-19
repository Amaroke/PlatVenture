package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

public class EcranJeu extends ScreenAdapter {

    final PlatVenture game;

    public EcranJeu(PlatVenture game) {
        this.game = game;
        this.game.setFont(new Texture("images/Back.png"));
        this.game.setCamera(new OrthographicCamera(16f, (16f * Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()));
        this.game.setNiveau(new Niveau("levels/level_001.txt"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.listeAff.begin();
        game.listeAff.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
