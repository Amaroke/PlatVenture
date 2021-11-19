package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class EcranPresentation extends ScreenAdapter {
    final PlatVenture game;

    public EcranPresentation(PlatVenture game) {
        this.game = game;
        this.game.setFont(new Texture("images/Intro.png"));
    }

    @Override
    public void show(){
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                game.setScreen(new EcranJeu(game));
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.listeAff.begin();
        game.listeAff.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
